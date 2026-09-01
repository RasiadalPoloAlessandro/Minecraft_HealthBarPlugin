package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.gui.HealthManagerGUI;
import org.coolplugins.cool_HealthBar.gui.healthformatter.DefaultFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.FriendlyMobFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.HostileMobFormatter;
import org.coolplugins.cool_HealthBar.listener.*;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;

/*
Plugin that adds a simple health bar
* */
public final class Coolhealthbar extends JavaPlugin {

    private HealthManager healthManager;
    private HealthManagerGUI healthManagerGUI;
    private HealthBarRegistry registry;
    private MobNamePDC mobNamePDC;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.healthManager = new HealthManager(getLogger());
        this.mobNamePDC = new MobNamePDC(this);
        this.healthManagerGUI = new HealthManagerGUI(mobNamePDC);
        setUpFormatters();
        setUpListeners();

        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Remove all healthbars
        healthManagerGUI.clearAll();
    }

    private void setUpListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerConnectionListener(this, this.healthManagerGUI, registry), this);
        pm.registerEvents(new MobDamageListener(this.healthManagerGUI), this);
        pm.registerEvents(new MobDeathListener(this.healthManagerGUI), this);
        pm.registerEvents(new WorldEventListener(this.healthManagerGUI), this);
    }

    private void setUpFormatters() {
        this.registry = new HealthBarRegistry(new DefaultFormatter(this.mobNamePDC));
        this.registry.register(new HostileMobFormatter(this.mobNamePDC));
        this.registry.register(new FriendlyMobFormatter(this.mobNamePDC));
    }
}
