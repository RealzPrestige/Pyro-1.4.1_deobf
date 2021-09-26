//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.ItemStack;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import dev.nuker.pyro.deobfuscated.util.Rotation;
import dev.nuker.pyro.deobfuscated.util.VecRotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.Packet;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.init.Items;
import net.minecraft.world.World;
import dev.nuker.pyro.deobfuscated.util.CrystalUtils;
import dev.nuker.pyro.deobfuscated.util.CrystalUtils2;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.ArrayList;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.RotationUtils;
import net.minecraft.entity.Entity;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoCrystalBypass extends Module
{
    public static final Value<Float> PlaceRange;
    public static final Value<Float> BreakRange;
    public static final Value<Integer> Ticks;
    public static final Value<Float> MinDmg;
    public static final Value<Float> MaxSelfDmg;
    public static final Value<Boolean> PauseIfHittingBlock;
    public static final Value<Boolean> PauseWhileEating;
    private int ticks;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    
    public AutoCrystalBypass() {
        super("AutoCrystalBypass", new String[0], "AutoCrystal for 2b2t", "NONE", -1, ModuleType.COMBAT);
        EntityEnderCrystal crystal;
        AxisAlignedBB bb;
        VecRotation rot;
        Rotation r;
        ArrayList<EntityPlayer> playerTargets;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;
        BlockPos selected;
        float selDmg;
        int x;
        int y;
        int z;
        BlockPos pos;
        IBlockState state;
        final Iterator<EntityPlayer> iterator2;
        EntityPlayer player2;
        float selfDamage;
        float dmg;
        int i;
        ItemStack stack;
        VecRotation rot2;
        final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock;
        final Object o;
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (!event.isCancelled()) {
                    if (!AutoCrystal.NeedPause()) {
                        if (this.ticks > 0) {
                            --this.ticks;
                        }
                        else {
                            crystal = (EntityEnderCrystal)this.mc.world.getLoadedEntityList().stream().filter(e -> e instanceof EntityEnderCrystal).map(e -> (EntityEnderCrystal)e).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                            if (crystal != null && crystal.getDistance((Entity)this.mc.player) <= AutoCrystalBypass.BreakRange.getValue()) {
                                bb = crystal.getEntityBoundingBox();
                                rot = RotationUtils.getThis().searchCenter(bb, false, true, false, true);
                                if (rot != null) {
                                    r = RotationUtils.getThis().limitAngleChange(RotationUtils.getThis().serverRotation, rot.getRotation(), 180.0f);
                                    if (r != null) {
                                        event.setYaw(r.getYaw());
                                        event.setPitch(r.getPitch());
                                        event.cancel();
                                        this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, (Entity)crystal);
                                        this.mc.player.swingArm(EnumHand.MAIN_HAND);
                                        this.ticks = AutoCrystalBypass.Ticks.getValue();
                                        return;
                                    }
                                }
                            }
                            playerTargets = new ArrayList<EntityPlayer>();
                            this.mc.world.playerEntities.iterator();
                            while (iterator.hasNext()) {
                                player = iterator.next();
                                if (!(player instanceof EntityPlayerSP) && !FriendManager.Get().IsFriend((Entity)player) && player.getDistance((Entity)this.mc.player) < 25.0f && !player.isDead && player.getHealth() + player.getAbsorptionAmount() > 0.0f) {
                                    playerTargets.add(player);
                                }
                            }
                            minX = (int)(this.mc.player.posX - AutoCrystalBypass.PlaceRange.getValue());
                            minY = (int)(this.mc.player.posY - AutoCrystalBypass.PlaceRange.getValue());
                            minZ = (int)(this.mc.player.posZ - AutoCrystalBypass.PlaceRange.getValue());
                            maxX = (int)(this.mc.player.posX + AutoCrystalBypass.PlaceRange.getValue());
                            maxY = (int)(this.mc.player.posY + AutoCrystalBypass.PlaceRange.getValue());
                            maxZ = (int)(this.mc.player.posZ + AutoCrystalBypass.PlaceRange.getValue());
                            selected = null;
                            selDmg = 0.0f;
                            for (x = minX; x <= maxX; ++x) {
                                for (y = minY; y <= maxY; ++y) {
                                    for (z = minZ; z <= maxZ; ++z) {
                                        pos = new BlockPos(x, y, z);
                                        state = this.mc.world.getBlockState(pos);
                                        if ((state.getBlock() == Blocks.OBSIDIAN || state.getBlock() == Blocks.BEDROCK) && CrystalUtils2.canPlaceCrystalAt(pos, state)) {
                                            playerTargets.iterator();
                                            while (iterator2.hasNext()) {
                                                player2 = iterator2.next();
                                                selfDamage = CrystalUtils.calculateDamage((World)this.mc.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (Entity)this.mc.player, 0);
                                                dmg = CrystalUtils.calculateDamage((World)this.mc.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (Entity)player2, 0);
                                                if (selfDamage <= AutoCrystalBypass.MaxSelfDmg.getValue() && dmg > selDmg && dmg > AutoCrystalBypass.MinDmg.getValue()) {
                                                    selected = pos;
                                                    selDmg = dmg;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (selected != null) {
                                if (this.mc.player.getHeldItemOffhand().getItem() != Items.END_CRYSTAL && this.mc.player.getHeldItemMainhand().getItem() != Items.END_CRYSTAL) {
                                    i = 0;
                                    while (i < 9) {
                                        stack = this.mc.player.inventory.getStackInSlot(i);
                                        if (!stack.isEmpty() && stack.getItem() == Items.END_CRYSTAL) {
                                            this.mc.player.inventory.currentItem = i;
                                            this.mc.playerController.updateController();
                                            break;
                                        }
                                        else {
                                            ++i;
                                        }
                                    }
                                }
                                rot2 = RotationUtils.getThis().faceBlock(selected);
                                if (rot2 != null) {
                                    event.setYaw(rot2.getRotation().getYaw());
                                    event.setPitch(rot2.getRotation().getPitch());
                                    event.cancel();
                                    this.mc.getConnection();
                                    new CPacketPlayerTryUseItemOnBlock(selected, EnumFacing.UP, (this.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f);
                                    ((NetHandlerPlayClient)o).sendPacket((Packet)cPacketPlayerTryUseItemOnBlock);
                                    this.ticks = AutoCrystalBypass.Ticks.getValue();
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
    static {
        PlaceRange = new Value<Float>("PlaceRange", new String[] { "PlaceDistance", "PlaceRange" }, "Range at which Crystals will be placed.", 4.2f, 0.0f, 6.0f, 0.1f);
        BreakRange = new Value<Float>("BreakRange", new String[] { "BreakDistance", "BreakRange" }, "Range at which Crystals will be broken.", 4.2f, 0.0f, 6.0f, 0.1f);
        Ticks = new Value<Integer>("Ticks", new String[] { "IgnoreTicks" }, "The number of ticks to ignore on client update", 2, 0, 20, 1);
        MinDmg = new Value<Float>("MinDamage", new String[] { "MinDmg", "MinDamage" }, "Range at which Crystals will be placed.", 4.0f, 0.0f, 20.0f, 0.1f);
        MaxSelfDmg = new Value<Float>("MaxSelfDmg", new String[] { "MaxSelf", "MaxSelfDmg" }, "Max amount of damage allowed against yourself.", 4.0f, 0.0f, 20.0f, 0.1f);
        PauseIfHittingBlock = new Value<Boolean>("PauseIfHittingBlock", new String[] { "PauseIfHittingBlock", "PauseWhenHittingBlock" }, "Stops placing / breaking when hitting a block with your pickaxe.", true);
        PauseWhileEating = new Value<Boolean>("PauseWhileEating", new String[] { "PauseWhileEating" }, "Pauses while eating food.", true);
    }
}
