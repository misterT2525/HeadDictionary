package net.mistertgroup.headdictionary.commands;

import net.mistertgroup.headdictionary.Head;
import net.mistertgroup.headdictionary.HeadDictionary;
import net.mistertgroup.headdictionary.utils.TabCompleteUtils;

import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author misterT2525
 */
@RequiredArgsConstructor
public class HeadsCommand implements TabExecutor {

    private final HeadDictionary plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "このコマンドはプレイヤーからのみ実行出来ます");
            return false;
        }

        if (args.length == 0) {
            plugin.openHeadsMenu(((Player) sender), "All Heads", plugin.getHeadManager().getAllHeads());
            return true;
        }
        if (args.length == 1) {
            String key = args[0];
            List<Head> heads = plugin.getHeadManager().getHeads(key);
            if (heads == null || heads.size() == 0) {
                sender.sendMessage(ChatColor.RED + "存在しない辞書です");
                return false;
            }
            plugin.openHeadsMenu(((Player) sender), key, heads);

            return true;
        }
        if (args.length >= 2 && args[0].equalsIgnoreCase("search")) {
            String q = Joiner.on(' ').join(args).substring(7);
            List<Head> heads = plugin.getHeadManager().findMatchHeads(q);
            if (heads.size() == 0) {
                sender.sendMessage(ChatColor.RED + "結果が見つかりませんでした");
                return false;
            }
            plugin.openHeadsMenu(((Player) sender), "Search result", heads);

            return true;
        }

        sender.sendMessage(ChatColor.RED + "使い方: /heads");
        sender.sendMessage(ChatColor.RED + "使い方: /heads <辞書名>");
        sender.sendMessage(ChatColor.RED + "使い方: /heads search <キーワード>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.addAll(plugin.getHeadManager().getHeads().keySet());
            list.add("search");
            return TabCompleteUtils.complete(args[0], list);
        }

        return Collections.emptyList();
    }
}
