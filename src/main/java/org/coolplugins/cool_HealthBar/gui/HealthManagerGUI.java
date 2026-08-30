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


public class HealthManagerGUI {

    // track and associate the Healthisplay to the correct mob
    private final Map<UUID, UUID> mobToDisplayMap = new ConcurrentHashMap<>();

    public void showOrUpdateText(LivingEntity livingEntity, Component text) {

        // Case 1: the mob is dead
        if (livingEntity.isDead() || livingEntity.getHealth() <= 0) {
            removeDisplay(livingEntity.getUniqueId());
            return;
        }

        //Case 2: the mob is alive
        UUID mobUUID = livingEntity.getUniqueId();
        UUID displayUUID = mobToDisplayMap.get(mobUUID);

        // Check if an active and valid display already exists
        if (displayUUID != null) {
            Entity displayEntity = Bukkit.getEntity(displayUUID);
            if (displayEntity instanceof TextDisplay textDisplay && textDisplay.isValid()) {
                textDisplay.text(text);
                return;
            }
        }

        // Make a new display
        World world = livingEntity.getWorld();
        Location loc = livingEntity.getLocation();

        TextDisplay txt = world.spawn(loc, TextDisplay.class, display -> {
            display.text(text);
            display.setBillboard(Display.Billboard.CENTER);
            display.setPersistent(false);
            display.addScoreboardTag("cool_health_bar");
        });

        livingEntity.addPassenger(txt);
        mobToDisplayMap.put(mobUUID, txt.getUniqueId());
    }

    public void removeDisplay(UUID entityID) {

        //With the map it's known if the current entity has a display as a passenger
        UUID textDisplayUUID = mobToDisplayMap.remove(entityID);

        if(textDisplayUUID != null) {
            Entity entity = Bukkit.getEntity(textDisplayUUID);
            if (entity != null)
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