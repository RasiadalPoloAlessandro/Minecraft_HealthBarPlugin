package org.coolplugins.cool_HealthBar.gui.healthformatter;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.coolplugins.cool_HealthBar.HealthManager;
import org.coolplugins.cool_HealthBar.gui.HealthBar;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

public abstract class EntityHealthBarFormatter implements HealthBarLabel {


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
     * @param entity we want to know the health
     * @return the text component that will be displayed
     */
    @Override
    public Component format(LivingEntity entity) {

        HealthBar bar = new HealthBar();
        double percentage = HealthManager.getPercentage(entity);
        Component mobHeader = getEntityHeader(entity);

        TextComponent.Builder builder = Component.text();

        builder.append(bar.buildBarComponent(percentage));
        // It's a mob whose name has been memorized
        // if it's null it means the entity is a Player, so the name will automatically appear
        if(mobHeader != null)
            builder.append(Component.newline()).append(mobHeader);

        return builder.build();
    }

}
