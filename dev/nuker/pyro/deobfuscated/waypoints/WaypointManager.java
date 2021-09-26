//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.waypoints;

import com.mojang.authlib.GameProfile;
import dev.nuker.pyro.deobfuscated.managers.UUIDManager;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.module.render.Waypoints;
import java.io.Writer;
import java.io.IOException;
import java.nio.file.OpenOption;
import com.google.gson.GsonBuilder;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import java.io.Reader;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.gson.Gson;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.PyroMod;
import dev.nuker.pyro.deobfuscated.util.SalVec3d;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import com.google.common.collect.Maps;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJoin;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerLeave;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.entity.player.EntityPlayer;
import java.util.Map;
import java.util.List;
import dev.nuker.pyro.deobfuscated.events.bus.EventListener;

public class WaypointManager implements EventListener
{
    private List<Waypoint> _waypoints;
    private final Map<String, EntityPlayer> playerCache;
    private final Map<String, PlayerData> logoutCache;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerLeave> onPlayerLeave;
    @EventHandler
    private Listener<EventPlayerJoin> onPlayerJoin;
    
    public WaypointManager() {
        this.playerCache = (Map<String, EntityPlayer>)Maps.newConcurrentMap();
        this.logoutCache = (Map<String, PlayerData>)Maps.newConcurrentMap();
        final Minecraft mc;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            mc = Wrapper.GetMC();
            mc.world.playerEntities.iterator();
            while (iterator.hasNext()) {
                player = iterator.next();
                if (player != null) {
                    if (player.equals((Object)mc.player)) {
                        continue;
                    }
                    else {
                        this.updatePlayerCache(player.getGameProfile().getId().toString(), player);
                    }
                }
            }
            return;
        });
        final Iterator<String> iterator2;
        String uuid;
        EntityPlayer player2;
        PlayerData data;
        this.onPlayerLeave = new Listener<EventPlayerLeave>(event -> {
            this.playerCache.keySet().iterator();
            while (iterator2.hasNext()) {
                uuid = iterator2.next();
                if (!uuid.equals(event.getId())) {
                    continue;
                }
                else {
                    player2 = this.playerCache.get(uuid);
                    data = new PlayerData(player2.getPositionVector(), player2.getGameProfile(), player2);
                    if (!this.hasPlayerLogged(uuid)) {
                        this.logoutCache.put(uuid, data);
                        this.AddWaypoint(Waypoint.Type.Logout, player2.getName() + " logout spot", new SalVec3d(player2.posX, player2.posY, player2.posZ), player2.dimension);
                    }
                    else {
                        continue;
                    }
                }
            }
            this.playerCache.clear();
            return;
        });
        final Iterator<String> iterator3;
        String uuid2;
        this.onPlayerJoin = new Listener<EventPlayerJoin>(event -> {
            this.logoutCache.keySet().iterator();
            while (iterator3.hasNext()) {
                uuid2 = iterator3.next();
                if (!uuid2.equals(event.getId())) {
                    continue;
                }
                else {
                    this.logoutCache.remove(uuid2);
                }
            }
            this.playerCache.clear();
            return;
        });
        PyroMod.EVENT_BUS.subscribe(this);
        this._waypoints = new CopyOnWriteArrayList<Waypoint>();
        try {
            final Gson gson = new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get("Pyro/Waypoints/Waypoints.json", new String[0]));
            this._waypoints = (List<Waypoint>)gson.fromJson(reader, new TypeToken<CopyOnWriteArrayList<Waypoint>>() {}.getType());
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static WaypointManager Get() {
        return Pyro.GetWaypointManager();
    }
    
    public List<Waypoint> GetWaypoints() {
        return this._waypoints;
    }
    
    public final Map<String, PlayerData> GetLogoutCache() {
        return this.logoutCache;
    }
    
    public void AddWaypoint(final Waypoint.Type type, final String name, final SalVec3d pos, final int dimension) {
        this._waypoints.add(new Waypoint(name, pos, type, (Wrapper.GetMC().getCurrentServerData() != null) ? Wrapper.GetMC().getCurrentServerData().serverIP : "singleplayer", dimension));
        this.ProcessSave();
    }
    
    public boolean RemoveWaypoint(final String name) {
        if (this._waypoints.isEmpty()) {
            return false;
        }
        if (name == null) {
            this._waypoints.remove(this._waypoints.size() - 1);
            return true;
        }
        Waypoint pointToRemove = null;
        for (final Waypoint point : this._waypoints) {
            if (point.getDisplayName().equals(name)) {
                pointToRemove = point;
                break;
            }
        }
        if (pointToRemove != null) {
            this._waypoints.remove(pointToRemove);
            this.ProcessSave();
        }
        return pointToRemove != null;
    }
    
    public boolean EditWaypoint(final String name, final SalVec3d pos) {
        Waypoint pointToEdit = null;
        for (final Waypoint point : this._waypoints) {
            if (point.getDisplayName().equals(name)) {
                pointToEdit = point;
                break;
            }
        }
        if (pointToEdit != null) {
            pointToEdit.setPos(pos);
            this.ProcessSave();
            return true;
        }
        return false;
    }
    
    private void ProcessSave() {
        try {
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.setPrettyPrinting().create();
            final Writer writer = Files.newBufferedWriter(Paths.get("Pyro/Waypoints/Waypoints.json", new String[0]), new OpenOption[0]);
            gson.toJson((Object)this._waypoints, (Appendable)writer);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void updatePlayerCache(final String uuid, final EntityPlayer player) {
        this.playerCache.put(uuid, player);
    }
    
    private boolean hasPlayerLogged(final String uuid) {
        return this.logoutCache.containsKey(uuid);
    }
    
    public boolean isOutOfRange(final PlayerData data) {
        final Vec3d position = data.position;
        return Wrapper.GetMC().player.getDistance(position.x, position.y, position.z) > Waypoints.RemoveDistance.getValue();
    }
    
    public Map<String, EntityPlayer> getPlayerCache() {
        return this.playerCache;
    }
    
    public Map<String, PlayerData> getLogoutCache() {
        return this.logoutCache;
    }
    
    public void RemoveLogoutCache(final String uuid) {
        this.logoutCache.remove(uuid);
        final String name;
        new Thread(() -> {
            name = UUIDManager.Get().resolveName(uuid);
            if (name != null) {
                this.RemoveWaypoint(name);
            }
        }).start();
    }
    
    public class PlayerData
    {
        public Vec3d position;
        public GameProfile profile;
        public EntityPlayer ghost;
        
        public PlayerData(final Vec3d position, final GameProfile profile, final EntityPlayer ghost) {
            this.position = position;
            this.profile = profile;
            this.ghost = ghost;
        }
    }
}
