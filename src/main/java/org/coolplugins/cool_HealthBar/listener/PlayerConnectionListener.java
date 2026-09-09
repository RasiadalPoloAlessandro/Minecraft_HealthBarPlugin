package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.HealthBarFilterManager;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;
import org.coolplugins.cool_HealthBar.task.PlayerRayCastTask;

public class PlayerConnectionListener implements Listener {

    private final Plugin plugin;
    private final HealthBarController controller;
    private final HealthBarRegistry registry;
    private final HealthBarFilterManager filterManager;

    public PlayerConnectionListener(Plugin plugin, HealthBarController controller, HealthBarRegistry registry, HealthBarFilterManager filterManager) {
        this.plugin = plugin;
        this.controller = controller;
        this.registry = registry;
        this.filterManager = filterManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                new PlayerRayCastTask(player.getUniqueId(), controller, registry, filterManager),
                0L, //Starts immediately
                2L // Repeat every 2 ticks
        );
    }
}
