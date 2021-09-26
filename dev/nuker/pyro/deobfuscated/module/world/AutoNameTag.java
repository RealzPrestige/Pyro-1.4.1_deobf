//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.entity.boss.EntityWither;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import java.util.Comparator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemNameTag;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoNameTag extends Module
{
    public final Value<Integer> Radius;
    public final Value<Boolean> ReplaceOldNames;
    public final Value<Boolean> AutoSwitch;
    public final Value<Boolean> WithersOnly;
    public final Value<Float> Delay;
    private Timer timer;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    
    public AutoNameTag() {
        super("AutoNameTag", new String[] { "" }, "Automatically name tags entities in range, if they meet the requirements.", "NONE", -1, ModuleType.MISC);
        this.Radius = new Value<Integer>("Radius", new String[] { "R" }, "Radius to search for entities", 4, 0, 10, 1);
        this.ReplaceOldNames = new Value<Boolean>("ReplaceOldNames", new String[] { "" }, "Automatically replaces old names of the mobs if a previous nametag was used", true);
        this.AutoSwitch = new Value<Boolean>("AutoSwitch", new String[] { "" }, "Automatically switches to a nametag in your hotbar", false);
        this.WithersOnly = new Value<Boolean>("WithersOnly", new String[] { "" }, "Only renames withers", true);
        this.Delay = new Value<Float>("Delay", new String[] { "D" }, "Delay to use", 1.0f, 0.0f, 10.0f, 1.0f);
        this.timer = new Timer();
        int l_Slot;
        int l_I;
        ItemStack l_Stack;
        ItemStack l_Stack2;
        EntityLivingBase l_Entity;
        double[] l_Pos;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (this.mc.currentScreen == null) {
                if (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemNameTag)) {
                    l_Slot = -1;
                    if (this.AutoSwitch.getValue()) {
                        for (l_I = 0; l_I < 9; ++l_I) {
                            l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                            if (!l_Stack.isEmpty()) {
                                if (l_Stack.getItem() instanceof ItemNameTag) {
                                    if (!(!l_Stack.hasDisplayName())) {
                                        l_Slot = l_I;
                                        this.mc.player.inventory.currentItem = l_Slot;
                                        this.mc.playerController.updateController();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (l_Slot == -1) {
                        return;
                    }
                }
                l_Stack2 = this.mc.player.getHeldItemMainhand();
                if (!(!l_Stack2.hasDisplayName())) {
                    l_Entity = (EntityLivingBase)this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidEntity(p_Entity, l_Stack2.getDisplayName())).map(p_Entity -> (EntityLivingBase)p_Entity).min(Comparator.comparing(p_Entity -> this.mc.player.getDistance(p_Entity))).orElse(null);
                    if (l_Entity != null) {
                        this.timer.reset();
                        p_Event.cancel();
                        l_Pos = EntityUtil.calculateLookAt(l_Entity.posX, l_Entity.posY, l_Entity.posZ, (EntityPlayer)this.mc.player);
                        this.SendMessage(String.format("Gave %s the nametag of %s", l_Entity.getName(), l_Stack2.getDisplayName()));
                        p_Event.setPitch(l_Pos[1]);
                        p_Event.setYaw(l_Pos[0]);
                        this.mc.getConnection().sendPacket((Packet)new CPacketUseEntity((Entity)l_Entity, EnumHand.MAIN_HAND));
                    }
                }
            }
        });
    }
    
    private boolean IsValidEntity(final Entity p_Entity, final String p_Name) {
        return p_Entity instanceof EntityLivingBase && p_Entity.getDistance((Entity)this.mc.player) <= this.Radius.getValue() && !(p_Entity instanceof EntityPlayer) && (p_Entity.getCustomNameTag().isEmpty() || this.ReplaceOldNames.getValue()) && (!this.ReplaceOldNames.getValue() || p_Entity.getCustomNameTag().isEmpty() || !p_Entity.getName().equals(p_Name)) && (!this.WithersOnly.getValue() || p_Entity instanceof EntityWither);
    }
}
