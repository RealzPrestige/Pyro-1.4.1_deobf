// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module;

import java.util.ArrayList;
import java.util.List;

public class Value<T>
{
    private String name;
    private String[] alias;
    private String desc;
    private Module Mod;
    public ValueListeners Listener;
    private T value;
    private T min;
    private T max;
    private T inc;
    private List<String> modes;
    
    public Value(final String name, final String[] alias, final String desc) {
        this.modes = new ArrayList<String>();
        this.name = name;
        this.alias = alias;
        this.desc = desc;
    }
    
    public Value(final String name, final String[] alias, final String desc, final T value) {
        this(name, alias, desc);
        this.value = value;
    }
    
    public Value(final String name, final String[] alias, final String desc, final T value, final T min, final T max, final T inc) {
        this(name, alias, desc, value);
        this.min = min;
        this.max = max;
        this.inc = inc;
    }
    
    public Value(final String string, final T val) {
        this(string, new String[0], "", val);
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        if (this.min != null && this.max != null) {
            final Number val = (Number)value;
            final Number min = (Number)this.min;
            final Number max = (Number)this.max;
            this.value = (T)val;
        }
        else {
            this.value = value;
        }
        if (this.Mod != null) {
            this.Mod.SignalValueChange(this);
        }
        if (this.Listener != null) {
            this.Listener.OnValueChange(this);
        }
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public T getInc() {
        return this.inc;
    }
    
    public void setInc(final T inc) {
        this.inc = inc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String[] getAlias() {
        return this.alias;
    }
    
    public void setAlias(final String[] alias) {
        this.alias = alias;
    }
    
    public String getDesc() {
        return this.desc;
    }
    
    public void setDesc(final String desc) {
        this.desc = desc;
    }
    
    public void SetListener(final ValueListeners p_VListener) {
        this.Listener = p_VListener;
    }
    
    public void InitalizeMod(final Module p_Mod) {
        this.Mod = p_Mod;
    }
    
    public void SetForcedValue(final T value) {
        if (this.min != null && this.max != null) {
            final Number val = (Number)value;
            final Number min = (Number)this.min;
            final Number max = (Number)this.max;
            this.value = (T)val;
        }
        else {
            this.value = value;
        }
    }
    
    public void addString(final String string) {
        this.modes.add(string);
    }
    
    public String getNextStringValue(final boolean recursive) {
        if (this.modes.isEmpty()) {
            return this.getValue();
        }
        final int currIndex = this.modes.indexOf(this.getValue());
        if (currIndex == -1) {
            return this.modes.get(0);
        }
        if (currIndex == this.modes.size() - 1) {
            return recursive ? this.modes.get(currIndex - 1) : this.modes.get(0);
        }
        return recursive ? ((currIndex == 0) ? this.modes.get(this.modes.size() - 1) : this.modes.get(currIndex - 1)) : this.modes.get(currIndex + 1);
    }
    
    public void setStringValue(final String unknownValue) {
        if (this.modes.isEmpty()) {
            this.SetForcedValue(unknownValue);
        }
        else if (this.modes.contains(unknownValue)) {
            this.SetForcedValue(unknownValue);
        }
    }
}
