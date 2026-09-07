package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Enemy;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class HostileMobFormatter extends EntityHealthBarFormatter {


    public  HostileMobFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Enemy;
    }
}
