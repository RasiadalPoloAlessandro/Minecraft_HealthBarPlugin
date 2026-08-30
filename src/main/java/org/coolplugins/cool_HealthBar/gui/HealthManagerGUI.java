package org.coolplugins.cool_HealthBar.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class HealthManagerGUI {

    // track and associate the Healthisplay to the correct mob
    private final Map<UUID, UUID> mobToDisplayMap = new ConcurrentHashMap<>();

    public void showOrUpdateText(LivingEntity livingEntity, Component text, float yOffset) {

        // Case 1: the mob is dead
        if (livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            removeDisplay(livingEntity.getUniqueId());
            return;
        }

        //Case 2: the mob is alive
        UUID mobUUID = livingEntity.getUniqueId();
        UUID displayUUID = mobToDisplayMap.get(mobUUID);
        Location targetLoc = livingEntity.getLocation().add(0, livingEntity.getHeight() + 0.35 + yOffset, 0);

        // Check if an active and valid display already exists
        if (displayUUID != null) {
            Entity displayEntity = Bukkit.getEntity(displayUUID);
            if (displayEntity instanceof TextDisplay textDisplay && textDisplay.isValid()) {

                /*
                Using passengers made more problems than benefits
                if(!livingEntity.getPassengers().contains(textDisplay))
                    livingEntity.addPassenger(textDisplay);*/

                textDisplay.text(text);
                textDisplay.teleport(targetLoc);
                return;
            }
        }

        // Make a new display
        World world = livingEntity.getWorld();
        TextDisplay txt = world.spawn(targetLoc, TextDisplay.class, display -> {
            display.text(text);
            display.setBillboard(Display.Billboard.VERTICAL);
            display.setPersistent(false);
            display.addScoreboardTag("cool_health_bar");
        });

        mobToDisplayMap.put(mobUUID, txt.getUniqueId());
    }

    public void removeDisplay(UUID entityID) {

        //With the map it's known if the current entity has a display as a passenger
        UUID textDisplayUUID = mobToDisplayMap.remove(entityID);

        if(textDisplayUUID != null) {
            Entity entity = Bukkit.getEntity(textDisplayUUID);
            if (entity != null && entity.isValid())
                entity.remove();
        }
    }

    public void clearAll() {
        for (UUID displayUUID : mobToDisplayMap.values()) {
            Entity display = Bukkit.getEntity(displayUUID);
            if (display != null) {
                display.remove();
            }
        }
        mobToDisplayMap.clear();
    }
}