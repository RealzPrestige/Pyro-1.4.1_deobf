//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.util.MovementInput;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.BaritoneAPI;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.entity.item.EntityBoat;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdateMoveState;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class AutoWalk extends Module
{
    public final Value<String> Mode;
    @EventHandler
    private Listener<EventPlayerUpdateMoveState> OnUpdateMoveState;
    
    public AutoWalk() {
        super("AutoWalk", new String[] { "AW" }, "Automatically walks forward", "NONE", 12723419, ModuleType.MOVEMENT);
        this.Mode = new Value<String>("Mode", new String[] { "Modes", "M" }, "The mode for walking", "Forward");
        MovementInput movementInput;
        double[] dir;
        GoalXZ goal;
        this.OnUpdateMoveState = new Listener<EventPlayerUpdateMoveState>(p_Event -> {
            if (this.Mode.getValue().equals("Forward")) {
                if (!this.NeedPause()) {
                    movementInput = this.mc.player.movementInput;
                    ++movementInput.moveForward;
                    if (this.mc.player.getRidingEntity() instanceof EntityBoat) {
                        dir = MathUtil.directionSpeed(0.4699999988079071);
                        this.mc.player.getRidingEntity().motionX = dir[0];
                        this.mc.player.getRidingEntity().motionZ = dir[1];
                    }
                }
            }
            else {
                BaritoneAPI.getSettings().allowSprint.value = true;
                BaritoneAPI.getSettings().allowBreak.value = false;
                BaritoneAPI.getSettings().primaryTimeoutMS.value = 2000L;
                goal = GoalXZ.fromDirection(this.mc.player.getPositionVector(), this.mc.player.rotationYawHead, 100.0);
                BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath((Goal)goal);
            }
            return;
        });
        this.Mode.addString("Forward");
        this.Mode.addString("Smart");
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoal((Goal)null);
    }
    
    private boolean NeedPause() {
        return false;
    }
}
