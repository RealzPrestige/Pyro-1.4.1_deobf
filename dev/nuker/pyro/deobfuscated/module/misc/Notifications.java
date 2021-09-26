//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import java.awt.Toolkit;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.world.World;
import net.minecraft.network.play.server.SPacketEntityStatus;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.client.Minecraft;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityAdded;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import java.awt.TrayIcon;
import java.awt.Image;
import java.awt.SystemTray;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Notifications extends Module
{
    public final Value<Boolean> Queue;
    public final Value<Boolean> Kick;
    public final Value<Boolean> PM;
    public final Value<Boolean> Name;
    public final Value<Boolean> Stuck;
    public final Value<Boolean> Damage;
    public final Value<Boolean> Totem;
    public final Value<Boolean> Nearby;
    public final Value<Boolean> ShowFriends;
    private SystemTray tray;
    private Image image;
    private TrayIcon trayIcon;
    private Timer timer;
    private Timer healthTimer;
    private Timer totemPopTimer;
    private Timer chatTimer;
    private int prevDimension;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventEntityAdded> onEntityAdd;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public Notifications() {
        super("Notifications", new String[] { "WinNotifications" }, "Sends you notifications when the game is not focused when events happen", "NONE", 10030606, ModuleType.MISC);
        this.Queue = new Value<Boolean>("Queue", new String[] { "Q" }, "Q", true);
        this.Kick = new Value<Boolean>("Kick", new String[] { "Q" }, "Q", true);
        this.PM = new Value<Boolean>("PM", new String[] { "Q" }, "Q", true);
        this.Name = new Value<Boolean>("Name", new String[] { "Q" }, "Q", true);
        this.Stuck = new Value<Boolean>("Stuck", new String[] { "Q" }, "Q", true);
        this.Damage = new Value<Boolean>("Damage", new String[] { "Q" }, "Q", true);
        this.Totem = new Value<Boolean>("Totem", new String[] { "Q" }, "Q", true);
        this.Nearby = new Value<Boolean>("Nearby", new String[] { "Q" }, "Q", true);
        this.ShowFriends = new Value<Boolean>("Show Friends", new String[] { "Q" }, "Q", true);
        this.timer = new Timer();
        this.healthTimer = new Timer();
        this.totemPopTimer = new Timer();
        this.chatTimer = new Timer();
        this.prevDimension = -1337;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (Minecraft.getMinecraft().inGameHasFocus) {
                return;
            }
            else if (!this.timer.passed(10000.0)) {
                return;
            }
            else {
                if (PyroStatic.AUTOWALK.isEnabled() && this.Stuck.getValue() && Math.abs(this.mc.player.motionX) < 0.1 && Math.abs(this.mc.player.motionZ) < 0.1) {
                    this.sendNotificationIfNeed("AutoWalk has detected you are stuck.");
                    this.timer.reset();
                }
                if (this.prevDimension != this.mc.player.dimension) {
                    if (this.prevDimension == 1 && this.mc.getCurrentServerData() != null && this.mc.getCurrentServerData().serverIP.equals("2b2t.org") && this.Queue.getValue()) {
                        this.sendNotificationIfNeed("You've finished going through the queue.");
                    }
                    this.prevDimension = this.mc.player.dimension;
                }
                return;
            }
        });
        this.onEntityAdd = new Listener<EventEntityAdded>(event -> {
            if (!Minecraft.getMinecraft().inGameHasFocus && this.Nearby.getValue() && !(event.GetEntity() instanceof EntityPlayerSP) && event.GetEntity() instanceof EntityPlayer) {
                if (!this.ShowFriends.getValue()) {
                    if (!(!FriendManager.Get().IsFriend(event.GetEntity()))) {
                        return;
                    }
                }
                this.sendNotificationIfNeed(event.GetEntity().getName() + " " + "has just came into your render distance!");
            }
            return;
        });
        SPacketEntityStatus packet;
        SPacketUpdateHealth packet2;
        SPacketChat packet3;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (!Minecraft.getMinecraft().inGameHasFocus) {
                    try {
                        if (event.getPacket() instanceof SPacketEntityStatus && this.Totem.getValue()) {
                            if (!(!this.totemPopTimer.passed(10000.0))) {
                                if (this.mc.world != null) {
                                    packet = (SPacketEntityStatus)event.getPacket();
                                    if (packet.getEntity((World)this.mc.world) == this.mc.player && packet.getOpCode() == 35) {
                                        this.totemPopTimer.reset();
                                        this.sendNotificationIfNeed("You just popped a totem.");
                                    }
                                }
                            }
                        }
                        else if (event.getPacket() instanceof SPacketUpdateHealth && this.Damage.getValue()) {
                            if (!(!this.healthTimer.passed(10000.0))) {
                                packet2 = (SPacketUpdateHealth)event.getPacket();
                                if (packet2.getHealth() < this.mc.player.getHealth()) {
                                    this.healthTimer.reset();
                                    this.sendNotificationIfNeed("You've just taken damage.");
                                }
                            }
                        }
                        else if (event.getPacket() instanceof SPacketChat && (this.PM.getValue() || this.Name.getValue())) {
                            if (!(!this.chatTimer.passed(10000.0))) {
                                packet3 = (SPacketChat)event.getPacket();
                                if (packet3.chatComponent.getFormattedText().contains(this.mc.player.getName()) && this.Name.getValue()) {
                                    this.chatTimer.reset();
                                    this.sendNotificationIfNeed("You were mentioned in chat.");
                                }
                                else if (packet3.chatComponent.getFormattedText().contains("whispers") && this.PM.getValue()) {
                                    this.chatTimer.reset();
                                    this.sendNotificationIfNeed("You recieved a private message");
                                }
                            }
                        }
                        else if (event.getPacket() instanceof SPacketDisconnect && this.Kick.getValue()) {
                            this.sendNotificationIfNeed("You've just disconnected");
                        }
                    }
                    catch (Exception ex) {}
                }
            }
        });
    }
    
    @Override
    public void init() {
        this.tray = SystemTray.getSystemTray();
        final String path = this.getClass().getResource("/assets/Pyro/imgs/summitlogo.png").getPath();
        this.image = Toolkit.getDefaultToolkit().createImage(path);
        (this.trayIcon = new TrayIcon(this.image, "Pyro")).setImageAutoSize(true);
        this.trayIcon.setToolTip("Pyro Notifications");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        try {
            this.tray.add(this.trayIcon);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.sendNotificationIfNeed("Initalized Pyro Notifications Module!");
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    private void sendNotificationIfNeed(final String msg) {
        try {
            this.trayIcon.displayMessage("Pyro", msg, TrayIcon.MessageType.INFO);
        }
        catch (Exception ex) {}
    }
}
