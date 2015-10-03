package net.mistertgroup.headdictionary;

import net.mistertgroup.headdictionary.commands.HeadsCommand;
import net.mistertgroup.headdictionary.data.Head;
import net.mistertgroup.headdictionary.data.HeadManager;
import net.mistertgroup.headdictionary.inventory.InventoryManager;
import net.mistertgroup.headdictionary.inventory.PagedInventoryMenu;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.stream.Collectors;

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
        headManager = new HeadManager(this);
        inventoryManager = new InventoryManager(this);

        getCommand("heads").setExecutor(new HeadsCommand(this));
    }

    @Override
    public void onDisable() {
        headManager = null;
    }

    public void openHeadsMenu(Player player, String name, List<Head> heads) {
        PagedInventoryMenu menu = inventoryManager.createPagedInventoryMenu("Head Dictionary - " + name);
        menu.getItems().addAll(heads.stream().<ItemStack>map(headManager::getItem).collect(Collectors.toList()));
        menu.openInventory(player);
    }
}
