package me.lucko.helperdev;

import me.lucko.helper.Commands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.utils.Color;
import me.lucko.helperdev.commands.ItemCommands;
import me.lucko.helperdev.commands.TestEffect;
import me.lucko.helperdev.commands.TestParticle;
import me.lucko.helperdev.commands.TestSound;

import java.util.stream.Collectors;

@Plugin(
        name = "helper-dev",
        description = "Misc utilities to aid with Minecraft in-game content design",
        authors = "Luck",
        hardDepends = "helper"
)
public class HelperDevPlugin extends ExtendedJavaPlugin {

    @Override
    protected void enable() {

        // item commands
        bindComposite(new ItemCommands(this));

        // echo command
        Commands.create()
                .assertPermission("helperdev.echo")
                .handler(c -> {
                    c.getSender().sendMessage(Color.colorize(c.getArgs().stream().collect(Collectors.joining(" ")).replace("{}", " ")));
                })
                .register(this, "echo");

        // testing commands
        registerCommand(new TestSound(), "testsound");
        registerCommand(new TestEffect(), "testeffect");
        registerCommand(new TestParticle(), "testparticle");

    }

}
