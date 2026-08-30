package org.coolplugins.cool_HealthBar.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.coolplugins.cool_HealthBar.gui.HealthManagerGUI;

public class WorldEventListener implements Listener {

    private final HealthManagerGUI healthManagerGUI;

    public WorldEventListener(HealthManagerGUI healthManagerGUI) {
        this.healthManagerGUI = healthManagerGUI;
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof LivingEntity livingEntity) {
                healthManagerGUI.removeDisplay(livingEntity.getUniqueId());
            }
        }
    }
}
