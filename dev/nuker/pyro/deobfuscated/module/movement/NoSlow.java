//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.util.MovementInput;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.EnumFacing;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.network.Packet;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.item.ItemShield;
import net.minecraft.client.settings.KeyBinding;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import org.lwjgl.input.Keyboard;
import dev.nuker.pyro.deobfuscated.gui.chat.SalGuiChat;
import net.minecraft.client.gui.GuiChat;
import dev.nuker.pyro.deobfuscated.gui.SalGuiScreen;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdateMoveState;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class NoSlow extends Module
{
    public final Value<Boolean> InventoryMove;
    public final Value<Boolean> OnlyOnCustom;
    public final Value<Boolean> noInputGUIs;
    public final Value<Boolean> items;
    public final Value<Boolean> NCPStrict;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnIsKeyPressed;
    @EventHandler
    private Listener<EventClientTick> OnTick;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnUpdateMoveState;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public NoSlow() {
        super("NoSlow", new String[] { "AntiSlow", "NoSlowdown", "AntiSlowdown", "InventoryMove" }, "Allows you to move faster with things that slow you down", "NONE", 2384091, ModuleType.MOVEMENT);
        this.InventoryMove = new Value<Boolean>("InventoryMove", new String[] { "InvMove", "InventoryMove", "GUIMove" }, "Allows you to move while guis are open", true);
        this.OnlyOnCustom = new Value<Boolean>("OnlyOnCustom", new String[] { "Custom" }, "Only inventory move on custom GUIs", true);
        this.noInputGUIs = new Value<Boolean>("NoInputGUIs", new String[] { "NoConsole" }, "Doesn't Inventory move on the Console / Chat GUI.", true);
        this.items = new Value<Boolean>("Items", new String[] { "it" }, "Disables the slowness from using items (shields, eating, etc).", true);
        this.NCPStrict = new Value<Boolean>("NCPStrict", new String[] { "NCP" }, "Allows NoSlow to work on nocheatplus", true);
        MovementInput movementInput;
        MovementInput movementInput2;
        MovementInput movementInput3;
        MovementInput movementInput4;
        this.OnIsKeyPressed = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this.InventoryMove.getValue() && this.mc.currentScreen != null) {
                if (!this.OnlyOnCustom.getValue() || this.mc.currentScreen instanceof SalGuiScreen) {
                    if (this.noInputGUIs.getValue()) {
                        if (this.mc.currentScreen instanceof GuiChat) {
                            return;
                        }
                        else if (this.mc.currentScreen instanceof SalGuiChat) {
                            return;
                        }
                    }
                    if (!(this.mc.currentScreen instanceof SalGuiScreen)) {
                        if (Keyboard.isKeyDown(200)) {
                            SalGuiScreen.UpdateRotationPitch(-5.0f);
                        }
                        if (Keyboard.isKeyDown(208)) {
                            SalGuiScreen.UpdateRotationPitch(5.0f);
                        }
                        if (Keyboard.isKeyDown(205)) {
                            SalGuiScreen.UpdateRotationYaw(5.0f);
                        }
                        if (Keyboard.isKeyDown(203)) {
                            SalGuiScreen.UpdateRotationYaw(-5.0f);
                        }
                    }
                    if (!PyroStatic.AUTOWALK.isEnabled()) {
                        this.mc.player.movementInput.moveStrafe = 0.0f;
                        this.mc.player.movementInput.moveForward = 0.0f;
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindForward.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode()));
                        if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindForward.getKeyCode())) {
                            movementInput = this.mc.player.movementInput;
                            ++movementInput.moveForward;
                            this.mc.player.movementInput.forwardKeyDown = true;
                        }
                        else {
                            this.mc.player.movementInput.forwardKeyDown = false;
                        }
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindBack.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode()));
                        if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindBack.getKeyCode())) {
                            movementInput2 = this.mc.player.movementInput;
                            --movementInput2.moveForward;
                            this.mc.player.movementInput.backKeyDown = true;
                        }
                        else {
                            this.mc.player.movementInput.backKeyDown = false;
                        }
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindLeft.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode()));
                        if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindLeft.getKeyCode())) {
                            movementInput3 = this.mc.player.movementInput;
                            ++movementInput3.moveStrafe;
                            this.mc.player.movementInput.leftKeyDown = true;
                        }
                        else {
                            this.mc.player.movementInput.leftKeyDown = false;
                        }
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindRight.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode()));
                        if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindRight.getKeyCode())) {
                            movementInput4 = this.mc.player.movementInput;
                            --movementInput4.moveStrafe;
                            this.mc.player.movementInput.rightKeyDown = true;
                        }
                        else {
                            this.mc.player.movementInput.rightKeyDown = false;
                        }
                        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindJump.getKeyCode(), Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode()));
                        this.mc.player.movementInput.jump = Keyboard.isKeyDown(this.mc.gameSettings.keyBindJump.getKeyCode());
                    }
                }
            }
            return;
        });
        this.OnTick = new Listener<EventClientTick>(p_Event -> {
            if (this.mc.player.isHandActive() && this.mc.player.getHeldItem(this.mc.player.getActiveHand()).getItem() instanceof ItemShield && (this.mc.player.movementInput.moveStrafe != 0.0f || (this.mc.player.movementInput.moveForward != 0.0f && this.mc.player.getItemInUseMaxCount() >= 8))) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
            }
            return;
        });
        MovementInput movementInput5;
        MovementInput movementInput6;
        this.OnUpdateMoveState = new Listener<EventPlayerUpdateMoveState>(event -> {
            if (this.items.getValue() && this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
                movementInput5 = this.mc.player.movementInput;
                movementInput5.moveForward /= 0.2f;
                movementInput6 = this.mc.player.movementInput;
                movementInput6.moveStrafe /= 0.2f;
            }
            return;
        });
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Post && this.NCPStrict.getValue() && this.items.getValue() && this.mc.player.isHandActive() && !this.mc.player.isRiding()) {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, PlayerUtil.GetLocalPlayerPosFloored(), EnumFacing.DOWN));
            }
            return;
        });
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (this.NCPStrict.getValue() && event.getPacket() instanceof CPacketClickWindow) {
                if (event.getStage() == MinecraftEvent.Stage.Pre) {
                    if (this.mc.player.isActiveItemStackBlocking()) {
                        this.mc.playerController.onStoppedUsingItem((EntityPlayer)this.mc.player);
                    }
                    if (this.mc.player.isSneaking()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    }
                    if (this.mc.player.isSprinting()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                    }
                }
                else {
                    if (this.mc.player.isSneaking()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    }
                    if (this.mc.player.isSprinting()) {
                        this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                    }
                }
            }
        });
    }
}
