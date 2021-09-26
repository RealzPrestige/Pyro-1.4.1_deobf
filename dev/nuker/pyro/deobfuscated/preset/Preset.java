// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.preset;

import dev.nuker.pyro.deobfuscated.module.Value;
import java.io.Writer;
import java.util.HashMap;
import java.nio.file.OpenOption;
import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.Iterator;
import java.io.Reader;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.FileVisitOption;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.io.File;
import dev.nuker.pyro.deobfuscated.module.Module;
import dev.nuker.pyro.deobfuscated.managers.ModuleManager;
import dev.nuker.pyro.deobfuscated.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

public class Preset
{
    private String _displayName;
    private ConcurrentHashMap<String, ConcurrentHashMap<String, String>> _valueListMods;
    private boolean _active;
    private Timer _timer;
    
    public Preset(final String displayName) {
        this._valueListMods = new ConcurrentHashMap<String, ConcurrentHashMap<String, String>>();
        this._timer = new Timer();
        this._displayName = displayName;
    }
    
    public void initNewPreset() {
        ModuleManager.Get().GetModuleList().forEach(mod -> this.addModuleSettings(mod));
    }
    
    public void onUpdate() {
        if (this._timer.passed(5000.0)) {
            this._timer.reset();
            ModuleManager.Get().GetModuleList().forEach(mod -> this.addModuleSettings(mod));
            this.save();
        }
    }
    
    public void addModuleSettings(final Module mod) {
        final ConcurrentHashMap<String, String> valsMap = new ConcurrentHashMap<String, String>();
        valsMap.put("enabled", mod.isEnabled() ? "true" : "false");
        valsMap.put("display", mod.getDisplayName());
        valsMap.put("keybind", mod.getKey());
        valsMap.put("hidden", mod.isHidden() ? "true" : "false");
        final ConcurrentHashMap<String, String> concurrentHashMap;
        mod.getValueList().forEach(val -> {
            if (val.getValue() != null) {
                concurrentHashMap.put(val.getName(), val.getValue().toString());
            }
            return;
        });
        this._valueListMods.put(mod.getDisplayName(), valsMap);
    }
    
    public void load(final File directory) {
        final File exists = new File("Pyro/Presets/" + directory.getName() + "/" + directory.getName() + ".json");
        if (!exists.exists()) {
            return;
        }
        try {
            final Gson gson = new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get("Pyro/Presets/" + directory.getName() + "/" + directory.getName() + ".json", new String[0]));
            final Map<?, ?> map = (Map<?, ?>)gson.fromJson(reader, (Class)Map.class);
            for (final Map.Entry<?, ?> entry : map.entrySet()) {
                final String key = (String)entry.getKey();
                final String val = (String)entry.getValue();
                if (key == "displayName") {
                    this._displayName = val;
                }
            }
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        try (final Stream<Path> paths = Files.walk(Paths.get("Pyro/Presets/" + directory.getName() + "/Modules/", new String[0]), new FileVisitOption[0])) {
            Gson gson2;
            Reader reader2;
            Map<?, ?> map2;
            ConcurrentHashMap<String, String> valsMap;
            final Iterator<Map.Entry<?, ?>> iterator2;
            Map.Entry<?, ?> entry2;
            String key2;
            String val2;
            paths.filter(x$0 -> Files.isRegularFile(x$0, new LinkOption[0])).forEach(path -> {
                try {
                    gson2 = new Gson();
                    reader2 = Files.newBufferedReader(Paths.get("Pyro/Presets/" + directory.getName() + "/Modules/" + path.getFileName().toString(), new String[0]));
                    map2 = (Map<?, ?>)gson2.fromJson(reader2, (Class)Map.class);
                    valsMap = new ConcurrentHashMap<String, String>();
                    map2.entrySet().iterator();
                    while (iterator2.hasNext()) {
                        entry2 = iterator2.next();
                        key2 = (String)entry2.getKey();
                        val2 = (String)entry2.getValue();
                        valsMap.put(key2, val2);
                    }
                    this._valueListMods.put(path.getFileName().toString().substring(0, path.getFileName().toString().indexOf(".json")), valsMap);
                    reader2.close();
                }
                catch (Exception ex2) {
                    System.out.println("Failed to read file: " + path.getFileName().toString());
                    ex2.printStackTrace();
                }
                return;
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        try {
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.setPrettyPrinting().create();
            final Writer writer = Files.newBufferedWriter(Paths.get("Pyro/Presets/" + this._displayName + "/" + this._displayName + ".json", new String[0]), new OpenOption[0]);
            final Map<String, String> map = new HashMap<String, String>();
            map.put("displayName", this._displayName);
            gson.toJson((Object)map, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            for (final Map.Entry<String, ConcurrentHashMap<String, String>> entry : this._valueListMods.entrySet()) {
                final GsonBuilder builder2 = new GsonBuilder();
                final Gson gson2 = builder2.setPrettyPrinting().create();
                final Writer writer2 = Files.newBufferedWriter(Paths.get("Pyro/Presets/" + this._displayName + "/Modules/" + entry.getKey() + ".json", new String[0]), new OpenOption[0]);
                final Map<String, String> map2 = new HashMap<String, String>();
                for (final Map.Entry<String, String> value : entry.getValue().entrySet()) {
                    final String key = value.getKey();
                    final String val = value.getValue();
                    map2.put(key, val);
                }
                gson2.toJson((Object)map2, (Appendable)writer2);
                writer2.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String getName() {
        return this._displayName;
    }
    
    public boolean isActive() {
        return this._active;
    }
    
    public void setActive(final boolean b) {
        this._active = b;
    }
    
    public void initValuesForMod(final Module mod) {
        if (this._valueListMods.containsKey(mod.getDisplayName().toString())) {
            for (final Map.Entry<String, String> value : this._valueListMods.get(mod.getDisplayName().toString()).entrySet()) {
                final String l_Key = value.getKey();
                final String l_Value = value.getValue();
                if (l_Key.equalsIgnoreCase("enabled")) {
                    if (l_Value.equalsIgnoreCase("true")) {
                        if (mod.isEnabled()) {
                            continue;
                        }
                        mod.toggleNoSave();
                    }
                    else {
                        if (!mod.isEnabled()) {
                            continue;
                        }
                        mod.toggle();
                    }
                }
                else if (l_Key.equalsIgnoreCase("display")) {
                    mod.displayName = l_Value;
                }
                else if (l_Key.equalsIgnoreCase("keybind")) {
                    mod.key = l_Value;
                }
                else if (l_Key.equalsIgnoreCase("hidden")) {
                    mod.hidden = l_Value.equalsIgnoreCase("true");
                }
                else {
                    for (final Value l_Val : mod.valueList) {
                        if (l_Val.getName().equalsIgnoreCase(value.getKey())) {
                            try {
                                if (l_Val.getValue() instanceof Number && !(l_Val.getValue() instanceof Enum)) {
                                    if (l_Val.getValue() instanceof Integer) {
                                        l_Val.SetForcedValue(Integer.parseInt(l_Value));
                                    }
                                    else if (l_Val.getValue() instanceof Float) {
                                        l_Val.SetForcedValue(Float.parseFloat(l_Value));
                                    }
                                    else if (l_Val.getValue() instanceof Double) {
                                        l_Val.SetForcedValue(Double.parseDouble(l_Value));
                                    }
                                }
                                else if (l_Val.getValue() instanceof Boolean) {
                                    l_Val.SetForcedValue(l_Value.equalsIgnoreCase("true"));
                                }
                                else if (l_Val.getValue() instanceof String) {
                                    l_Val.setStringValue(l_Value);
                                }
                            }
                            catch (Exception ex) {}
                            break;
                        }
                    }
                }
            }
        }
    }
}
