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

/**
 * Periodic task that raycasts from the player's view direction to detect
 * targeted living entities and display their health bar.
 */
public class PlayerRayCastTask implements Consumer<BukkitTask> {

    private final UUID playerUUID;
    private final HealthManagerGUI healthManagerGUI;
    private final HealthBarRegistry registry;
    private UUID lastTargetUUID = null;



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
            if (entity instanceof LivingEntity livingTarget) {
                if (!livingTarget.isDead() && livingTarget.getHealth() > 0) {
                    UUID currentUUID = livingTarget.getUniqueId();

                    // Remove the previous health bar if the player switched to a different target
                    if(lastTargetUUID != null && !lastTargetUUID.equals(currentUUID))
                        healthManagerGUI.removeDisplay(lastTargetUUID);
                    EntityHealthBarFormatter entityHealthBarFormatter = registry.getFormatter(livingTarget);
                    healthManagerGUI.showOrUpdateText(livingTarget, entityHealthBarFormatter.format(livingTarget), entityHealthBarFormatter.getYOffset(livingTarget));
                    lastTargetUUID = currentUUID;
                } else
                    healthManagerGUI.removeDisplay(livingTarget.getUniqueId());

            } else
                removeLastUUID();

        } else {
            removeLastUUID();

            bukkitTask.cancel();
        }

    }

    private void removeLastUUID() {
        if(lastTargetUUID != null)
            healthManagerGUI.removeDisplay(lastTargetUUID);
        lastTargetUUID = null;
    }

}
