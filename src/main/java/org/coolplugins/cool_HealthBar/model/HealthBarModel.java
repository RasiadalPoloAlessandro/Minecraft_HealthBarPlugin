package org.coolplugins.cool_HealthBar.model;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HealthBarModel {

    // track and associate the Health isplay to the correct mob
    private final Map<UUID, UUID> mobToDisplayMap = new ConcurrentHashMap<>();


    public void addElements(@NotNull UUID mobuuid, @NotNull UUID uuid) {
        mobToDisplayMap.put(mobuuid, uuid);
    }

    /**
     *
     * @return the TextDisplay shown above the mob
     */
    public UUID removeElement(@NotNull UUID entityID) {
        //With the map it's known if the current entity has a display as a passenger
        return mobToDisplayMap.remove(entityID);
    }

    public void clearMap() {
        mobToDisplayMap.clear();
    }

    public Map<UUID, UUID> getMobToDisplayMap() {
        return mobToDisplayMap;
    }

    public UUID getElement(@NotNull UUID uuid) {return mobToDisplayMap.get(uuid);}

    public Collection<UUID> getAllDisplays() {return mobToDisplayMap.values();}
}
