//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockCollisionBoundingBox;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Jesus extends Module
{
    public final Value<String> mode;
    public final Value<Boolean> NCPStrict;
    public final Value<Boolean> Boost;
    private boolean wasWater;
    private int ticks;
    private int boostTicks;
    private float prevYOffset;
    private static final double[] motionArr;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventBlockCollisionBoundingBox> OnLiquidCollisionBB;
    
    public Jesus() {
        super("Jesus", new String[] { "LiquidWalk", "WaterWalk" }, "Allows you to walk on water", "NONE", 8969707, ModuleType.MOVEMENT);
        this.mode = new Value<String>("Mode", new String[] { "Mode", "M" }, "The current Jesus/WaterWalk mode to use.", "Solid");
        this.NCPStrict = new Value<Boolean>("NCP Strict", new String[] { "NCP" }, "Allow jesus to work on a strict NCP Config", true);
        this.Boost = new Value<Boolean>("Boost", new String[] { "B" }, "Allows you to boost while jesusing", true);
        this.wasWater = false;
        this.ticks = 0;
        this.boostTicks = 0;
        EntityPlayerSP player;
        EntityPlayerSP player2;
        EntityPlayerSP player3;
        EntityPlayerSP player4;
        EntityPlayerSP player5;
        EntityPlayerSP player6;
        EntityPlayerSP player7;
        EntityPlayerSP player8;
        EntityPlayerSP player9;
        EntityPlayerSP player10;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.setMetaData(this.getMetaData());
            if (this.mode.getValue().equals("Dolphin")) {
                if (!this.mc.player.movementInput.sneak && !this.mc.player.movementInput.jump && PlayerUtil.isInLiquid()) {
                    this.mc.player.motionY = 0.13099999725818634;
                }
            }
            else if (this.mode.getValue().equals("Trampoline")) {
                if (!this.mc.player.onGround) {
                    if (this.ticks > 0 && this.ticks <= Jesus.motionArr.length) {
                        this.mc.player.motionY = Jesus.motionArr[this.ticks - 1];
                        ++this.ticks;
                    }
                    else if (this.mc.player.isInWater() || this.mc.player.isInLava()) {
                        this.mc.player.motionY = 0.1;
                        this.wasWater = true;
                        this.ticks = 0;
                    }
                    else {
                        if (this.wasWater) {
                            this.mc.player.motionY = 0.5;
                            ++this.ticks;
                        }
                        this.wasWater = false;
                    }
                }
                else {
                    this.ticks = 0;
                }
            }
            else if (this.mode.getValue().equals("Solid")) {
                if (!this.mc.player.movementInput.sneak && !this.mc.player.movementInput.jump && PlayerUtil.isInLiquid()) {
                    this.mc.player.motionY = 0.1;
                }
                if (PlayerUtil.isOnLiquid() && this.canJesus() && this.Boost.getValue()) {
                    switch (this.boostTicks) {
                        case 0: {
                            player = this.mc.player;
                            player.motionX *= 1.1;
                            player2 = this.mc.player;
                            player2.motionZ *= 1.1;
                            break;
                        }
                        case 1: {
                            player3 = this.mc.player;
                            player3.motionX *= 1.27;
                            player4 = this.mc.player;
                            player4.motionZ *= 1.27;
                            break;
                        }
                        case 2: {
                            player5 = this.mc.player;
                            player5.motionX *= 1.51;
                            player6 = this.mc.player;
                            player6.motionZ *= 1.51;
                            break;
                        }
                        case 3: {
                            player7 = this.mc.player;
                            player7.motionX *= 1.15;
                            player8 = this.mc.player;
                            player8.motionZ *= 1.15;
                            break;
                        }
                        case 4: {
                            player9 = this.mc.player;
                            player9.motionX *= 1.23;
                            player10 = this.mc.player;
                            player10.motionZ *= 1.23;
                            break;
                        }
                    }
                    ++this.boostTicks;
                    if (this.boostTicks > 4) {
                        this.boostTicks = 0;
                    }
                }
            }
            return;
        });
        this.onPlayerPosLook = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre && event.getPacket() instanceof SPacketPlayerPosLook) {
                this.boostTicks = 0;
            }
            return;
        });
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (this.mode.getValue().equals("Solid") && this.mc.player.isRowingBoat()) {
                return;
            }
            else {
                return;
            }
        });
        CPacketPlayer packet;
        final CPacketPlayer cPacketPlayer;
        CPacketVehicleMove packet2;
        double before;
        final CPacketVehicleMove cPacketVehicleMove;
        final CPacketVehicleMove cPacketVehicleMove2;
        final CPacketVehicleMove cPacketVehicleMove3;
        final CPacketVehicleMove cPacketVehicleMove4;
        final CPacketVehicleMove cPacketVehicleMove5;
        final CPacketVehicleMove cPacketVehicleMove6;
        final CPacketVehicleMove cPacketVehicleMove7;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                try {
                    if (this.mode.getValue().equals("Solid") && this.mc.world != null && (this.mc.player != null || this.mc.player.isRowingBoat())) {
                        if (event.getPacket() instanceof CPacketPlayer) {
                            packet = (CPacketPlayer)event.getPacket();
                            if (PlayerUtil.isOnLiquid() && this.canJesus()) {
                                packet.onGround = false;
                                if (this.NCPStrict.getValue()) {
                                    this.prevYOffset += 0.12f;
                                    if (this.prevYOffset > 0.4f) {
                                        this.prevYOffset = 0.2f;
                                    }
                                    cPacketPlayer.y -= this.prevYOffset;
                                }
                                else {
                                    packet.y = ((this.mc.player.ticksExisted % 2 == 0) ? (packet.y - 0.05) : packet.y);
                                }
                            }
                        }
                        else if (event.getPacket() instanceof CPacketVehicleMove && this.NCPStrict.getValue()) {
                            packet2 = (CPacketVehicleMove)event.getPacket();
                            if (PlayerUtil.isOnLiquid() && this.canJesus()) {
                                before = packet2.y;
                                if (this.mc.player.ticksExisted % 3 == 0) {
                                    cPacketVehicleMove.y -= 0.48;
                                }
                                else if (this.mc.player.ticksExisted % 4 == 0) {
                                    cPacketVehicleMove2.y -= 0.33;
                                }
                                else if (this.mc.player.ticksExisted % 5 == 0) {
                                    cPacketVehicleMove3.y -= 0.73;
                                }
                                else if (this.mc.player.ticksExisted % 6 == 0) {
                                    cPacketVehicleMove4.y -= 0.63;
                                }
                                else if (this.mc.player.ticksExisted % 7 == 0) {
                                    cPacketVehicleMove5.y -= 0.42;
                                }
                                else if (this.mc.player.ticksExisted % 8 == 0) {
                                    cPacketVehicleMove6.y -= 0.52;
                                }
                                if (packet2.y == before) {
                                    cPacketVehicleMove7.y -= 0.3;
                                }
                            }
                        }
                    }
                }
                catch (Exception ex) {}
                return;
            }
        });
        IBlockState state;
        final AxisAlignedBB boundingBox;
        this.OnLiquidCollisionBB = new Listener<EventBlockCollisionBoundingBox>(event -> {
            if (this.mc.world == null || this.mc.player == null) {
                return;
            }
            else if (this.mode.getValue().equals("Dolphin")) {
                return;
            }
            else if (this.mode.getValue().equals("Solid") && !PlayerUtil.isOnLiquid()) {
                return;
            }
            else if (PlayerUtil.isInLiquid()) {
                return;
            }
            else {
                state = this.mc.world.getBlockState(event.getPos());
                if (state.getBlock() instanceof BlockLiquid && !this.mc.player.isRowingBoat()) {
                    event.cancel();
                    event.setBoundingBox(Block.FULL_BLOCK_AABB);
                    if (this.mc.player.getRidingEntity() != null) {
                        event.setBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.949999988079071, 1.0));
                    }
                    else if (this.mode.getValue().equals("Dolphin")) {
                        if (this.canJesus()) {
                            new AxisAlignedBB((double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getY(), (double)event.getPos().getX(), event.getPos().getY() + (this.mc.player.movementInput.jump ? 0.95 : 0.99), (double)event.getPos().getZ());
                            event.setBoundingBox(boundingBox);
                        }
                    }
                    else if (this.mode.getValue().equals("Trampoline") && !this.mc.player.isSneaking()) {
                        event.setBoundingBox(new AxisAlignedBB((double)event.getPos().getX(), (double)event.getPos().getY(), (double)event.getPos().getY(), (double)event.getPos().getX(), event.getPos().getY() + 0.96, (double)event.getPos().getZ()));
                    }
                }
                return;
            }
        });
        this.setMetaData(this.getMetaData());
        this.mode.addString("Solid");
        this.mode.addString("Trampoline");
        this.mode.addString("Dolphin");
    }
    
    @Override
    public String getMetaData() {
        if (this.mc.player != null && this.mc.player.isRiding()) {
            return null;
        }
        return this.mode.getValue();
    }
    
    private boolean canJesus() {
        return this.mc.player.fallDistance < 3.0f && !this.mc.player.movementInput.jump && !PlayerUtil.isInLiquid() && !this.mc.player.isSneaking();
    }
    
    static {
        motionArr = new double[] { 0.5, 0.46879999999995, 0.43759999999989996, 0.40639999999984994, 0.3751999999997999, 0.3439999999997499, 0.3127999999996999, 0.28159999999964985, 0.25039999999959983, 0.21919999999954984, 0.18799999999949987, 0.15679999999944988, 0.1255999999993999, 0.09439999999934989, 0.0631999999992999, 0.0319999999992499, 7.999999991998991E-4, -0.06240000000010001, -0.09360000000015001, -0.12480000000020003, -0.15600000000025005, -0.18720000000030004, -0.21840000000035, -0.24960000000039997, -0.32300800628701304, -0.3949478538480405, -0.4654489058299911, -0.5345399381170035, -0.6022491510760825, -0.6686041810674306 };
    }
}
