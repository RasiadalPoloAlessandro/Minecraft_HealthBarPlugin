package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.LivingEntity;

public class HostileMobFormatter extends EntityHealthBarFormatter {


    public  HostileMobFormatter() {
        super(TextColor.color(100,0,0));
    }

    public HostileMobFormatter(TextColor textColor) {
        super(textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Enemy;
    }
}
