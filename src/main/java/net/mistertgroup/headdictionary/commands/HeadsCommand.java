package net.mistertgroup.headdictionary.commands;

import net.mistertgroup.headdictionary.HeadDictionary;
import net.mistertgroup.headdictionary.data.Head;
import net.mistertgroup.headdictionary.utils.TabCompleteUtils;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

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

        if (args.length == 0 || args.length == 1) {
            String key = args.length == 1 ? args[0] : "default";
            List<Head> heads = plugin.getHeadManager().getHeads(key);
            if (heads == null) {
                sender.sendMessage(ChatColor.RED + "存在しない辞書です");
                return false;
            }
            plugin.openHeadsMenu(((Player) sender), key, heads);

            return true;
        }

        sender.sendMessage(ChatColor.RED + "使い方: /heads <辞書名>");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            return TabCompleteUtils.complete(args[0], plugin.getHeadManager().getHeads().keySet());
        }
        return null;
    }
}
