package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.HealthManager;
import org.coolplugins.cool_HealthBar.task.PlayerRayCastTask;

public class PlayerConnectionListener implements Listener {

    private final Plugin plugin;
    private final HealthManager healthManager;


    public PlayerConnectionListener(Plugin plugin, HealthManager healthManager){
        this.plugin = plugin;
        this.healthManager = healthManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                new PlayerRayCastTask(player.getUniqueId(), healthManager),
                0L, //Starts immediately
                2L // Repeat every 2 ticks
        );
    }
}
