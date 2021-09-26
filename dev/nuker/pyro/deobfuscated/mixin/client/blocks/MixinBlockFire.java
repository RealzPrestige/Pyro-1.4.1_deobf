// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.blocks;

import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockCollisionBoundingBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockGetRenderLayer;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.block.BlockFire;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockFire.class })
public class MixinBlockFire
{
    @Inject(method = { "getRenderLayer" }, at = { @At("HEAD") }, cancellable = true)
    public void getRenderLayer(final CallbackInfoReturnable<BlockRenderLayer> callback) {
        final EventBlockGetRenderLayer event = new EventBlockGetRenderLayer((Block)this);
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            callback.cancel();
            callback.setReturnValue((Object)event.getBlockRenderLayer());
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
