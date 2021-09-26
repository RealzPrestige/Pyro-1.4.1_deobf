// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client.blocks;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockCollisionBoundingBox;
import net.minecraft.util.math.AxisAlignedBB;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockAir;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockAir.class })
public class MixinBlockAir
{
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
