package org.coolplugins.cool_HealthBar.controller;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.coolplugins.cool_HealthBar.gui.HealthBarDisplayManager;
import org.coolplugins.cool_HealthBar.model.HealthBarManager;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class HealthBarController {

    private final HealthBarManager healthBarManager;
    private final HealthBarDisplayManager displayManager;

    public HealthBarController(HealthBarManager healthBarManager, HealthBarDisplayManager displayManager) {
        this.healthBarManager = healthBarManager;
        this.displayManager = displayManager;
    }


    public void removeDisplayByType(EntityType type) {
        for(UUID uuid : healthBarManager.getMobToDisplayMap().keySet()) {
            Entity entity = Bukkit.getEntity(uuid);
            if(entity != null && entity.getClass() == type.getEntityClass()) {
                displayManager.removeDisplay(uuid);
                healthBarManager.removeElement(uuid);
            }
        }
    }


    public void removeDisplay(@NotNull UUID uuid) {
        UUID displayUUID = healthBarManager.removeElement(uuid);
        if (displayUUID != null) {
            Entity entity = Bukkit.getEntity(displayUUID);
            if (entity instanceof TextDisplay textDisplay) {
                displayManager.removeDisplay(textDisplay.getUniqueId()); // o displayManager.removeDisplay(displayUUID)
            }
        }
    }

    public void addElements(UUID mobuuid, @NotNull UUID uuid) {
        healthBarManager.addElements(mobuuid, uuid);
    }

    public UUID getElementFromMap(@NotNull UUID uuid) { return  healthBarManager.getMobToDisplayMap().get(uuid);}



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
        UUID displayUUID = healthBarManager.getElement(mobUUID);
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

        healthBarManager.addElements(mobUUID, newDisplay.getUniqueId());
    }

    public void clearAll() {

        for (UUID display : healthBarManager.getAllDisplays()) {
            displayManager.removeDisplay(display);
        }
        healthBarManager.clearMap();
    }
}
