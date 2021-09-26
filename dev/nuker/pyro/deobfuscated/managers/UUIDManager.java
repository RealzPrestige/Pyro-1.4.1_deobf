// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import java.net.URL;
import com.google.common.collect.Maps;
import java.util.Map;

public class UUIDManager
{
    private final Map<String, String> uuidNameCache;
    
    public UUIDManager() {
        this.uuidNameCache = (Map<String, String>)Maps.newConcurrentMap();
    }
    
    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url), "UTF-8");
            if (nameJson != null && nameJson.length() > 0) {
                final JsonParser parser = new JsonParser();
                return parser.parse(nameJson).getAsJsonArray().get(parser.parse(nameJson).getAsJsonArray().size() - 1).getAsJsonObject().get("name").toString();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static UUIDManager Get() {
        return Pyro.GetUUIDManager();
    }
}
