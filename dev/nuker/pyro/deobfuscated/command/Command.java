// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import java.util.List;
import net.minecraft.client.Minecraft;

public abstract class Command
{
    private final String name;
    private final String description;
    protected final Minecraft mc;
    protected final List<String> commandChunks;
    
    public Command(final String name, final String description) {
        this.mc = Wrapper.GetMC();
        this.commandChunks = new ArrayList<String>();
        this.name = name;
        this.description = description;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public abstract void processCommand(final String p0, final String[] p1);
    
    protected void SendToChat(final String desc) {
        Pyro.SendMessage(ChatFormatting.LIGHT_PURPLE + "[" + this.getName() + "]: " + ChatFormatting.YELLOW + desc);
    }
    
    public List<String> getChunks() {
        return this.commandChunks;
    }
    
    public String getHelp() {
        return this.description;
    }
}
