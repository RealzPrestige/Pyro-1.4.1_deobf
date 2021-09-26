//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockDirt;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoFarmland extends Module
{
    public final Value<Integer> Radius;
    public final Value<Float> Delay;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoFarmland() {
        super("AutoFarmland", new String[] { "" }, "Automatically sets grass or dirt to farmland", "NONE", -1, ModuleType.WORLD);
        this.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for grass/dirt", 4, 0, 10, 1);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to use", 1.0f, 0.0f, 10.0f, 1.0f);
        this.timer = new Timer();
        BlockPos l_ClosestPos;
        double[] l_Pos;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (!(!this.timer.passed(this.Delay.getValue() * 100.0f))) {
                l_ClosestPos = BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), this.Radius.getValue(), this.Radius.getValue(), false, true, 0).stream().filter(p_Pos -> this.IsValidBlockPos(p_Pos)).min(Comparator.comparing(p_Pos -> EntityUtil.GetDistanceOfEntityToBlock((Entity)this.mc.player, p_Pos))).orElse(null);
                if (l_ClosestPos != null && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemHoe) {
                    this.timer.reset();
                    p_Event.cancel();
                    l_Pos = EntityUtil.calculateLookAt(l_ClosestPos.getX() + 0.5, l_ClosestPos.getY() - 0.5, l_ClosestPos.getZ() + 0.5, (EntityPlayer)this.mc.player);
                    p_Event.setPitch(l_Pos[1]);
                    p_Event.setYaw(l_Pos[0]);
                    this.mc.player.swingArm(EnumHand.MAIN_HAND);
                    this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(l_ClosestPos, EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                }
            }
        });
    }
    
    private boolean IsValidBlockPos(final BlockPos p_Pos) {
        final IBlockState l_State = this.mc.world.getBlockState(p_Pos);
        return (l_State.getBlock() instanceof BlockDirt || l_State.getBlock() instanceof BlockGrass) && this.mc.world.getBlockState(p_Pos.up()).getBlock() == Blocks.AIR;
    }
}
