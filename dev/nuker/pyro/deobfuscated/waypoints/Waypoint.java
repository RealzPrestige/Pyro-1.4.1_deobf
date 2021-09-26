// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.waypoints;

import dev.nuker.pyro.deobfuscated.util.SalVec3d;

public class Waypoint
{
    private String _displayName;
    private SalVec3d _pos;
    private Type _type;
    private String _address;
    private int _dimension;
    
    public Waypoint(final String displayName, final SalVec3d pos, final Type type, final String address, final int dimension) {
        this._displayName = displayName;
        this._pos = pos;
        this._type = type;
        this._address = address;
        this._dimension = dimension;
    }
    
    @Override
    public String toString() {
        return this._pos.toString() + " Type: " + String.valueOf(this._type);
    }
    
    public Type getType() {
        return this._type;
    }
    
    public String getDisplayName() {
        return this._displayName;
    }
    
    public double getX() {
        return this._pos.x;
    }
    
    public double getY() {
        return this._pos.y;
    }
    
    public double getZ() {
        return this._pos.z;
    }
    
    public void setPos(final SalVec3d pos) {
        this._pos = pos;
    }
    
    public String getAddress() {
        return this._address;
    }
    
    public int getDimension() {
        return this._dimension;
    }
    
    public enum Type
    {
        Normal, 
        Logout, 
        Death, 
        CoordTPExploit;
    }
}
