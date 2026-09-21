package org.coolplugins.cool_HealthBar.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;

import java.util.UUID;

/**
 * Class that implements how the health bar is spawn or updated
 */
public class HealthBarView {


    /**
     * @return the text display spawn above the mob's head
     */
    public TextDisplay spawnDisplay(World world, Location loc, Component text) {
        return world.spawn(loc, TextDisplay.class, display -> {
            display.text(text);
            display.setBillboard(Display.Billboard.CENTER);
            display.setPersistent(false);
            display.addScoreboardTag("cool_health_bar");
            display.setTeleportDuration(2);
            display.setBackgroundColor(Color.fromARGB(0, 0, 0, 0));
            display.setShadowed(true);
        });
    }

    /**
     * Update the text of an existing health bar
     */
    public void updateDisplay(TextDisplay display, Component text, Location loc) {
        if (display != null && display.isValid()) {
            display.text(text);
            display.teleport(loc);
        }
    }

    /**
     * remove the health bar from an entity
     */
    public void removeDisplay(UUID display) {
        Entity entity = Bukkit.getEntity(display);

        if (entity instanceof TextDisplay text && text.isValid()) {
            entity.remove();
        }
    }
}