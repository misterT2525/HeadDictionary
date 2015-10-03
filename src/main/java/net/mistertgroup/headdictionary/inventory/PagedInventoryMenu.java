package net.mistertgroup.headdictionary.inventory;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author misterT2525
 */
@Getter
public class PagedInventoryMenu {

    private final InventoryManager manager;
    private final String title;

    private final List<ItemStack> items = new ArrayList<>();

    /*package*/ PagedInventoryMenu(@NonNull InventoryManager manager, @NonNull String title) {
        this.manager = manager;
        this.title = title;
    }

    public void openInventory(Player player) {
        new OpeningPagedInventoryMenu(player, this);
    }

    public int getLastPage() {
        return items.size() / 45;
    }
}
