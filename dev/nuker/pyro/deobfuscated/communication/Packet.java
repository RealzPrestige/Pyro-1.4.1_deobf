// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.communication;

import java.nio.charset.StandardCharsets;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.DataOutputStream;

public class Packet
{
    private int _opcodeVal;
    private Type _type;
    private byte[] _data;
    private int _offset;
    
    public Packet(final int opcodeVal, final Type type) {
        this._opcodeVal = opcodeVal;
        this._type = type;
        this._offset = 0;
    }
    
    public Packet(final ClientOpcodes opcode) {
        this(opcode.GetID(), Type.Client);
    }
    
    public Packet(final ServerOpcodes opcode) {
        this(opcode.GetID(), Type.Server);
    }
    
    public int GetOpcode() {
        return this._opcodeVal;
    }
    
    public Type GetType() {
        return this._type;
    }
    
    public void Write(final DataOutputStream outputStream) throws IOException {
        final int byteLength = (this._data != null) ? this._data.length : 0;
        outputStream.writeInt(this.GetOpcode());
        outputStream.writeInt(byteLength);
        if (byteLength > 0) {
            outputStream.write(this._data);
        }
    }
    
    public void InitBytes(final byte[] data) throws IOException {
        this._data = data;
    }
    
    public int ReadInt32() {
        final ByteBuffer wrapped = ByteBuffer.wrap(this._data, this._offset, 4);
        this._offset += 4;
        return wrapped.getInt();
    }
    
    public String ReadString() {
        final int size = this.ReadInt32();
        final ByteBuffer wrapped = ByteBuffer.wrap(this._data, this._offset, size);
        final byte[] byteString = new byte[size];
        wrapped.get(byteString);
        this._offset += size;
        return new String(byteString, StandardCharsets.UTF_8);
    }
    
    public void WriteNewBytes(final byte[] append) {
        final byte[] oldData = this._data;
        this._data = new byte[this._offset + append.length];
        if (oldData != null) {
            for (int i = 0; i < oldData.length; ++i) {
                this._data[i] = oldData[i];
            }
        }
        for (int i = 0; i < append.length; ++i) {
            this._data[i + this._offset] = append[i];
        }
    }
    
    public int WriteInt32(final int result) {
        final ByteBuffer dbuf = ByteBuffer.allocate(4);
        dbuf.putInt(result);
        this.WriteNewBytes(dbuf.array());
        this._offset += 4;
        return result;
    }
    
    public void WriteString(final String string) {
        final int len = string.length();
        final ByteBuffer dbuf = ByteBuffer.allocate(len);
        dbuf.put(string.getBytes());
        final byte[] stringByteArray = dbuf.array();
        this.WriteInt32(stringByteArray.length);
        this.WriteNewBytes(dbuf.array());
        this._offset += stringByteArray.length;
    }
    
    public enum Type
    {
        Client, 
        Server;
    }
}
