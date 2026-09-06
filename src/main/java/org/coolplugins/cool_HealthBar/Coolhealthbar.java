package org.coolplugins.cool_HealthBar;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.coolplugins.cool_HealthBar.gui.HealthBarDisplayManager;
import org.coolplugins.cool_HealthBar.gui.healthformatter.DefaultFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.FriendlyMobFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.HostileMobFormatter;
import org.coolplugins.cool_HealthBar.gui.healthformatter.PlayerFormatter;
import org.coolplugins.cool_HealthBar.listener.*;
import org.coolplugins.cool_HealthBar.pdc.MobNamePDC;
import org.coolplugins.cool_HealthBar.registry.HealthBarRegistry;

/*
Plugin that adds a simple health bar
* */
public final class Coolhealthbar extends JavaPlugin {

    private HealthManager healthManager;
    private HealthBarDisplayManager healthBarDisplayManager;
    private HealthBarRegistry registry;
    private MobNamePDC mobNamePDC;

    @Override
    public void onEnable() {
        // Plugin startup logic
        //this.healthManager = new HealthManager(getLogger());
        this.mobNamePDC = new MobNamePDC(this);
        this.healthBarDisplayManager = new HealthBarDisplayManager();
        setUpFormatters();
        setUpListeners();

        getLogger().info("CoolHealthBar e' stato avviato correttamente!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //Remove all healthbars
        healthBarDisplayManager.clearAll();
    }

    private void setUpListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerConnectionListener(this, this.healthBarDisplayManager, registry), this);
        pm.registerEvents(new MobDamageListener(this.healthBarDisplayManager), this);
        pm.registerEvents(new MobDeathListener(this.healthBarDisplayManager), this);
        pm.registerEvents(new WorldEventListener(this.healthBarDisplayManager), this);
        pm.registerEvents(new MobNameListener(this.mobNamePDC, this.healthBarDisplayManager), this);
    }

    private void setUpFormatters() {
        this.registry = new HealthBarRegistry(new DefaultFormatter(this.mobNamePDC));
        this.registry.register(new PlayerFormatter(this.mobNamePDC));
        this.registry.register(new HostileMobFormatter(this.mobNamePDC));
        this.registry.register(new FriendlyMobFormatter(this.mobNamePDC));
    }
}
