package org.coolplugins.cool_HealthBar.model;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;

import java.util.Objects;

public class EntityHealth {


    public static double getPercentage(LivingEntity entity){
        final int MAX_PERCENT = 100;
        return (entity.getHealth() * MAX_PERCENT)/Objects.requireNonNull(entity.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

}
