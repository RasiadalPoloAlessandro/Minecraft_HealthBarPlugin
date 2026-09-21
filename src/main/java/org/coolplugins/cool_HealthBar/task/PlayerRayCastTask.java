package org.coolplugins.cool_HealthBar.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.coolplugins.cool_HealthBar.HealthBarFilterManager;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.gui.healthformatter.AbstractEntityFormatter;
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

    /**
     * Default Constructor
     */
    public PlayerRayCastTask(UUID playerUUID, HealthBarController controller, HealthBarRegistry registry, HealthBarFilterManager filterManager) {
        this.playerUUID = playerUUID;
        this.controller = controller;
        this.registry = registry;
        this.filterManager = filterManager;
    }

    /**
     * Verufy if the entity the player's looking is alive, is not hidden by a command and its health is greater then 0
     * @param player that is looking
     * @param livingEntity that has been looked by the player
     * @return a boolean
     */
    private boolean isValid(Player player, LivingEntity livingEntity) {
        if (!filterManager.isSingleEntityBarVisible(livingEntity.getType()) || !livingEntity.isValid() || livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            return false;
        }
        if (player.getVehicle() != null && player.getVehicle().equals(livingEntity)) {
            return false;
        }

        return true;
    }

    /**
     * When a player connect on the server, a thread starts and capture everything the player looks and, if it's a valid entity, it shows the health bar
     * @param bukkitTask the input argument
     */
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

                    AbstractEntityFormatter abstractEntityFormatter = registry.getFormatter(livingTarget);
                    controller.showOrUpdateText(livingTarget, abstractEntityFormatter.format(livingTarget), abstractEntityFormatter.getYOffset());
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

    /**
     * Method tha clear last target variable when the player changed what is looking
     */
    private void removeLastUUID() {
        if (lastTargetUUID != null)
            controller.removeDisplay(lastTargetUUID);
        lastTargetUUID = null;
    }

}