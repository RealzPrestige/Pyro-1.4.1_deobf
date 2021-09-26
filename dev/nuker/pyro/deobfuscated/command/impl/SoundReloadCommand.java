//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.command.Command;

public class SoundReloadCommand extends Command
{
    public SoundReloadCommand() {
        super("SoundReload", "Reloads the sound system");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        this.mc.getSoundHandler().sndManager.reloadSoundSystem();
        this.SendToChat(ChatFormatting.GREEN + "Reloaded the SoundSystem!");
    }
    
    @Override
    public String getHelp() {
        return "Reloads the sound manager sound system";
    }
}
