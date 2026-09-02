package org.coolplugins.cool_HealthBar.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.coolplugins.cool_HealthBar.gui.HealthManagerGUI;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;

/**
 * Class that implements methods to handle customNames on different events
 * the major problems are when the player rename an entity with a nametag or spawn the entity directly with a customName
 */
public class MobNameListener implements Listener {
    private final HealthManagerGUI healthManagerGUI;
    private final MobNamePDC mobNamePDC;

    /**
     * As soon as the player log in, check all the entities that have loaded
     */
    private void scanLoadedEntities() {
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity living : world.getLivingEntities()) {
                mobNamePDC.backUpAndHideName(living);
            }
        }
    }

    /**
     * Constructor for MobNameListener
     */

    public MobNameListener(MobNamePDC mobNamePDC, HealthManagerGUI healthManagerGUI) {
        this.mobNamePDC = mobNamePDC;
        this.healthManagerGUI = healthManagerGUI;
        scanLoadedEntities();
    }

    /**
     * When a chunk loads check immediately if there are living entities with a custom name
     */
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities())
            if(entity instanceof LivingEntity livingEntity)
                mobNamePDC.backUpAndHideName(livingEntity);
    }

    /**
     * When an entity spawns check immediately if it has a custom name
     */
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity living) {
            mobNamePDC.backUpAndHideName(living);
        }
    }

    /**
     * Method that prevent the name given by the nametag from visualizing on screen
     *
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onNameTagUse(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!(event.getRightClicked() instanceof LivingEntity living)) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        Player player = event.getPlayer();
        if (item.getType() == Material.NAME_TAG && item.hasItemMeta()) {
            Component newName = item.getItemMeta().displayName();

            if (newName != null) {
                // when the player tries to use the nametag, cancel the event
                event.setCancelled(true);

                // update the name saved in the pdc
                mobNamePDC.setCustomName(living, newName);

                // if changed, reload the health bar
                healthManagerGUI.removeDisplay(living.getUniqueId());

                // if the player isn't in creative mod, consume the nametag
                if (player.getGameMode() != GameMode.CREATIVE) {
                    item.subtract(1);
                }
            }
        }
    }
}
