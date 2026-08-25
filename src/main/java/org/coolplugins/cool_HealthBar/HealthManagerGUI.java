package org.coolplugins.cool_HealthBar;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;

import java.util.UUID;

public class HealthManagerGUI {

    public void showOrUpdateText(LivingEntity livingEntity, Component text) {

        for(Entity entity : livingEntity.getPassengers())
            if (entity instanceof TextDisplay textDisplay) {
                textDisplay.text(text);
                return;
            }


        World world = livingEntity.getWorld();
        Location loc = livingEntity.getLocation();

        TextDisplay txt = world.spawn(loc, TextDisplay.class, display -> {
            display.text(text);
            display.setBillboard(Display.Billboard.CENTER);
            display.setPersistent(false);
        });

        livingEntity.addPassenger(txt);
    }

    public void removeDisplay(UUID entityID){
        Entity entity = Bukkit.getEntity(entityID);

        if(entity != null)
            for(Entity e : entity.getPassengers())
                if(e instanceof TextDisplay)
                    e.remove();
    }
}