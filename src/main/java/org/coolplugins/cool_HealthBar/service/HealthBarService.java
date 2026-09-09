package org.coolplugins.cool_HealthBar.service;

import org.bukkit.entity.EntityType;
import org.coolplugins.cool_HealthBar.HealthBarFilterManager;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;

public class HealthBarService {

    private final HealthBarFilterManager filterManager;
    private final HealthBarController controller;

    public HealthBarService(HealthBarFilterManager filterManager, HealthBarController controller) {
        this.filterManager = filterManager;
        this.controller = controller;
    }

    public void hideAll() {
        filterManager.hideAll();
        controller.clearAll();
    }

    public void showAll() {
        filterManager.showAll();
    }

    public void hideEntity(EntityType type) {
        filterManager.hideEntity(type);
        controller.removeDisplayByType(type);
    }

    public void showEntity(EntityType type) {
        filterManager.showEntity(type);
    }

}
