//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.util.math.Vec3d;
import java.text.DecimalFormat;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoTrapFeet extends Module
{
    public final Value<Float> Distance;
    public final Value<Boolean> rotate;
    public final Value<Integer> BlocksPerTick;
    public final Value<Boolean> Toggles;
    EntityPlayer Target;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoTrapFeet() {
        super("AutoTrapFeet", new String[] { "AutoTrapFeet" }, "Traps enemies with holes at their feet", "NONE", 2415480, ModuleType.COMBAT);
        this.Distance = new Value<Float>("Distance", new String[] { "Dist" }, "Distance to start searching for targets", 5.0f, 0.0f, 10.0f, 1.0f);
        this.rotate = new Value<Boolean>("rotate", new String[] { "rotate" }, "Rotate", true);
        this.BlocksPerTick = new Value<Integer>("BlocksPerTick", new String[] { "BPT" }, "Blocks per tick", 4, 1, 10, 1);
        this.Toggles = new Value<Boolean>("Toggles", new String[] { "Toggles" }, "Toggles off after a trap", false);
        this.Target = null;
        DecimalFormat l_Format;
        Vec3d pos;
        float playerSpeed;
        BlockPos interpPos;
        BlockPos northAbove;
        BlockPos southAbove;
        BlockPos eastAbove;
        BlockPos westAbove;
        BlockPos topBlock;
        BlockPos[] l_Array;
        int slot;
        int lastSlot;
        int l_BlocksPerTick;
        final BlockPos[] array;
        int length;
        int i = 0;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        BlockPos[] l_Test;
        BlockInteractionHelper.PlaceResult l_PlaceResult;
        final BlockPos[] array2;
        int length2;
        int j = 0;
        BlockPos l_Pos2;
        BlockInteractionHelper.ValidResult l_Result2;
        BlockInteractionHelper.PlaceResult l_ResultPlace;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() == MinecraftEvent.Stage.Pre) {
                if (this.Target != null) {
                    if (this.IsCurrentTargetTrapped()) {
                        if (this.Toggles.getValue()) {
                            this.toggle();
                            Pyro.SendMessage(ChatFormatting.LIGHT_PURPLE + "[AutoTrapFeet]: Current target is trapped. Toggling");
                        }
                    }
                    else if (!(!this.HasObsidian())) {
                        l_Format = new DecimalFormat("#.###");
                        pos = new Vec3d((double)Double.valueOf(l_Format.format(this.Target.posX)), this.Target.posY, (double)Double.valueOf(l_Format.format(this.Target.posZ)));
                        playerSpeed = (float)MathUtil.getDistance(pos, this.Target.posX, this.Target.posY, this.Target.posZ);
                        interpPos = new BlockPos(pos.x, pos.y, pos.z);
                        northAbove = interpPos.north().up();
                        southAbove = interpPos.south().up();
                        eastAbove = interpPos.east().up();
                        westAbove = interpPos.west().up();
                        topBlock = interpPos.up().up();
                        l_Array = new BlockPos[] { northAbove, southAbove, eastAbove, westAbove, topBlock };
                        slot = this.findStackHotbar(Blocks.OBSIDIAN);
                        if ((this.hasStack(Blocks.OBSIDIAN) || slot != -1) && this.mc.player.onGround && playerSpeed <= 0.005f) {
                            lastSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = slot;
                            this.mc.playerController.updateController();
                            l_BlocksPerTick = this.BlocksPerTick.getValue();
                            for (length = array.length; i < length; ++i) {
                                l_Pos = array[i];
                                l_Result = BlockInteractionHelper.valid(l_Pos);
                                if (l_Result != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                                    if (l_Result == BlockInteractionHelper.ValidResult.NoNeighbors) {
                                        l_Test = new BlockPos[] { l_Pos.north(), l_Pos.south(), l_Pos.east(), l_Pos.west(), l_Pos.up(), l_Pos.down() };
                                        l_PlaceResult = BlockInteractionHelper.PlaceResult.CantPlace;
                                        length2 = array2.length;
                                        while (j < length2) {
                                            l_Pos2 = array2[j];
                                            l_Result2 = BlockInteractionHelper.valid(l_Pos2);
                                            if (l_Result2 != BlockInteractionHelper.ValidResult.NoNeighbors && l_Result2 != BlockInteractionHelper.ValidResult.NoEntityCollision) {
                                                l_PlaceResult = BlockInteractionHelper.place(l_Pos2, this.Distance.getValue(), this.rotate.getValue(), false);
                                                break;
                                            }
                                            else {
                                                ++j;
                                            }
                                        }
                                        if (l_PlaceResult != BlockInteractionHelper.PlaceResult.CantPlace) {
                                            break;
                                        }
                                    }
                                    l_ResultPlace = BlockInteractionHelper.place(l_Pos, this.Distance.getValue(), this.rotate.getValue(), false);
                                    if (l_ResultPlace == BlockInteractionHelper.PlaceResult.Placed && --l_BlocksPerTick <= 0) {
                                        break;
                                    }
                                }
                            }
                            if (!this.slotEqualsBlock(lastSlot, Blocks.OBSIDIAN)) {
                                this.mc.player.inventory.currentItem = lastSlot;
                            }
                            this.mc.playerController.updateController();
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.world == null || this.mc.player == null) {
            this.toggle();
            return;
        }
        float l_LastDist = 100.0f;
        for (final EntityPlayer l_Player : this.mc.world.playerEntities) {
            if (l_Player != null) {
                if (l_Player == this.mc.player) {
                    continue;
                }
                if (FriendManager.Get().IsFriend((Entity)l_Player)) {
                    continue;
                }
                final float l_Dist = l_Player.getDistance((Entity)this.mc.player);
                if (l_Dist > this.Distance.getValue()) {
                    continue;
                }
                if (l_LastDist <= l_Dist) {
                    continue;
                }
                this.Target = l_Player;
                l_LastDist = l_Dist;
            }
        }
        if (this.Target != null) {
            Pyro.SendMessage("[AutoTrapFeet]: Found target " + this.Target.getName());
        }
    }
    
    public boolean IsCurrentTargetTrapped() {
        if (this.Target == null) {
            return true;
        }
        final DecimalFormat l_Format = new DecimalFormat("#.###");
        final Vec3d l_PlayerPos = new Vec3d((double)Double.valueOf(l_Format.format(this.Target.posX)), this.Target.posY, (double)Double.valueOf(l_Format.format(this.Target.posZ)));
        final BlockPos l_InterPos = new BlockPos(l_PlayerPos.x, l_PlayerPos.y, l_PlayerPos.z);
        final BlockPos l_North = l_InterPos.north().up();
        final BlockPos l_South = l_InterPos.south().up();
        final BlockPos l_East = l_InterPos.east().up();
        final BlockPos l_West = l_InterPos.west().up();
        final BlockPos l_Top = l_InterPos.up().up();
        final BlockPos[] array;
        final BlockPos[] l_Array = array = new BlockPos[] { l_North, l_South, l_East, l_West, l_Top };
        for (final BlockPos l_Pos : array) {
            if (BlockInteractionHelper.valid(l_Pos) != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    public boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    public int findStackHotbar(final Block type) {
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
    
    public boolean HasObsidian() {
        return this.findStackHotbar(Blocks.OBSIDIAN) != -1;
    }
}
