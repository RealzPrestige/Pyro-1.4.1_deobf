// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.managers.FontManager;
import java.io.IOException;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.File;
import dev.nuker.pyro.deobfuscated.managers.DirectoryManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class FontCommand extends Command
{
    public FontCommand() {
        super("Font", "Allows you to set the font of the client, this must be an existing TTF in your Pyro folder");
        this.commandChunks.add("<ttfFontName>");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (args.length <= 2) {
            this.SendToChat(String.format("Trying to load \"%s\"", args[1]));
            try {
                if (!new File(DirectoryManager.Get().GetCurrentDirectory() + "/Pyro/Fonts/" + args[1] + ".ttf").exists()) {
                    this.SendToChat(ChatFormatting.RED + "That file doesn't exist in Pyro/Fonts/ directory!");
                    return;
                }
            }
            catch (IOException ex) {}
            FontManager.Get().LoadCustomFont(args[1]);
        }
    }
    
    @Override
    public String getHelp() {
        return "Allows you to load a custom font";
    }
}
