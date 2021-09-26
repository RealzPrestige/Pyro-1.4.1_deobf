//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import dev.nuker.pyro.deobfuscated.main.Pyro;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Set;
import java.util.HashMap;

public class PlayerAlert
{
    private static HashMap<String, PlayerData> playeruuidmap;
    
    public static HashMap<String, String> getUUIDPlayerName() {
        final HashMap<String, String> toReturn = new HashMap<String, String>();
        for (final String uuid : PlayerAlert.playeruuidmap.keySet()) {
            toReturn.put(uuid, PlayerAlert.playeruuidmap.get(uuid).oldname);
        }
        return toReturn;
    }
    
    public static HashMap<String, PlayerData> dataCopy() {
        return new HashMap<String, PlayerData>(PlayerAlert.playeruuidmap);
    }
    
    public static Set<String> getOrderedNames() {
        final TreeSet<String> toReturn = new TreeSet<String>();
        for (final String uuid : PlayerAlert.playeruuidmap.keySet()) {
            toReturn.add(PlayerAlert.playeruuidmap.get(uuid).oldname);
        }
        return toReturn;
    }
    
    public static boolean alertExists(final String name) {
        final String[] data = MojangGetter.getInfoFromName(name);
        return data != null && PlayerAlert.playeruuidmap.containsKey(data[0]);
    }
    
    public static String addName(final String name) {
        if (alertExists(name)) {
            return null;
        }
        final String[] data = MojangGetter.getInfoFromName(name);
        if (data == null) {
            return null;
        }
        PlayerAlert.playeruuidmap.put(data[0], new PlayerData(data[0], data[1]));
        return data[1];
    }
    
    public static boolean removeName(final String name) {
        final String[] data = MojangGetter.getInfoFromName(name);
        if (data == null) {
            return false;
        }
        final String playeruuid = data[0];
        return PlayerAlert.playeruuidmap.remove(playeruuid) != null;
    }
    
    public static void removeUUID(final String uuid) {
        PlayerAlert.playeruuidmap.remove(uuid);
    }
    
    public static void addFromConfig(final String uuid, final String name, final String prefix, final boolean alert, final boolean queue) {
        PlayerAlert.playeruuidmap.put(uuid, new PlayerData(uuid, name, prefix, alert, queue));
    }
    
    public static String getMessage(final String uuid) {
        if (PlayerAlert.playeruuidmap.get(uuid) == null) {
            return null;
        }
        final String setname = PlayerAlert.playeruuidmap.get(uuid).oldname;
        final String realname = MojangGetter.getCurrentNameFromUUID(uuid);
        if (realname == null || setname.equals(realname)) {
            return "Player " + setname + " has %s";
        }
        return "Player " + realname + " has %s. Previously " + setname;
    }
    
    public static String getNameFromMap(final String uuid) {
        return PlayerAlert.playeruuidmap.get(uuid).oldname;
    }
    
    public static List<String> getOrderedUUIDFromName() {
        final List<String> uuids = new ArrayList<String>(PlayerAlert.playeruuidmap.keySet());
        Collections.sort(uuids, new Comparator<String>() {
            @Override
            public int compare(final String o1, final String o2) {
                return PlayerAlert.playeruuidmap.get(o1).oldname.toLowerCase().compareTo(PlayerAlert.playeruuidmap.get(o2).oldname.toLowerCase());
            }
        });
        return uuids;
    }
    
    public static void updateQueuePositions() {
        final ArrayList<String> nameArray = RebaneGetter.getNames();
        for (final String uuid : getOrderedUUIDFromName()) {
            final String name = getNameFromMap(uuid);
            if (nameArray.contains(name)) {
                final PlayerData playerData;
                if (!(playerData = PlayerAlert.playeruuidmap.get(uuid)).inGame) {
                    playerData.queuePos = nameArray.indexOf(name) + 1;
                    if (!playerData.queue || playerData.sentInQueue) {
                        continue;
                    }
                    playerData.sentInQueue = true;
                    playerData.sentLeftQueue = false;
                    playerData.wasInQueue = true;
                    final String toSend = "Player: " + PlayerAlert.playeruuidmap.get(uuid).oldname + " is in queue [" + playerData.queuePos + "]";
                    if (Wrapper.GetMC().player == null) {
                        continue;
                    }
                    Pyro.SendMessage(toSend);
                }
                else {
                    PlayerAlert.playeruuidmap.get(uuid).queuePos = -1;
                    if (playerData.sentLeftQueue || !playerData.wasInQueue) {
                        continue;
                    }
                    playerData.wasInQueue = false;
                    playerData.sentLeftQueue = true;
                    final String toSend = "" + PlayerAlert.playeruuidmap.get(uuid).oldname + " has left the queue.";
                    if (Wrapper.GetMC().player == null || !playerData.queue) {
                        continue;
                    }
                    Pyro.SendMessage(toSend);
                }
            }
            else {
                final PlayerData player;
                (player = PlayerAlert.playeruuidmap.get(uuid)).queuePos = -1;
                player.sentInQueue = false;
                if (player.sentLeftQueue || !player.wasInQueue) {
                    continue;
                }
                player.wasInQueue = false;
                player.sentLeftQueue = true;
                final String toSend = "" + PlayerAlert.playeruuidmap.get(uuid).oldname + " has left the queue.";
                if (Wrapper.GetMC().player == null || !player.queue) {
                    continue;
                }
                Pyro.SendMessage(toSend);
            }
        }
    }
    
    public static void toggleAllAlerts(final boolean bool) {
        for (final String uuid : PlayerAlert.playeruuidmap.keySet()) {
            PlayerAlert.playeruuidmap.get(uuid).alert = bool;
        }
    }
    
    public static void toggleAllQueue(final boolean bool) {
        for (final String uuid : PlayerAlert.playeruuidmap.keySet()) {
            PlayerAlert.playeruuidmap.get(uuid).queue = bool;
        }
    }
    
    public static void updateSendAlert(final String uuid, final boolean bool) {
        PlayerAlert.playeruuidmap.get(uuid).alert = bool;
    }
    
    public static void updateQueueSendAlert(final String uuid, final boolean bool) {
        PlayerAlert.playeruuidmap.get(uuid).queue = bool;
    }
    
    public static void updatePrefix(final String uuid, final String prefix) {
        PlayerAlert.playeruuidmap.get(uuid).chatPrefix = prefix;
    }
    
    public static void updateName(final String uuid, final String newname) {
        PlayerAlert.playeruuidmap.get(uuid).oldname = newname;
    }
    
    public static void toggleOnline(final String uuid, final boolean bool) {
        PlayerAlert.playeruuidmap.get(uuid).inGame = bool;
    }
    
    public static void updateQueuePos(final String uuid, final int indexOf) {
        PlayerAlert.playeruuidmap.get(uuid).queuePos = indexOf;
    }
    
    static {
        PlayerAlert.playeruuidmap = new HashMap<String, PlayerData>();
    }
    
    public static class PlayerData
    {
        private String uuid;
        private String oldname;
        private String newname;
        private String chatPrefix;
        private boolean inGame;
        private boolean alert;
        private boolean queue;
        private boolean sentInQueue;
        private boolean sentLeftQueue;
        private boolean wasInQueue;
        private int queuePos;
        
        private PlayerData(final String uuid, final String oldname) {
            this(uuid, oldname, "", true, true);
        }
        
        private PlayerData(final String uuid, final String oldname, final String prefix, final boolean alert, final boolean queue) {
            this.oldname = oldname;
            this.uuid = uuid;
            this.chatPrefix = prefix;
            this.newname = MojangGetter.getCurrentNameFromUUID(uuid);
            this.inGame = false;
            this.sentInQueue = false;
            this.sentLeftQueue = false;
            this.wasInQueue = false;
            this.queuePos = -1;
            this.alert = alert;
            this.queue = queue;
        }
        
        public boolean needNameUpdate() {
            return this.newname != null && !this.oldname.equals(this.newname);
        }
        
        public String getOldName() {
            return this.oldname;
        }
        
        public String getNewName() {
            return this.newname;
        }
        
        public String getUUID() {
            return this.uuid;
        }
        
        public String getChatPrefix() {
            return this.chatPrefix;
        }
        
        public boolean getInGame() {
            return this.inGame;
        }
        
        public boolean getSendAlert() {
            return this.alert;
        }
        
        public boolean getSendQueueAlert() {
            return this.queue;
        }
        
        public int queuePos() {
            return this.queuePos;
        }
    }
}
