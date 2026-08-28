package org.coolplugins.cool_HealthBar.registry;

import org.bukkit.entity.LivingEntity;
import org.coolplugins.cool_HealthBar.healthformatter.DefaultFormatter;
import org.coolplugins.cool_HealthBar.healthformatter.EntityHealthBarFormatter;

import java.util.ArrayList;
import java.util.List;

public class HealthBarRegistry {

    private final List<EntityHealthBarFormatter> formatters = new ArrayList<>();
    private final EntityHealthBarFormatter defaultEntityHealthBarFormatter = new DefaultFormatter();

    public void register(EntityHealthBarFormatter formatter) {
        formatters.add(formatter);
    }
    public EntityHealthBarFormatter getFormatter(LivingEntity livingEntity) {
        for(EntityHealthBarFormatter entityFormatter : formatters)
            if(entityFormatter.support(livingEntity))
                return entityFormatter;

        return defaultEntityHealthBarFormatter;
    }
}
