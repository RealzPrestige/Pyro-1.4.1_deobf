// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin;

import java.util.Map;
import dev.nuker.pyro.deobfuscated.PyroMod;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class MixinLoaderForge implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public MixinLoaderForge() {
        MixinBootstrap.init();
        Mixins.addConfigurations(new String[] { "mixins.baritone.json", "mixins.pyro.json" });
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        PyroMod.log.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        MixinLoaderForge.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        MixinLoaderForge.isObfuscatedEnvironment = false;
    }
}
