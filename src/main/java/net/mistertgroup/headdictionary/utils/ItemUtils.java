package net.mistertgroup.headdictionary.utils;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author misterT2525
 */
public final class ItemUtils {

    private ItemUtils() {
        throw new RuntimeException("Non instantiable class");
    }

    public static void setDisplayName(@NonNull ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }
}
