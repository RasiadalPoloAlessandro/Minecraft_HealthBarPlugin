package org.coolplugins.cool_HealthBar;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class HealthManager {


    public static double getPercentage(LivingEntity entity){
        final int MAX_PERCENT = 100;
        return (entity.getHealth() * MAX_PERCENT)/Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

    /*public void displayHealth(Player player, LivingEntity entity){
        logger.info(String.format("Player %s punta %s (%.1f%% HP)",
                player.getName(), entity.getType(), getPercentage(entity)));

    }

    public void hideHealthBar(LivingEntity entity) {

        List<Entity> entities = entity.getPassengers();
        for(Entity e : entities)
            if(e instanceof TextDisplay textDisplay) {
                textDisplay.remove();
            }
    }

     */

}
