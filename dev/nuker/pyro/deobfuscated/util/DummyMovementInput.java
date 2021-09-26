//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInputFromOptions;

public class DummyMovementInput extends MovementInputFromOptions
{
    public DummyMovementInput(final GameSettings gameSettingsIn) {
        super(gameSettingsIn);
    }
    
    public void updatePlayerMoveState() {
    }
}
