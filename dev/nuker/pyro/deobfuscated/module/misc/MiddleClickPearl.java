//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.world.World;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.init.Items;
import org.lwjgl.input.Mouse;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Module;

public class MiddleClickPearl extends Module
{
    int slot;
    boolean needSendPackets;
    private Timer timer;
    @EventHandler
    private Listener<InputEvent.MouseInputEvent> onMouseEvent;
    @EventHandler
    private Listener<EventPlayerUpdate> onUpdate;
    
    public MiddleClickPearl() {
        super("MiddleClickPearl", new String[] { "MCP" }, "Automatically throws a pearl", "NONE", -1, ModuleType.MISC);
        this.slot = -1;
        this.timer = new Timer();
        int hotbarSlot;
        int prevSlot;
        this.onMouseEvent = new Listener<InputEvent.MouseInputEvent>(event -> {
            if (this.mc.world == null || this.mc.player == null || this.mc.player.inventory == null) {
                return;
            }
            else {
                if (Mouse.getEventButton() == 2) {
                    this.slot = PlayerUtil.GetItemSlot(Items.ENDER_PEARL);
                    hotbarSlot = PlayerUtil.GetItemInHotbar(Items.ENDER_PEARL);
                    if (this.slot != -1 && this.slot < 36) {
                        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.slot, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                        this.mc.playerController.updateController();
                        this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                        this.timer.reset();
                        this.needSendPackets = true;
                    }
                    else if (hotbarSlot != -1) {
                        prevSlot = this.mc.player.inventory.currentItem;
                        this.mc.player.inventory.currentItem = hotbarSlot;
                        this.mc.playerController.updateController();
                        this.mc.playerController.processRightClick((EntityPlayer)this.mc.player, (World)this.mc.world, EnumHand.MAIN_HAND);
                        this.mc.player.inventory.currentItem = prevSlot;
                        this.mc.playerController.updateController();
                    }
                }
                return;
            }
        });
        this.onUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (this.needSendPackets && this.timer.passed(100.0)) {
                this.needSendPackets = false;
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, this.slot, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                this.mc.playerController.updateController();
            }
        });
    }
}
