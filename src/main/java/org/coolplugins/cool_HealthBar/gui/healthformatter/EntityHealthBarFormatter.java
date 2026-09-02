package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.HealthManager;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public abstract class EntityHealthBarFormatter implements HealthBarLabel {


    protected final TextColor textColor;
    private final MobNamePDC mobNamePDC;

    public EntityHealthBarFormatter(MobNamePDC mobNamePDC) {
        this.mobNamePDC = mobNamePDC;
        textColor = TextColor.color(255,255,255);
    }

    public EntityHealthBarFormatter(MobNamePDC mobNamePDC, TextColor textColor) {
        this.mobNamePDC = mobNamePDC;
        this.textColor = textColor;
    }

    @Override
    public Component format(LivingEntity entity) {
        double percentage = HealthManager.getPercentage(entity);

        // get the actual name that has been saved
        Component displayName = mobNamePDC.getEffectiveName(entity);

        return Component.text()
                .append(displayName != null ? displayName : Component.text("Entity"))
                .append(Component.text(":"))
                .append(Component.space())
                .append(Component.text(String.format("%.1f", percentage))) // 1 decimal place
                .color(textColor)
                .build();
    }

}
