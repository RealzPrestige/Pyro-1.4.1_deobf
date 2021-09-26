// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.events.player;

public class EventPlayerMotionUpdateCancelled extends EventPlayerMotionUpdate
{
    public EventPlayerMotionUpdateCancelled(final Stage stage, final float pitch, final float yaw) {
        super(stage, 0.0, 0.0, 0.0, false);
        this._pitch = pitch;
        this._yaw = yaw;
    }
}
