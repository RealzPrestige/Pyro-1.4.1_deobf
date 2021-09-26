// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated;

import dev.nuker.pyro.deobfuscated.events.bus.EventManager;
import org.apache.logging.log4j.LogManager;
import dev.nuker.pyro.deobfuscated.main.ForgeEventProcessor;
import net.minecraftforge.common.MinecraftForge;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventBus;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "pyro", name = "Pyro", version = "Pyro-1.4.1")
public final class PyroMod
{
    public static final String NAME = "Pyro";
    public static final String VERSION = "1.4.1";
    public static final String WATERMARK = "Pyro 1.4.1";
    public static final Logger log;
    public static final EventBus EVENT_BUS;
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        PyroMod.log.info("init pyro v: 1.4.1");
        Pyro.Init();
        MinecraftForge.EVENT_BUS.register((Object)new ForgeEventProcessor());
    }
    
    static {
        log = LogManager.getLogger("pyro");
        EVENT_BUS = new EventManager();
    }
}
