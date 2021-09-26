// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.blocks;

import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockCollisionBoundingBox;
import net.minecraft.util.math.AxisAlignedBB;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockGetRenderLayer;
import net.minecraft.util.BlockRenderLayer;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.blocks.EventCanPlaceCheck;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Block.class })
public class MixinBlock
{
    @Inject(method = { "canPlaceBlockAt" }, at = { @At("HEAD") }, cancellable = true)
    public void canPlaceBlockAt(final World world, final BlockPos pos, final CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        final EventCanPlaceCheck l_Event = new EventCanPlaceCheck(world, pos, this.getClass());
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            callbackInfoReturnable.setReturnValue((Object)!l_Event.isCancelled());
            callbackInfoReturnable.cancel();
        }
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callback) {
        if (PyroStatic.WALLHACK != null && PyroStatic.WALLHACK.isEnabled()) {
            PyroStatic.WALLHACK.processShouldSideBeRendered((Block)this, blockState, blockAccess, pos, side, callback);
        }
    }
    
    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void getRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> callback) {
        final EventBlockGetRenderLayer event = new EventBlockGetRenderLayer((Block)this);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue((Object)event.getBlockRenderLayer());
        }
    }
    
    @Inject(method = { "getLightValue" }, at = { @At("HEAD") }, cancellable = true)
    public void getLightValue(final CallbackInfoReturnable<Integer> callback) {
        if (PyroStatic.WALLHACK != null && PyroStatic.WALLHACK.isEnabled()) {
            PyroStatic.WALLHACK.processGetLightValue((Block)this, callback);
        }
    }
    
    @Inject(method = { "getCollisionBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getCollisionBoundingBox(final IBlockState blockState, final IBlockAccess worldIn, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> callbackInfoReturnable) {
        final EventBlockCollisionBoundingBox event = new EventBlockCollisionBoundingBox(pos);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue((Object)event.getBoundingBox());
            callbackInfoReturnable.cancel();
        }
    }
}
