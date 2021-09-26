//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.block.state.IBlockState;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.util.Hole;
import dev.nuker.pyro.deobfuscated.util.render.ESPUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import baritone.api.BaritoneAPI;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class HoleFinder extends Module
{
    public final Value<Integer> Radius;
    public final Value<Boolean> Blink;
    public final Value<Boolean> ToggleSpeed;
    private boolean wasActive;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    
    public HoleFinder() {
        super("HoleFinder", new String[] { "HoleBlink" }, "Blinks and uses baritone goto to go to a hole", "NONE", -1, ModuleType.MOVEMENT);
        this.Radius = new Value<Integer>("Radius", new String[] { "Radius", "Range", "Distance" }, "Radius in blocks to scan for holes.", 8, 0, 32, 1);
        this.Blink = new Value<Boolean>("Blink", new String[] { "Blink", "UseBlink" }, "Radius in blocks to scan for holes", true);
        this.ToggleSpeed = new Value<Boolean>("ToggleSpeed", new String[] { "ToggleSpeed", "ToggleStrafe" }, "Toggles speed if its enabled.", true);
        this.wasActive = false;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().isActive()) {
                if (this.wasActive) {
                    this.toggle();
                    this.SendMessage("Finished!");
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        final Vec3i playerPos = new Vec3i(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        BlockPos bestHole = null;
        double dist = 100.0;
        for (int x = playerPos.getX() - this.Radius.getValue(); x < playerPos.getX() + this.Radius.getValue(); ++x) {
            for (int z = playerPos.getZ() - this.Radius.getValue(); z < playerPos.getZ() + this.Radius.getValue(); ++z) {
                for (int y = playerPos.getY() + this.Radius.getValue(); y > playerPos.getY() - this.Radius.getValue(); --y) {
                    final BlockPos blockPos = new BlockPos(x, y, z);
                    final double holeDist = this.mc.player.getDistanceSq(blockPos);
                    if (holeDist > 1.0) {
                        final IBlockState blockState = this.mc.world.getBlockState(blockPos);
                        Hole.HoleTypes l_Type = ESPUtil.isBlockValid(blockState, blockPos);
                        if (l_Type != Hole.HoleTypes.None) {
                            final IBlockState downBlockState = this.mc.world.getBlockState(blockPos.down());
                            if (downBlockState.getBlock() == Blocks.AIR) {
                                l_Type = ESPUtil.isBlockValid(downBlockState, blockPos);
                                if (l_Type != Hole.HoleTypes.None && holeDist < dist) {
                                    dist = holeDist;
                                    bestHole = blockPos;
                                }
                            }
                            else if (holeDist < dist) {
                                dist = holeDist;
                                bestHole = blockPos;
                            }
                        }
                    }
                }
            }
        }
        if (bestHole == null) {
            this.SendMessage("Couldn't find a proper hole");
            this.toggle();
            return;
        }
        if (!PyroStatic.BLINK.isEnabled() && this.Blink.getValue()) {
            PyroStatic.BLINK.toggle();
        }
        if (PyroStatic.SPEED.isEnabled() && this.ToggleSpeed.getValue()) {
            PyroStatic.SPEED.toggle();
        }
        this.wasActive = true;
        BaritoneAPI.getSettings().allowSprint.value = true;
        BaritoneAPI.getSettings().allowBreak.value = false;
        BaritoneAPI.getSettings().primaryTimeoutMS.value = 2000L;
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalBlock(bestHole));
        this.SendMessage("Found a hole at " + bestHole.toString());
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.wasActive = false;
        if (PyroStatic.BLINK.isEnabled()) {
            PyroStatic.BLINK.toggle();
        }
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoal((Goal)null);
    }
}
