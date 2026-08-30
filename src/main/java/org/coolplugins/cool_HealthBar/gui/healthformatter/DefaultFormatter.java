package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;

public class DefaultFormatter extends EntityHealthBarFormatter {

    public DefaultFormatter() {
        super();
    }

    public DefaultFormatter(TextColor textColor) {
        super(textColor);
    }

    @Override
    public boolean support(LivingEntity livingEntity) {
        return true;
    }
}
