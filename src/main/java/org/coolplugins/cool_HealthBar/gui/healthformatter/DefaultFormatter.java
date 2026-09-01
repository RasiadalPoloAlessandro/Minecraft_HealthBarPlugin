package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class DefaultFormatter extends EntityHealthBarFormatter {

    public DefaultFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC);
    }

    public DefaultFormatter(MobNamePDC mobNamePDC, TextColor textColor) {
        super(mobNamePDC,textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return true;
    }
}
