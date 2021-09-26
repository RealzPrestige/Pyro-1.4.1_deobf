// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.io.File;
import java.io.IOException;

public class DirectoryManager
{
    public void Init() {
        try {
            this.CreateDirectory("Pyro");
            this.CreateDirectory("Pyro/Modules");
            this.CreateDirectory("Pyro/GUI");
            this.CreateDirectory("Pyro/HUD");
            this.CreateDirectory("Pyro/Locater");
            this.CreateDirectory("Pyro/StashFinder");
            this.CreateDirectory("Pyro/Config");
            this.CreateDirectory("Pyro/Capes");
            this.CreateDirectory("Pyro/Music");
            this.CreateDirectory("Pyro/CoordExploit");
            this.CreateDirectory("Pyro/LogoutSpots");
            this.CreateDirectory("Pyro/DeathSpots");
            this.CreateDirectory("Pyro/Waypoints");
            this.CreateDirectory("Pyro/Fonts");
            this.CreateDirectory("Pyro/CustomMods");
            this.CreateDirectory("Pyro/Presets");
            this.CreateDirectory("Pyro/Presets/Default");
            this.CreateDirectory("Pyro/Presets/Default/Modules");
            this.CreateDirectory("Pyro/PacketLogger");
            this.CreateDirectory("Pyro/Macros");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void CreateDirectory(final String p_Path) throws IOException {
        new File(p_Path).mkdirs();
    }
    
    public static DirectoryManager Get() {
        return Pyro.GetDirectoryManager();
    }
    
    public String GetCurrentDirectory() throws IOException {
        return new File(".").getCanonicalPath();
    }
}
