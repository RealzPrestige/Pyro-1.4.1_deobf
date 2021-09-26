// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import dev.nuker.pyro.deobfuscated.util.PlayerAlert;
import dev.nuker.pyro.deobfuscated.util.RebaneGetter;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Module;

public class QueuePeek extends Module
{
    private Timer timer;
    @EventHandler
    private Listener<EventClientTick> onTick;
    
    public QueuePeek() {
        super("QueuePeek", new String[] { "QP" }, "Notifies you when someone enters the queue", "NONE", -1, ModuleType.MISC);
        this.timer = new Timer();
        this.onTick = new Listener<EventClientTick>(event -> {
            if (this.timer.passed(12000.0)) {
                this.timer.reset();
                new Thread(() -> {
                    RebaneGetter.init();
                    PlayerAlert.updateQueuePositions();
                }).start();
            }
        });
    }
}
