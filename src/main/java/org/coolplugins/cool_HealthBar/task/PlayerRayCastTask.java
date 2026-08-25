package org.coolplugins.cool_HealthBar.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.coolplugins.cool_HealthBar.HealthManager;

import java.util.UUID;
import java.util.function.Consumer;

public class PlayerRayCastTask implements Consumer<BukkitTask> {

    private final UUID playerUUID;

    // How many blocks does the ray cover
    private final int maxDistance = 8;
    private final HealthManager healthManager;


    public PlayerRayCastTask(UUID playerUUID, HealthManager healthManager){
        this.playerUUID = playerUUID;
        this.healthManager = healthManager;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {

        // get player
        Player player = Bukkit.getPlayer(playerUUID);
        if(player != null && player.isOnline()){
            //get target entity
            Entity entity = player.getTargetEntity(maxDistance);
            if(entity instanceof LivingEntity livingTarget) {
                healthManager.displayHealth(player, livingTarget);
            }
                // TODO Hide HealthBar
        }
        else
            bukkitTask.cancel();

    }
}
