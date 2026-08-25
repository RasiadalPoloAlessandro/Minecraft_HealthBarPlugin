package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.coolplugins.cool_HealthBar.HealthManagerGUI;

public class MobDamageListener implements Listener {

    private final HealthManagerGUI healthGUI;

    public MobDamageListener(HealthManagerGUI healthGUI){
        this.healthGUI = healthGUI;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {


        if(event.isCancelled())
            return;

        Entity entity = event.getEntity();

        if(entity instanceof LivingEntity livingEntity) {

            double currentHealth = livingEntity.getHealth() - event.getFinalDamage();
            if(currentHealth <= 0)
                healthGUI.removeDisplay(livingEntity.getUniqueId());
        }
    }
}
