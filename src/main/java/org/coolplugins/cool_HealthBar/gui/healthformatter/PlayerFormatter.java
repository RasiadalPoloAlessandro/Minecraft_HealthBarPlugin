package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class PlayerFormatter extends EntityHealthBarFormatter{


    public PlayerFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC, TextColor.color(200,0,0));
    }

    public PlayerFormatter(MobNamePDC mobNamePDC, TextColor textColor) {
        super(mobNamePDC, textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Player;
    }

    @Override
    public float getYOffset() {
        return 0.28f;
    }
}
