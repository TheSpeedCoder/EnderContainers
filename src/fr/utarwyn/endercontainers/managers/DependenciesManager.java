package fr.utarwyn.endercontainers.managers;

import fr.utarwyn.endercontainers.EnderContainers;
import fr.utarwyn.endercontainers.utils.Config;
import fr.utarwyn.endercontainers.utils.CoreUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;

public class DependenciesManager {
    EnderContainers instance;
    HashMap<String, Boolean> dependencies = new HashMap<String, Boolean>();

    public DependenciesManager() {
        this.instance = EnderContainers.getInstance();

        loadDependencies();
    }

    public void loadDependencies() {
        CoreUtils.log("-----------§8[§bDependencies§8]§7-----------");
        if (Config.factionsSupport) {
            loadDependency("Factions");
        } else {
            this.dependencies.put("Factions", Boolean.valueOf(false));
        }
        CoreUtils.log("------------------------------------");
    }

    public HashMap<String, Boolean> getDependencies() {
        return this.dependencies;
    }

    public boolean isDependencyLoaded(String dependency) {
        if (getDependencies().containsKey(dependency)) {
            return ((Boolean) getDependencies().get(dependency)).booleanValue();
        }
        return false;
    }

    public void loadDependency(String dependency) {
        PluginManager pm = this.instance.getServer().getPluginManager();
        boolean isEnabled = false;
        if ((pm.getPlugin(dependency) != null) && (pm.getPlugin(dependency).isEnabled())) {
            isEnabled = true;
        }
        this.dependencies.put(dependency, Boolean.valueOf(isEnabled));
        if (isEnabled) {
            CoreUtils.log("§e   - " + dependency + "§r: " + ChatColor.GREEN + "[Enabled]");
        } else {
            CoreUtils.log("§e   - " + dependency + "§r: " + ChatColor.RED + "[Disabled]");
        }
    }
}
