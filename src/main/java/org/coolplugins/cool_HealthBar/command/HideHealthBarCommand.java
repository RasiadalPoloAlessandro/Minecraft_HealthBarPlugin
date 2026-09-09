package org.coolplugins.cool_HealthBar.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.coolplugins.cool_HealthBar.HealthBarFilterManager;
import org.coolplugins.cool_HealthBar.gui.HealthBarDisplayManager;
import org.coolplugins.cool_HealthBar.service.HealthBarService;

public class HideHealthBarCommand {

    public static LiteralArgumentBuilder<CommandSourceStack> create(HealthBarService service) {
        return Commands.literal("hide")
                .then(Commands.literal("all")
                        .executes(ctx -> {
                            service.hideAll();

                            ctx.getSource().getSender().sendRichMessage("<yellow>Health bar disabilitate per tutti.");
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(Commands.argument("entity", StringArgumentType.word())
                        .executes(ctx -> {
                            CommandSender sender = ctx.getSource().getSender();
                            String entityName = StringArgumentType.getString(ctx, "entity");

                            try {
                                EntityType type = EntityType.valueOf(entityName.toUpperCase());
                                service.hideEntity(type);
                                service.removeDisplaysByType(type);
                                sender.sendRichMessage("<yellow>Health bar nascosta per: " + type.name().toLowerCase());
                            } catch (IllegalArgumentException e) {
                                sender.sendRichMessage("<red>Tipo entità non valido: " + entityName);
                            }
                            return Command.SINGLE_SUCCESS;
                        })
                );
    }
}