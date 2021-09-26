//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.managers.BlockManager;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerClickBlock;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.block.Block;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Nuker extends Module
{
    public final Value<String> Mode;
    public final Value<Boolean> ClickSelect;
    public final Value<Boolean> Flatten;
    public final Value<Boolean> Rotates;
    public final Value<Boolean> Raytrace;
    public final Value<Float> Range;
    private Block _clickSelectBlock;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerClickBlock> onClickBlock;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdates;
    
    public Nuker() {
        super("Nuker", new String[] { "Nukes" }, "Destroys blocks in a radius around you", "NONE", -1, ModuleType.WORLD);
        this.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode of breaking to use, Creative will get you kicked on most servers.", "Survival");
        this.ClickSelect = new Value<Boolean>("Click Select", new String[] { "CS" }, "Click blocks you want nuker to only target", false);
        this.Flatten = new Value<Boolean>("Flatten", new String[] { "F" }, "Flattens at your feet", false);
        this.Rotates = new Value<Boolean>("Rotates", new String[] { "R" }, "Rotates towards selected blocks, you won't bypass NCP without this", true);
        this.Raytrace = new Value<Boolean>("Raytrace", new String[] { "Ray" }, "Performs a raytrace calculation in order to determine the best facing towards the block", true);
        this.Range = new Value<Float>("Range", new String[] { "Range" }, "The range to search for blocks", 3.0f, 0.0f, 10.0f, 1.0f);
        this._clickSelectBlock = null;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        final IBlockState state;
        this.onClickBlock = new Listener<EventPlayerClickBlock>(event -> {
            state = this.mc.world.getBlockState(event.Location);
            if (state == null || state.getBlock() == Blocks.AIR) {
                return;
            }
            else {
                this._clickSelectBlock = state.getBlock();
                return;
            }
        });
        BlockPos selectedBlock;
        double[] rotations;
        float range;
        BlockPos flooredPos;
        final Iterator<BlockPos> iterator;
        BlockPos pos;
        IBlockState state2;
        double dist;
        this.onMotionUpdates = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
                return;
            }
            else if (this.ClickSelect.getValue() && this._clickSelectBlock == null) {
                return;
            }
            else {
                selectedBlock = null;
                if (BlockManager.GetCurrBlock() != null) {
                    event.cancel();
                    rotations = EntityUtil.calculateLookAt(BlockManager.GetCurrBlock().getX() + 0.5, BlockManager.GetCurrBlock().getY() - 0.5, BlockManager.GetCurrBlock().getZ() + 0.5, (EntityPlayer)this.mc.player);
                    event.setPitch(rotations[1]);
                    event.setYaw(rotations[0]);
                    if (BlockManager.Update(this.Range.getValue(), this.Raytrace.getValue())) {}
                    return;
                }
                else {
                    range = this.Range.getValue();
                    flooredPos = PlayerUtil.GetLocalPlayerPosFloored();
                    BlockInteractionHelper.getSphere(flooredPos, range, (int)range, false, true, 0).iterator();
                    while (iterator.hasNext()) {
                        pos = iterator.next();
                        if (this.Flatten.getValue() && pos.getY() < flooredPos.getY()) {
                            continue;
                        }
                        else {
                            state2 = this.mc.world.getBlockState(pos);
                            if (this.ClickSelect.getValue() && this._clickSelectBlock != null && state2.getBlock() != this._clickSelectBlock) {
                                continue;
                            }
                            else if (this.Mode.getValue().equals("Creative")) {
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
                            }
                            else if (state2.getBlock() != Blocks.BEDROCK && state2.getBlock() != Blocks.AIR) {
                                if (state2.getBlock() == Blocks.WATER) {
                                    continue;
                                }
                                else if (selectedBlock == null) {
                                    selectedBlock = pos;
                                }
                                else {
                                    dist = pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ);
                                    if (selectedBlock.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ) < dist) {
                                        continue;
                                    }
                                    else if (dist <= this.Range.getValue()) {
                                        selectedBlock = pos;
                                    }
                                    else {
                                        continue;
                                    }
                                }
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    if (selectedBlock == null) {
                        return;
                    }
                    else {
                        if (!this.Mode.getValue().equals("Creative")) {
                            BlockManager.SetCurrentBlock(selectedBlock);
                        }
                        return;
                    }
                }
            }
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Survival");
        this.Mode.addString("Creative");
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this._clickSelectBlock = null;
    }
}
