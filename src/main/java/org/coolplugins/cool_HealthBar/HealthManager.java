package org.coolplugins.cool_HealthBar;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Logger;

public class HealthManager {

    private final Logger logger;


    public HealthManager(Logger logger){
        this.logger = logger;
    }

    private double getPercentage(LivingEntity entity){
        return (entity.getHealth() * 100)/Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

    public void displayHealth(Player player, LivingEntity entity){
        logger.info(String.format("Player %s punta %s (%.1f%% HP)",
                player.getName(), entity.getType(), getPercentage(entity)));
    }

}
