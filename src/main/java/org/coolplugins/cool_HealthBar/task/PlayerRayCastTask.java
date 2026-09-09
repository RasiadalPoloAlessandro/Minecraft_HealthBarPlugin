package org.coolplugins.cool_HealthBar.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.coolplugins.cool_HealthBar.HealthBarFilterManager;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
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
    private final HealthBarRegistry registry;
    private final HealthBarController controller;
    private final HealthBarFilterManager filterManager;
    private UUID lastTargetUUID = null;

    public PlayerRayCastTask(UUID playerUUID, HealthBarController controller, HealthBarRegistry registry, HealthBarFilterManager filterManager) {
        this.playerUUID = playerUUID;
        this.controller = controller;
        this.registry = registry;
        this.filterManager = filterManager;
    }


    private boolean isValid(Player player, LivingEntity livingEntity) {
        if (!filterManager.isSingleEntityBarVisible(livingEntity.getType()) || !livingEntity.isValid() || livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            return false;
        }
        if (player.getVehicle() != null && player.getVehicle().equals(livingEntity)) {
            return false;
        }

        return true;
    }

    @Override
    public void accept(BukkitTask bukkitTask) {
        // the task's still running, it's better to "freeze" it instead of creating it every time a player want to show again the health bars
        if(!filterManager.hasAnyEnabled())
            return;

        // get player
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null && player.isOnline()) {
            //get target entity
            // How many blocks does the ray cover
            final int maxDistance = 8;
            Entity entity = player.getTargetEntity(maxDistance);
            if (entity instanceof LivingEntity livingTarget) {
                // FIXED: removed the negation so valid entities show the bar, and handle cleanup in else
                if (isValid(player, livingTarget)) {
                    UUID currentUUID = livingTarget.getUniqueId();

                    // Remove the previous health bar if the player switched to a different target
                    if (lastTargetUUID != null && !lastTargetUUID.equals(currentUUID))
                        controller.removeDisplay(lastTargetUUID);

                    EntityHealthBarFormatter entityHealthBarFormatter = registry.getFormatter(livingTarget);
                    controller.showOrUpdateText(livingTarget, entityHealthBarFormatter.format(livingTarget), entityHealthBarFormatter.getYOffset());
                    lastTargetUUID = currentUUID;
                } else {
                    removeLastUUID();
                }

            } else
                removeLastUUID();

        } else {
            removeLastUUID();
            bukkitTask.cancel();
        }

    }

    private void removeLastUUID() {
        if (lastTargetUUID != null)
            controller.removeDisplay(lastTargetUUID);
        lastTargetUUID = null;
    }

}