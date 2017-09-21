package me.lucko.helperdev.commands;

import lombok.RequiredArgsConstructor;

import me.lucko.helper.Commands;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.composite.CompositeTerminable;
import me.lucko.helper.utils.Color;
import me.lucko.helperdev.CommandUtil;
import me.lucko.helperdev.HelperDevPlugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

@RequiredArgsConstructor
public class ItemCommands implements CompositeTerminable {
    private final HelperDevPlugin plugin;

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {

        // iaddlore
        Commands.create()
                .assertPlayer()
                .assertPermission("helperdev.iaddlore")
                .handler(c -> {
                    ItemStack hand = c.getSender().getItemInHand();
                    if (hand == null || hand.getType() == Material.AIR) {
                        CommandUtil.msg(c.getSender(), "You need to be holding an item.");
                        return;
                    }

                    if (c.getArgs().size() == 0) {
                        CommandUtil.msg(c.getSender(), "/iaddlore <lore>");
                        return;
                    }

                    String name = c.getArgs().stream().collect(Collectors.joining(" "));
                    name = name.replace("{}", " ");

                    hand = ItemStackBuilder.of(hand).lore(name).build();
                    c.getSender().setItemInHand(hand);
                    CommandUtil.msg(c.getSender(), "Added lore: &r" + name);
                })
                .register(plugin, "iaddlore");

        // iinsertlore
        Commands.create()
                .assertPlayer()
                .assertPermission("helperdev.iinsertlore")
                .handler(c -> {
                    ItemStack hand = c.getSender().getItemInHand();
                    if (hand == null || hand.getType() == Material.AIR) {
                        CommandUtil.msg(c.getSender(), "You need to be holding an item.");
                        return;
                    }

                    if (c.getArgs().size() < 2) {
                        CommandUtil.msg(c.getSender(), "/iinsertlore <line> <string>");
                        return;
                    }

                    int line = Integer.parseInt(c.getArg(0));
                    List<String> arguments = new ArrayList<>(c.getArgs());
                    arguments.remove(0);

                    String str = arguments.stream().collect(Collectors.joining(" "));
                    str = str.replace("{}", " ");

                    ItemMeta meta = hand.getItemMeta();
                    List<String> lore = meta.getLore();
                    if (lore == null) {
                        lore = new ArrayList<>();
                    }

                    lore.add(line, Color.colorize(str));

                    meta.setLore(lore);
                    hand.setItemMeta(meta);
                    c.getSender().setItemInHand(hand);
                    CommandUtil.msg(c.getSender(), "Inserted lore on line: &r" + line);
                })
                .register(plugin, "iinsertlore");

        // iremovelore
        Commands.create()
                .assertPlayer()
                .assertPermission("helperdev.iremovelore")
                .handler(c -> {
                    ItemStack hand = c.getSender().getItemInHand();
                    if (hand == null || hand.getType() == Material.AIR) {
                        CommandUtil.msg(c.getSender(), "You need to be holding an item.");
                        return;
                    }

                    if (c.getArgs().size() == 0) {
                        CommandUtil.msg(c.getSender(), "/iremovelore <line>");
                        return;
                    }

                    int line = Integer.parseInt(c.getArg(0));

                    ItemMeta meta = hand.getItemMeta();
                    List<String> lore = meta.getLore();
                    lore.remove(line);

                    meta.setLore(lore);
                    hand.setItemMeta(meta);
                    c.getSender().setItemInHand(hand);
                    CommandUtil.msg(c.getSender(), "Removed lore on line: &r" + line);
                })
                .register(plugin, "iremovelore");

        // isetname
        Commands.create()
                .assertPlayer()
                .assertPermission("helperdev.isetname")
                .handler(c -> {
                    ItemStack hand = c.getSender().getItemInHand();
                    if (hand == null || hand.getType() == Material.AIR) {
                        CommandUtil.msg(c.getSender(), "You need to be holding an item.");
                        return;
                    }

                    if (c.getArgs().size() == 0) {
                        CommandUtil.msg(c.getSender(), "/isetname <name>");
                        return;
                    }

                    String name = c.getArgs().stream().collect(Collectors.joining(" "));
                    name = name.replace("{}", " ");

                    hand = ItemStackBuilder.of(hand).name(name).build();
                    c.getSender().setItemInHand(hand);
                    CommandUtil.msg(c.getSender(), "Set name to: &r" + name);
                })
                .register(plugin, "isetname");

        // isettype
        Commands.create()
                .assertPlayer()
                .assertPermission("helperdev.isettype")
                .handler(c -> {
                    ItemStack hand = c.getSender().getItemInHand();
                    if (hand == null || hand.getType() == Material.AIR) {
                        CommandUtil.msg(c.getSender(), "You need to be holding an item.");
                        return;
                    }

                    if (c.getArgs().size() == 0) {
                        CommandUtil.msg(c.getSender(), "/isettype <type>");
                        return;
                    }

                    String name = c.getArgs().stream().collect(Collectors.joining(" "));
                    Material mat = Material.valueOf(name.toUpperCase().replace(" ", "_"));

                    hand = ItemStackBuilder.of(hand).type(mat).build();
                    c.getSender().setItemInHand(hand);
                    CommandUtil.msg(c.getSender(), "Set name to: &r" + name);
                })
                .register(plugin, "isettype");
    }
}
