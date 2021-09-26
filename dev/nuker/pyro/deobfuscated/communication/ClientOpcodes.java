// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.communication;

public enum ClientOpcodes
{
    CMSG_RIDE_DONKEY(1), 
    CMSG_END_OF_ROAD(2), 
    CMSG_CHUNKS_LOADED(3), 
    CMSG_REMOUNT_DESYNC(4), 
    CMSG_CLOSE_SOCKET(5), 
    CMSG_PING(6), 
    CMSG_DESYNCER(7), 
    CMSG_OTHER_ACC(8), 
    CMSG_DISMOUNTED_DONKEY(9), 
    CMSG_TEST_OPCODE(10), 
    INVALID_OPCODE(47837);
    
    int _internal;
    
    private ClientOpcodes(final int i) {
        this._internal = i;
    }
    
    public int GetID() {
        return this._internal;
    }
    
    public boolean IsEmpty() {
        return this.equals(ClientOpcodes.INVALID_OPCODE);
    }
    
    public boolean Compare(final int i) {
        return this._internal == i;
    }
    
    public static ClientOpcodes GetValue(final int val) {
        final ClientOpcodes[] As = values();
        for (int i = 0; i < As.length; ++i) {
            if (As[i].Compare(val)) {
                return As[i];
            }
        }
        return ClientOpcodes.INVALID_OPCODE;
    }
}
