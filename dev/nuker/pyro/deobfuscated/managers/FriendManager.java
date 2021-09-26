//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.Entity;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.OpenOption;
import com.google.gson.GsonBuilder;
import java.io.Reader;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.io.File;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import dev.nuker.pyro.deobfuscated.friend.Friend;
import com.google.gson.internal.LinkedTreeMap;

public class FriendManager
{
    private LinkedTreeMap<String, Friend> FriendList;
    
    public static FriendManager Get() {
        return Pyro.GetFriendManager();
    }
    
    public FriendManager() {
        this.FriendList = (LinkedTreeMap<String, Friend>)new LinkedTreeMap();
    }
    
    public void LoadFriends() {
        final File l_Exists = new File("Pyro/FriendList.json");
        if (!l_Exists.exists()) {
            return;
        }
        try {
            final Gson gson = new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get("Pyro/FriendList.json", new String[0]));
            this.FriendList = (LinkedTreeMap<String, Friend>)gson.fromJson(reader, new TypeToken<LinkedTreeMap<String, Friend>>() {}.getType());
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void SaveFriends() {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.setPrettyPrinting().create();
        try {
            final Writer writer = Files.newBufferedWriter(Paths.get("Pyro/FriendList.json", new String[0]), new OpenOption[0]);
            gson.toJson((Object)this.FriendList, new TypeToken<LinkedTreeMap<String, Friend>>() {}.getType(), (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String GetFriendName(final Entity p_Entity) {
        if (!this.FriendList.containsKey((Object)p_Entity.getName().toLowerCase())) {
            return p_Entity.getName();
        }
        return ((Friend)this.FriendList.get((Object)p_Entity.getName().toLowerCase())).GetAlias();
    }
    
    public boolean IsFriend(final Entity p_Entity) {
        return p_Entity instanceof EntityPlayer && this.FriendList.containsKey((Object)p_Entity.getName().toLowerCase());
    }
    
    public boolean AddFriend(final String p_Name) {
        if (this.FriendList.containsKey((Object)p_Name)) {
            return false;
        }
        final Friend l_Friend = new Friend(p_Name, p_Name, null);
        this.FriendList.put((Object)p_Name, (Object)l_Friend);
        this.SaveFriends();
        return true;
    }
    
    public boolean RemoveFriend(final String p_Name) {
        if (!this.FriendList.containsKey((Object)p_Name)) {
            return false;
        }
        this.FriendList.remove((Object)p_Name);
        this.SaveFriends();
        return true;
    }
    
    public final LinkedTreeMap<String, Friend> GetFriends() {
        return this.FriendList;
    }
    
    public boolean IsFriend(final String p_Name) {
        return PyroStatic.FRIENDS.isEnabled() && this.FriendList.containsKey((Object)p_Name.toLowerCase());
    }
    
    public Friend GetFriend(final Entity e) {
        if (!PyroStatic.FRIENDS.isEnabled()) {
            return null;
        }
        if (!this.FriendList.containsKey((Object)e.getName().toLowerCase())) {
            return null;
        }
        return (Friend)this.FriendList.get((Object)e.getName().toLowerCase());
    }
    
    public void Load() {
        this.LoadFriends();
    }
}
