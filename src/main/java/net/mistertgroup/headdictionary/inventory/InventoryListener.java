package net.mistertgroup.headdictionary.inventory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author misterT2525
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class InventoryListener implements Listener {

    private final InventoryManager manager;
    private final List<Player> cooltime = new ArrayList<>();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getInventory().getHolder() instanceof OpeningPagedInventoryMenu)) {
            return;
        }
        OpeningPagedInventoryMenu inventory = ((OpeningPagedInventoryMenu) event.getInventory().getHolder());

        if (event.getSlot() < 45) {
            // TODO: Click Item Event
            return;
        }

        event.setCancelled(true);
        if (cooltime.contains(inventory.getPlayer())) {
            return;
        }
        cooltime.add(inventory.getPlayer());
        new BukkitRunnable() {
            @Override
            public void run() {
                cooltime.remove(inventory.getPlayer());
            }
        }.runTaskLater(manager.getPlugin(), 10);

        if (event.getSlot() == 48 && inventory.getPage() != 0) {
            inventory.setPage(inventory.getPage() - 1);
        }
        if (event.getSlot() == 50 && inventory.getPage() != inventory.getMenu().getLastPage()) {
            inventory.setPage(inventory.getPage() + 1);
        }
    }
}
