package me.lucko.helperdev;

import lombok.experimental.UtilityClass;

import me.lucko.helper.utils.Color;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@UtilityClass
public class CommandUtil {

    public static Player castPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender);
        } else {
            throw new IllegalStateException("Command not usable from console");
        }
    }

    public static void msg(CommandSender sender, String msg) {
        sender.sendMessage(Color.colorize("&c" + msg));
    }

}
