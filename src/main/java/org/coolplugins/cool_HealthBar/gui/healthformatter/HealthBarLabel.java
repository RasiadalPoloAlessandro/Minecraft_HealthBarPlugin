package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;

/*
Every HealthBar has to implement this interface
* */
public interface HealthBarLabel {

    /*returns the component formatted for a certain mob
        hostile mob: label with red text, black border ecc...
     */
    Component format(LivingEntity entity);

    boolean support(LivingEntity livingEntity);

    default float getYOffset(LivingEntity entity) { return 0.0f; }

}
