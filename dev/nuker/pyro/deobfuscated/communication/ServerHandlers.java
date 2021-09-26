// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.communication;

import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import java.util.function.Consumer;

public class ServerHandlers
{
    private Client _client;
    Consumer<Packet> OnStartDupe;
    Consumer<Packet> OnStopDupe;
    Consumer<Packet> OnRideDonkey;
    Consumer<Packet> OnEndOfRoad;
    Consumer<Packet> OnChunksLoaded;
    Consumer<Packet> OnRemountDesync;
    Consumer<Packet> OnShutdown;
    Consumer<Packet> OnPong;
    Consumer<Packet> OnReady;
    Consumer<Packet> OnDismountedDonkey;
    Consumer<Packet> OnTestOpcode;
    Consumer<Packet> OnInvalidOpcode;
    
    public ServerHandlers(final Client client) {
        this.OnStartDupe = (packet -> PyroStatic.DUPEBOT.OnStartDupe());
        this.OnStopDupe = (packet -> PyroStatic.DUPEBOT.OnStopDupe());
        this.OnRideDonkey = (packet -> PyroStatic.DUPEBOT.OnRideDonkey());
        this.OnEndOfRoad = (packet -> PyroStatic.DUPEBOT.OnEndOfRoad());
        this.OnChunksLoaded = (packet -> PyroStatic.DUPEBOT.OnChunksLoad());
        this.OnRemountDesync = (packet -> PyroStatic.DUPEBOT.OnRemountDesync());
        this.OnShutdown = (packet -> PyroStatic.DUPEBOT.OnShutdown());
        this.OnPong = (packet -> this._client.SendOpcodeSafe(new Packet(ClientOpcodes.CMSG_PING)));
        this.OnReady = (packet -> PyroStatic.DUPEBOT.OnReady(packet));
        this.OnDismountedDonkey = (packet -> PyroStatic.DUPEBOT.OnDismountedDonkey());
        this.OnTestOpcode = (packet -> {});
        this.OnInvalidOpcode = (packet -> System.out.println("Recieved invalid opcode..."));
        this._client = client;
    }
}
