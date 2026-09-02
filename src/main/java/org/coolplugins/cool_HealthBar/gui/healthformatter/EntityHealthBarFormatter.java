package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.HealthManager;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public abstract class EntityHealthBarFormatter implements HealthBarLabel {


    protected final TextColor textColor;
    protected static final int TOTAL_SEGMENTS = 20;
    protected static final char FILLED_CHAR = '▰';
    protected static final char EMPTY_CHAR = '▱';
    protected static final TextColor EMPTY_COLOR = TextColor.color(60, 60, 60);
    protected static final TextColor BRACKET_COLOR = TextColor.color(120, 120, 120);
    private final MobNamePDC mobNamePDC;

    public EntityHealthBarFormatter(MobNamePDC mobNamePDC) {
        this.mobNamePDC = mobNamePDC;
        textColor = TextColor.color(255,255,255);
    }

    public EntityHealthBarFormatter(MobNamePDC mobNamePDC, TextColor textColor) {
        this.mobNamePDC = mobNamePDC;
        this.textColor = textColor;
    }

    /**
     *
     * @param entity we're want to know the health
     * @return the text component that will be displayed
     */
    @Override
    public Component format(LivingEntity entity) {
        double percentage = HealthManager.getPercentage(entity);

        int filledSegments = (int) Math.round((percentage / 100) * TOTAL_SEGMENTS);
        if(percentage > 0 && filledSegments == 0)
            filledSegments = 1;

        TextComponent.Builder bar = Component.text();
        for(int i = 0; i<  filledSegments; i++)
            bar.append(Component.text(FILLED_CHAR, textColor));

        for(int i = filledSegments; i < TOTAL_SEGMENTS; i++)
            bar.append(Component.text(EMPTY_CHAR, EMPTY_COLOR));

        // get the actual name that has been saved
        Component displayName = mobNamePDC.getEffectiveName(entity);

        return Component.text()
                .append(displayName)
                .append(Component.newline())
                .append(Component.text("[", BRACKET_COLOR))
                .append(bar.build())
                .append(Component.text("] ", BRACKET_COLOR))
                .append(Component.text(String.format("%.0f%%", percentage), textColor))
                .build();
    }

}
