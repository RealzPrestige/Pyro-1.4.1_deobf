//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.main;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import com.mojang.realmsclient.gui.ChatFormatting;
import dev.nuker.pyro.deobfuscated.util.RebaneGetter;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.communication.Client;
import dev.nuker.pyro.deobfuscated.util.RotationUtils;
import dev.nuker.pyro.deobfuscated.managers.MacroManager;
import dev.nuker.pyro.deobfuscated.managers.RotationManager;
import dev.nuker.pyro.deobfuscated.managers.UUIDManager;
import dev.nuker.pyro.deobfuscated.managers.PresetsManager;
import dev.nuker.pyro.deobfuscated.managers.CapeManager;
import dev.nuker.pyro.deobfuscated.waypoints.WaypointManager;
import dev.nuker.pyro.deobfuscated.managers.NotificationManager;
import dev.nuker.pyro.deobfuscated.managers.TickRateManager;
import dev.nuker.pyro.deobfuscated.managers.CommandManager;
import dev.nuker.pyro.deobfuscated.managers.DirectoryManager;
import dev.nuker.pyro.deobfuscated.managers.DiscordManager;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import dev.nuker.pyro.deobfuscated.managers.HudManager;
import dev.nuker.pyro.deobfuscated.managers.FontManager;
import dev.nuker.pyro.deobfuscated.managers.ImageManager;
import dev.nuker.pyro.deobfuscated.managers.ModuleManager;

public class Pyro
{
    private static ModuleManager m_ModuleManager;
    private static ImageManager m_ImageManager;
    private static FontManager m_FontManager;
    private static HudManager m_HudManager;
    private static FriendManager m_FriendManager;
    private static DiscordManager m_DiscordManager;
    private static DirectoryManager m_DirectoryManager;
    private static CommandManager m_CommandManager;
    private static TickRateManager m_TickRateManager;
    private static NotificationManager m_NotificationManager;
    private static WaypointManager m_WaypointManager;
    private static CapeManager m_CapeManager;
    private static AlwaysEnabledModule m_AlwaysEnabledMod;
    private static PresetsManager m_PresetsManager;
    private static UUIDManager m_UUIDManager;
    private static RotationManager m_RotationManager;
    private static MacroManager m_MacroManager;
    private static RotationUtils m_RotationUtils;
    private static Client m_Client;
    private static Timer updateTimer;
    
    public static void Init() {
        PyroMod.log.info("initalizing Pyro object (all static fields)");
        RenderUtil.init();
        Pyro.m_DirectoryManager.Init();
        Pyro.m_FontManager.Load();
        Pyro.m_CapeManager.Init();
        try {
            Pyro.m_PresetsManager.LoadPresets();
            Pyro.m_ModuleManager.Init();
            Pyro.m_HudManager.Init();
            Pyro.m_CommandManager.InitializeCommands();
            Pyro.m_ImageManager.Load();
            Pyro.m_FriendManager.Load();
            Pyro.m_MacroManager.Load();
            RebaneGetter.init();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while (true) {
                if (!Pyro.updateTimer.passed(50.0)) {
                    continue;
                }
                else {
                    Pyro.updateTimer.reset();
                    try {
                        Pyro.m_PresetsManager.getActivePreset().onUpdate();
                    }
                    catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            }
        }).start();
        (Pyro.m_AlwaysEnabledMod = new AlwaysEnabledModule()).init();
        Pyro.m_RotationManager.init();
        new Thread(() -> (Pyro.m_Client = new Client("127.0.0.1", 5056)).Update()).start();
    }
    
    public static ModuleManager GetModuleManager() {
        return Pyro.m_ModuleManager;
    }
    
    public static ImageManager GetImageManager() {
        return Pyro.m_ImageManager;
    }
    
    public static FontManager GetFontManager() {
        return Pyro.m_FontManager;
    }
    
    public static void SendMessage(final String string) {
        if (Wrapper.GetMC().ingameGUI != null || Wrapper.GetPlayer() == null) {
            Wrapper.GetMC().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(ChatFormatting.RED + "[Pyro]: " + string));
        }
    }
    
    public static HudManager GetHudManager() {
        return Pyro.m_HudManager;
    }
    
    public static FriendManager GetFriendManager() {
        return Pyro.m_FriendManager;
    }
    
    public static DiscordManager GetDiscordManager() {
        return Pyro.m_DiscordManager;
    }
    
    public static DirectoryManager GetDirectoryManager() {
        return Pyro.m_DirectoryManager;
    }
    
    public static CommandManager GetCommandManager() {
        return Pyro.m_CommandManager;
    }
    
    public static TickRateManager GetTickRateManager() {
        return Pyro.m_TickRateManager;
    }
    
    public static NotificationManager GetNotificationManager() {
        return Pyro.m_NotificationManager;
    }
    
    public static WaypointManager GetWaypointManager() {
        return Pyro.m_WaypointManager;
    }
    
    public static CapeManager GetCapeManager() {
        return Pyro.m_CapeManager;
    }
    
    public static PresetsManager GetPresetsManager() {
        return Pyro.m_PresetsManager;
    }
    
    public static UUIDManager GetUUIDManager() {
        return Pyro.m_UUIDManager;
    }
    
    public static Client GetClient() {
        return Pyro.m_Client;
    }
    
    public static RotationManager GetRotationManager() {
        return Pyro.m_RotationManager;
    }
    
    public static void SendMessage(final TextComponentString portalTextComponent) {
        Wrapper.GetMC().ingameGUI.getChatGUI().printChatMessage((ITextComponent)portalTextComponent);
    }
    
    public static MacroManager GetMacroManager() {
        return Pyro.m_MacroManager;
    }
    
    public static RotationUtils GetRotationUtils() {
        return Pyro.m_RotationUtils;
    }
    
    static {
        Pyro.m_ModuleManager = new ModuleManager();
        Pyro.m_ImageManager = new ImageManager();
        Pyro.m_FontManager = new FontManager();
        Pyro.m_HudManager = new HudManager();
        Pyro.m_FriendManager = new FriendManager();
        Pyro.m_DiscordManager = new DiscordManager();
        Pyro.m_DirectoryManager = new DirectoryManager();
        Pyro.m_CommandManager = new CommandManager();
        Pyro.m_TickRateManager = new TickRateManager();
        Pyro.m_NotificationManager = new NotificationManager();
        Pyro.m_WaypointManager = new WaypointManager();
        Pyro.m_CapeManager = new CapeManager();
        Pyro.m_PresetsManager = new PresetsManager();
        Pyro.m_UUIDManager = new UUIDManager();
        Pyro.m_RotationManager = new RotationManager();
        Pyro.m_MacroManager = new MacroManager();
        Pyro.m_RotationUtils = new RotationUtils();
        Pyro.updateTimer = new Timer();
    }
}
