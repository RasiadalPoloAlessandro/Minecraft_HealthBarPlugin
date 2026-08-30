package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;

public class FriendlyMobFormatter extends EntityHealthBarFormatter {

    public  FriendlyMobFormatter() {
        super(TextColor.color(0,255,0));
    }

    public FriendlyMobFormatter(TextColor textColor) {
        super(textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Animals;
    }
}
