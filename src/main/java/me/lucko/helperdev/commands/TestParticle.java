package me.lucko.helperdev.commands;

import me.lucko.helper.utils.Color;
import me.lucko.helperdev.CommandUtil;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestParticle implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = CommandUtil.castPlayer(sender);

        if (!player.isOp()) {
            CommandUtil.msg(sender, "No permission.");
            return true;
        }

        String particle = args.length > 0 ? args[0] : null;

        if (particle != null) {
            try {
                Particle.valueOf(particle.toUpperCase());
            } catch (IllegalArgumentException e) {
                particle = null;
            }
        }

        if (particle == null) {
            List<String> particleNames = Arrays.stream(Particle.values()).map(Particle::name).collect(Collectors.toList());
            particleNames.sort(String.CASE_INSENSITIVE_ORDER);
            particleNames.forEach(name -> player.sendMessage(Color.colorize("&7- &c" + name)));
            return true;
        }

        int times = args.length > 1 ? Integer.parseInt(args[1]) : 1;

        player.sendMessage(Color.colorize("Running effect..."));

        Vector v = player.getLocation().getDirection().clone().setY(0).normalize().multiply(2);
        Location loc = player.getEyeLocation().clone().add(v);

        for (int i = 0; i < times; i++) {
            player.getWorld().spawnParticle(Particle.valueOf(particle.toUpperCase()), loc, 1);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        String lastArg = strings.length == 0 ? "" : strings[0].toUpperCase();
        return Arrays.stream(Particle.values()).map(Particle::name).filter(i -> i.startsWith(lastArg)).collect(Collectors.toList());
    }

}
