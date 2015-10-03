package net.mistertgroup.headdictionary.inventory;

import net.mistertgroup.headdictionary.utils.ItemUtils;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author misterT2525
 */
@Getter
public class OpeningPagedInventoryMenu implements InventoryHolder {


    private final PagedInventoryMenu menu;
    private final Player player;
    private int page;
    private Inventory inventory;

    /*package*/ OpeningPagedInventoryMenu(Player player, PagedInventoryMenu menu) {
        this.player = player;
        this.menu = menu;

        inventory = Bukkit.createInventory(this, 54, menu.getTitle());
        setPage(0);

        player.openInventory(inventory);
    }

    public void setPage(int page) {
        List<ItemStack> items = menu.getItems();
        int firstItem = 45 * page;
        int lastItem = firstItem + 45;
        if (lastItem > items.size()) {
            lastItem = items.size();
        }

        inventory.clear();

        int slot = 0;
        for (int i = firstItem; i < lastItem; i++) {
            inventory.setItem(slot++, items.get(i));
        }

        if (page != 0) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemUtils.setDisplayName(item, "<= Previous page (" + (page) + " page)");
            inventory.setItem(48, item);
        } else {
            inventory.setItem(48, null);
        }

        if (page != menu.getLastPage()) {
            ItemStack item = new ItemStack(Material.ARROW);
            ItemUtils.setDisplayName(item, "Next page (" + (page + 2) + " page) ==>");
            inventory.setItem(50, item);
        } else {
            inventory.setItem(50, null);
        }

        this.page = page;
    }

}
