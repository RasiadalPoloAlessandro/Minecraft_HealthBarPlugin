package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.PlayerNameTagManager;
import org.coolplugins.cool_HealthBar.gui.HealthBarDisplayManager;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;
import org.coolplugins.cool_HealthBar.task.PlayerRayCastTask;

public class PlayerConnectionListener implements Listener {

    private final Plugin plugin;
    private final HealthBarDisplayManager healthBarDisplayManager;
    private final HealthBarRegistry registry;
    private final PlayerNameTagManager playerNameTagManager;

    public PlayerConnectionListener(Plugin plugin, HealthBarDisplayManager healthBarDisplayManagerGUi, HealthBarRegistry registry, PlayerNameTagManager playerNameTagManager) {
        this.plugin = plugin;
        this.healthBarDisplayManager = healthBarDisplayManagerGUi;
        this.registry = registry;
        this.playerNameTagManager = playerNameTagManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        playerNameTagManager.hide(player);

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                new PlayerRayCastTask(player.getUniqueId(), healthBarDisplayManager, registry),
                0L, //Starts immediately
                2L // Repeat every 2 ticks
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerNameTagManager.restore(event.getPlayer());
    }
}
