//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.managers.DiscordManager;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class DiscordRPC extends Module
{
    public final Value<Boolean> Username;
    public final Value<Boolean> ServerIP;
    public final Value<String> DetailsAddon;
    public final Value<Boolean> Speed;
    public final Value<Boolean> Movement;
    public final Value<Boolean> Crystalling;
    public final Value<Boolean> Health;
    
    public DiscordRPC() {
        super("DiscordRPC", new String[] { "RPC" }, "Shows discord rich presence for this mod", "NONE", -1, ModuleType.MISC);
        this.Username = new Value<Boolean>("Username", new String[] { "U" }, "Displays your username in the rich presence", true);
        this.ServerIP = new Value<Boolean>("ServerIP", new String[] { "S" }, "Displays your current playing server in the rich presence", true);
        this.DetailsAddon = new Value<String>("DetailsAddon", new String[] { "D" }, "Displays a custom message after the previous", "Gaming");
        this.Speed = new Value<Boolean>("Speed", new String[] { "U" }, "Displays your speed in the rich presence", true);
        this.Movement = new Value<Boolean>("Movement", new String[] { "U" }, "Displays if you're flying/onground in the rich presence", true);
        this.Crystalling = new Value<Boolean>("Crystalling", new String[] { "U" }, "Displays the current target from autocrystal", true);
        this.Health = new Value<Boolean>("Health", new String[] { "U" }, "Displays your Health in the rich presence", true);
        this.setEnabled(true);
    }
    
    @Override
    public void init() {
        if (this.isEnabled()) {
            DiscordManager.Get().enable();
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        DiscordManager.Get().enable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        try {
            DiscordManager.Get().disable();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public String generateDetails() {
        String result = this.DetailsAddon.getValue();
        if (result == null) {
            result = "";
        }
        if (this.DetailsAddon.getValue() != "") {
            if (this.ServerIP.getValue() && this.Username.getValue()) {
                result = Wrapper.GetMC().session.getUsername() + " | " + ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "none") + " | " + result;
            }
            else if (this.Username.getValue()) {
                result = Wrapper.GetMC().session.getUsername() + " | " + result;
            }
            else if (this.ServerIP.getValue()) {
                result = ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "none") + " | " + result;
            }
        }
        else if (this.Username.getValue() && this.ServerIP.getValue()) {
            result = Wrapper.GetMC().session.getUsername() + " | " + ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "none");
        }
        else if (this.Username.getValue()) {
            result = Wrapper.GetMC().session.getUsername();
        }
        else if (this.ServerIP.getValue()) {
            result = ((Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "none");
        }
        return result;
    }
    
    public String generateState() {
        if (this.mc.player == null) {
            return "Loading...";
        }
        String result = "";
        if (this.Crystalling.getValue() && PyroStatic.AUTOCRYSTAL.isEnabled() && PyroStatic.AUTOCRYSTAL.getTarget() != null) {
            return "Crystalling " + PyroStatic.AUTOCRYSTAL.getTarget() + " with Pyro!";
        }
        if (this.Movement.getValue()) {
            result = (this.mc.player.onGround ? "On the ground" : "Airborne");
            if (this.mc.player.isElytraFlying()) {
                result = "Zooming";
            }
        }
        if (this.Speed.getValue()) {
            final float speed = PlayerUtil.getSpeedInKM();
            if (result.isEmpty()) {
                result = "Moving " + speed + " km/h";
            }
            else if (result.equals("Zooming")) {
                result = result + " at " + speed + " km/h";
            }
            else {
                result = result + " going " + speed + " km/h";
            }
        }
        if (this.Health.getValue()) {
            if (!result.isEmpty()) {
                result += " ";
            }
            result = result + Math.floor(this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) + " hp";
        }
        return result;
    }
}
