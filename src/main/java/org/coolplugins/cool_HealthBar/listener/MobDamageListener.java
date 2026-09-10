package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.coolplugins.cool_HealthBar.controller.HealthBarController;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;

public class MobDamageListener implements Listener {

    private final HealthBarController controller;

    public MobDamageListener(HealthBarController controller){
        this.controller = controller;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {


        if(event.isCancelled())
            return;

        Entity entity = event.getEntity();

        if(entity instanceof LivingEntity livingEntity) {

            double currentHealth = livingEntity.getHealth() - event.getFinalDamage();
            if(currentHealth <= 0.0)
                controller.removeDisplay(livingEntity.getUniqueId());
        }
    }
}
