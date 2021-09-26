//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.item.ItemBlock;
import net.minecraft.client.entity.EntityPlayerSP;
import java.util.function.Consumer;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Scaffold extends Module
{
    public final Value<String> Mode;
    public final Value<Boolean> StopMotion;
    public final Value<Float> Delay;
    private Timer _timer;
    private Timer _towerPauseTimer;
    private Timer _towerTimer;
    private float[] _rotations;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    
    public Scaffold() {
        super("Scaffold", new String[] { "Scaffold" }, "Places blocks under you", "NONE", 9740883, ModuleType.WORLD);
        this.Mode = new Value<String>("Mode", new String[] { "" }, "Tower lets you go up fast when holding space and placing blocks, normal will disable that", "Tower");
        this.StopMotion = new Value<Boolean>("StopMotion", new String[] { "" }, "Stops you from moving if the block isn't placed yet", true);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay of the place", 0.0f, 0.0f, 1.0f, 0.1f);
        this._timer = new Timer();
        this._towerPauseTimer = new Timer();
        this._towerTimer = new Timer();
        this._rotations = null;
        final Entity en;
        ItemStack stack;
        int prevSlot;
        int i;
        BlockPos toPlaceAt;
        BlockPos feetBlock;
        boolean placeAtFeet;
        float towerMotion;
        BlockInteractionHelper.ValidResult result;
        BlockPos[] array;
        BlockPos toSelect;
        double lastDistance;
        final BlockPos[] array3;
        int length;
        int k = 0;
        BlockPos pos;
        double dist;
        Vec3d eyesPos;
        final EnumFacing[] array4;
        int length2;
        int l = 0;
        EnumFacing side;
        BlockPos neighbor;
        EnumFacing side2;
        Vec3d hitVec;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.setMetaData(this.getMetaData());
            en = this.mc.player.getRidingEntity();
            if (en == null) {
                return;
            }
            else {
                this.mc.player.horseJumpPower = 0.0f;
                if (!this._timer.passed(this.Delay.getValue() * 1000.0f)) {
                    return;
                }
                else {
                    if (this.mc.player.movementInput.jump && en.onGround) {
                        en.motionY = 0.65;
                    }
                    stack = this.mc.player.getHeldItemMainhand();
                    prevSlot = -1;
                    if (!this.verifyStack(stack)) {
                        i = 0;
                        while (i < 9) {
                            stack = this.mc.player.inventory.getStackInSlot(i);
                            if (this.verifyStack(stack)) {
                                prevSlot = this.mc.player.inventory.currentItem;
                                this.mc.player.inventory.currentItem = i;
                                this.mc.playerController.updateController();
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                    if (!this.verifyStack(stack)) {
                        return;
                    }
                    else {
                        this._timer.reset();
                        toPlaceAt = null;
                        feetBlock = PlayerUtil.GetLocalPlayerPosFloored().down();
                        placeAtFeet = (feetBlock != BlockPos.ORIGIN && this.isValidPlaceBlockState(feetBlock));
                        if (this.Mode.getValue().equals("Tower") && placeAtFeet && this.mc.player.movementInput.jump && this._towerTimer.passed(250.0) && !this.mc.player.isElytraFlying()) {
                            if (this._towerPauseTimer.passed(1500.0)) {
                                this._towerPauseTimer.reset();
                                en.motionY = -0.2800000011920929;
                            }
                            else {
                                towerMotion = 0.42f;
                                en.setVelocity(0.0, 0.41999998688697815, 0.0);
                            }
                        }
                        if (placeAtFeet) {
                            toPlaceAt = feetBlock;
                        }
                        else {
                            result = BlockInteractionHelper.valid(feetBlock);
                            if (result != BlockInteractionHelper.ValidResult.Ok && result != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                                array = new BlockPos[] { feetBlock.north(), feetBlock.south(), feetBlock.east(), feetBlock.west() };
                                toSelect = null;
                                lastDistance = 420.0;
                                for (length = array3.length; k < length; ++k) {
                                    pos = array3[k];
                                    if (!(!this.isValidPlaceBlockState(pos))) {
                                        dist = pos.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ);
                                        if (lastDistance > dist) {
                                            lastDistance = dist;
                                            toSelect = pos;
                                        }
                                    }
                                }
                                if (toSelect != null) {
                                    toPlaceAt = toSelect;
                                }
                            }
                        }
                        if (toPlaceAt != null) {
                            eyesPos = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
                            EnumFacing.values();
                            for (length2 = array4.length; l < length2; ++l) {
                                side = array4[l];
                                neighbor = toPlaceAt.offset(side);
                                side2 = side.getOpposite();
                                if (this.mc.world.getBlockState(neighbor).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor), false)) {
                                    hitVec = new Vec3d((Vec3i)neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));
                                    if (eyesPos.distanceTo(hitVec) <= 5.0) {
                                        this._rotations = BlockInteractionHelper.getFacingRotations(toPlaceAt.getX(), toPlaceAt.getY(), toPlaceAt.getZ(), side);
                                        break;
                                    }
                                }
                            }
                            if (BlockInteractionHelper.place(toPlaceAt, 5.0f, false, false, true) == BlockInteractionHelper.PlaceResult.Placed) {}
                        }
                        else {
                            this._rotations = null;
                            this._towerPauseTimer.reset();
                        }
                        if (prevSlot != -1) {
                            this.mc.player.inventory.currentItem = prevSlot;
                            this.mc.playerController.updateController();
                        }
                        return;
                    }
                }
            }
        });
        ItemStack stack2;
        int prevSlot2;
        int j;
        BlockPos toPlaceAt2;
        BlockPos feetBlock2;
        boolean placeAtFeet2;
        float towerMotion2;
        BlockInteractionHelper.ValidResult result2;
        BlockPos[] array2;
        BlockPos toSelect2;
        double lastDistance2;
        final BlockPos[] array5;
        int length3;
        int n = 0;
        BlockPos pos2;
        double dist2;
        Vec3d eyesPos2;
        final EnumFacing[] array6;
        int length4;
        int n2 = 0;
        EnumFacing side3;
        BlockPos neighbor2;
        EnumFacing side4;
        Vec3d hitVec2;
        float[] rotations;
        BlockPos finalToPlaceAt;
        int finalPrevSlot;
        final BlockPos pos3;
        final int currentItem;
        Consumer<EntityPlayerSP> post;
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.isCancelled() || this.mc.player.inventory == null || this.mc.world == null) {
                return;
            }
            else if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (!this._timer.passed(this.Delay.getValue() * 1000.0f)) {
                return;
            }
            else {
                stack2 = this.mc.player.getHeldItemMainhand();
                prevSlot2 = -1;
                if (!this.verifyStack(stack2)) {
                    j = 0;
                    while (j < 9) {
                        stack2 = this.mc.player.inventory.getStackInSlot(j);
                        if (this.verifyStack(stack2)) {
                            prevSlot2 = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = j;
                            this.mc.playerController.updateController();
                            break;
                        }
                        else {
                            ++j;
                        }
                    }
                }
                if (!this.verifyStack(stack2)) {
                    return;
                }
                else {
                    this._timer.reset();
                    toPlaceAt2 = null;
                    feetBlock2 = PlayerUtil.GetLocalPlayerPosFloored().down();
                    placeAtFeet2 = (feetBlock2 != BlockPos.ORIGIN && this.isValidPlaceBlockState(feetBlock2));
                    if (this.Mode.getValue().equals("Tower") && placeAtFeet2 && this.mc.player.movementInput.jump && this._towerTimer.passed(250.0) && !this.mc.player.isElytraFlying()) {
                        if (this._towerPauseTimer.passed(1500.0)) {
                            this._towerPauseTimer.reset();
                            this.mc.player.motionY = -0.2800000011920929;
                        }
                        else {
                            towerMotion2 = 0.42f;
                            this.mc.player.setVelocity(0.0, 0.41999998688697815, 0.0);
                        }
                    }
                    if (placeAtFeet2) {
                        toPlaceAt2 = feetBlock2;
                    }
                    else {
                        result2 = BlockInteractionHelper.valid(feetBlock2);
                        if (result2 != BlockInteractionHelper.ValidResult.Ok && result2 != BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
                            array2 = new BlockPos[] { feetBlock2.north(), feetBlock2.south(), feetBlock2.east(), feetBlock2.west() };
                            toSelect2 = null;
                            lastDistance2 = 420.0;
                            for (length3 = array5.length; n < length3; ++n) {
                                pos2 = array5[n];
                                if (!(!this.isValidPlaceBlockState(pos2))) {
                                    dist2 = pos2.getDistance((int)this.mc.player.posX, (int)this.mc.player.posY, (int)this.mc.player.posZ);
                                    if (lastDistance2 > dist2) {
                                        lastDistance2 = dist2;
                                        toSelect2 = pos2;
                                    }
                                }
                            }
                            if (toSelect2 != null) {
                                toPlaceAt2 = toSelect2;
                            }
                        }
                    }
                    if (toPlaceAt2 != null) {
                        eyesPos2 = new Vec3d(this.mc.player.posX, this.mc.player.posY + this.mc.player.getEyeHeight(), this.mc.player.posZ);
                        EnumFacing.values();
                        for (length4 = array6.length; n2 < length4; ++n2) {
                            side3 = array6[n2];
                            neighbor2 = toPlaceAt2.offset(side3);
                            side4 = side3.getOpposite();
                            if (this.mc.world.getBlockState(neighbor2).getBlock().canCollideCheck(this.mc.world.getBlockState(neighbor2), false)) {
                                hitVec2 = new Vec3d((Vec3i)neighbor2).add(0.5, 0.5, 0.5).add(new Vec3d(side4.getDirectionVec()).scale(0.5));
                                if (eyesPos2.distanceTo(hitVec2) <= 5.0) {
                                    rotations = BlockInteractionHelper.getFacingRotations(toPlaceAt2.getX(), toPlaceAt2.getY(), toPlaceAt2.getZ(), side3);
                                    event.cancel();
                                    event.setPitch(rotations[1]);
                                    event.setYaw(rotations[0]);
                                    break;
                                }
                            }
                        }
                        if (event.isCancelled()) {
                            finalToPlaceAt = toPlaceAt2;
                            finalPrevSlot = prevSlot2;
                            post = (p -> {
                                if (BlockInteractionHelper.place(pos3, 5.0f, false, false, true) == BlockInteractionHelper.PlaceResult.Placed) {}
                                if (currentItem != -1) {
                                    this.mc.player.inventory.currentItem = currentItem;
                                    this.mc.playerController.updateController();
                                }
                                return;
                            });
                            event.setFunct(post);
                        }
                        else {
                            this._towerPauseTimer.reset();
                        }
                    }
                    else {
                        this._towerPauseTimer.reset();
                    }
                    if (!event.isCancelled() && prevSlot2 != -1) {
                        this.mc.player.inventory.currentItem = prevSlot2;
                        this.mc.playerController.updateController();
                    }
                    return;
                }
            }
        });
        this.onPlayerPosLook = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre && event.getPacket() instanceof SPacketPlayerPosLook) {
                this._towerTimer.reset();
                if (this.mc.player.movementInput.jump) {
                    this.mc.player.motionY = 0.41999998688697815;
                }
            }
            return;
        });
        CPacketPlayer.Rotation packet;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof CPacketPlayer.Rotation && this.mc.player.isRiding() && this._rotations != null) {
                    packet = (CPacketPlayer.Rotation)event.getPacket();
                    packet.pitch = this._rotations[1];
                    packet.yaw = this._rotations[0];
                }
                return;
            }
        });
        double x;
        double y;
        double z;
        double increment;
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            if (!this.StopMotion.getValue()) {
                return;
            }
            else {
                x = p_Event.X;
                y = p_Event.Y;
                z = p_Event.Z;
                if (this.mc.player.onGround && !this.mc.player.noClip) {
                    increment = 0.05;
                    while (x != 0.0 && this.isOffsetBBEmpty(x, -1.0, 0.0)) {
                        if (x < increment && x >= -increment) {
                            x = 0.0;
                        }
                        else if (x > 0.0) {
                            x -= increment;
                        }
                        else {
                            x += increment;
                        }
                    }
                    while (z != 0.0 && this.isOffsetBBEmpty(0.0, -1.0, z)) {
                        if (z < increment && z >= -increment) {
                            z = 0.0;
                        }
                        else if (z > 0.0) {
                            z -= increment;
                        }
                        else {
                            z += increment;
                        }
                    }
                    while (x != 0.0 && z != 0.0 && this.isOffsetBBEmpty(x, -1.0, z)) {
                        if (x < increment && x >= -increment) {
                            x = 0.0;
                        }
                        else if (x > 0.0) {
                            x -= increment;
                        }
                        else {
                            x += increment;
                        }
                        if (z < increment && z >= -increment) {
                            z = 0.0;
                        }
                        else if (z > 0.0) {
                            z -= increment;
                        }
                        else {
                            z += increment;
                        }
                    }
                }
                p_Event.X = x;
                p_Event.Y = y;
                p_Event.Z = z;
                p_Event.cancel();
                return;
            }
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Tower");
        this.Mode.addString("Normal");
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    private boolean isOffsetBBEmpty(final double x, final double y, final double z) {
        return this.mc.world.getCollisionBoxes((Entity)this.mc.player, this.mc.player.getEntityBoundingBox().offset(x, y, z)).isEmpty();
    }
    
    private boolean isValidPlaceBlockState(final BlockPos pos) {
        final BlockInteractionHelper.ValidResult result = BlockInteractionHelper.valid(pos);
        if (result == BlockInteractionHelper.ValidResult.AlreadyBlockThere) {
            return this.mc.world.getBlockState(pos).getMaterial().isReplaceable();
        }
        return result == BlockInteractionHelper.ValidResult.Ok;
    }
    
    private boolean verifyStack(final ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof ItemBlock;
    }
}
