//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketRespawn;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.util.CameraEntity;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.blocks.EventSetOpaqueCube;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Freecam extends Module
{
    public final Value<Float> speed;
    public final Value<Boolean> CancelPackes;
    public final Value<String> Mode;
    private Entity riding;
    private EntityOtherPlayerMP Camera;
    private Vec3d position;
    private float yaw;
    private float pitch;
    @EventHandler
    private Listener<EventClientTick> onTick;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    @EventHandler
    private Listener<EventSetOpaqueCube> OnEventSetOpaqueCube;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public Freecam() {
        super("Freecam", new String[] { "OutOfBody" }, "Allows out of body movement", "NONE", -1, ModuleType.RENDER);
        this.speed = new Value<Float>("Speed", new String[] { "Spd" }, "Speed of freecam flight, higher number equals quicker motion.", 1.0f, 0.0f, 10.0f, 0.1f);
        this.CancelPackes = new Value<Boolean>("Cancel Packets", new String[] { "" }, "Cancels the packets, you won't be able to freely move without this.", true);
        this.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode of freecam to use, camera allows you to watch baritone, etc", "Camera");
        this.onTick = new Listener<EventClientTick>(event -> {
            if (this.Mode.getValue().equals("Camera")) {
                CameraEntity.movementTick(this.mc.player.movementInput.sneak, this.mc.player.movementInput.jump);
            }
            return;
        });
        this.OnPlayerMove = new Listener<EventPlayerMove>(p_Event -> {
            if (this.Mode.getValue().equals("Normal")) {
                this.mc.player.noClip = true;
            }
            return;
        });
        this.OnEventSetOpaqueCube = new Listener<EventSetOpaqueCube>(p_Event -> p_Event.cancel());
        double[] dir;
        EntityPlayerSP player;
        EntityPlayerSP player2;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(p_Event -> {
            if (this.Mode.getValue().equals("Normal")) {
                this.mc.player.noClip = true;
                this.mc.player.setVelocity(0.0, 0.0, 0.0);
                dir = MathUtil.directionSpeed(this.speed.getValue());
                if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                    this.mc.player.motionX = dir[0];
                    this.mc.player.motionZ = dir[1];
                }
                else {
                    this.mc.player.motionX = 0.0;
                    this.mc.player.motionZ = 0.0;
                }
                this.mc.player.setSprinting(false);
                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    player = this.mc.player;
                    player.motionY += this.speed.getValue();
                }
                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    player2 = this.mc.player;
                    player2.motionY -= this.speed.getValue();
                }
                if (this.mc.player.isRiding()) {
                    this.mc.player.dismountRidingEntity();
                }
            }
            return;
        });
        SPacketSetPassengers packet;
        Entity en;
        final int[] array;
        int length;
        int i = 0;
        int id;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (event.getPacket() instanceof SPacketRespawn) {
                this.toggle();
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketSetPassengers && this.Mode.getValue().equals("Normal")) {
                    packet = (SPacketSetPassengers)event.getPacket();
                    en = this.mc.world.getEntityByID(packet.getEntityId());
                    if (en != null) {
                        packet.getPassengerIds();
                        length = array.length;
                        while (i < length) {
                            id = array[i];
                            if (id == this.mc.player.getEntityId()) {
                                this.riding = en;
                                break;
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                }
                return;
            }
        });
        CPacketUseEntity packet2;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (this.Mode.getValue().equals("Normal")) {
                    if (this.CancelPackes.getValue() && (event.getPacket() instanceof CPacketUseEntity || event.getPacket() instanceof CPacketPlayerTryUseItem || event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock || event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketVehicleMove || event.getPacket() instanceof CPacketChatMessage)) {
                        event.cancel();
                    }
                }
                else if (this.Mode.getValue().equals("Camera") && event.getPacket() instanceof CPacketUseEntity) {
                    packet2 = (CPacketUseEntity)event.getPacket();
                    if (packet2.getEntityFromWorld((World)this.mc.world) == this.mc.player) {
                        event.cancel();
                    }
                }
                return;
            }
        });
        this.Mode.addString("Camera");
        this.Mode.addString("Normal");
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.world == null) {
            return;
        }
        if (this.Mode.getValue().equals("Normal")) {
            this.riding = null;
            if (this.mc.player.getRidingEntity() != null) {
                this.riding = this.mc.player.getRidingEntity();
                this.mc.player.dismountRidingEntity();
            }
            (this.Camera = new EntityOtherPlayerMP((World)this.mc.world, this.mc.getSession().getProfile())).copyLocationAndAnglesFrom((Entity)this.mc.player);
            this.Camera.prevRotationYaw = this.mc.player.rotationYaw;
            this.Camera.rotationYawHead = this.mc.player.rotationYawHead;
            this.Camera.inventory.copyInventory(this.mc.player.inventory);
            this.mc.world.addEntityToWorld(-69, (Entity)this.Camera);
            if (this.riding != null) {
                this.Camera.startRiding(this.riding);
            }
            this.position = this.mc.player.getPositionVector();
            this.yaw = this.mc.player.rotationYaw;
            this.pitch = this.mc.player.rotationPitch;
            this.mc.player.noClip = true;
        }
        else {
            CameraEntity.setCameraState(true);
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mc.world == null) {
            return;
        }
        if (this.Mode.getValue().equals("Normal")) {
            if (this.riding != null) {
                if (this.Camera != null) {
                    this.Camera.dismountRidingEntity();
                }
                this.mc.player.startRiding(this.riding, true);
                this.riding = null;
            }
            if (this.Camera != null) {
                this.mc.world.removeEntity((Entity)this.Camera);
            }
            if (this.position != null) {
                this.mc.player.setPosition(this.position.x, this.position.y, this.position.z);
            }
            this.mc.player.rotationYaw = this.yaw;
            this.mc.player.rotationPitch = this.pitch;
            this.mc.player.noClip = false;
            this.mc.player.setVelocity(0.0, 0.0, 0.0);
        }
        else {
            CameraEntity.setCameraState(false);
        }
    }
}
