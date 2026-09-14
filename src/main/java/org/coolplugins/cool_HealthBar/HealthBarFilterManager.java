package org.coolplugins.cool_HealthBar;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public class HealthBarFilterManager {

    // List of entities we don't want to show the health bar
    private final Set<EntityType> disabledEntities = new HashSet<>(List.of(EntityType.WITHER));
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

        // searching I found out that applying filters is more effective, It doesn't require if statements and It's more compact
        Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                // Remove the entities that we don't want to be enabled and seen with the health bar (Wither, ecc...)
                .filter(type -> !disabledEntities.contains(type))
                .forEach(enabledEntities::add);
    }
}
