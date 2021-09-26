// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.friend;

public class Friend
{
    private String Name;
    private String Alias;
    private String Cape;
    
    public Friend(final String p_Name, final String p_Alias, final String p_Cape) {
        this.Name = p_Name;
        this.Alias = p_Alias;
        this.Cape = p_Cape;
    }
    
    public void SetAlias(final String p_Alias) {
        this.Alias = p_Alias;
    }
    
    public void SetCape(final String p_Cape) {
        this.Cape = p_Cape;
    }
    
    public String GetName() {
        return this.Name;
    }
    
    public String GetAlias() {
        return this.Alias;
    }
    
    public String GetCape() {
        return this.Cape;
    }
}
