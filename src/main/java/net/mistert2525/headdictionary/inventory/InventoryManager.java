package net.mistert2525.headdictionary.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author misterT2525
 */
public class InventoryManager {

    @Getter
    private final JavaPlugin plugin;
    private final InventoryListener listener;

    public InventoryManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.listener = new InventoryListener(this);

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public PagedInventoryMenu createPagedInventoryMenu(String title) {
        return new PagedInventoryMenu(this, title);
    }
}
