//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.world.EventGetSkyColor;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class SkyRender extends Module
{
    public final Value<Float> OWRed;
    public final Value<Float> OWGreen;
    public final Value<Float> OWBlue;
    public final Value<Float> NERed;
    public final Value<Float> NEGreen;
    public final Value<Float> NEBlue;
    public final Value<Float> ENDRed;
    public final Value<Float> ENDGreen;
    public final Value<Float> ENDBlue;
    @EventHandler
    private Listener<EventGetSkyColor> onCollisionBoundingBox;
    
    public SkyRender() {
        super("SkyRender", new String[] { "SkyColor" }, "Changes color of the Sky.", "NONE", -1, ModuleType.WORLD);
        this.OWRed = new Value<Float>("OwRed", new String[] { "OwR" }, "Amount of red for the overworld sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.OWGreen = new Value<Float>("OwGreen", new String[] { "OwG" }, "Amount of green for the overworld sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.OWBlue = new Value<Float>("OwBlue", new String[] { "OwB" }, "Amount of blue for the overworld sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.NERed = new Value<Float>("NetherRed", new String[] { "NeR" }, "Amount of red for the Nether sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.NEGreen = new Value<Float>("NetherGreen", new String[] { "NeG" }, "Amount of green for the Nether sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.NEBlue = new Value<Float>("NetherBlue", new String[] { "NeB" }, "Amount of blue for the Nether sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ENDRed = new Value<Float>("EndRed", new String[] { "EndR" }, "Amount of red for the end sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ENDGreen = new Value<Float>("EndGreen", new String[] { "EndG" }, "Amount of green for the end sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ENDBlue = new Value<Float>("EndBlue", new String[] { "EndB" }, "Amount of blue for the end sky.", 0.0f, 0.0f, 1.0f, 0.1f);
        this.onCollisionBoundingBox = new Listener<EventGetSkyColor>(event -> {
            if (this.mc.world != null) {
                event.cancel();
                if (this.mc.player.dimension == -1) {
                    event.setColor(new Vec3d((double)this.NERed.getValue(), (double)this.NEGreen.getValue(), (double)this.NEBlue.getValue()));
                }
                if (this.mc.player.dimension == 0) {
                    event.setColor(new Vec3d((double)this.OWRed.getValue(), (double)this.OWGreen.getValue(), (double)this.OWBlue.getValue()));
                }
                if (this.mc.player.dimension == 1) {
                    event.setColor(new Vec3d((double)this.ENDRed.getValue(), (double)this.ENDGreen.getValue(), (double)this.ENDBlue.getValue()));
                }
            }
        });
    }
}
