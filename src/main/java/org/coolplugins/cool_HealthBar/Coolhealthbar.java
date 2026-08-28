package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.healthformatter.FriendlyMobFormatter;
import org.coolplugins.cool_HealthBar.healthformatter.HostileMobFormatter;
import org.coolplugins.cool_HealthBar.listener.MobDamageListener;
import org.coolplugins.cool_HealthBar.listener.MobDeathListener;
import org.coolplugins.cool_HealthBar.listener.PlayerConnectionListener;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;

/*
Plugin that adds a simple health bar
* */
public final class Coolhealthbar extends JavaPlugin {

    private HealthManager healthManager;
    private  HealthManagerGUI healthManagerGUI;
    private HealthBarRegistry registry;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.healthManager = new HealthManager(getLogger());
        this.healthManagerGUI = new HealthManagerGUI();
        this.registry = new HealthBarRegistry();

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
    }

    private void setUpFormatters() {
        this.registry = new HealthBarRegistry();
        this.registry.register(new HostileMobFormatter());
        this.registry.register(new FriendlyMobFormatter());
    }
}
