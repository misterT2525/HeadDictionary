package net.mistertgroup.headdictionary;

import net.mistertgroup.headdictionary.commands.HeadsCommand;
import net.mistertgroup.headdictionary.data.Head;
import net.mistertgroup.headdictionary.data.HeadManager;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author misterT2525
 */
public class HeadDictionary extends JavaPlugin {

    @Getter
    private HeadManager manager;

    @Override
    public void onEnable() {
        manager = new HeadManager(this);

        getCommand("heads").setExecutor(new HeadsCommand(this));
    }

    @Override
    public void onDisable() {
        manager = null;
    }

    public void openHeadsMenu(Player player, String name, List<Head> heads) {
        int size = heads.size();
        size += 9 - (size % 9);// インベントリのサイズが9単位じゃないといけないため
        Inventory inventory = Bukkit.createInventory(null, size, "Head Dictionary - " + name);

        for (int i = 0; i < heads.size(); i++) {
            inventory.setItem(i, manager.getItem(heads.get(i)));
        }

        player.openInventory(inventory);
    }
}
