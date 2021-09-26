//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.waypoints.Waypoint;
import dev.nuker.pyro.deobfuscated.waypoints.WaypointManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dev.nuker.pyro.deobfuscated.util.SalVec3d;
import dev.nuker.pyro.deobfuscated.command.Command;

public class WaypointCommand extends Command
{
    public WaypointCommand() {
        super("Waypoint", "Allows you to create waypoints, remove them, or edit them, if no args are put in, the last created waypoint is used");
        this.commandChunks.add("add <optional: name> x y z");
        this.commandChunks.add("remove <optional: name>");
        this.commandChunks.add("edit <optional: name> x y z");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat(this.getHelp());
            return;
        }
        String name = null;
        SalVec3d pos = null;
        if (args.length >= 3) {
            name = args[2];
        }
        if (args.length > 3) {
            pos = new SalVec3d(0.0, -420.0, 0.0);
            pos.x = Double.parseDouble(args[3]);
            if (args.length == 4) {
                pos.z = Double.parseDouble(args[4]);
            }
            else {
                pos.y = Double.parseDouble(args[4]);
            }
            if (args.length > 5) {
                pos.z = Double.parseDouble(args[5]);
            }
            if (pos.y == -420.0) {
                pos.y = 100.0;
            }
        }
        if (pos == null) {
            pos = new SalVec3d(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        }
        if (args[1].startsWith("a")) {
            if (name == null) {
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                final LocalDateTime now = LocalDateTime.now();
                name = dtf.format(now);
            }
            WaypointManager.Get().AddWaypoint(Waypoint.Type.Normal, name, pos, this.mc.player.dimension);
        }
        else if (args[1].startsWith("r")) {
            if (WaypointManager.Get().RemoveWaypoint(name)) {
                this.SendToChat("Successfully removed the waypoint named " + ((name == null) ? "last" : name));
            }
            else {
                this.SendToChat("Fail!");
            }
        }
        else if (args[1].startsWith("e")) {
            if (WaypointManager.Get().EditWaypoint(name, pos)) {
                this.SendToChat("Successfully edited the waypoint");
            }
            else {
                this.SendToChat("Fail!");
            }
        }
    }
}
