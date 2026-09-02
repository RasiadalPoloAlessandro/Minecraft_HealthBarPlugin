package org.coolplugins.cool_HealthBar.pdc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

/**
 * Class responsible for saving the mob's custom name and hide it if needed
 * */

public class MobNamePDC {

    private final NamespacedKey namespacedKey;
    //Using a serializer is important because of the component beside the text has also other properties we need to preserve
    private final JSONComponentSerializer serializer = JSONComponentSerializer.json();

    public MobNamePDC(Plugin plugin) {
        this.namespacedKey = new NamespacedKey(plugin, "original_custom_name");
    }

    /**
    * Hide a mob's custom name and save it inside the pdc if it's present
    * */
    public void backUpAndHideName(LivingEntity livingEntity) {

        // get data container
        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
        // get entity's custom name
        Component customName = livingEntity.customName();

        // if it's the first time we hide the custom name
        if (customName != null && !pdc.has(namespacedKey, PersistentDataType.STRING)) {
            String jsonName = serializer.serialize(customName);
            pdc.set(namespacedKey, PersistentDataType.STRING, jsonName);
            livingEntity.customName(null);
        }

        livingEntity.customName(null);
        livingEntity.setCustomNameVisible(false);
    }

    /**
     * Restore and show the original name of the mob
     * */
    public void restoreAndShowName(LivingEntity livingEntity) {

        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();

        if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
            String jsonName = pdc.get(namespacedKey, PersistentDataType.STRING);
            if (jsonName != null) {
                livingEntity.customName(serializer.deserialize(jsonName));
            }
            pdc.remove(namespacedKey);
        }
    }

    /**
     * return the mob's name as a Component
     */
    public Component getEffectiveName(LivingEntity livingEntity) {
        PersistentDataContainer pdc = livingEntity.getPersistentDataContainer();
        if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
            String jsonName = pdc.get(namespacedKey, PersistentDataType.STRING);
            if (jsonName != null) {
                return serializer.deserialize(jsonName);
            }
        }
        if (livingEntity.customName() != null) {
            return livingEntity.customName();
        }
        return Component.translatable(livingEntity.getType().translationKey());
    }

    public void setCustomName(LivingEntity entity, Component name) {
        PersistentDataContainer pdc = entity.getPersistentDataContainer();
        String jsonName = serializer.serialize(name);

        // Salva/sovrascrive nel PDC
        pdc.set(namespacedKey, PersistentDataType.STRING, jsonName);

        // Assicurati che il mob Vanilla non abbia il nome nativo visibile
        entity.customName(null);
        entity.setCustomNameVisible(false);
    }
}
