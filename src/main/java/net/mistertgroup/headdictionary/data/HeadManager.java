package net.mistertgroup.headdictionary.data;

import net.mistertgroup.headdictionary.HeadDictionary;
import net.mistertgroup.headdictionary.utils.HeadUtils;
import net.mistertgroup.headdictionary.utils.ItemUtils;
import net.mistertgroup.headdictionary.utils.ResourcesUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author misterT2525
 */
public class HeadManager {

    private static final Gson gson = new Gson();
    private static final Type LIST_TYPE = new TypeToken<ArrayList<Head>>() {
    }.getType();

    private final HeadDictionary plugin;
    @Getter
    private final Map<String, List<Head>> heads = new HashMap<>();
    private final Map<Head, ItemStack> items = new WeakHashMap<>();

    public HeadManager(HeadDictionary plugin) {
        this.plugin = plugin;
        loadHeads();
    }

    public void loadHeads() {
        File headsDirectory = new File(plugin.getDataFolder(), "heads");
        if (!headsDirectory.exists()) {
            try {
                ResourcesUtils.copyDirectory("/heads/", headsDirectory);
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Can not copy default heads data", e);
                return;
            }
        }
        if (!headsDirectory.isDirectory()) {
            return;
        }

        for (File file : headsDirectory.listFiles(((dir, name) -> name.endsWith(".json")))) {
            try {
                String key = file.getName().substring(0, file.getName().length() - 5).toLowerCase();
                List<Head> headList = gson.fromJson(new FileReader(file), LIST_TYPE);
                heads.put(key, headList);
            } catch (FileNotFoundException e) {
                // 発生しないはず？
            }
        }
    }

    public List<Head> getAllHeads() {
        List<Head> headList = new ArrayList<>();
        heads.forEach(((key, heads) -> headList.addAll(heads)));
        return Collections.unmodifiableList(headList);
    }

    public List<Head> getHeads(@NonNull String key) {
        List<Head> headList = heads.get(key.toLowerCase());
        return headList != null ? Collections.unmodifiableList(headList) : null;
    }

    public List<Head> findMatchHeads(@NonNull String q) {
        List<Head> headList = new ArrayList<>();
        String lower = q.toLowerCase();

        // TODO: AND検索とか？
        headList.addAll(getAllHeads().stream()
                .filter(head -> head.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList()));

        return Collections.unmodifiableList(headList);
    }

    public ItemStack getItem(@NonNull Head head) {
        ItemStack item = items.get(head);
        if (item == null) {
            item = HeadUtils.createHeadItem(head.getUrl(), head.getUuid());
            ItemUtils.setDisplayName(item, head.getName());
            items.put(head, item);
        }
        return item;
    }
}
