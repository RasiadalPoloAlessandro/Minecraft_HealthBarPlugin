package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public class PlayerFormatter extends AbstractEntityFormatter {


    public PlayerFormatter(MobNamePDC mobNamePDC) {
        super(mobNamePDC);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return livingEntity instanceof Player;
    }

    @Override
    public float getYOffset() {
        return 0.28f;
    }

    /**
     *
     * Players don't have their names saved, so the Header is null
     */
    @Override
    protected Component getEntityHeader(LivingEntity livingEntity) {
        return null;
    }
}
