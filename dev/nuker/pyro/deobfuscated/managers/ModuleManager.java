//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import net.minecraft.client.gui.GuiScreen;

import java.util.function.Function;
import java.util.Comparator;
import java.util.ArrayList;
import java.lang.reflect.Field;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.preset.Preset;
import dev.nuker.pyro.deobfuscated.module.bot.DupeBot;
import dev.nuker.pyro.deobfuscated.module.world.Weather;
import dev.nuker.pyro.deobfuscated.module.world.TimerModule;
import dev.nuker.pyro.deobfuscated.module.world.StashLogger;
import dev.nuker.pyro.deobfuscated.module.world.StashFinder;
import dev.nuker.pyro.deobfuscated.module.world.SpeedyGonzales;
import dev.nuker.pyro.deobfuscated.module.world.SkyRender;
import dev.nuker.pyro.deobfuscated.module.world.Scaffold;
import dev.nuker.pyro.deobfuscated.module.world.Nuker;
import dev.nuker.pyro.deobfuscated.module.world.NoGlitchBlocks;
import dev.nuker.pyro.deobfuscated.module.world.Lawnmower;
import dev.nuker.pyro.deobfuscated.module.world.FastPlace;
import dev.nuker.pyro.deobfuscated.module.world.EnderChestFarmer;
import dev.nuker.pyro.deobfuscated.module.world.Avoid;
import dev.nuker.pyro.deobfuscated.module.world.AutoWither;
import dev.nuker.pyro.deobfuscated.module.world.AutoTunnel;
import dev.nuker.pyro.deobfuscated.module.world.AutoTool;
import dev.nuker.pyro.deobfuscated.module.world.AutoMine;
import dev.nuker.pyro.deobfuscated.module.world.AutoNameTag;
import dev.nuker.pyro.deobfuscated.module.world.AutoBuilder;
import dev.nuker.pyro.deobfuscated.module.ui.ReliantChat;
import dev.nuker.pyro.deobfuscated.module.ui.Keybinds;
import dev.nuker.pyro.deobfuscated.module.ui.Hud;
import dev.nuker.pyro.deobfuscated.module.ui.HudEditor;
import dev.nuker.pyro.deobfuscated.module.ui.ClickGui;
import dev.nuker.pyro.deobfuscated.module.ui.Console;
import dev.nuker.pyro.deobfuscated.module.ui.Commands;
import dev.nuker.pyro.deobfuscated.module.ui.Colors;
import dev.nuker.pyro.deobfuscated.module.render.WoWTooltips;
import dev.nuker.pyro.deobfuscated.module.render.Waypoints;
import dev.nuker.pyro.deobfuscated.module.render.Wallhack;
import dev.nuker.pyro.deobfuscated.module.render.VoidESP;
import dev.nuker.pyro.deobfuscated.module.render.ViewClip;
import dev.nuker.pyro.deobfuscated.module.render.Trajectories;
import dev.nuker.pyro.deobfuscated.module.render.Tracers;
import dev.nuker.pyro.deobfuscated.module.render.SuperheroFX;
import dev.nuker.pyro.deobfuscated.module.render.StorageESP;
import dev.nuker.pyro.deobfuscated.module.render.SmallShield;
import dev.nuker.pyro.deobfuscated.module.render.Skeleton;
import dev.nuker.pyro.deobfuscated.module.render.ShulkerPreview;
import dev.nuker.pyro.deobfuscated.module.render.Search;
import dev.nuker.pyro.deobfuscated.module.render.NoRender;
import dev.nuker.pyro.deobfuscated.module.render.NoBob;
import dev.nuker.pyro.deobfuscated.module.render.MapTooltip;
import dev.nuker.pyro.deobfuscated.module.render.Nametags;
import dev.nuker.pyro.deobfuscated.module.render.ItemPhysics;
import dev.nuker.pyro.deobfuscated.module.render.HoleESP;
import dev.nuker.pyro.deobfuscated.module.render.Fullbright;
import dev.nuker.pyro.deobfuscated.module.render.Freecam;
import dev.nuker.pyro.deobfuscated.module.render.FarmESP;
import dev.nuker.pyro.deobfuscated.module.movement.EntitySpeed;
import dev.nuker.pyro.deobfuscated.module.render.EntityESP;
import dev.nuker.pyro.deobfuscated.module.render.ContainerPreview;
import dev.nuker.pyro.deobfuscated.module.render.CityESP;
import dev.nuker.pyro.deobfuscated.module.render.BreakHighlight;
import dev.nuker.pyro.deobfuscated.module.render.BreadCrumbs;
import dev.nuker.pyro.deobfuscated.module.render.BlockHighlight;
import dev.nuker.pyro.deobfuscated.module.render.AntiFog;
import dev.nuker.pyro.deobfuscated.module.movement.WebSpeed;
import dev.nuker.pyro.deobfuscated.module.movement.Yaw;
import dev.nuker.pyro.deobfuscated.module.movement.Step;
import dev.nuker.pyro.deobfuscated.module.movement.Sprint;
import dev.nuker.pyro.deobfuscated.module.movement.Speed;
import dev.nuker.pyro.deobfuscated.module.movement.Sneak;
import dev.nuker.pyro.deobfuscated.module.movement.SafeWalk;
import dev.nuker.pyro.deobfuscated.module.movement.ReverseStep;
import dev.nuker.pyro.deobfuscated.module.movement.Jesus;
import dev.nuker.pyro.deobfuscated.module.render.Parkour;
import dev.nuker.pyro.deobfuscated.module.movement.NoSlow;
import dev.nuker.pyro.deobfuscated.module.movement.NoRotate;
import dev.nuker.pyro.deobfuscated.module.movement.NoFall;
import dev.nuker.pyro.deobfuscated.module.movement.LevitationControl;
import dev.nuker.pyro.deobfuscated.module.movement.LongJump;
import dev.nuker.pyro.deobfuscated.module.movement.IceSpeed;
import dev.nuker.pyro.deobfuscated.module.movement.HoleFinder;
import dev.nuker.pyro.deobfuscated.module.movement.HighJump;
import dev.nuker.pyro.deobfuscated.module.movement.Flight;
import dev.nuker.pyro.deobfuscated.module.movement.FastSwim;
import dev.nuker.pyro.deobfuscated.module.movement.EntityControl;
import dev.nuker.pyro.deobfuscated.module.movement.ElytraFly;
import dev.nuker.pyro.deobfuscated.module.movement.Blink;
import dev.nuker.pyro.deobfuscated.module.movement.BoatFly;
import dev.nuker.pyro.deobfuscated.module.movement.AutoWalk;
import dev.nuker.pyro.deobfuscated.module.misc.XCarry;
import dev.nuker.pyro.deobfuscated.module.misc.VisualRange;
import dev.nuker.pyro.deobfuscated.module.misc.TotemPopNotifier;
import dev.nuker.pyro.deobfuscated.module.misc.StopWatch;
import dev.nuker.pyro.deobfuscated.module.misc.QueuePeek;
import dev.nuker.pyro.deobfuscated.module.misc.PacketLogger;
import dev.nuker.pyro.deobfuscated.module.misc.Notifications;
import dev.nuker.pyro.deobfuscated.module.misc.MiddleClickPearl;
import dev.nuker.pyro.deobfuscated.module.misc.MiddleClickFriends;
import dev.nuker.pyro.deobfuscated.module.misc.HotbarCache;
import dev.nuker.pyro.deobfuscated.module.misc.GlobalLocation;
import dev.nuker.pyro.deobfuscated.module.misc.ImgurUploader;
import dev.nuker.pyro.deobfuscated.module.misc.Friends;
import dev.nuker.pyro.deobfuscated.module.misc.FakePlayer;
import dev.nuker.pyro.deobfuscated.module.misc.DurabilityAlert;
import dev.nuker.pyro.deobfuscated.module.misc.DiscordRPC;
import dev.nuker.pyro.deobfuscated.module.misc.ChestSwap;
import dev.nuker.pyro.deobfuscated.module.misc.ChorusFruitBypass;
import dev.nuker.pyro.deobfuscated.module.misc.ChestStealer;
import dev.nuker.pyro.deobfuscated.module.misc.ChatNotifier;
import dev.nuker.pyro.deobfuscated.module.misc.ChatModifications;
import dev.nuker.pyro.deobfuscated.module.misc.BuildHeight;
import dev.nuker.pyro.deobfuscated.module.world.AutoTend;
import dev.nuker.pyro.deobfuscated.module.misc.AutoTame;
import dev.nuker.pyro.deobfuscated.module.misc.AutoSign;
import dev.nuker.pyro.deobfuscated.module.misc.AutoShear;
import dev.nuker.pyro.deobfuscated.module.misc.AutoReconnect;
import dev.nuker.pyro.deobfuscated.module.misc.AutoMount;
import dev.nuker.pyro.deobfuscated.module.misc.AutoMendArmor;
import dev.nuker.pyro.deobfuscated.module.world.AutoFarmland;
import dev.nuker.pyro.deobfuscated.module.misc.AutoEat;
import dev.nuker.pyro.deobfuscated.module.misc.AntiShulkerPlace;
import dev.nuker.pyro.deobfuscated.module.misc.AntiAFK;
import dev.nuker.pyro.deobfuscated.module.misc.Annoy;
import dev.nuker.pyro.deobfuscated.module.misc.Announcer;
import dev.nuker.pyro.deobfuscated.module.exploit.Swing;
import dev.nuker.pyro.deobfuscated.module.exploit.Reach;
import dev.nuker.pyro.deobfuscated.module.exploit.PortalGodMode;
import dev.nuker.pyro.deobfuscated.module.exploit.PacketFly;
import dev.nuker.pyro.deobfuscated.module.exploit.PacketCanceller;
import dev.nuker.pyro.deobfuscated.module.exploit.NewChunks;
import dev.nuker.pyro.deobfuscated.module.exploit.NoMiningTrace;
import dev.nuker.pyro.deobfuscated.module.exploit.LiquidInteract;
import dev.nuker.pyro.deobfuscated.module.exploit.EntityDesync;
import dev.nuker.pyro.deobfuscated.module.exploit.CrashItem;
import dev.nuker.pyro.deobfuscated.module.exploit.CoordTPExploit;
import dev.nuker.pyro.deobfuscated.module.exploit.AntiHunger;
import dev.nuker.pyro.deobfuscated.module.combat.Velocity;
import dev.nuker.pyro.deobfuscated.module.combat.Trigger;
import dev.nuker.pyro.deobfuscated.module.combat.Surround;
import dev.nuker.pyro.deobfuscated.module.combat.SelfWeb;
import dev.nuker.pyro.deobfuscated.module.combat.SelfTrap;
import dev.nuker.pyro.deobfuscated.module.combat.Offhand;
import dev.nuker.pyro.deobfuscated.module.combat.HoleFiller;
import dev.nuker.pyro.deobfuscated.module.combat.Criticals;
import dev.nuker.pyro.deobfuscated.module.combat.BowSpam;
import dev.nuker.pyro.deobfuscated.module.combat.BowAim;
import dev.nuker.pyro.deobfuscated.module.combat.BlinkDetect;
import dev.nuker.pyro.deobfuscated.module.combat.AutoTrapFeet;
import dev.nuker.pyro.deobfuscated.module.combat.AutoTrap;
import dev.nuker.pyro.deobfuscated.module.combat.AutoTotem;
import dev.nuker.pyro.deobfuscated.module.combat.Aura;
import dev.nuker.pyro.deobfuscated.module.combat.AutoLog;
import dev.nuker.pyro.deobfuscated.module.combat.AutoCrystalBypass;
import dev.nuker.pyro.deobfuscated.module.combat.AutoCrystal;
import dev.nuker.pyro.deobfuscated.module.combat.AutoCity;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.module.combat.AutoArmor;
import dev.nuker.pyro.deobfuscated.module.combat.AntiGappleDisease;
import dev.nuker.pyro.deobfuscated.module.combat.AntiCityBoss;
import dev.nuker.pyro.deobfuscated.module.combat.AntiBowkick;
import dev.nuker.pyro.deobfuscated.module.combat.AntiBots;
import dev.nuker.pyro.deobfuscated.module.combat.Anchor;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import dev.nuker.pyro.deobfuscated.module.Module;
import java.util.List;

public class ModuleManager
{
    public List<Module> Mods;
    
    public static ModuleManager Get() {
        return Pyro.GetModuleManager();
    }
    
    public ModuleManager() {
        this.Mods = new CopyOnWriteArrayList<Module>();
    }
    
    public void Init() {
        this.Add(new Anchor());
        this.Add(new AntiBots());
        this.Add(new AntiBowkick());
        this.Add(new AntiCityBoss());
        this.Add(new AntiGappleDisease());
        this.Add(PyroStatic.AUTOARMOR = new AutoArmor());
        this.Add(PyroStatic.AUTOCITY = new AutoCity());
        this.Add(PyroStatic.AUTOCRYSTAL = new AutoCrystal());
        this.Add(new AutoCrystalBypass());
        this.Add(new AutoLog());
        this.Add(PyroStatic.AURA = new Aura());
        this.Add(PyroStatic.AUTOTOTEM = new AutoTotem());
        this.Add(PyroStatic.AUTOTRAP = new AutoTrap());
        this.Add(PyroStatic.AUTOTRAPFEET = new AutoTrapFeet());
        this.Add(new BlinkDetect());
        this.Add(new BowAim());
        this.Add(new BowSpam());
        this.Add(PyroStatic.CRITICALS = new Criticals());
        this.Add(PyroStatic.HOLEFILLER = new HoleFiller());
        this.Add(PyroStatic.OFFHAND = new Offhand());
        this.Add(PyroStatic.SELFTRAP = new SelfTrap());
        this.Add(new SelfWeb());
        this.Add(PyroStatic.SURROUND = new Surround());
        this.Add(new Trigger());
        this.Add(new Velocity());
        this.Add(new AntiHunger());
        this.Add(new CoordTPExploit());
        this.Add(new CrashItem());
        this.Add(PyroStatic.ENTITYDESYNC = new EntityDesync());
        this.Add(PyroStatic.LIQUIDINTERACT = new LiquidInteract());
        this.Add(new NoMiningTrace());
        this.Add(new NewChunks());
        this.Add(PyroStatic.PACKETCANCELLER = new PacketCanceller());
        this.Add(PyroStatic.PACKETFLY = new PacketFly());
        this.Add(new PortalGodMode());
        this.Add(PyroStatic.REACH = new Reach());
        this.Add(new Swing());
        this.Add(new Announcer());
        this.Add(PyroStatic.ANNOY = new Annoy());
        this.Add(PyroStatic.ANTIAFK = new AntiAFK());
        this.Add(new AntiShulkerPlace());
        this.Add(new AutoEat());
        this.Add(new AutoFarmland());
        this.Add(PyroStatic.AUTOMEND = new AutoMendArmor());
        this.Add(new AutoMount());
        this.Add(PyroStatic.AUTORECONNECT = new AutoReconnect());
        this.Add(new AutoShear());
        this.Add(new AutoSign());
        this.Add(new AutoTame());
        this.Add(new AutoTend());
        this.Add(new BuildHeight());
        this.Add(PyroStatic.CHATMODIFICATIONS = new ChatModifications());
        this.Add(new ChatNotifier());
        this.Add(PyroStatic.CHESTSTEALER = new ChestStealer());
        this.Add(PyroStatic.CHORUSFRUITBYPASS = new ChorusFruitBypass());
        this.Add(new ChestSwap());
        this.Add(PyroStatic.DISCORDRPC = new DiscordRPC());
        this.Add(new DurabilityAlert());
        this.Add(new FakePlayer());
        this.Add(PyroStatic.FRIENDS = new Friends());
        this.Add(PyroStatic.IMGURUPLOADER = new ImgurUploader());
        this.Add(new GlobalLocation());
        this.Add(new HotbarCache());
        this.Add(new MiddleClickFriends());
        this.Add(new MiddleClickPearl());
        this.Add(new Notifications());
        this.Add(new PacketLogger());
        this.Add(new QueuePeek());
        this.Add(PyroStatic.STOPWATCH = new StopWatch());
        this.Add(new TotemPopNotifier());
        this.Add(new VisualRange());
        this.Add(new XCarry());
        this.Add(PyroStatic.AUTOWALK = new AutoWalk());
        this.Add(PyroStatic.BOATFLY = new BoatFly());
        this.Add(PyroStatic.BLINK = new Blink());
        this.Add(PyroStatic.ELYTRAFLY = new ElytraFly());
        this.Add(new EntityControl());
        this.Add(new FastSwim());
        this.Add(PyroStatic.FLIGHT = new Flight());
        this.Add(new HighJump());
        this.Add(PyroStatic.HOLEFINDER = new HoleFinder());
        this.Add(new IceSpeed());
        this.Add(new LongJump());
        this.Add(new LevitationControl());
        this.Add(new NoFall());
        this.Add(PyroStatic.NOROTATE = new NoRotate());
        this.Add(PyroStatic.NOSLOW = new NoSlow());
        this.Add(new Parkour());
        this.Add(new Jesus());
        this.Add(new ReverseStep());
        this.Add(new SafeWalk());
        this.Add(new Sneak());
        this.Add(PyroStatic.SPEED = new Speed());
        this.Add(new Sprint());
        this.Add(new Step());
        this.Add(new Yaw());
        this.Add(new WebSpeed());
        this.Add(new AntiFog());
        this.Add(new BlockHighlight());
        this.Add(new BreadCrumbs());
        this.Add(new BreakHighlight());
        this.Add(new CityESP());
        this.Add(new ContainerPreview());
        this.Add(PyroStatic.ENTITYESP = new EntityESP());
        this.Add(new EntitySpeed());
        this.Add(new FarmESP());
        this.Add(PyroStatic.FREECAM = new Freecam());
        this.Add(new Fullbright());
        this.Add(new HoleESP());
        this.Add(PyroStatic.ITEMPHYSICS = new ItemPhysics());
        this.Add(new Nametags());
        this.Add(new MapTooltip());
        this.Add(new NoBob());
        this.Add(PyroStatic.NORENDER = new NoRender());
        this.Add(new Search());
        this.Add(new ShulkerPreview());
        this.Add(new Skeleton());
        this.Add(new SmallShield());
        this.Add(PyroStatic.STORAGEESP = new StorageESP());
        this.Add(new SuperheroFX());
        this.Add(new Tracers());
        this.Add(new Trajectories());
        this.Add(PyroStatic.VIEWCLIP = new ViewClip());
        this.Add(new VoidESP());
        this.Add(PyroStatic.WALLHACK = new Wallhack());
        this.Add(new Waypoints());
        this.Add(new WoWTooltips());
        this.Add(PyroStatic.COLORS = new Colors());
        this.Add(new Commands());
        this.Add(new Console());
        this.Add(PyroStatic.CLICKGUI = new ClickGui());
        this.Add(new HudEditor());
        this.Add(PyroStatic.HUD = new Hud());
        this.Add(PyroStatic.KEYBINDS = new Keybinds());
        this.Add(PyroStatic.RELIANTCHAT = new ReliantChat());
        this.Add(new AutoBuilder());
        this.Add(new AutoNameTag());
        this.Add(new AutoMine());
        this.Add(new AutoTool());
        this.Add(PyroStatic.AUTOTUNNEL = new AutoTunnel());
        this.Add(new AutoWither());
        this.Add(new Avoid());
        this.Add(new EnderChestFarmer());
        this.Add(new FastPlace());
        this.Add(new Lawnmower());
        this.Add(PyroStatic.NOGLITCHBLOCKS = new NoGlitchBlocks());
        this.Add(new Nuker());
        this.Add(new Scaffold());
        this.Add(new SkyRender());
        this.Add(new SpeedyGonzales());
        this.Add(PyroStatic.STASHFINDER = new StashFinder());
        this.Add(PyroStatic.STASHLOGGER = new StashLogger());
        this.Add(PyroStatic.TIMER = new TimerModule());
        this.Add(new Weather());
        this.Add(PyroStatic.DUPEBOT = new DupeBot());
        this.Mods.sort((p_Mod1, p_Mod2) -> p_Mod1.getDisplayName().compareTo(p_Mod2.getDisplayName()));
        final Preset preset = PresetsManager.Get().getActivePreset();
        this.Mods.forEach(mod -> preset.initValuesForMod(mod));
        this.Mods.forEach(mod -> mod.init());
    }
    
    public void Add(final Module mod) {
        try {
            for (final Field field : mod.getClass().getDeclaredFields()) {
                if (Value.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Value<?> val = (Value<?>)field.get(mod);
                    val.InitalizeMod(mod);
                    mod.getValueList().add(val);
                }
            }
            this.Mods.add(mod);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public final List<Module> GetModuleList(final Module.ModuleType p_Type) {
        final List<Module> list = new ArrayList<Module>();
        for (final Module module : this.Mods) {
            if (module.getType().equals(p_Type)) {
                list.add(module);
            }
        }
        list.sort(Comparator.comparing((Function<? super Module, ? extends Comparable>)Module::getDisplayName));
        return list;
    }
    
    public final List<Module> GetModuleList() {
        return this.Mods;
    }
    
    public void OnKeyPress(final String string) {
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("NONE")) {
            return;
        }
        this.Mods.forEach(p_Mod -> {
            if (p_Mod.IsKeyPressed(string)) {
                p_Mod.toggle();
            }
        });
    }
    
    public Module GetModLike(final String p_String) {
        for (final Module l_Mod : this.Mods) {
            if (l_Mod.GetArrayListDisplayName().toLowerCase().startsWith(p_String.toLowerCase())) {
                return l_Mod;
            }
        }
        return null;
    }
    
    public boolean IgnoreStrictKeybinds() {
        return (GuiScreen.isAltKeyDown() && !PyroStatic.KEYBINDS.Alt.getValue()) || (GuiScreen.isCtrlKeyDown() && !PyroStatic.KEYBINDS.Ctrl.getValue()) || (GuiScreen.isShiftKeyDown() && !PyroStatic.KEYBINDS.Shift.getValue());
    }
    
    public int GetTotalModsOfCategory(final Module.ModuleType type) {
        int total = 0;
        for (final Module mod : this.Mods) {
            if (mod.getType() == type) {
                ++total;
            }
        }
        return total;
    }
}
