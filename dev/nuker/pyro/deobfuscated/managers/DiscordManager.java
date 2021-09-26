// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;

public class DiscordManager
{
    private Thread _thread;
    
    public DiscordManager() {
        this._thread = null;
    }
    
    public void enable() {
        final DiscordRPC lib = DiscordRPC.INSTANCE;
        final String applicationId = "";
        final String steamId = "";
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user -> System.out.println("Ready!"));
        lib.Discord_Initialize(applicationId, handlers, true, steamId);
        final DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000L;
        lib.Discord_UpdatePresence(presence);
        presence.largeImageKey = "discordrpc2";
        presence.largeImageText = "Pyro 1.4.1";
        final DiscordRPC discordRPC;
        final DiscordRichPresence discordRichPresence;
        (this._thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                discordRPC.Discord_RunCallbacks();
                discordRichPresence.details = PyroStatic.DISCORDRPC.generateDetails();
                discordRichPresence.state = PyroStatic.DISCORDRPC.generateState();
                discordRPC.Discord_UpdatePresence(discordRichPresence);
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException ex) {}
            }
        }, "RPC-Callback-Handler")).start();
    }
    
    public void disable() throws InterruptedException {
        if (this._thread != null) {
            this._thread.interrupt();
        }
    }
    
    public static DiscordManager Get() {
        return Pyro.GetDiscordManager();
    }
}
