package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.coolplugins.cool_HealthBar.gui.HealthBarView;

public class WorldEventListener implements Listener {

    private final HealthBarView healthBarView;

    public WorldEventListener(HealthBarView healthBarView) {
        this.healthBarView = healthBarView;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                healthBarView.removeDisplay(livingEntity.getUniqueId());
            }
        }
    }
}
