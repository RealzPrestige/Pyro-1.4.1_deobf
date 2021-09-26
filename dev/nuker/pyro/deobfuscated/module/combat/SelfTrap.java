//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class SelfTrap extends Module
{
    public final Value<Boolean> HoleCheck;
    public final Value<Boolean> disable;
    private BlockPos TrapPos;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public SelfTrap() {
        super("SelfTrap", new String[] { "SelfTrapHole" }, "Automatically places an obsidian over your head, and optionally if you're in a hole", "NONE", 5448923, ModuleType.COMBAT);
        this.HoleCheck = new Value<Boolean>("HoleCheck", new String[] { "HC" }, "Only functions if you're in a hole", true);
        this.disable = new Value<Boolean>("Toggles", new String[] { "Toggles", "Disables" }, "Will toggle off after a place", false);
        this.TrapPos = null;
        Vec3d pos;
        int slot;
        int lastSlot;
        BlockInteractionHelper.ValidResult l_Result;
        BlockPos[] array;
        BlockPos[] l_Test;
        int length;
        int i = 0;
        BlockPos l_Pos2;
        BlockInteractionHelper.ValidResult l_Result2;
        BlockInteractionHelper.PlaceResult l_Result3;
        float[] rotations;
        BlockInteractionHelper.PlaceResult l_Result4;
        float[] rotations2;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() == MinecraftEvent.Stage.Pre) {
                if (!this.HoleCheck.getValue() || PlayerUtil.IsPlayerInHole()) {
                    pos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                    this.TrapPos = new BlockPos(pos.x, pos.y, pos.z).up().up();
                    if (this.IsSelfTrapped()) {
                        if (this.disable.getValue()) {
                            this.toggle();
                        }
                    }
                    else {
                        slot = this.findStackHotbar(Blocks.OBSIDIAN);
                        if ((this.hasStack(Blocks.OBSIDIAN) || slot != -1) && this.mc.player.onGround) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            l_Result = BlockInteractionHelper.valid(this.TrapPos);
                            if (l_Result == BlockInteractionHelper.ValidResult.AlreadyBlockThere && !this.mc.world.getBlockState(this.TrapPos).getMaterial().isReplaceable()) {
                                this.Finish(lastSlot);
                            }
                            else if (l_Result == BlockInteractionHelper.ValidResult.NoNeighbors) {
                                l_Test = (array = new BlockPos[] { this.TrapPos.north(), this.TrapPos.south(), this.TrapPos.east(), this.TrapPos.west(), this.TrapPos.up(), this.TrapPos.down().west() });
                                for (length = array.length; i < length; ++i) {
                                    l_Pos2 = array[i];
                                    l_Result2 = BlockInteractionHelper.valid(l_Pos2);
                                    if (l_Result2 != BlockInteractionHelper.ValidResult.NoNeighbors) {
                                        if (l_Result2 != BlockInteractionHelper.ValidResult.NoEntityCollision) {
                                            l_Result3 = BlockInteractionHelper.place(l_Pos2, 5.0f, false, false);
                                            if (l_Result3 == BlockInteractionHelper.PlaceResult.Placed) {
                                                p_Event.cancel();
                                                rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_Pos2.getX(), (double)l_Pos2.getY(), (double)l_Pos2.getZ()));
                                                p_Event.setPitch(rotations[1]);
                                                p_Event.setYaw(rotations[0]);
                                                this.Finish(lastSlot);
                                                return;
                                            }
                                        }
                                    }
                                }
                                this.Finish(lastSlot);
                            }
                            else {
                                l_Result4 = BlockInteractionHelper.place(this.TrapPos, 5.0f, false, false);
                                if (l_Result4 == BlockInteractionHelper.PlaceResult.Placed) {
                                    p_Event.cancel();
                                    rotations2 = BlockInteractionHelper.getLegitRotations(new Vec3d((double)this.TrapPos.getX(), (double)this.TrapPos.getY(), (double)this.TrapPos.getZ()));
                                    p_Event.setPitch(rotations2[1]);
                                    p_Event.setYaw(rotations2[0]);
                                }
                                this.Finish(lastSlot);
                            }
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private void Finish(final int p_LastSlot) {
        if (!this.slotEqualsBlock(p_LastSlot, Blocks.OBSIDIAN)) {
            this.mc.player.inventory.currentItem = p_LastSlot;
        }
        this.mc.playerController.updateController();
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private int findStackHotbar(final Block type) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                if (block.getBlock() == type) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public boolean IsSelfTrapped() {
        if (this.TrapPos == null) {
            return false;
        }
        final IBlockState l_State = this.mc.world.getBlockState(this.TrapPos);
        return l_State.getBlock() != Blocks.AIR && l_State.getBlock() != Blocks.WATER && l_State.getBlock() != Blocks.LAVA;
    }
}
