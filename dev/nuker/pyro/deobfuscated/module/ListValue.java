// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module;

public class ListValue extends Value<String>
{
    public ListValue(final String name, final String[] alias, final String desc, final String[] vals) {
        super(name, alias, desc, vals[0]);
        for (final String v : vals) {
            this.addString(v);
        }
    }
    
    public ListValue(final String name, final String desc, final String[] vals) {
        this(name, new String[0], desc, vals);
    }
}
