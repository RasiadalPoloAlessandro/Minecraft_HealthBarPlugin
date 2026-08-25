package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.listener.PlayerConnectionListener;

public final class Coolhealthbar extends JavaPlugin {

    private HealthManager healthManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.healthManager = new HealthManager(getLogger());
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this, healthManager),
                this);
        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
