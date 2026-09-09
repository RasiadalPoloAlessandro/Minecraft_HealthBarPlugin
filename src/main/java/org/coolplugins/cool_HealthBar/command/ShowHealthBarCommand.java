package org.coolplugins.cool_HealthBar.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.coolplugins.cool_HealthBar.service.HealthBarService;

public class ShowHealthBarCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> create(HealthBarService service) {
        // it's possible to execute the command with multiple parameters
        // it explores all the possibilities
        return Commands.literal("show")
                // Ramo 1: /hb show all
                .then(Commands.literal("all")
                        .executes(ctx -> {
                            service.showAll();
                            CommandSender sender = ctx.getSource().getSender();
                            sender.sendRichMessage("<green>Health bar abilitata per tutte le entità!");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                // Ramo 2: /hb show <entity> (fratello di "all")
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
                                    sender.sendRichMessage("<red>L'entità <yellow>" + entityName + "</yellow> non è un'entità vivente.");
                                    return Command.SINGLE_SUCCESS;
                                }

                                service.showEntity(type);
                                sender.sendRichMessage("<green>Health bar abilitata per: <yellow>" + type.name().toLowerCase());
                            } catch (IllegalArgumentException e) {
                                sender.sendRichMessage("<red>Tipo di entità non valido: <yellow>" + entityName);
                            }

                            return Command.SINGLE_SUCCESS;
                        })
                );

    }
}
