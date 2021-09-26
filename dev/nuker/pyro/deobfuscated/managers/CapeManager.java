//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.io.IOException;
import java.net.MalformedURLException;
import net.minecraft.client.renderer.texture.DynamicTexture;
import javax.imageio.ImageIO;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerGetLocationCape;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.ResourceLocation;
import java.util.HashMap;
import dev.nuker.pyro.deobfuscated.events.bus.EventListener;

public class CapeManager implements EventListener
{
    private HashMap<String, ResourceLocation> CapeUsers;
    private HashMap<String, ResourceLocation> Capes;
    @EventHandler
    private Listener<EventPlayerGetLocationCape> OnGetLocationSkin;
    
    public CapeManager() {
        this.CapeUsers = new HashMap<String, ResourceLocation>();
        this.Capes = new HashMap<String, ResourceLocation>();
        this.OnGetLocationSkin = new Listener<EventPlayerGetLocationCape>(p_Event -> {
            if (Wrapper.GetMC().renderEngine != null) {
                if (this.CapeUsers.containsKey(p_Event.Player.getName())) {
                    p_Event.cancel();
                    p_Event.SetResourceLocation(this.CapeUsers.get(p_Event.Player.getName()));
                }
            }
        });
    }
    
    public void LoadCapes() {
        try {
            URL l_URL = null;
            URLConnection l_Connection = null;
            BufferedReader l_Reader = null;
            System.out.println("Downloading cape imgs");
            l_URL = new URL("http://Pyro.com/cape.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            String l_Line;
            while ((l_Line = l_Reader.readLine()) != null) {
                final String[] l_Split = l_Line.split(" ");
                if (l_Split.length == 2) {
                    this.DownloadCapeFromLocationWithName(l_Split[0], l_Split[1]);
                }
            }
            l_Reader.close();
            System.out.println("Loading capes");
            l_URL = new URL("http://Pyro.com/capes.txt");
            l_Connection = l_URL.openConnection();
            l_Connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
            l_Reader = new BufferedReader(new InputStreamReader(l_Connection.getInputStream()));
            while ((l_Line = l_Reader.readLine()) != null) {
                this.ProcessCapeString(l_Line);
            }
            l_Reader.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("Done loading capes");
    }
    
    private void ProcessCapeString(final String p_String) {
        final String[] l_Split = p_String.split(" ");
        if (l_Split.length == 2) {
            final ResourceLocation l_Cape = this.GetCapeFromName(l_Split[1]);
            if (l_Cape != null) {
                this.CapeUsers.put(l_Split[0], l_Cape);
            }
            else {
                System.out.println("Invalid cape name " + l_Split[1] + " for user " + l_Split[0]);
            }
        }
    }
    
    private final ResourceLocation GetCapeFromName(final String p_Name) {
        if (!this.Capes.containsKey(p_Name)) {
            return null;
        }
        return this.Capes.get(p_Name);
    }
    
    public void DownloadCapeFromLocationWithName(final String p_Link, final String p_Name) throws MalformedURLException, IOException {
        final DynamicTexture l_Texture = new DynamicTexture(ImageIO.read(new URL(p_Link)));
        this.Capes.put(p_Name, Wrapper.GetMC().getTextureManager().getDynamicTextureLocation("Pyro/capes", l_Texture));
    }
    
    public static CapeManager Get() {
        return Pyro.GetCapeManager();
    }
    
    public void Init() {
        this.LoadCapes();
        PyroMod.EVENT_BUS.subscribe(this);
    }
}
