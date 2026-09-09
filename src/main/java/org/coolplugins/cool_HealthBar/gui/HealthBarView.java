package org.coolplugins.cool_HealthBar.gui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.UUID;


public class HealthBarView {


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

    public void updateDisplay(TextDisplay display, Component text, Location loc) {
        if (display != null && display.isValid()) {
            display.text(text);
            display.teleport(loc);
        }
    }

    public void removeDisplay(UUID display) {
        Entity entity = Bukkit.getEntity(display);

        if (entity instanceof TextDisplay text && text.isValid()) {
            entity.remove();
        }
    }
}