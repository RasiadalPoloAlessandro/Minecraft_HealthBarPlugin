package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.coolplugins.cool_HealthBar.gui.HealthBarDisplayManager;

public class MobDeathListener implements Listener {

    private final HealthBarDisplayManager healthGUI;

    public MobDeathListener(HealthBarDisplayManager healthGUI) {
        this.healthGUI = healthGUI;
    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent event){
        //Entity entity = event.getEntity(); returns a livingEntity
            healthGUI.removeDisplay(event.getEntity().getUniqueId());
    }
}
