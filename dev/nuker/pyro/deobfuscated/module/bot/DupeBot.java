//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.bot;

import dev.nuker.pyro.deobfuscated.module.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import java.util.TimerTask;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumHand;
import java.util.Comparator;
import net.minecraft.entity.passive.AbstractChestHorse;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.communication.Packet;
import dev.nuker.pyro.deobfuscated.communication.ClientOpcodes;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.client.gui.inventory.GuiScreenHorseInventory;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityAdded;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerStartRiding;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdateMoveState;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.Timer;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.module.ListValue;
import dev.nuker.pyro.deobfuscated.module.Module;

public class DupeBot extends Module
{
    public final ListValue Mode;
    private boolean _started;
    private boolean _runToStart;
    private boolean _runToEnd;
    private boolean _inDesync;
    private boolean _needDismount;
    private boolean _tryToRemount;
    private boolean _sentRemountDesync;
    private Vec3d _startPosition;
    private Vec3d _endPosition;
    private Timer _timer;
    private float _startYaw;
    private boolean _sentInventoryPacket;
    @EventHandler
    private Listener<EventPlayerUpdate> OnUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRender;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnMovementInput;
    @EventHandler
    private Listener<EventPlayerStartRiding> OnStartRiding;
    @EventHandler
    private Listener<EventEntityAdded> OnEntityAdded;
    
    public DupeBot() {
        super("DupeBot", new String[0], "9b9t dupe bot", "NONE", -1, ModuleType.BOT);
        this.Mode = new ListValue("Mode", "Mode to use", new String[] { "Desyncer", "OtherAcc" });
        this._started = false;
        this._runToStart = false;
        this._runToEnd = false;
        this._inDesync = false;
        this._needDismount = false;
        this._tryToRemount = false;
        this._sentRemountDesync = false;
        this._startPosition = Vec3d.ZERO;
        this._endPosition = Vec3d.ZERO;
        this._timer = new Timer();
        this._startYaw = 0.0f;
        this._sentInventoryPacket = false;
        GuiScreenHorseInventory inv;
        boolean sendPacket;
        int i;
        ItemStack stack;
        double[] rotations;
        AbstractChestHorse horse;
        double[] rotations2;
        AbstractChestHorse horse2;
        this.OnUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!this._started) {
                return;
            }
            else {
                if (this.Mode.getValue().equals("Desyncer")) {
                    this.mc.player.rotationYaw = this._startYaw;
                }
                if (this.mc.currentScreen instanceof GuiScreenHorseInventory && this.Mode.getValue().equals("Desyncer")) {
                    inv = (GuiScreenHorseInventory)this.mc.currentScreen;
                    sendPacket = true;
                    i = 0;
                    while (i < inv.horseInventory.getSizeInventory()) {
                        stack = inv.horseInventory.getStackInSlot(i);
                        if (!stack.isEmpty()) {
                            sendPacket = false;
                            break;
                        }
                        else {
                            ++i;
                        }
                    }
                    if (sendPacket && !this._sentRemountDesync) {
                        this.SendMessage("Sent Remound Desync");
                        this._sentRemountDesync = true;
                        this._tryToRemount = true;
                        Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_REMOUNT_DESYNC));
                    }
                }
                if (this._runToStart && this._startPosition != Vec3d.ZERO) {
                    rotations = EntityUtil.calculateLookAt(this._startPosition.x, this._startPosition.y, this._startPosition.z, (EntityPlayer)this.mc.player);
                    this.mc.player.rotationYaw = (float)rotations[0];
                    if (!PyroStatic.AUTOWALK.isEnabled()) {
                        PyroStatic.AUTOWALK.toggle();
                    }
                    if (this.getDistance2D(this._startPosition) < 1.0) {
                        horse = (AbstractChestHorse)this.mc.world.loadedEntityList.stream().filter(e -> e instanceof AbstractChestHorse && e.hasChest()).map(e -> (AbstractChestHorse)e).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                        if (horse != null) {
                            if (PyroStatic.AUTOWALK.isEnabled()) {
                                PyroStatic.AUTOWALK.toggle();
                            }
                            this._runToStart = false;
                            this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, (Entity)horse, EnumHand.MAIN_HAND);
                        }
                    }
                    return;
                }
                else if (this._runToEnd && this._endPosition != Vec3d.ZERO) {
                    rotations2 = EntityUtil.calculateLookAt(this._endPosition.x + 0.5, this._endPosition.y - 0.5, this._endPosition.z + 0.5, (EntityPlayer)this.mc.player);
                    this.mc.player.rotationYaw = (float)rotations2[0];
                    if (!PyroStatic.AUTOWALK.isEnabled()) {
                        PyroStatic.AUTOWALK.toggle();
                    }
                    if (this.getDistance2D(this._endPosition) < 1.0) {
                        if (PyroStatic.AUTOWALK.isEnabled()) {
                            PyroStatic.AUTOWALK.toggle();
                        }
                        this._runToEnd = false;
                        Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_END_OF_ROAD));
                    }
                    return;
                }
                else {
                    if (this._needDismount && !this.mc.player.isRiding()) {
                        this._needDismount = false;
                        Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_DISMOUNTED_DONKEY));
                        this._runToEnd = true;
                    }
                    if (this._tryToRemount) {
                        horse2 = (AbstractChestHorse)this.mc.world.loadedEntityList.stream().filter(e -> e instanceof AbstractChestHorse && ((AbstractChestHorse)e).hasChest() && e != this.mc.player.getRidingEntity()).map(e -> (AbstractChestHorse)e).min(Comparator.comparing(e -> this.mc.player.getDistance(e))).orElse(null);
                        if (horse2 != null) {
                            this.mc.playerController.interactWithEntity((EntityPlayer)this.mc.player, (Entity)horse2, EnumHand.MAIN_HAND);
                        }
                    }
                    if (this.Mode.getValue().equals("Desyncer")) {
                        this.mc.player.rotationYaw = this._startYaw;
                    }
                    return;
                }
            }
        });
        BlockPos pos;
        AxisAlignedBB bb;
        this.OnRender = new Listener<RenderEvent>(event -> {
            if (this._startPosition == Vec3d.ZERO) {
                return;
            }
            else {
                pos = new BlockPos(this._startPosition.x, this._startPosition.y, this._startPosition.z);
                bb = new AxisAlignedBB(pos.getX() - this.mc.getRenderManager().viewerPosX, pos.getY() - this.mc.getRenderManager().viewerPosY, pos.getZ() - this.mc.getRenderManager().viewerPosZ, pos.getX() + 1 - this.mc.getRenderManager().viewerPosX, pos.getY() + 2 - this.mc.getRenderManager().viewerPosY, pos.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                    GlStateManager.pushMatrix();
                    GlStateManager.enableBlend();
                    GlStateManager.disableDepth();
                    GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                    GlStateManager.disableTexture2D();
                    GlStateManager.depthMask(false);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    GL11.glLineWidth(1.5f);
                    RenderUtil.drawFilledBox(bb, 704577552);
                    GL11.glDisable(2848);
                    GlStateManager.depthMask(true);
                    GlStateManager.enableDepth();
                    GlStateManager.enableTexture2D();
                    GlStateManager.disableBlend();
                    GlStateManager.popMatrix();
                }
                return;
            }
        });
        this.OnMovementInput = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this._needDismount) {
                this.mc.player.movementInput.sneak = true;
            }
            return;
        });
        this.OnStartRiding = new Listener<EventPlayerStartRiding>(event -> {
            if (!this._inDesync || this.Mode.getValue().equals("OtherAcc")) {
                this._tryToRemount = false;
                Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_RIDE_DONKEY));
            }
            this._timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (DupeBot.this.Mode.getValue().equals("Desyncer") && !DupeBot.this._sentInventoryPacket && !DupeBot.this._inDesync) {
                        Module.this.SendMessage("Sent inventory packet");
                        DupeBot.this._sentInventoryPacket = true;
                        DupeBot.this.mc.player.sendHorseInventory();
                    }
                }
            }, 2000L);
            return;
        });
        this.OnEntityAdded = new Listener<EventEntityAdded>(p_Event -> {
            if (p_Event.GetEntity() instanceof EntityPlayer && p_Event.GetEntity() != this.mc.player && this._inDesync) {
                Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_CHUNKS_LOADED));
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.SendMessage("Initalizing socket for " + String.valueOf(((Value<Object>)this.Mode).getValue()));
        if (Pyro.GetClient() == null) {
            this.SendMessage("Client is null, server is not reachable");
            return;
        }
        final Packet packet = new Packet(this.Mode.getValue().equals("Desyncer") ? ClientOpcodes.CMSG_DESYNCER : ClientOpcodes.CMSG_OTHER_ACC);
        packet.WriteInt32((int)this.mc.player.posX);
        packet.WriteInt32((int)this.mc.player.posY);
        packet.WriteInt32((int)this.mc.player.posZ);
        Pyro.GetClient().SendOpcodeSafe(packet);
        this.SendMessage("Sent the packet " + String.valueOf(this.Mode.getValue().equals("Desyncer") ? ClientOpcodes.CMSG_DESYNCER : ClientOpcodes.CMSG_OTHER_ACC));
        this._started = false;
        this._runToStart = false;
        this._runToEnd = false;
        this._startPosition = Vec3d.ZERO;
        this._endPosition = Vec3d.ZERO;
        this._startYaw = this.mc.player.rotationYaw;
        this._needDismount = false;
        this._tryToRemount = false;
        if (this._timer != null) {
            this._timer.cancel();
            this._timer = new Timer();
        }
    }
    
    public void OnReady(final Packet packet) {
        this._started = true;
        final String s = this.Mode.getValue();
        switch (s) {
            case "Desyncer": {
                this._endPosition = new Vec3d((double)packet.ReadInt32(), (double)packet.ReadInt32(), (double)packet.ReadInt32());
                this.SendMessage("_endPosition: " + this._endPosition.toString());
                if (this._endPosition.x >= -10.0 && this._endPosition.x <= 10.0) {
                    this._endPosition = Vec3d.ZERO;
                    break;
                }
                break;
            }
            case "OtherAcc": {
                this._endPosition = this.mc.player.getPositionVector();
                this._startPosition = new Vec3d((double)packet.ReadInt32(), (double)packet.ReadInt32(), (double)packet.ReadInt32());
                Pyro.GetClient().SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_END_OF_ROAD));
                this.SendMessage("startPosition: " + this._startPosition.toString());
                break;
            }
        }
    }
    
    public void OnRideDonkey() {
        final String s = this.Mode.getValue();
        switch (s) {
            case "Desyncer": {
                this._sentRemountDesync = false;
                this._sentInventoryPacket = false;
                if (PyroStatic.ENTITYDESYNC.isEnabled()) {
                    PyroStatic.ENTITYDESYNC.toggle();
                }
                this._inDesync = false;
            }
        }
    }
    
    public void OnEndOfRoad() {
        if (this.Mode.getValue().equals("Desyncer")) {
            this._inDesync = true;
            this.mc.player.rotationYaw = this._startYaw;
            if (this._endPosition.equals((Object)Vec3d.ZERO)) {
                final Packet packet = new Packet(this.Mode.getValue().equals("Desyncer") ? ClientOpcodes.CMSG_DESYNCER : ClientOpcodes.CMSG_OTHER_ACC);
                packet.WriteInt32((int)this.mc.player.posX);
                packet.WriteInt32((int)this.mc.player.posY);
                packet.WriteInt32((int)this.mc.player.posZ);
                Pyro.GetClient().SendOpcodeSafe(packet);
                return;
            }
            if (!PyroStatic.ENTITYDESYNC.isEnabled()) {
                PyroStatic.ENTITYDESYNC.HClip.setValue(false);
                PyroStatic.ENTITYDESYNC.toggle();
                this.mc.player.setPosition(this._endPosition.x, this._endPosition.y - 20.0, this._endPosition.z);
            }
        }
    }
    
    public void OnChunksLoad() {
        if (this.Mode.getValue().equals("OtherAcc")) {
            this._timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    DupeBot.this._runToStart = true;
                }
            }, 5000L);
        }
    }
    
    public void OnRemountDesync() {
        this.SendMessage("RECV OnRemountDesync");
        if (this.Mode.getValue().equals("OtherAcc")) {
            this._needDismount = true;
            this.SendMessage("Trying to dismount..");
        }
    }
    
    public void OnShutdown() {
    }
    
    public void OnStartDupe() {
    }
    
    public void OnStopDupe() {
    }
    
    public void OnDismountedDonkey() {
        final String s = this.Mode.getValue();
        switch (s) {
            case "OtherAcc": {
                this._runToEnd = true;
                break;
            }
        }
    }
    
    private double getDistance2D(final Vec3d pos) {
        final double posX = Math.abs(this.mc.player.posX - pos.x);
        final double posZ = Math.abs(this.mc.player.posZ - pos.z);
        return posX + posZ;
    }
}
