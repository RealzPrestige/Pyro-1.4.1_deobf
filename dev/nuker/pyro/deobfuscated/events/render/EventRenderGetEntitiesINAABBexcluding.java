// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.render;

import com.google.common.base.Predicate;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.Entity;
import net.minecraft.client.multiplayer.WorldClient;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventRenderGetEntitiesINAABBexcluding extends MinecraftEvent
{
    public EventRenderGetEntitiesINAABBexcluding(final WorldClient worldClient, final Entity entityIn, final AxisAlignedBB boundingBox, final Predicate predicate) {
    }
}
