package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;

public class MobDeathListener implements Listener {

    private final HealthBarController controller;

    public MobDeathListener(HealthBarController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        //Entity entity = event.getEntity(); returns a livingEntity
            controller.removeDisplay(event.getEntity().getUniqueId());
    }
}
