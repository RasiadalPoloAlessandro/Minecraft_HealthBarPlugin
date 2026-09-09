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

public class HealthBarController {

    private final HealthBarModel healthBarModel;
    private final HealthBarView displayManager;

    public HealthBarController(HealthBarModel healthBarModel, HealthBarView displayManager) {
        this.healthBarModel = healthBarModel;
        this.displayManager = displayManager;
    }


    public void removeDisplayByType(EntityType type) {
        for(UUID uuid : healthBarModel.getMobToDisplayMap().keySet()) {
            Entity entity = Bukkit.getEntity(uuid);
            Bukkit.getLogger().info(entity.getClass().toString() + "confrontata con " + type.toString());
            if(entity != null && entity.getClass() == type.getEntityClass()) {
                displayManager.removeDisplay(uuid);
                healthBarModel.removeElement(uuid);
            }
        }
    }


    public void removeDisplay(@NotNull UUID uuid) {
        UUID displayUUID = healthBarModel.removeElement(uuid);
        if (displayUUID != null) {
            Entity entity = Bukkit.getEntity(displayUUID);
            if (entity instanceof TextDisplay textDisplay) {
                displayManager.removeDisplay(textDisplay.getUniqueId()); // o displayManager.removeDisplay(displayUUID)
            }
        }
    }

    public void addElements(UUID mobuuid, @NotNull UUID uuid) {
        healthBarModel.addElements(mobuuid, uuid);
    }



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

    public void clearAll() {

        for (UUID display : healthBarModel.getAllDisplays()) {
            displayManager.removeDisplay(display);
        }
        healthBarModel.clearMap();
    }
}
