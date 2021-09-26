//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.command.Command;

public class HClipCommand extends Command
{
    public HClipCommand() {
        super("HClip", "Allows you to horizontally clip x blocks");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        final double blocksForward = Double.parseDouble(args[1]);
        final Vec3d direction = MathUtil.direction(this.mc.player.rotationYaw);
        final Entity entity = (Entity)(this.mc.player.isRiding() ? this.mc.player.getRidingEntity() : this.mc.player);
        if (entity != null) {
            entity.setPosition(this.mc.player.posX + direction.x * blocksForward, this.mc.player.posY, this.mc.player.posZ + direction.z * blocksForward);
        }
        this.SendToChat(String.format("Teleported you %s blocks forward", blocksForward));
    }
    
    @Override
    public String getHelp() {
        return "Allows you teleport forward x amount of blocks.";
    }
}
