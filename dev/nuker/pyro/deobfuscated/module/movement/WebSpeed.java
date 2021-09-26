//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockCollisionBoundingBox;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class WebSpeed extends Module
{
    public final Value<Boolean> BoundingBox;
    public final Value<Float> BBOffset;
    @EventHandler
    private Listener<EventPlayerUpdate> onUpdate;
    @EventHandler
    private Listener<EventBlockCollisionBoundingBox> onCollisionBoundingBox;
    
    public WebSpeed() {
        super("WebSpeed", new String[] { "WebSpeed" }, "Speed hax in web", "NONE", -1, ModuleType.MOVEMENT);
        this.BoundingBox = new Value<Boolean>("BoundingBox", new String[] { "" }, "Allows you to walk on the web", false);
        this.BBOffset = new Value<Float>("BBOffset", new String[] { "" }, "How much Y to subtract from the BB", 0.25f);
        EntityPlayerSP player;
        EntityPlayerSP player2;
        this.onUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (this.mc.player.isInWeb) {
                this.mc.player.onGround = false;
                this.mc.player.isInWeb = false;
                player = this.mc.player;
                player.motionX *= 0.84;
                player2 = this.mc.player;
                player2.motionZ *= 0.84;
            }
            return;
        });
        Block block;
        this.onCollisionBoundingBox = new Listener<EventBlockCollisionBoundingBox>(event -> {
            if (this.mc.world != null && this.BoundingBox.getValue()) {
                block = this.mc.world.getBlockState(event.getPos()).getBlock();
                if (block.equals(Blocks.WEB)) {
                    event.cancel();
                    event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0.0, (double)this.BBOffset.getValue(), 0.0));
                }
            }
        });
    }
}
