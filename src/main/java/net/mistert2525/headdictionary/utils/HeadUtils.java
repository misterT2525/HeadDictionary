package net.mistert2525.headdictionary.utils;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.comphenix.protocol.wrappers.nbt.NbtList;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Base64;

/**
 * @author misterT2525
 */
public final class HeadUtils {

    private static final Gson gson = new Gson();

    private HeadUtils() {
        throw new RuntimeException("Non instantiable class");
    }

    /**
     * SkinのURLと、UUIDから対応するヘッドを生成します。
     * <p>
     * 最新版Minecraftでは、セキュリティーの問題でSkinのURLに使用出来るドメインが制限されています。
     * そのため、{@code minecraft.net}又は{@code mojang.com}以外のドメインのSkinは使用出来ません。
     *
     * @param url  SkinのURL
     * @param uuid UUID
     * @return 新しく生成された対応するヘッド
     * @throws NullPointerException 引数にnullが渡された場合に発生します。
     */
    public static ItemStack createHeadItem(@NonNull String url, @NonNull String uuid) throws NullPointerException {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        item = MinecraftReflection.getBukkitItemStack(item);

        NbtCompound nbt = NbtFactory.asCompound(NbtFactory.fromItemTag(item));

        NbtCompound texture = NbtFactory.ofCompound("");
        texture.put("Value", createTextureProperty(url));

        NbtList textures = NbtFactory.ofList("textures", texture);

        NbtCompound properties = NbtFactory.ofCompound("Properties");
        properties.put(textures);

        NbtCompound skullOwner = NbtFactory.ofCompound("SkullOwner");
        skullOwner.put("Id", uuid);
        skullOwner.put(properties);

        nbt.put(skullOwner);
        return item;
    }

    private static String createTextureProperty(String url) {
        JsonObject SKIN = new JsonObject();
        SKIN.addProperty("url", url);
        JsonObject textures = new JsonObject();
        textures.add("SKIN", SKIN);
        JsonObject root = new JsonObject();
        root.add("textures", textures);
        return Base64.getEncoder().encodeToString(gson.toJson(root).getBytes());
    }
}
