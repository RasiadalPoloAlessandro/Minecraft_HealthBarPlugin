package org.coolplugins.cool_HealthBar.gui.component;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

/**
 * Class that represent the health bar
 */
public class HealthBar {

    protected static final TextColor FULL_HEALTH_COLOR = TextColor.color(0, 255, 0);
    protected static final TextColor HALF_HEALTH_COLOR = TextColor.color(255,255,0);
    protected static final TextColor LOW_HEALTH_COLOR = TextColor.color(255,0,0);
    private final int totalSegments;
    private final char filledChar;
    private final char emptyChar;
    private final TextColor emptyColor;
    private final TextColor bracketColor;

    /**
     * Default constructor
     */
    public HealthBar() {
        this(10, '▰', '▱', TextColor.color(60, 60, 60), TextColor.color(120, 120, 120));
    }

    /**
     * Public constructor
     * @param totalSegments representing the health
     * @param filledChar symbol for health remained
     * @param emptyChar symbol for health lost
     * @param emptyColor color for emptyChar symbol
     * @param bracketColor color for symbol at the edge of the bar
     */
    public HealthBar(int totalSegments, char filledChar, char emptyChar, TextColor emptyColor, TextColor bracketColor) {
        this.totalSegments = totalSegments;
        this.filledChar = filledChar;
        this.emptyChar = emptyChar;
        this.emptyColor = emptyColor;
        this.bracketColor = bracketColor;
    }


    /**
     *
     * @param percentage health expressed in percentage
     * @return the health bar formed by specified chars
     */
    public Component buildBarComponent(double percentage) {

        int filledSegments = (int) Math.round((percentage / 100) * totalSegments);
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
            bar.append(Component.text(filledChar, textColor));

        for(int i = filledSegments; i < totalSegments; i++)
            bar.append(Component.text(emptyChar, emptyColor));

        return Component.text()
                .append(Component.text("[", bracketColor))
                .append(bar.build())
                .append(Component.text("] ", bracketColor))
                .append(Component.text(String.format("%.0f%%", percentage)))
                .build();
    }


}
