// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.communication;

public enum ServerOpcodes
{
    SMSG_START_DUPE(1), 
    SMSG_STOP_DUPE(2), 
    SMSG_RIDE_DONKEY(3), 
    SMSG_END_OF_ROAD(4), 
    SMSG_CHUNKS_LOADED(5), 
    SMSG_REMOUNT_DESYNC(6), 
    SMSG_SHUTDOWN(7), 
    SMSG_PONG(8), 
    SMSG_READY(9), 
    SMSG_DISMOUNTED_DONKEY(10), 
    SMSG_TEST_OPCODE(11), 
    SMSG_INVALID_OPCODE(47837);
    
    int _internal;
    
    private ServerOpcodes(final int i) {
        this._internal = i;
    }
    
    public int GetID() {
        return this._internal;
    }
    
    public boolean IsEmpty() {
        return this.equals(ServerOpcodes.SMSG_INVALID_OPCODE);
    }
    
    public boolean Compare(final int i) {
        return this._internal == i;
    }
    
    public static ServerOpcodes GetValue(final int val) {
        final ServerOpcodes[] As = values();
        for (int i = 0; i < As.length; ++i) {
            if (As[i].Compare(val)) {
                return As[i];
            }
        }
        return ServerOpcodes.SMSG_INVALID_OPCODE;
    }
}
