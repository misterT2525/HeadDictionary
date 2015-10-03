package net.mistertgroup.headdictionary.utils;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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

    public static void setDisplayNameAndLore(@NonNull ItemStack item, String name, String... lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        if (lore != null && lore.length > 0) {
            meta.setLore(Arrays.asList(lore));
        } else {
            meta.setLore(null);
        }
        item.setItemMeta(meta);
    }
}
