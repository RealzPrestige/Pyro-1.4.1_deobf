//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import dev.nuker.pyro.deobfuscated.util.CrystalUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.init.Items;
import java.util.function.Function;
import java.util.Comparator;
import net.minecraft.entity.item.EntityEnderCrystal;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoLog extends Module
{
    public final Value<Float> MinHealth;
    public final Value<Boolean> NoTotems;
    public final Value<Boolean> LethalCrystals;
    float potentialCrystalDamage;
    boolean isCrystalLethal;
    @EventHandler
    private final Listener<EventPlayerUpdate> OnPlayerUpdate;
    
    public AutoLog() {
        super("AutoLog", new String[] { "AutoLog" }, "Automatically logs out before death.", "NONE", 14295296, ModuleType.COMBAT);
        this.MinHealth = new Value<Float>("MinHealth", new String[] { "MinHealth" }, "HP to logout at, 2 per heart.", 16.0f, 0.0f, 20.0f, 1.0f);
        this.NoTotems = new Value<Boolean>("NoTotems", new String[] { "NoTotems" }, "Only logs out when no totems are left.", true);
        this.LethalCrystals = new Value<Boolean>("LethalCrystals", new String[] { "LethalCrystals" }, "Logs out when a lethal crystal is detected.", false);
        this.potentialCrystalDamage = 0.0f;
        this.isCrystalLethal = false;
        float playerHealth;
        EntityEnderCrystal crystal;
        boolean healthCheck;
        int totemCount;
        int playerHealthToInt;
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (this.mc.player != null && this.mc.getConnection() != null && this.mc.world != null) {
                playerHealth = PlayerUtil.GetHealthWithAbsorption();
                if (this.LethalCrystals.getValue()) {
                    crystal = (EntityEnderCrystal)this.mc.world.getLoadedEntityList().stream().filter(entity -> entity instanceof EntityEnderCrystal).map(entity -> (EntityEnderCrystal)entity).max(Comparator.comparing((Function<? super T, ? extends Comparable>)this::calculateSelfDamage)).orElse(null);
                    if (crystal != null) {
                        this.potentialCrystalDamage = this.calculateSelfDamage(crystal);
                        if (this.potentialCrystalDamage > playerHealth - 8.0f) {
                            this.isCrystalLethal = true;
                        }
                    }
                    else {
                        this.potentialCrystalDamage = 0.0f;
                        this.isCrystalLethal = false;
                    }
                }
                healthCheck = (playerHealth <= this.MinHealth.getValue());
                totemCount = PlayerUtil.GetItemCount(Items.TOTEM_OF_UNDYING);
                playerHealthToInt = (int)playerHealth;
                if ((healthCheck || (this.LethalCrystals.getValue() && this.isCrystalLethal)) && (totemCount == 0 || !this.NoTotems.getValue())) {
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getRecord(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f));
                    this.sendDisconnectMessage("Logged out with " + ChatFormatting.YELLOW + totemCount + " Totems " + ChatFormatting.RESET + "remaining, and " + this.healthToColor(playerHealth) + playerHealthToInt + " hp.");
                    this.toggle();
                }
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.isCrystalLethal = false;
        this.potentialCrystalDamage = 0.0f;
    }
    
    private ChatFormatting healthToColor(final double health) {
        if (health <= 6.0) {
            return ChatFormatting.RED;
        }
        if (health <= 10.0) {
            return ChatFormatting.YELLOW;
        }
        if (health <= 20.0) {
            return ChatFormatting.GREEN;
        }
        return ChatFormatting.RESET;
    }
    
    private void sendDisconnectMessage(final String message) {
        if (this.mc.getConnection() != null) {
            this.mc.getConnection().handleDisconnect(new SPacketDisconnect((ITextComponent)new TextComponentString(ChatFormatting.RED + "[AutoLog] " + ChatFormatting.RESET + message)));
        }
    }
    
    private float calculateSelfDamage(final EntityEnderCrystal crystal) {
        return CrystalUtils.calculateDamage((World)this.mc.world, crystal.posX, crystal.posY, crystal.posZ, (Entity)this.mc.player, 0);
    }
}
