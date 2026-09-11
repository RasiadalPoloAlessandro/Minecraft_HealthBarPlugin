package org.coolplugins.cool_HealthBar.gui.healthformatter;

import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class EntityFormatter extends AbstractEntityFormatter {

    public EntityFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return true;
    }
}
