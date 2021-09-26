// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.communication;

import java.util.Arrays;
import java.util.ArrayList;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.function.Consumer;
import java.util.HashMap;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream out;
    private ConcurrentLinkedQueue<Packet> _packetQueue;
    private ByteBuffer prevBuffer;
    private HashMap<ServerOpcodes, Consumer<Packet>> handlers;
    
    public Client(final String address, final int port) {
        this.socket = null;
        this.input = null;
        this.out = null;
        this._packetQueue = new ConcurrentLinkedQueue<Packet>();
        this.prevBuffer = null;
        this.handlers = new HashMap<ServerOpcodes, Consumer<Packet>>();
        try {
            this.socket = new Socket(address, port);
            System.out.println("Connected");
            this.input = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
        }
        catch (UnknownHostException u) {
            System.out.println(u);
        }
        catch (IOException i) {
            System.out.println(i);
        }
        final ServerHandlers serverHandlers = new ServerHandlers(this);
        this.handlers.put(ServerOpcodes.SMSG_START_DUPE, serverHandlers.OnStartDupe);
        this.handlers.put(ServerOpcodes.SMSG_STOP_DUPE, serverHandlers.OnStopDupe);
        this.handlers.put(ServerOpcodes.SMSG_RIDE_DONKEY, serverHandlers.OnRideDonkey);
        this.handlers.put(ServerOpcodes.SMSG_END_OF_ROAD, serverHandlers.OnEndOfRoad);
        this.handlers.put(ServerOpcodes.SMSG_CHUNKS_LOADED, serverHandlers.OnChunksLoaded);
        this.handlers.put(ServerOpcodes.SMSG_REMOUNT_DESYNC, serverHandlers.OnRemountDesync);
        this.handlers.put(ServerOpcodes.SMSG_SHUTDOWN, serverHandlers.OnShutdown);
        this.handlers.put(ServerOpcodes.SMSG_PONG, serverHandlers.OnPong);
        this.handlers.put(ServerOpcodes.SMSG_READY, serverHandlers.OnReady);
        this.handlers.put(ServerOpcodes.SMSG_DISMOUNTED_DONKEY, serverHandlers.OnDismountedDonkey);
        this.handlers.put(ServerOpcodes.SMSG_TEST_OPCODE, serverHandlers.OnTestOpcode);
        this.handlers.put(ServerOpcodes.SMSG_INVALID_OPCODE, serverHandlers.OnInvalidOpcode);
    }
    
    private void HandleOpcode(final Packet packet) throws IOException {
        final ServerOpcodes opcode = ServerOpcodes.GetValue(packet.GetOpcode());
        if (this.handlers.containsKey(opcode)) {
            this.handlers.get(opcode).accept(packet);
            return;
        }
        System.out.println("Recvieied unhandled opcode " + Integer.toHexString(packet.GetOpcode()));
    }
    
    public void SendOpcodeUnsafe(final Packet packet) throws IOException {
        packet.Write(this.out);
        this.out.flush();
    }
    
    public void SendOpcodeSafe(final Packet packet) {
        this._packetQueue.add(packet);
    }
    
    public void Update() {
        while (true) {
            try {
                while (this.input != null && this.input.available() > 0) {
                    int totalBytes = 0;
                    final ArrayList<byte[]> byteArray = new ArrayList<byte[]>();
                    if (this.prevBuffer != null) {
                        byteArray.add(this.prevBuffer.array());
                        totalBytes += this.prevBuffer.array().length;
                    }
                    while (this.input.available() > 0) {
                        final int bytes = this.input.available();
                        totalBytes += bytes;
                        final byte[] data = new byte[bytes];
                        this.input.readFully(data);
                        byteArray.add(data);
                    }
                    ByteBuffer buffer = ByteBuffer.allocate(totalBytes);
                    for (final byte[] b : byteArray) {
                        buffer.put(b);
                    }
                    byte[] bufferArray = buffer.array();
                    this.prevBuffer = buffer;
                    if (bufferArray.length < 8) {
                        break;
                    }
                    buffer = ByteBuffer.wrap(bufferArray);
                    final ServerOpcodes opcode = ServerOpcodes.GetValue(buffer.getInt(0));
                    if (opcode == ServerOpcodes.SMSG_INVALID_OPCODE) {
                        System.out.println("SMSG_INVALID_OPCODE!");
                    }
                    final int size = buffer.getInt(4);
                    int currPosition = 8;
                    final Packet packet = new Packet(opcode);
                    if (size > 0) {
                        if (bufferArray.length < 8 + size) {
                            System.out.println("Not enough data to read size is " + size + " length is " + bufferArray.length);
                            continue;
                        }
                        packet.InitBytes(Arrays.copyOfRange(bufferArray, currPosition, currPosition + size));
                        currPosition += size;
                    }
                    this.HandleOpcode(packet);
                    bufferArray = Arrays.copyOfRange(bufferArray, currPosition, bufferArray.length);
                    if (bufferArray.length == 0) {
                        this.prevBuffer = null;
                        break;
                    }
                    this.prevBuffer = ByteBuffer.wrap(bufferArray);
                    break;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            try {
                while (!this._packetQueue.isEmpty()) {
                    this.SendOpcodeUnsafe(this._packetQueue.poll());
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
