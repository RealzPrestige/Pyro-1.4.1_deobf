// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.command.impl;

import dev.nuker.pyro.deobfuscated.friend.Friend;
import com.google.gson.internal.LinkedTreeMap;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import dev.nuker.pyro.deobfuscated.command.Command;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("Friend", "Allows you to communicate with the friend manager, allowing for adding/removing/updating friends");
        this.commandChunks.add("add <username>");
        this.commandChunks.add("remove <username>");
        this.commandChunks.add("list");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        if (args.length <= 1) {
            this.SendToChat("Invalid Input");
            return;
        }
        if (args[1].toLowerCase().startsWith("a")) {
            if (FriendManager.Get().AddFriend(args[2].toLowerCase())) {
                this.SendToChat(String.format("Added %s as a friend.", args[2]));
            }
            else {
                this.SendToChat(String.format("%s is already a friend.", args[2]));
            }
        }
        else if (args[1].toLowerCase().startsWith("r")) {
            if (FriendManager.Get().RemoveFriend(args[2].toLowerCase())) {
                this.SendToChat(String.format("Removed %s as a friend.", args[2]));
            }
            else {
                this.SendToChat(String.format("%s is not a friend.", args[2]));
            }
        }
        else if (args[1].toLowerCase().startsWith("l")) {
            final LinkedTreeMap<String, Friend> friendMap = FriendManager.Get().GetFriends();
            friendMap.forEach((k, v) -> this.SendToChat(String.format("F: %s A: %s", v.GetName(), v.GetAlias())));
            if (friendMap.isEmpty()) {
                this.SendToChat("You don't have any friends...");
            }
        }
    }
    
    @Override
    public String getHelp() {
        return "Allows you to add friends, or remove friends or list friends..\nfriend add <name>\nfriend remove<name>\nfriend list";
    }
}
