package org.coolplugins.cool_HealthBar;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.command.HideHealthBarCommand;
import org.coolplugins.cool_HealthBar.command.ShowHealthBarCommand;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;
import org.coolplugins.cool_HealthBar.gui.healthformatter.EntityFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.PlayerFormatter;
import org.coolplugins.cool_HealthBar.listener.*;
import org.coolplugins.cool_HealthBar.model.HealthBarModel;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;
import org.coolplugins.cool_HealthBar.service.HealthBarService;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/*
Plugin that adds a simple health bar
* */
public final class Coolhealthbar extends JavaPlugin {

    private HealthBarModel healthManager;
    private HealthBarView healthBarView;
    private HealthBarRegistry registry;
    private MobNamePDC mobNamePDC;
    private HealthBarFilterManager filterManager;
    private HealthBarService service;
    private HealthBarController controller;
    private TranslationStore.StringBased<MessageFormat> translationStore;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.healthManager = new HealthManager(getLogger());
        this.mobNamePDC = new MobNamePDC(this);
        this.healthManager = new HealthBarModel();
        this.healthBarView = new HealthBarView();
        this.controller = new HealthBarController(healthManager, healthBarView);
        this.filterManager = new HealthBarFilterManager();
        this.service = new HealthBarService(filterManager, controller);
        setUpTranslations();
        setUpFormatters();
        setUpListeners();

        setUpCommands();

        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Remove all health bars
        controller.clearAll();
    }

    private void setUpListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerConnectionListener(this, this.controller, registry, filterManager), this);
        pm.registerEvents(new MobDamageListener(this.controller), this);
        pm.registerEvents(new MobDeathListener(this.controller), this);
        pm.registerEvents(new WorldEventListener(this.controller), this);
        pm.registerEvents(new MobNameListener(this.mobNamePDC, this.controller), this);
    }

    private void setUpFormatters() {
        this.registry = new HealthBarRegistry(new EntityFormatter(this.mobNamePDC));
        this.registry.register(new PlayerFormatter(this.mobNamePDC));
        this.registry = new HealthBarRegistry(new EntityFormatter(this.mobNamePDC));
    }

    private void setUpCommands() {
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            commands.register(
                    Commands.literal("healthbar")
                            .then(ShowHealthBarCommand.create(service))
                            .then(HideHealthBarCommand.create(service))
                            .build(),
                    "Gestione della visibilità della barra della vita",
                    List.of("hb", "chb")
            );
        });
    }

    /**
     * Method that setUp all the custom translations related to the plugin
     */
    private void setUpTranslations() {
        this.translationStore = TranslationStore.messageFormat(Key.key("cool_health_bar", "translations"));
        ClassLoader loader = this.getClassLoader();

        // English version (Default choice)
        ResourceBundle bundleEn = ResourceBundle.getBundle("Bundle", Locale.US, loader);
        this.translationStore.registerAll(Locale.US, bundleEn, true);

        // Italian version
        ResourceBundle bundleIt = ResourceBundle.getBundle("Bundle", Locale.ITALY, loader);
        this.translationStore.registerAll(Locale.ITALY, bundleIt, true);

        // add the store to the globalTranslator
        GlobalTranslator.translator().addSource(this.translationStore);
    }
}
