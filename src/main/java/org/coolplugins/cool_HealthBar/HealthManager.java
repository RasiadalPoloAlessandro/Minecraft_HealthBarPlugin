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

}
