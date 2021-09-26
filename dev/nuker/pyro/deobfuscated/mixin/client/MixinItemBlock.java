//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.EntityPlayerMP;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.events.blocks.EventPlaceBlockAt;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ItemBlock.class })
public abstract class MixinItemBlock
{
    @Shadow
    @Final
    protected Block block;
    
    @Overwrite
    public EnumActionResult onItemUse(final EntityPlayer player, final World worldIn, BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final IBlockState iblockstate = worldIn.getBlockState(pos);
        final Block block = iblockstate.getBlock();
        if (!block.isReplaceable((IBlockAccess)worldIn, pos)) {
            pos = pos.offset(facing);
        }
        final ItemStack itemstack = player.getHeldItem(hand);
        if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity)null)) {
            final int i = ((ItemBlock)this).getMetadata(itemstack.getMetadata());
            IBlockState iblockstate2 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, (EntityLivingBase)player, hand);
            if (this.placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate2)) {
                iblockstate2 = worldIn.getBlockState(pos);
                final SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, worldIn, pos, (Entity)player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
                itemstack.shrink(1);
            }
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }
    
    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ, final IBlockState newState) {
        final EventPlaceBlockAt event = new EventPlaceBlockAt();
        PyroMod.EVENT_BUS.post(event);
        if (event.isCancelled()) {
            final IBlockState iblockstate1 = worldIn.getBlockState(pos);
            final SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, (Entity)player);
            worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
            return false;
        }
        if (!worldIn.setBlockState(pos, newState, 11)) {
            return false;
        }
        final IBlockState state = worldIn.getBlockState(pos);
        if (state.getBlock() == this.block) {
            ItemBlock.setTileEntityNBT(worldIn, player, pos, stack);
            this.block.onBlockPlacedBy(worldIn, pos, state, (EntityLivingBase)player, stack);
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
            }
        }
        return true;
    }
}
