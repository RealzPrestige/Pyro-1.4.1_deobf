// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import org.apache.commons.io.IOUtils;
import java.nio.charset.Charset;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;
import org.json.simple.JSONObject;

public class RebaneGetter
{
    private static JSONObject json;
    private static ArrayList<String> nameArray;
    public static int size;
    
    public static void init() {
        getBaseJSON();
        RebaneGetter.nameArray = getNameArray();
        RebaneGetter.size = getAmount();
    }
    
    private static void getBaseJSON() {
        try {
            final String rawjson = getDataFromURL("https://rebane2001.com/queuepeek/data.json");
            final JSONParser parser = new JSONParser();
            RebaneGetter.json = (JSONObject)parser.parse(rawjson);
        }
        catch (Exception ex) {}
    }
    
    private static ArrayList<String> getNameArray() {
        if (RebaneGetter.json == null) {
            return null;
        }
        final ArrayList<String> names = new ArrayList<String>();
        final JSONArray namejson = RebaneGetter.json.get("players");
        for (int jsonsize = namejson.size(), i = 0; i < jsonsize; ++i) {
            final String name = namejson.get(i).get("name");
            names.add(name);
        }
        return names;
    }
    
    private static int getAmount() {
        if (RebaneGetter.json == null) {
            return -1;
        }
        try {
            return Integer.parseInt(RebaneGetter.json.get("queuepos"));
        }
        catch (Exception e) {
            return -1;
        }
    }
    
    public static ArrayList<String> getNames() {
        return new ArrayList<String>(RebaneGetter.nameArray);
    }
    
    public static boolean hasJSON() {
        return RebaneGetter.json != null;
    }
    
    private static String getDataFromURL(final String url) {
        try {
            return IOUtils.toString(new URL(url), Charset.defaultCharset());
        }
        catch (Exception e) {
            return null;
        }
    }
    
    static {
        RebaneGetter.json = null;
        RebaneGetter.nameArray = new ArrayList<String>();
        RebaneGetter.size = -1;
    }
}
