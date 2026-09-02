package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class FriendlyMobFormatter extends EntityHealthBarFormatter {

    public  FriendlyMobFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC, TextColor.color(0,255,0));
    }

    public FriendlyMobFormatter(MobNamePDC mobNamePDC, TextColor textColor) {
        super(mobNamePDC, textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Animals;
    }
}
