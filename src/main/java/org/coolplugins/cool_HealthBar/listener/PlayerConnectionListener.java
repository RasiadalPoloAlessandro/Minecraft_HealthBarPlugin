package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.gui.HealthManagerGUI;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;
import org.coolplugins.cool_HealthBar.task.PlayerRayCastTask;

public class PlayerConnectionListener implements Listener {

    private final Plugin plugin;
    private final HealthManagerGUI healthManagerGUI;
    private final HealthBarRegistry registry;

    public PlayerConnectionListener(Plugin plugin, HealthManagerGUI healthManagerGUi, HealthBarRegistry registry) {
        this.plugin = plugin;
        this.healthManagerGUI = healthManagerGUi;
        this.registry = registry;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                new PlayerRayCastTask(player.getUniqueId(), healthManagerGUI, registry),
                0L, //Starts immediately
                2L // Repeat every 2 ticks
        );
    }
}
