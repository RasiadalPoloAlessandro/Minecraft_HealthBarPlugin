package org.coolplugins.cool_HealthBar.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class HealthBarDisplayManager {

    // track and associate the Healthisplay to the correct mob
    private final Map<UUID, UUID> mobToDisplayMap = new ConcurrentHashMap<>();

    public void showOrUpdateText(LivingEntity livingEntity, Component text, float yOffset) {

        // Case 1: the mob is dead
        if (livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            removeDisplay(livingEntity.getUniqueId());
            return;
        }

        // Hide custom name if present
        if (livingEntity.customName() != null) {
            livingEntity.setCustomNameVisible(false);
        }


        //Case 2: the mob is alive
        UUID mobUUID = livingEntity.getUniqueId();
        UUID displayUUID = mobToDisplayMap.get(mobUUID);
        Location targetLoc = livingEntity.getLocation().add(0, livingEntity.getHeight() + 0.35 + yOffset, 0);

        // Check if an active and valid display already exists
        if (displayUUID != null) {
            Entity displayEntity = Bukkit.getEntity(displayUUID);
            if (displayEntity instanceof TextDisplay textDisplay && textDisplay.isValid()) {

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
            display.setTeleportDuration(2); // it prevents lag sensation when the textDisplay teleport in a new position
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

        for (UUID mobUUID : mobToDisplayMap.keySet()) {
            removeDisplay(mobUUID);
        }

        mobToDisplayMap.clear();
    }
}