// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.HauseHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.JohnHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.GayHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.Boost;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.Frame;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.YPort;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.SNCPBHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.NCPBHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.Strafe;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.OnGround;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.LowHop;
import dev.nuker.pyro.deobfuscated.module.movement.speed.modes.BunnyHop;
import dev.nuker.pyro.deobfuscated.module.ValueListeners;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMove;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJump;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.movement.speed.SpeedMode;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Speed extends Module
{
    public final Value<String> Mode;
    public static final Value<Boolean> UseTimer;
    public static final Value<Boolean> AutoSprint;
    public static final Value<Boolean> SpeedInWater;
    public static final Value<Boolean> AutoJump;
    public static final Value<Boolean> Strict;
    private SpeedMode currSpeedMode;
    @EventHandler
    private Listener<EventPlayerUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerJump> OnPlayerJump;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    @EventHandler
    private Listener<EventPlayerMove> OnPlayerMove;
    @EventHandler
    private Listener<EventServerPacket> onPlayerPosLook;
    
    public Speed() {
        super("Speed", new String[] { "Strafe" }, "Speed strafe", "NONE", 3643061, ModuleType.MOVEMENT);
        this.Mode = new Value<String>("Mode", new String[] { "Mode" }, "The mode of speed to use", "Strafe");
        this.OnPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.setMetaData(String.valueOf(this.Mode.getValue()));
            this.currSpeedMode.onPlayerUpdate(event);
            return;
        });
        this.OnPlayerJump = new Listener<EventPlayerJump>(event -> this.currSpeedMode.onPlayerJump(event));
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> this.currSpeedMode.onMotionUpdates(event));
        this.OnPlayerMove = new Listener<EventPlayerMove>(event -> this.currSpeedMode.onPlayerMove(event));
        this.onPlayerPosLook = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre && event.getPacket() instanceof SPacketPlayerPosLook) {
                this.currSpeedMode.onRubberband();
            }
            return;
        });
        this.Mode.addString("Strafe");
        this.Mode.addString("OnGround");
        this.Mode.addString("BunnyHop");
        this.Mode.addString("LowHop");
        this.Mode.addString("NCPBHop");
        this.Mode.addString("SNCPBHop");
        this.Mode.addString("YPort");
        this.Mode.addString("Frame");
        this.Mode.addString("Boost");
        this.Mode.addString("GayHop");
        this.Mode.addString("JohnHop");
        this.Mode.addString("HauseHop");
        this.updateSpeedMode();
        this.Mode.Listener = new ValueListeners() {
            @Override
            public void OnValueChange(final Value<?> p_Val) {
                Speed.this.updateSpeedMode();
            }
        };
    }
    
    private void updateSpeedMode() {
        final String s = this.Mode.getValue();
        switch (s) {
            case "BunnyHop": {
                this.currSpeedMode = new BunnyHop();
                break;
            }
            case "LowHop": {
                this.currSpeedMode = new LowHop();
                break;
            }
            case "OnGround": {
                this.currSpeedMode = new OnGround();
                break;
            }
            case "Strafe": {
                this.currSpeedMode = new Strafe();
                break;
            }
            case "NCPBHop": {
                this.currSpeedMode = new NCPBHop();
                break;
            }
            case "SNCPBHop": {
                this.currSpeedMode = new SNCPBHop();
                break;
            }
            case "YPort": {
                this.currSpeedMode = new YPort();
                break;
            }
            case "Frame": {
                this.currSpeedMode = new Frame();
                break;
            }
            case "Boost": {
                this.currSpeedMode = new Boost();
                break;
            }
            case "GayHop": {
                this.currSpeedMode = new GayHop();
                break;
            }
            case "JohnHop": {
                this.currSpeedMode = new JohnHop();
                break;
            }
            case "HauseHop": {
                this.currSpeedMode = new HauseHop();
                break;
            }
        }
        this.setMetaData(String.valueOf(this.Mode.getValue()));
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        PyroStatic.TIMER.SetOverrideSpeed(1.0f);
        this.updateSpeedMode();
    }
    
    static {
        UseTimer = new Value<Boolean>("UseTimer", new String[] { "UseTimer" }, "Uses timer to go faster", false);
        AutoSprint = new Value<Boolean>("AutoSprint", new String[] { "AutoSprint" }, "Automatically sprints for you", false);
        SpeedInWater = new Value<Boolean>("SpeedInWater", new String[] { "SpeedInWater" }, "Speeds in water", false);
        AutoJump = new Value<Boolean>("AutoJump", new String[] { "AutoJump" }, "Automatically jumps", true);
        Strict = new Value<Boolean>("Strict", new String[] { "Strict" }, "Strict mode, use this for when hauses patch comes back for strafe", false);
    }
    
    public enum Modes
    {
        Strafe, 
        OnGround, 
        BunnyHop, 
        LowHop, 
        NCPBHop, 
        SNCPBHop, 
        YPort, 
        Frame, 
        Boost, 
        HauseHop;
    }
}
