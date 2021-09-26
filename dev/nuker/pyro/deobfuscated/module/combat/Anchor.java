//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.util.Hole;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerTravel;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Anchor extends Module
{
    public final Value<Boolean> disable;
    public final Value<Boolean> toggleStrafe;
    @EventHandler
    private Listener<EventPlayerTravel> OnTravel;
    
    public Anchor() {
        super("Anchor", new String[] { "HoleTP", "HoleF" }, "Stops motion over a hole, and falls down like superman", "NONE", -1, ModuleType.COMBAT);
        this.disable = new Value<Boolean>("Toggles", new String[] { "Toggles", "Disables" }, "toggles off after going in hole", false);
        this.toggleStrafe = new Value<Boolean>("ToggleSpeed", new String[] { "toggleStrafe", "toggleStrafe" }, "toggles off speed", false);
        BlockPos pos;
        int i;
        BlockPos newPos;
        IBlockState state;
        Hole.HoleTypes type;
        Vec3d center;
        double xDiff;
        double zDiff;
        double x;
        double z;
        EntityPlayerSP player;
        this.OnTravel = new Listener<EventPlayerTravel>(event -> {
            if (!event.isCancelled()) {
                pos = PlayerUtil.GetLocalPlayerPosFloored();
                if (!this.mc.player.onGround) {
                    i = 0;
                    while (i < 5) {
                        newPos = pos.add(0, -i, 0);
                        state = this.mc.world.getBlockState(newPos);
                        if (state.getBlock() == Blocks.AIR) {
                            type = HoleFiller.isBlockValid(this.mc, state, newPos);
                            if (type != Hole.HoleTypes.None) {
                                if (PyroStatic.SPEED.isEnabled() && this.toggleStrafe.getValue()) {
                                    PyroStatic.SPEED.toggle();
                                }
                                this.mc.player.motionX = 0.0;
                                this.mc.player.motionZ = 0.0;
                                this.mc.player.movementInput.moveForward = 0.0f;
                                this.mc.player.movementInput.moveStrafe = 0.0f;
                                center = Surround.GetCenter(pos.getX(), pos.getY(), pos.getZ());
                                xDiff = Math.abs(center.x - this.mc.player.posX);
                                zDiff = Math.abs(center.z - this.mc.player.posZ);
                                if (xDiff > 0.1 || zDiff > 0.1) {
                                    x = center.x - this.mc.player.posX;
                                    z = center.z - this.mc.player.posZ;
                                    this.mc.player.motionX = x / 2.0;
                                    if (this.mc.player.motionY >= 0.0) {
                                        player = this.mc.player;
                                        --player.motionY;
                                    }
                                    this.mc.player.motionZ = z / 2.0;
                                    event.cancel();
                                }
                            }
                            else {
                                ++i;
                            }
                        }
                    }
                }
                else if (PlayerUtil.IsPlayerInHole() && this.disable.getValue()) {
                    this.SendMessage("Done");
                    this.toggle();
                }
            }
        });
    }
}
