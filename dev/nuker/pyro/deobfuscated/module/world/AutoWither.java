//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.item.ItemSkull;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.RayTraceResult;
import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.block.Block;
import dev.nuker.pyro.deobfuscated.util.Pair;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoWither extends Module
{
    private BlockPos WitherFeetBlock;
    private ArrayList<Pair<BlockPos, Block>> Positions;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnMotionUpdate;
    
    public AutoWither() {
        super("AutoWither", new String[] { "" }, "Automatically places a wither at the location of your selection if available", "NONE", -1, ModuleType.WORLD);
        this.WitherFeetBlock = null;
        this.Positions = new ArrayList<Pair<BlockPos, Block>>();
        Iterator<Pair<BlockPos, Block>> l_Itr;
        Pair<BlockPos, Block> l_Pos;
        boolean l_Placed;
        int l_Slot;
        BlockInteractionHelper.PlaceResult l_Place;
        double[] l_Pos2;
        this.OnMotionUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (this.Positions.isEmpty()) {
                this.SendMessage("Positions is empty");
                this.toggle();
            }
            else {
                l_Itr = this.Positions.iterator();
                l_Pos = null;
                l_Placed = false;
                while (l_Itr.hasNext()) {
                    l_Pos = l_Itr.next();
                    l_Slot = -1;
                    if (l_Pos.getSecond() == Blocks.SOUL_SAND) {
                        l_Slot = this.GetSoulsandInHotbar();
                    }
                    else if (l_Pos.getSecond() == Blocks.SKULL) {
                        l_Slot = this.GetSkullInHotbar();
                    }
                    if (l_Slot != -1 && this.mc.player.inventory.currentItem != l_Slot) {
                        this.mc.player.inventory.currentItem = l_Slot;
                        this.mc.playerController.updateController();
                        return;
                    }
                    else {
                        l_Place = BlockInteractionHelper.place(l_Pos.getFirst(), 5.0f, false, false);
                        if (l_Place != BlockInteractionHelper.PlaceResult.Placed) {
                            continue;
                        }
                        else {
                            l_Pos2 = EntityUtil.calculateLookAt(l_Pos.getFirst().getX() + 0.5, l_Pos.getFirst().getY() + 0.5, l_Pos.getFirst().getZ() + 0.5, (EntityPlayer)this.mc.player);
                            p_Event.setPitch(l_Pos2[1]);
                            p_Event.setYaw(l_Pos2[0]);
                            l_Placed = true;
                            break;
                        }
                    }
                }
                if (l_Pos != null && l_Placed) {
                    this.Positions.remove(l_Pos);
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
        this.Positions.clear();
        this.WitherFeetBlock = null;
        final RayTraceResult l_Ray = this.mc.objectMouseOver;
        if (l_Ray == null) {
            return;
        }
        if (l_Ray.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        IBlockState l_State = this.mc.world.getBlockState(l_Ray.getBlockPos());
        if (l_State.getBlock() == Blocks.SOUL_SAND || l_State.getBlock() == Blocks.AIR || l_State.getBlock() == Blocks.WATER || l_State.getBlock() == Blocks.LAVA) {
            if (this.IsValidLocationForWitherBlocks(l_Ray.getBlockPos())) {
                this.WitherFeetBlock = l_Ray.getBlockPos();
            }
        }
        else {
            l_State = this.mc.world.getBlockState(l_Ray.getBlockPos().up());
            if ((l_State.getBlock() == Blocks.SOUL_SAND || l_State.getBlock() == Blocks.AIR || l_State.getBlock() == Blocks.WATER || l_State.getBlock() == Blocks.LAVA) && this.IsValidLocationForWitherBlocks(l_Ray.getBlockPos().up())) {
                this.WitherFeetBlock = l_Ray.getBlockPos().up();
                this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock, Blocks.SOUL_SAND));
            }
        }
        if (this.WitherFeetBlock == null) {
            this.SendMessage("Not a valid location for a wither.");
            this.toggle();
            return;
        }
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up(), Blocks.SOUL_SAND));
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up().east(), Blocks.SOUL_SAND));
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up().west(), Blocks.SOUL_SAND));
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up().up(), (Block)Blocks.SKULL));
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up().up().east(), (Block)Blocks.SKULL));
        this.Positions.add(new Pair<BlockPos, Block>(this.WitherFeetBlock.up().up().west(), (Block)Blocks.SKULL));
    }
    
    private boolean IsValidLocationForWitherBlocks(final BlockPos p_Pos) {
        final BlockPos[] array;
        final BlockPos[] l_Positions = array = new BlockPos[] { p_Pos.up(), p_Pos.up().east(), p_Pos.up().west(), p_Pos.up().up(), p_Pos.up().up().east(), p_Pos.up().up().west() };
        for (final BlockPos l_Pos : array) {
            final IBlockState l_State = this.mc.world.getBlockState(l_Pos);
            if (l_State.getBlock() != Blocks.AIR && l_State.getBlock() != Blocks.WATER && l_State.getBlock() != Blocks.LAVA) {
                return false;
            }
        }
        return true;
    }
    
    private int GetSoulsandInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)stack.getItem()).getBlock();
                if (block instanceof BlockSoulSand) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    private int GetSkullInHotbar() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != ItemStack.EMPTY && stack.getItem() instanceof ItemSkull) {
                return i;
            }
        }
        return -1;
    }
}
