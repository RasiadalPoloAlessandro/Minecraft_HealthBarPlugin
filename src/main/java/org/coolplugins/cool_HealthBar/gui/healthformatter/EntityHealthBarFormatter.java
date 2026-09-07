package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.coolplugins.cool_HealthBar.HealthManager;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public abstract class EntityHealthBarFormatter implements HealthBarLabel {


    protected static final TextColor FULL_HEALTH_COLOR = TextColor.color(0, 255, 0);
    protected static final TextColor HALF_HEALTH_COLOR = TextColor.color(255,255,0);
    protected static final TextColor LOW_HEALTH_COLOR = TextColor.color(255,0,0);
    protected static final int TOTAL_SEGMENTS = 10;
    protected static final char FILLED_CHAR = '▰';
    protected static final char EMPTY_CHAR = '▱';
    protected static final TextColor EMPTY_COLOR = TextColor.color(60, 60, 60);
    protected static final TextColor BRACKET_COLOR = TextColor.color(120, 120, 120);
    private final MobNamePDC mobNamePDC;

    public EntityHealthBarFormatter(MobNamePDC mobNamePDC) {
        this.mobNamePDC = mobNamePDC;
    }


    /**
     *
     * @param livingEntity entity
     * @return a Component that represent the mob's saved name
     */
    protected Component getEntityHeader(LivingEntity livingEntity) { return mobNamePDC.getEffectiveName(livingEntity);}

    /**
     *
     * @param livingEntity entity
     * @param percentage health expressed in percentage
     * @return the health bar formed by specified chars
     */
    private Component buildBarComponent(LivingEntity livingEntity, double percentage) {

        int filledSegments = (int) Math.round((percentage / 100) * TOTAL_SEGMENTS);
        if (percentage > 0 && filledSegments == 0)
            filledSegments = 1;

        TextComponent.Builder bar = Component.text();
        TextColor textColor;

        if(percentage > 50)
            textColor = FULL_HEALTH_COLOR;
        else if(percentage <= 50 && percentage > 20)
            textColor = HALF_HEALTH_COLOR;
        else
            textColor = LOW_HEALTH_COLOR;

        for(int i = 0; i < filledSegments; i++)
            bar.append(Component.text(FILLED_CHAR, textColor));

        for(int i = filledSegments; i < TOTAL_SEGMENTS; i++)
            bar.append(Component.text(EMPTY_CHAR, EMPTY_COLOR));

        return Component.text()
                .append(Component.text("[", BRACKET_COLOR))
                .append(bar.build())
                .append(Component.text("] ", BRACKET_COLOR))
                .append(Component.text(String.format("%.0f%%", percentage)))
                .build();
    }

    /**
     *
     * @param entity we want to know the health
     * @return the text component that will be displayed
     */
    @Override
    public Component format(LivingEntity entity) {

        double percentage = HealthManager.getPercentage(entity);
        Component bar = buildBarComponent(entity, percentage);
        Component mobHeader = getEntityHeader(entity);

        TextComponent.Builder builder = Component.text();

        builder.append(bar);
        // It's a mob whose name has been memorized
        // if it's null it means the entity is a Player, so the name will automatically appear
        if(mobHeader != null)
            builder.append(Component.newline()).append(mobHeader);

        return builder.build();
    }

}
