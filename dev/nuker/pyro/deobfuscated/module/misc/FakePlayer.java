//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;
import java.nio.charset.StandardCharsets;
import java.net.URL;
import com.google.gson.JsonParser;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class FakePlayer extends Module
{
    public static final Value<String> name;
    private EntityOtherPlayerMP _fakePlayer;
    
    public FakePlayer() {
        super("FakePlayer", new String[] { "Fake" }, "Summons a fake player", "NONE", 14342949, ModuleType.MISC);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this._fakePlayer = null;
        if (this.mc.world == null) {
            this.toggle();
            return;
        }
        try {
            this._fakePlayer = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(UUID.fromString(getUuid(FakePlayer.name.getValue())), (String)FakePlayer.name.getValue()));
        }
        catch (Exception e) {
            this._fakePlayer = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(UUID.fromString("70ee432d-0a96-4137-a2c0-37cc9df67f03"), (String)FakePlayer.name.getValue()));
            this.SendMessage("Failed to load uuid, setting another one.");
        }
        this.SendMessage(String.format("%s has been spawned.", FakePlayer.name.getValue()));
        this._fakePlayer.copyLocationAndAnglesFrom((Entity)this.mc.player);
        this._fakePlayer.rotationYawHead = this.mc.player.rotationYawHead;
        this.mc.world.addEntityToWorld(-100, (Entity)this._fakePlayer);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.world.removeEntity((Entity)this._fakePlayer);
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    public static String getUuid(final String name) {
        final JsonParser parser = new JsonParser();
        final String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            final String UUIDJson = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
            if (UUIDJson.isEmpty()) {
                return "invalid name";
            }
            final JsonObject UUIDObject = (JsonObject)parser.parse(UUIDJson);
            return reformatUuid(UUIDObject.get("id").toString());
        }
        catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    
    private static String reformatUuid(final String uuid) {
        String longUuid = "";
        longUuid = longUuid + uuid.substring(1, 9) + "-";
        longUuid = longUuid + uuid.substring(9, 13) + "-";
        longUuid = longUuid + uuid.substring(13, 17) + "-";
        longUuid = longUuid + uuid.substring(17, 21) + "-";
        longUuid += uuid.substring(21, 33);
        return longUuid;
    }
    
    static {
        name = new Value<String>("Name", new String[] { "name" }, "Name of the fake player", "jared2013");
    }
}
