package org.coolplugins.cool_HealthBar.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.coolplugins.cool_HealthBar.gui.HealthManagerGUI;
import org.coolplugins.cool_HealthBar.gui.healthformatter.EntityHealthBarFormatter;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;

import java.util.UUID;
import java.util.function.Consumer;

/*
    Class that uses an imaginary ray in order to know what entity the player is looking,
    It does this every 2 ticks
* */
public class PlayerRayCastTask implements Consumer<BukkitTask> {

    private final UUID playerUUID;
    private final HealthManagerGUI healthManagerGUI;
    private final HealthBarRegistry registry;



    public PlayerRayCastTask(UUID playerUUID, HealthManagerGUI healthManagerGUI, HealthBarRegistry registry) {
        this.playerUUID = playerUUID;
        this.healthManagerGUI = healthManagerGUI;
        this.registry = registry;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {

        // get player
        Player player = Bukkit.getPlayer(playerUUID);
        if(player != null && player.isOnline()){
            //get target entity
            // How many blocks does the ray cover
            final int maxDistance = 8;
            Entity entity = player.getTargetEntity(maxDistance);
            if (entity instanceof LivingEntity livingTarget && livingTarget.getHealth() > 0) {
                if (!livingTarget.isDead() && livingTarget.getHealth() > 0) {
                    EntityHealthBarFormatter entityHealthBarFormatter = registry.getFormatter(livingTarget);
                    healthManagerGUI.showOrUpdateText(livingTarget, entityHealthBarFormatter.format(livingTarget));
                } else {
                    healthManagerGUI.removeDisplay(livingTarget.getUniqueId());
                }
            }

        }
        else
            bukkitTask.cancel();

    }
}
