// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.managers;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.List;
import java.util.function.Function;
import java.util.Comparator;
import dev.nuker.pyro.deobfuscated.gui.hud.components.HudComponentItem;
import dev.nuker.pyro.deobfuscated.command.impl.ModuleCommand;
import dev.nuker.pyro.deobfuscated.module.Module;
import dev.nuker.pyro.deobfuscated.command.util.ModuleCommandListener;
import dev.nuker.pyro.deobfuscated.command.impl.MacroCommand;
import dev.nuker.pyro.deobfuscated.command.impl.StalkCommand;
import dev.nuker.pyro.deobfuscated.command.impl.DonkeyChestCommand;
import dev.nuker.pyro.deobfuscated.command.impl.WaypointCommand;
import dev.nuker.pyro.deobfuscated.command.impl.PresetsCommand;
import dev.nuker.pyro.deobfuscated.command.impl.FontCommand;
import dev.nuker.pyro.deobfuscated.command.impl.ResetGUICommand;
import dev.nuker.pyro.deobfuscated.command.impl.UnbindCommand;
import dev.nuker.pyro.deobfuscated.command.impl.BindCommand;
import dev.nuker.pyro.deobfuscated.command.impl.ToggleCommand;
import dev.nuker.pyro.deobfuscated.command.impl.VClipCommand;
import dev.nuker.pyro.deobfuscated.command.impl.AnnoyCommand;
import dev.nuker.pyro.deobfuscated.command.impl.HClipCommand;
import dev.nuker.pyro.deobfuscated.command.impl.SoundReloadCommand;
import dev.nuker.pyro.deobfuscated.command.impl.HelpCommand;
import dev.nuker.pyro.deobfuscated.command.impl.FriendCommand;
import dev.nuker.pyro.deobfuscated.command.Command;
import java.util.ArrayList;

public class CommandManager
{
    private ArrayList<Command> Commands;
    
    public CommandManager() {
        this.Commands = new ArrayList<Command>();
    }
    
    public void InitializeCommands() {
        this.Commands.add(new FriendCommand());
        this.Commands.add(new HelpCommand());
        this.Commands.add(new SoundReloadCommand());
        this.Commands.add(new HClipCommand());
        this.Commands.add(new AnnoyCommand());
        this.Commands.add(new VClipCommand());
        this.Commands.add(new ToggleCommand());
        this.Commands.add(new BindCommand());
        this.Commands.add(new UnbindCommand());
        this.Commands.add(new ResetGUICommand());
        this.Commands.add(new FontCommand());
        this.Commands.add(new PresetsCommand());
        this.Commands.add(new WaypointCommand());
        this.Commands.add(new DonkeyChestCommand());
        this.Commands.add(new StalkCommand());
        this.Commands.add(new MacroCommand());
        final ModuleCommandListener l_Listener;
        ModuleManager.Get().GetModuleList().forEach(p_Mod -> {
            l_Listener = new ModuleCommandListener() {
                final /* synthetic */ Module val$p_Mod;
                
                @Override
                public void onHide() {
                    this.val$p_Mod.setHidden(!this.val$p_Mod.isHidden());
                }
                
                @Override
                public void onToggle() {
                    this.val$p_Mod.toggle();
                }
                
                @Override
                public void onRename(final String newName) {
                    this.val$p_Mod.setDisplayName(newName);
                }
            };
            this.Commands.add(new ModuleCommand(p_Mod.getDisplayName(), p_Mod.getDesc(), l_Listener, p_Mod.getValueList()));
            return;
        });
        final ModuleCommandListener l_Listener2;
        HudManager.Get().Items.forEach(p_Item -> {
            l_Listener2 = new ModuleCommandListener() {
                final /* synthetic */ HudComponentItem val$p_Item;
                
                @Override
                public void onHide() {
                    this.val$p_Item.setEnabled(!this.val$p_Item.isEnabled());
                }
                
                @Override
                public void onToggle() {
                    this.val$p_Item.setEnabled(!this.val$p_Item.isEnabled());
                }
                
                @Override
                public void onRename(final String newName) {
                }
            };
            this.Commands.add(new ModuleCommand(p_Item.getDisplayName(), "NYI", l_Listener2, p_Item.getValueList()));
            return;
        });
        this.Commands.sort(Comparator.comparing((Function<? super Command, ? extends Comparable>)Command::getName));
    }
    
    public final ArrayList<Command> GetCommands() {
        return this.Commands;
    }
    
    public final List<Command> GetCommandsLike(final String p_Like) {
        return this.Commands.stream().filter(p_Command -> p_Command.getName().toLowerCase().startsWith(p_Like.toLowerCase())).collect((Collector<? super Object, ?, List<Command>>)Collectors.toList());
    }
    
    public static CommandManager Get() {
        return Pyro.GetCommandManager();
    }
    
    public final Command GetCommandLike(final String p_Like) {
        for (final Command l_Command : this.Commands) {
            if (l_Command.getName().toLowerCase().startsWith(p_Like.toLowerCase())) {
                return l_Command;
            }
        }
        return null;
    }
    
    public void Reload() {
        this.Commands.clear();
        this.InitializeCommands();
    }
    
    public void processCommand(final String substring) {
        final String[] split = substring.split(" ");
        final Command cmd = this.GetCommandLike((split != null) ? split[0] : substring);
        if (cmd == null) {
            Pyro.SendMessage("Invalid Command: " + substring);
            return;
        }
        cmd.processCommand(substring, split);
    }
}
