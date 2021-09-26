//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.command.Command;

public class VClipCommand extends Command
{
    public VClipCommand() {
        super("VClip", "Allows you to vclip x blocks");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double distance = Double.parseDouble(args[1]);
        final Entity entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
        if (entity != null) {
            entity.setPosition(this.mc.player.posX, this.mc.player.posY + distance, this.mc.player.posZ);
        }
        this.SendToChat(String.format("Teleported you %s blocks up", distance));
    }
    
    @Override
    public String getHelp() {
        return "Allows you teleport up x amount of blocks.";
    }
}
