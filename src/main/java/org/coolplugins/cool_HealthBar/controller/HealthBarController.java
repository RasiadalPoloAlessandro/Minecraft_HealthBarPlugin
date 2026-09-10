package org.coolplugins.cool_HealthBar.controller;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;
import org.coolplugins.cool_HealthBar.model.HealthBarModel;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Class that handle the communication between model and view
 * Model and view don't have to know each other
 */
public class HealthBarController {

    private final HealthBarModel healthBarModel;
    private final HealthBarView displayManager;

    /**
     * Public constructor
     * @param healthBarModel model
     * @param displayManager view
     */
    public HealthBarController(HealthBarModel healthBarModel, HealthBarView displayManager) {
        this.healthBarModel = healthBarModel;
        this.displayManager = displayManager;
    }


    /**
     * remove the health bar of certain entity type
     * @param type we want to remove
     */
    public void removeDisplayByType(EntityType type) {
        for(UUID uuid : healthBarModel.getMobToDisplayMap().keySet()) {
            Entity entity = Bukkit.getEntity(uuid);
            if(entity != null && entity.getClass() == type.getEntityClass()) {
                displayManager.removeDisplay(uuid);
                healthBarModel.removeElement(uuid);
            }
        }
    }

    /**
     * remove the health bar of certain entity
     * @param uuid from the entity
     */
    public void removeDisplay(@NotNull UUID uuid) {
        UUID displayUUID = healthBarModel.removeElement(uuid);
        if (displayUUID != null) {
            Entity entity = Bukkit.getEntity(displayUUID);
            if (entity instanceof TextDisplay textDisplay) {
                displayManager.removeDisplay(textDisplay.getUniqueId()); // o displayManager.removeDisplay(displayUUID)
            }
        }
    }

    /**
     * Save inside the model the mob and the correspective health bar
     * @param mobuuid entity UUID
     * @param uuid textDisplay UUID
     */
    public void addElements(UUID mobuuid, @NotNull UUID uuid) {
        healthBarModel.addElements(mobuuid, uuid);
    }


    /**
     * Show for the first time or update the health bar of a certain Entity
     *
     * @param livingEntity entity to check
     * @param text text to update or to insert
     * @param yOffset offset
     */
    public void showOrUpdateText(LivingEntity livingEntity, Component text, float yOffset) {

        UUID mobUUID = livingEntity.getUniqueId();
        // Case 1: the mob is dead
        if (livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            removeDisplay(mobUUID);
            return;
        }

        // Hide custom name if present
        if (livingEntity.customName() != null) {
            livingEntity.setCustomNameVisible(false);
        }


        //Case 2: the mob is alive
        UUID displayUUID = healthBarModel.getElement(mobUUID);
        Location targetLoc = livingEntity.getLocation().add(0, livingEntity.getHeight() + 0.35 + yOffset, 0);

        // Check if an active and valid display already exists
        if (displayUUID != null) {
            Entity displayEntity = Bukkit.getEntity(displayUUID);
            if (displayEntity instanceof TextDisplay textDisplay && textDisplay.isValid()) {

                displayManager.updateDisplay(textDisplay, text, targetLoc);
                return;
            }
        }

        TextDisplay newDisplay = displayManager.spawnDisplay(livingEntity.getWorld(), targetLoc, text);

        addElements(mobUUID, newDisplay.getUniqueId());
    }

    /**
     * clear the entity saved in both view and model
     */
    public void clearAll() {

        // if there are some display that are shown, remove them
        for (UUID display : healthBarModel.getAllDisplays()) {
            displayManager.removeDisplay(display);
        }
        // clear the map
        healthBarModel.clearMap();
    }
}
