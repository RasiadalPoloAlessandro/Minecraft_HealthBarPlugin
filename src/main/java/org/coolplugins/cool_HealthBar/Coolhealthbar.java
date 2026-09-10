package org.coolplugins.cool_HealthBar;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.command.HideHealthBarCommand;
import org.coolplugins.cool_HealthBar.command.ShowHealthBarCommand;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;
import org.coolplugins.cool_HealthBar.gui.healthformatter.DefaultFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.FriendlyMobFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.HostileMobFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.PlayerFormatter;
import org.coolplugins.cool_HealthBar.listener.*;
import org.coolplugins.cool_HealthBar.model.HealthBarModel;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;
import org.coolplugins.cool_HealthBar.service.HealthBarService;

import java.util.List;

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
        this.registry = new HealthBarRegistry(new DefaultFormatter(this.mobNamePDC));
        this.registry.register(new PlayerFormatter(this.mobNamePDC));
        this.registry.register(new HostileMobFormatter(this.mobNamePDC));
        this.registry.register(new FriendlyMobFormatter(this.mobNamePDC));
    }

    private void setUpCommands() {
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            commands.register(
                    Commands.literal("healthbar")
                            .requires(source -> source.getSender().hasPermission("coolhealthbar.admin"))
                            .then(ShowHealthBarCommand.create(service))
                            .then(HideHealthBarCommand.create(service))
                            .build(),
                    "Gestione della visibilità della barra della vita",
                    List.of("hb", "chb")
            );
        });
    }
}
