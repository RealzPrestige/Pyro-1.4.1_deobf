// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;

public class EventPlayerSendChatMessage extends MinecraftEvent
{
    public String Message;
    
    public EventPlayerSendChatMessage(final String p_Message) {
        this.Message = p_Message;
    }
}
