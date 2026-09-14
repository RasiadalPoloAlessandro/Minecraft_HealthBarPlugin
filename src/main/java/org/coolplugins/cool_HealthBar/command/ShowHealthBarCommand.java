package org.coolplugins.cool_HealthBar.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.coolplugins.cool_HealthBar.service.HealthBarService;

import static org.coolplugins.cool_HealthBar.command.MessageColorPalette.*;

/**
 * Class that when requested through a certain command, show the health bar of one or more entity types
 */
public class ShowHealthBarCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> create(HealthBarService service) {
        // it's possible to execute the command with multiple parameters
        // it explores all the possibilities
        return Commands.literal("show")
                // first case, the player asks to hide the health bar of all entities
                .then(Commands.literal("all")
                        .executes(ctx -> {
                            service.showAll();
                            CommandSender sender = ctx.getSource().getSender();
                            sender.sendMessage(Component.translatable("healthbar.command.show.all").color(successMessage));
                            return Command.SINGLE_SUCCESS;
                        })
                )
                // second case, the player wants to hide only a certain type
                .then(Commands.argument("entity", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            String remaining = builder.getRemaining().toLowerCase();
                            for (EntityType type : EntityType.values()) {
                                if (type.isAlive() && type.name().toLowerCase().startsWith(remaining)) {
                                    builder.suggest(type.name().toLowerCase());
                                }
                            }
                            return builder.buildFuture();
                        })
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            String entityName = StringArgumentType.getString(ctx, "entity");

                            try {
                                EntityType type = EntityType.valueOf(entityName.toUpperCase());
                                if (!type.isAlive()) {
                                    sender.sendMessage(Component.translatable("healthbar.error.not_living").color(errorMessage));
                                    return Command.SINGLE_SUCCESS;
                                }

                                service.showEntity(type);
                                sender.sendMessage(Component.translatable("healthbar.command.show.single", Component.text(type.name().toLowerCase()).color(successMessage)));
                            } catch (IllegalArgumentException e) {
                                sender.sendMessage(Component.translatable("healthbar.error.failedCommand").color(errorMessage));
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                );

    }
}
