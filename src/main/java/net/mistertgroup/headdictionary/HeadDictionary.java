package net.mistertgroup.headdictionary;

import net.mistertgroup.headdictionary.commands.HeadsCommand;
import net.mistertgroup.headdictionary.data.Head;
import net.mistertgroup.headdictionary.data.HeadManager;
import net.mistertgroup.headdictionary.inventory.InventoryManager;
import net.mistertgroup.headdictionary.inventory.PagedInventoryMenu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * @author misterT2525
 */
public class HeadDictionary extends JavaPlugin {

    @Getter
    private HeadManager headManager;
    @Getter
    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            // Ignore
        }

        if (!getConfig().getBoolean("no_update_check", false)) {
            new VersionChecker(this, "misterT2525/HeadDictionary", "master", result -> {
                if (result instanceof VersionChecker.Version) {
                    int behind = ((VersionChecker.Version) result).getBehind();
                    if (behind > 0) {
                        getLogger().warning("Your HeadDictionary is " + behind + " version(s) behind.");
                    }
                } else if (result instanceof VersionChecker.ExceptionResult) {
                    getLogger().log(Level.WARNING, "Failed to check version", ((VersionChecker.ExceptionResult) result).getException());
                }
            });
        }

        headManager = new HeadManager(this);
        inventoryManager = new InventoryManager(this);

        getCommand("heads").setExecutor(new HeadsCommand(this));
    }

    @Override
    public void onDisable() {
        headManager = null;
        inventoryManager = null;
    }

    public void openHeadsMenu(Player player, String name, List<Head> heads) {
        PagedInventoryMenu menu = inventoryManager.createPagedInventoryMenu(name);
        heads.stream().map(headManager::getItem).forEach(menu.getItems()::add);
        menu.openInventory(player);
    }
}
