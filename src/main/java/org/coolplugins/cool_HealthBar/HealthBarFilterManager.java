package org.coolplugins.cool_HealthBar;

import org.bukkit.entity.EntityType;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public class HealthBarFilterManager {

    private final Set<EntityType> enabledEntities = Collections.synchronizedSet(EnumSet.noneOf(EntityType.class));

    /**
     * Public constructor
     */
    public HealthBarFilterManager() {enableAllEntities();}

    /**
     *
     * @return if the player is able to see the health bar of at least one entity
     */
    public boolean hasAnyEnabled() { return !enabledEntities.isEmpty();}

    /**
     *
     * @param entityType Entity to check
     * @return if entityType is hidden or not
     */
    public boolean isSingleEntityBarVisible(EntityType entityType) { return enabledEntities.contains(entityType);}

    /**
     * Hide all health bars
     */
    public void hideAll() {
        enabledEntities.clear();}

    /**
     * Show all health bars
     */
    public void showAll() { enableAllEntities();}

    /**
     *
     * @param type EntityType to hide
     */
    public void hideEntity(EntityType type) { enabledEntities.remove(type);}

    /**
     *
     * @param type EntityType to show
     */
    public void showEntity(EntityType type) { enabledEntities.add(type);}

    /**
     * Enable all entities
     */
    private void enableAllEntities() {
        enabledEntities.clear();
        for(EntityType entityType : EntityType.values())
            if(entityType.isAlive())
                enabledEntities.add(entityType);
    }
}
