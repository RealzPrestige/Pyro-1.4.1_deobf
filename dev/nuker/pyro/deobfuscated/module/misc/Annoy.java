//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.item.Item;
import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.BaritoneAPI;
import net.minecraft.init.Items;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityPlayerSP;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerOnStoppedUsingItem;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Annoy extends Module
{
    public final Value<Boolean> manualTarget;
    public String targetName;
    EntityPlayer target;
    @EventHandler
    private final Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private final Listener<EventPlayerOnStoppedUsingItem> onStopUsingItem;
    
    public Annoy() {
        super("Annoy", new String[] { "Annoy", "Follow" }, "Follows selected player.", "NONE", 14342949, ModuleType.MISC);
        this.manualTarget = new Value<Boolean>("ManualTarget", new String[0], "Identify target manually via command.", true);
        this.targetName = null;
        this.target = null;
        final BlockPos playerPos;
        float distance;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        float distanceToMe;
        boolean isBlockAbovePlayerHead;
        boolean isPlayerInHole;
        boolean isPlayerTrapped;
        float playerHealth;
        BlockPos targetPos;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            playerPos = PlayerUtil.GetLocalPlayerPosFloored();
            distance = 20.0f;
            this.mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                player = iterator.next();
                if (!(player instanceof EntityPlayerSP)) {
                    if (player.getName().equals(this.mc.player.getName())) {
                        continue;
                    }
                    else if (this.manualTarget.getValue()) {
                        if (player.getName().toLowerCase().equals(this.targetName)) {
                            this.target = player;
                        }
                        else if (!player.getName().toLowerCase().equals(this.targetName) && player == this.target) {
                            this.target = null;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        distanceToMe = player.getDistance((Entity)this.mc.player);
                        if (distance > distanceToMe) {
                            distance = distanceToMe;
                            this.target = player;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            if (this.target == null) {
                return;
            }
            else {
                this.setMetaData(this.target.getName());
                isBlockAbovePlayerHead = PlayerUtil.isBlockAbovePlayerHead((EntityPlayer)this.mc.player, false);
                isPlayerInHole = PlayerUtil.IsPlayerInHole();
                isPlayerTrapped = (PlayerUtil.IsPlayerTrapped() || (isPlayerInHole && isBlockAbovePlayerHead));
                playerHealth = PlayerUtil.GetHealthWithAbsorption();
                if (playerHealth <= 20.0f) {
                    this.eat(Items.GOLDEN_APPLE);
                }
                else if (isPlayerTrapped) {
                    this.eat(Items.CHORUS_FRUIT);
                }
                if (!isPlayerTrapped) {
                    targetPos = PlayerUtil.EntityPosToFloorBlockPos((Entity)this.target);
                    if (targetPos != playerPos) {
                        BaritoneAPI.getSettings().allowParkour.value = true;
                        BaritoneAPI.getSettings().allowSprint.value = true;
                        BaritoneAPI.getSettings().allowBreak.value = false;
                        BaritoneAPI.getSettings().primaryTimeoutMS.value = 2000L;
                        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)new GoalBlock(targetPos));
                    }
                }
                return;
            }
        });
        this.onStopUsingItem = new Listener<EventPlayerOnStoppedUsingItem>(event -> {
            if (this.mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE || this.mc.player.getHeldItemMainhand().getItem() == Items.CHORUS_FRUIT) {
                event.cancel();
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoal((Goal)null);
        this.targetName = null;
        this.target = null;
    }
    
    private void eat(final Item item) {
        if (PlayerUtil.GetItemCount(item) > 0) {
            if (PlayerUtil.IsEating()) {
                return;
            }
            if (!this.mc.player.getHeldItemMainhand().getItem().equals(item)) {
                PlayerUtil.switchToItem(item);
            }
            else {
                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            }
        }
    }
}
