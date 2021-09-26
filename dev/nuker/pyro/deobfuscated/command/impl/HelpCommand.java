// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import java.util.List;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.managers.CommandManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class HelpCommand extends Command
{
    public HelpCommand() {
        super("Help", "Gives you help for commands");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat(this.getHelp());
            return;
        }
        final Command command = CommandManager.Get().GetCommandLike(args[1]);
        if (command == null) {
            this.SendToChat(String.format("Couldn't find any command named like %s", args[1]));
        }
        else {
            this.SendToChat(command.getHelp());
        }
    }
    
    @Override
    public String getHelp() {
        final List<Command> commandList = CommandManager.Get().GetCommands();
        final StringBuilder commandString = new StringBuilder("Available commands: (" + commandList.size() + ")" + ChatFormatting.WHITE + " [");
        for (int i = 0; i < commandList.size(); ++i) {
            final Command command = commandList.get(i);
            if (i == commandList.size() - 1) {
                commandString.append(command.getName()).append("]");
            }
            else {
                commandString.append(command.getName()).append(", ");
            }
        }
        return commandString.toString();
    }
}
