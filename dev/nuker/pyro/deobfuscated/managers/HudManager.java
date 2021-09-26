//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import java.util.Map;
import java.io.Writer;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import com.google.gson.GsonBuilder;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.client.gui.GuiScreen;
import dev.nuker.pyro.deobfuscated.gui.hud.components.HudComponentFlags;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.gui.hud.GuiHudEditor;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import java.lang.reflect.Field;
import dev.nuker.pyro.deobfuscated.module.ValueListeners;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.gui.hud.items.SelectorMenuComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.LagNotifierComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.PvPInfoComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.StopwatchComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TrueDurabilityComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TotemCountComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.YawComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.NearestEntityFrameComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.PlayerCountComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TooltipComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.DirectionComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.FPSComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TPSComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.NotificationComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TabGUIComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.TimeComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ChestCountComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.SpeedComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.PlayerFrameComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.PingComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.InventoryComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.CoordsComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.WatermarkComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.BiomeComponent;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ArmorComponent;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.gui.hud.items.ArrayListComponent;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.util.colors.SalRainbowUtil;
import dev.nuker.pyro.deobfuscated.gui.hud.components.HudComponentItem;
import java.util.List;
import dev.nuker.pyro.deobfuscated.gui.hud.components.util.CornerList;
import dev.nuker.pyro.deobfuscated.events.bus.EventListener;

public class HudManager implements EventListener
{
    private CornerList[] cornerLists;
    public List<HudComponentItem> Items;
    public SalRainbowUtil rainbow;
    
    public HudManager() {
        this.cornerLists = new CornerList[4];
        this.Items = new CopyOnWriteArrayList<HudComponentItem>();
        this.rainbow = new SalRainbowUtil(9);
    }
    
    public void Init() {
        this.cornerLists[0] = new CornerList(0);
        this.cornerLists[1] = new CornerList(1);
        this.cornerLists[2] = new CornerList(2);
        this.cornerLists[3] = new CornerList(3);
        this.Add(PyroStatic.ARRAYLIST = new ArrayListComponent());
        this.Add(new ArmorComponent());
        this.Add(new BiomeComponent());
        this.Add(new WatermarkComponent());
        this.Add(new CoordsComponent());
        this.Add(new InventoryComponent());
        this.Add(new PingComponent());
        this.Add(new PlayerFrameComponent());
        this.Add(new SpeedComponent());
        this.Add(new ChestCountComponent());
        this.Add(new TimeComponent());
        this.Add(new TabGUIComponent());
        this.Add(new NotificationComponent());
        this.Add(new TPSComponent());
        this.Add(new FPSComponent());
        this.Add(new DirectionComponent());
        this.Add(new TooltipComponent());
        this.Add(new PlayerCountComponent());
        this.Add(new NearestEntityFrameComponent());
        this.Add(new YawComponent());
        this.Add(new TotemCountComponent());
        this.Add(new TrueDurabilityComponent());
        this.Add(new StopwatchComponent());
        this.Add(new PvPInfoComponent());
        this.Add(new LagNotifierComponent());
        this.Add(PyroStatic.SELECTORMENU = new SelectorMenuComponent());
        this.LoadSettings();
        PyroMod.EVENT_BUS.subscribe(this);
    }
    
    public void Add(final HudComponentItem item) {
        try {
            for (final Field field : item.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Value<?> val = (Value<?>)field.get(item);
                    final ValueListeners listener = new ValueListeners() {
                        @Override
                        public void OnValueChange(final Value<?> val) {
                            HudManager.this.ScheduleSave(item);
                        }
                    };
                    val.Listener = listener;
                    item.getValueList().add(val);
                }
            }
            this.Items.add(item);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void OnRender(final float partialTicks) {
        final GuiScreen l_CurrScreen = Wrapper.GetMC().currentScreen;
        if (l_CurrScreen != null && l_CurrScreen instanceof GuiHudEditor) {
            return;
        }
        this.rainbow.OnRender();
        GlStateManager.pushMatrix();
        final ScaledResolution res = new ScaledResolution(Wrapper.GetMC());
        if (this.cornerLists != null) {
            for (final CornerList list : this.cornerLists) {
                list.render(res, true, true, 20);
            }
        }
        final ScaledResolution res2;
        this.Items.forEach(item -> {
            if (item.isEnabled() && !item.hasFlag(HudComponentFlags.OnlyVisibleInHudEditor) && !item.hasFlag(HudComponentFlags.IsInCornerList)) {
                try {
                    item.onRender(res2, 0.0f, 0.0f, partialTicks);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return;
        });
        GlStateManager.popMatrix();
    }
    
    public static HudManager Get() {
        return Pyro.GetHudManager();
    }
    
    private void LoadSettings() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        com/Pyro/pyro/managers/HudManager.Items:Ljava/util/List;
        //     4: invokedynamic   BootstrapMethod #1, accept:()Ljava/util/function/Consumer;
        //     9: invokeinterface java/util/List.forEach:(Ljava/util/function/Consumer;)V
        //    14: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Could not infer any expression.
        //     at com.strobel.decompiler.ast.TypeAnalysis.runInference(TypeAnalysis.java:374)
        //     at com.strobel.decompiler.ast.TypeAnalysis.run(TypeAnalysis.java:96)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:344)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:214)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:675)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:552)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void ScheduleSave(final HudComponentItem p_Item) {
        try {
            System.out.println("Saving " + p_Item.getDisplayName());
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.setPrettyPrinting().create();
            final Writer writer = Files.newBufferedWriter(Paths.get("Pyro/HUD/" + p_Item.getDisplayName() + ".json", new String[0]), new OpenOption[0]);
            final Map<String, String> map = new HashMap<String, String>();
            map.put("displayname", p_Item.getDisplayName());
            map.put("visible", p_Item.isEnabled() ? "true" : "false");
            map.put("PositionX", String.valueOf(p_Item.getX()));
            map.put("PositionY", String.valueOf(p_Item.getY()));
            for (final Value<?> l_Val : p_Item.getValueList()) {
                map.put(l_Val.getName().toString(), l_Val.getValue().toString());
            }
            gson.toJson((Object)map, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public final CornerList GetModList(final String string) {
        switch (string) {
            case "TopRight": {
                return this.cornerLists[0];
            }
            case "BottomRight": {
                return this.cornerLists[1];
            }
            case "BottomLeft": {
                return this.cornerLists[2];
            }
            case "TopLeft": {
                return this.cornerLists[3];
            }
            default: {
                return null;
            }
        }
    }
    
    public void onHudEditorClosed() {
        new Thread(() -> this.Items.forEach(item -> this.ScheduleSave(item))).start();
    }
}
