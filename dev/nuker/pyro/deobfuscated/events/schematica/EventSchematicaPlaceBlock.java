// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.schematica;

import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventSchematicaPlaceBlock extends MinecraftEvent
{
    public BlockPos Pos;
    
    public EventSchematicaPlaceBlock(final BlockPos p_Pos) {
        this.Pos = p_Pos;
    }
}
