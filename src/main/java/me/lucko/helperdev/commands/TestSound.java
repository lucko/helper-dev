package me.lucko.helperdev.commands;

import me.lucko.helper.utils.Color;
import me.lucko.helperdev.CommandUtil;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestSound implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = CommandUtil.castPlayer(sender);

        if (!player.isOp()) {
            CommandUtil.msg(sender, "No permission.");
            return true;
        }

        String sound = args.length == 1 ? args[0] : null;

        if (sound != null) {
            try {
                Sound.valueOf(sound.toUpperCase());
            } catch (IllegalArgumentException e) {
                sound = null;
            }
        }

        if (sound == null) {
            List<String> soundNames = Arrays.stream(Sound.values()).map(Sound::name).collect(Collectors.toList());
            soundNames.sort(String.CASE_INSENSITIVE_ORDER);
            soundNames.forEach(name -> player.sendMessage(Color.colorize("&7- &c" + name)));
            return true;
        }

        player.sendMessage(Color.colorize("Running effect..."));
        player.getWorld().playSound(player.getLocation(), Sound.valueOf(sound.toUpperCase()), 1.0f, 1.0f);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String lastArg = strings.length == 0 ? "" : strings[0].toUpperCase();
        return Arrays.stream(Sound.values()).map(Sound::name).filter(i -> i.startsWith(lastArg)).collect(Collectors.toList());
    }
}
