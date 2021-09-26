// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import java.util.Base64;
import org.json.simple.JSONArray;
import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.HashMap;

public class MojangGetter
{
    private static HashMap<String, String> savedUUIDNames;
    
    public static String[] getInfoFromName(final String name) {
        try {
            final String[] toReturn = new String[2];
            final JSONParser jsonparse = new JSONParser();
            final JSONObject json = (JSONObject)jsonparse.parse(IOUtils.toString(new URL("https://api.mojang.com/users/profiles/minecraft/" + name), Charset.defaultCharset()));
            toReturn[0] = json.get("id");
            toReturn[1] = json.get("name");
            return toReturn;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String getCurrentNameFromUUID(final String uuid) {
        if (MojangGetter.savedUUIDNames.containsKey(uuid)) {
            return MojangGetter.savedUUIDNames.get(uuid);
        }
        try {
            final String url = String.format("https://api.mojang.com/user/profiles/%s/names", uuid);
            final JSONParser jsonparse = new JSONParser();
            final JSONArray json = (JSONArray)jsonparse.parse(IOUtils.toString(new URL(url), Charset.defaultCharset()));
            final int last = json.size() - 1;
            final String name = json.get(last).get("name");
            MojangGetter.savedUUIDNames.put(uuid, name);
            return name;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static String getTexturesFromUUID(final String uuid) {
        try {
            final String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid;
            final JSONParser jsonparse = new JSONParser();
            final JSONObject data = (JSONObject)jsonparse.parse(IOUtils.toString(new URL(url), Charset.defaultCharset()));
            final JSONArray properties = data.get("properties");
            final String value64 = properties.get(0).get("value");
            final byte[] decodedbytes = Base64.getDecoder().decode(value64);
            final JSONObject fromBytes = (JSONObject)jsonparse.parse(new String(decodedbytes));
            final JSONObject textures = fromBytes.get("textures");
            final JSONObject skin = textures.get("SKIN");
            final String skinURL = skin.get("url");
            return skinURL;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        MojangGetter.savedUUIDNames = new HashMap<String, String>();
    }
}
