//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import dev.nuker.pyro.deobfuscated.events.player.EventPlayerOnStoppedUsingItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.item.ItemSword;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerDestroyBlock;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerClickBlock;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerResetBlockRemoving;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerDamageBlock;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ PlayerControllerMP.class })
public class MixinPlayerControllerMP
{
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private GameType currentGameType;
    @Shadow
    private BlockPos currentBlock;
    
    @Inject(method = { "getBlockReachDistance" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfoReturnable<Float> callbackInfo) {
        if (PyroStatic.REACH != null && PyroStatic.REACH.isEnabled()) {
            final float attrib = (float)this.mc.player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue() + ((PyroStatic.REACH != null && PyroStatic.REACH.isEnabled()) ? PyroStatic.REACH.ReachAdd.getValue() : 0.0f);
            callbackInfo.setReturnValue((Object)(this.currentGameType.isCreative() ? attrib : (attrib - 0.5f)));
        }
    }
    
    @Inject(method = { "onPlayerDamageBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDamageBlock(final BlockPos posBlock, final EnumFacing directionFacing, final CallbackInfoReturnable<Boolean> p_Info) {
        final EventPlayerDamageBlock l_Event = new EventPlayerDamageBlock(posBlock, directionFacing);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Info.setReturnValue((Object)false);
            p_Info.cancel();
        }
    }
    
    @Inject(method = { "resetBlockRemoving" }, at = { @At("HEAD") }, cancellable = true)
    public void resetBlockRemoving(final CallbackInfo callbackInfo) {
        final EventPlayerResetBlockRemoving event = new EventPlayerResetBlockRemoving();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "clickBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void clickBlock(final BlockPos loc, final EnumFacing face, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerClickBlock event = new EventPlayerClickBlock(loc, face);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.setReturnValue((Object)false);
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "onPlayerDestroyBlock" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfo) {
        final EventPlayerDestroyBlock event = new EventPlayerDestroyBlock(pos);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfo.cancel();
            callbackInfo.setReturnValue((Object)false);
        }
        if (PyroStatic.NOGLITCHBLOCKS != null && PyroStatic.NOGLITCHBLOCKS.isEnabled()) {
            callbackInfo.cancel();
            if (this.currentGameType.hasLimitedInteractions()) {
                if (this.currentGameType == GameType.SPECTATOR) {
                    callbackInfo.setReturnValue((Object)false);
                }
                if (!this.mc.player.isAllowEdit()) {
                    final ItemStack itemstack = this.mc.player.getHeldItemMainhand();
                    if (itemstack.isEmpty()) {
                        callbackInfo.setReturnValue((Object)false);
                    }
                    if (!itemstack.canDestroy(this.mc.world.getBlockState(pos).getBlock())) {
                        callbackInfo.setReturnValue((Object)false);
                    }
                }
            }
            if (this.currentGameType.isCreative() && !this.mc.player.getHeldItemMainhand().isEmpty() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                callbackInfo.setReturnValue((Object)false);
            }
            else {
                final World world = (World)this.mc.world;
                final IBlockState iblockstate = world.getBlockState(pos);
                final Block block = iblockstate.getBlock();
                if ((block instanceof BlockCommandBlock || block instanceof BlockStructure) && !this.mc.player.canUseCommandBlock()) {
                    callbackInfo.setReturnValue((Object)false);
                }
                else if (iblockstate.getMaterial() == Material.AIR) {
                    callbackInfo.setReturnValue((Object)false);
                }
                else {
                    world.playEvent(2001, pos, Block.getStateId(iblockstate));
                    block.onBlockHarvested(world, pos, iblockstate, (EntityPlayer)this.mc.player);
                    boolean flag = false;
                    final boolean skipClientDestroy = PyroStatic.NOGLITCHBLOCKS != null && PyroStatic.NOGLITCHBLOCKS.isEnabled() && PyroStatic.NOGLITCHBLOCKS.Destroy.getValue();
                    if (!skipClientDestroy) {
                        flag = world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
                        if (flag) {
                            block.onPlayerDestroy(world, pos, iblockstate);
                        }
                    }
                    this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
                    if (!this.currentGameType.isCreative()) {
                        final ItemStack itemstack2 = this.mc.player.getHeldItemMainhand();
                        if (!itemstack2.isEmpty()) {
                            itemstack2.onBlockDestroyed(world, iblockstate, pos, (EntityPlayer)this.mc.player);
                            if (itemstack2.isEmpty()) {
                                this.mc.player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
                            }
                        }
                    }
                    callbackInfo.setReturnValue((Object)flag);
                }
            }
        }
    }
    
    @Inject(method = { "onStoppedUsingItem" }, at = { @At("HEAD") }, cancellable = true)
    public void onPlayerDestroyBlock(final EntityPlayer playerIn, final CallbackInfo info) {
        final EventPlayerOnStoppedUsingItem event = new EventPlayerOnStoppedUsingItem();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            info.cancel();
        }
    }
}
