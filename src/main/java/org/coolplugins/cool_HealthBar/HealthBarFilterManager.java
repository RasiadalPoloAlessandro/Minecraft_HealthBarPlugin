package org.coolplugins.cool_HealthBar;

import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class HealthBarFilterManager {

    private final Set<EntityType> enabledEntities = Collections.synchronizedSet(EnumSet.noneOf(EntityType.class));

    public HealthBarFilterManager() {enableAllEntities();}

    public boolean hasAnyEnabled() { return !enabledEntities.isEmpty();}

    public boolean isSingleEntityBarVisible(EntityType entityType) { return enabledEntities.contains(entityType);}

    public void hideAll() {
        enabledEntities.clear();}

    public void showAll() { enableAllEntities();}

    public void hideEntity(EntityType type) { enabledEntities.remove(type);}

    public void showEntity(EntityType type) { enabledEntities.add(type);}

    private void enableAllEntities() {
        enabledEntities.clear();
        for(EntityType entityType : EntityType.values())
            if(entityType.isAlive())
                enabledEntities.add(entityType);
    }
}
