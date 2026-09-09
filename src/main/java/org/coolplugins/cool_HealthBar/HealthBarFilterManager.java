package org.coolplugins.cool_HealthBar;

import org.bukkit.entity.EntityType;

import java.util.EnumSet;
import java.util.Set;

public class HealthBarFilterManager {

    private boolean visibility = true;
    private final Set<EntityType> disabledEntities = EnumSet.noneOf(EntityType.class);

    public boolean isHealthBarVisible() {return visibility;}

    public boolean isSingleEntityBarVisible(EntityType entityType) {
        if(!visibility)
            return false;
        return !disabledEntities.contains(entityType);
    }

    public void hideAll() {visibility = false;}

    public void showAll() {
        visibility = true;
        disabledEntities.clear();
    }

    public void hideEntity(EntityType type) {
        disabledEntities.add(type);
    }

    public void showEntity(EntityType type) {
        disabledEntities.remove(type);
    }
}
