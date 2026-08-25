package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.listener.MobDamageListener;
import org.coolplugins.cool_HealthBar.listener.MobDeathListener;
import org.coolplugins.cool_HealthBar.listener.PlayerConnectionListener;

/*
Plugin that adds a simple health bar
* */
public final class Coolhealthbar extends JavaPlugin {

    private HealthManager healthManager;
    private  HealthManagerGUI healthManagerGUI;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.healthManager = new HealthManager(getLogger());
        this.healthManagerGUI = new HealthManagerGUI();
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerConnectionListener(this, this.healthManagerGUI), this);
        pm.registerEvents(new MobDamageListener(this.healthManagerGUI), this);
        pm.registerEvents(new MobDeathListener(this.healthManagerGUI), this);

        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
