package org.coolplugins.cool_HealthBar.registry;

import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.gui.healthformatter.AbstractEntityFormatter;

import java.util.ArrayList;
import java.util.List;

public class HealthBarRegistry {

    private final List<AbstractEntityFormatter> formatters = new ArrayList<>();
    private final AbstractEntityFormatter defaultAbstractEntityFormatter;

    public HealthBarRegistry(AbstractEntityFormatter defaultAbstractEntityFormatter) {
        this.defaultAbstractEntityFormatter = defaultAbstractEntityFormatter;
    }

    public void register(AbstractEntityFormatter formatter) {
        formatters.add(formatter);
    }
    public AbstractEntityFormatter getFormatter(LivingEntity livingEntity) {
        for(AbstractEntityFormatter entityFormatter : formatters)
            if(entityFormatter.support(livingEntity))
                return entityFormatter;

        return defaultAbstractEntityFormatter;
    }
}
