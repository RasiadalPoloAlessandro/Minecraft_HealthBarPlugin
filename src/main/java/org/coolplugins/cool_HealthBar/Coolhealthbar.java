package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.java.JavaPlugin;

public final class Coolhealthbar extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
