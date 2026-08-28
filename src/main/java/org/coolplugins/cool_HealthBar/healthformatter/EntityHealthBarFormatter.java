package org.coolplugins.cool_HealthBar.healthformatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.HealthBarLabel;
import org.coolplugins.cool_HealthBar.HealthManager;

public abstract class EntityHealthBarFormatter implements HealthBarLabel {


    protected final TextColor textColor;

    public EntityHealthBarFormatter() {
        textColor = TextColor.color(255,255,255);
    }

    public EntityHealthBarFormatter(TextColor textColor) {
        this.textColor = textColor;
    }

    @Override
    public Component format(LivingEntity entity) {
        double percentage = HealthManager.getPercentage(entity);

        return Component.text()
                .append(Component.text(entity.getName(), NamedTextColor.WHITE))
                .append(Component.text(":"))
                .append(Component.space())
                .append(Component.text(String.format("%.1f%%", percentage), textColor))
                .build();
    }

}
