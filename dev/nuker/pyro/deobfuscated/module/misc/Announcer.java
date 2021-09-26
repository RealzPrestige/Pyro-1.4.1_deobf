//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.item.ItemStack;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.item.ItemBlock;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerLeave;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerJoin;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.Random;
import dev.nuker.pyro.deobfuscated.util.Timer;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Announcer extends Module
{
    public final Value<Boolean> Join;
    public final Value<Boolean> Leave;
    public final Value<Boolean> Place;
    public final Value<Boolean> Break;
    public final Value<Boolean> Food;
    public final Value<Boolean> WorldTime;
    public final Value<Boolean> ClientSideOnly;
    private String lastMsg;
    private ArrayList<String> stringsToChose;
    private Timer timer;
    private Random rand;
    private final String[] leaveMsgs;
    private final String[] joinMsgs;
    private final String[] morningMsgs;
    private final String[] noonMsgs;
    private final String[] afternoonMsgs;
    private final String[] dinnerMsgs;
    private final String[] nightMsgs;
    private final String[] sunsetMsgs;
    private final String[] midNight;
    private final String[] daylightMsgs;
    private String lastBlockBroken;
    private int blocksBroken;
    private ArrayList<BlockPos> blocksBrokenAtPos;
    private String lastBlockPlaced;
    private int blocksPlaced;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    @EventHandler
    private Listener<EventPlayerJoin> onPlayerJoin;
    @EventHandler
    private Listener<EventPlayerLeave> onPlayerLeave;
    
    public Announcer() {
        super("Announcer", new String[] { "Announcer", "Announcer", "Anounce", "Greeter", "Greet" }, "Announces when you do a task", "NONE", -1, ModuleType.MISC);
        this.Join = new Value<Boolean>("Join", new String[0], "Notifies when a player joins", true);
        this.Leave = new Value<Boolean>("Leave", new String[0], "Notifies when a player leaves", true);
        this.Place = new Value<Boolean>("Place", new String[0], "Notifies when you places", true);
        this.Break = new Value<Boolean>("Break", new String[0], "Notifies when you breaks a block", true);
        this.Food = new Value<Boolean>("Food", new String[0], "Notifies when you eat food", true);
        this.WorldTime = new Value<Boolean>("WorldTime", new String[0], "Notifies when the time changes", true);
        this.ClientSideOnly = new Value<Boolean>("ClientSideOnly", new String[0], "Only display clientside", true);
        this.lastMsg = null;
        this.stringsToChose = new ArrayList<String>();
        this.timer = new Timer();
        this.rand = new Random();
        this.leaveMsgs = new String[] { "See you later, ", "Catch ya later, ", "See you next time, ", "Farewell, ", "Bye, ", "Good bye, ", "Later, " };
        this.joinMsgs = new String[] { "Good to see you, ", "Greetings, ", "Hello, ", "Howdy, ", "Hey, ", "Good evening, ", "Welcome to SERVERIP1D5A9E, " };
        this.morningMsgs = new String[] { "Good morning!", "Top of the morning to you!", "Good day!", "You survived another night!", "Good morning everyone!", "The sun is rising in the east, hurrah, hurrah!" };
        this.noonMsgs = new String[] { "Let's go tanning!", "Let's go to the beach!", "Grab your sunglasses!", "Enjoy the sun outside! It is currently very bright!", "It's the brightest time of the day!" };
        this.afternoonMsgs = new String[] { "Good afternoon!", "Let's grab lunch!", "Lunch time, kids!", "Good afternoon everyone!", "IT'S HIGH NOON!" };
        this.dinnerMsgs = new String[] { "Happy hour!", "Let's get crunk!", "Enjoy the sunset everyone!" };
        this.nightMsgs = new String[] { "Let's get comfy!", "Netflix and chill!", "You survived another day!", "Time to go to bed kids!" };
        this.sunsetMsgs = new String[] { "Sunset has now ended! You may eat your lunch now if you are a muslim." };
        this.midNight = new String[] { "It's so dark outside...", "It's the opposite of noon!" };
        this.daylightMsgs = new String[] { "Good bye, zombies!", "Monsters are now burning!", "Burn baby, burn!" };
        this.lastBlockBroken = null;
        this.blocksBroken = 0;
        this.blocksBrokenAtPos = new ArrayList<BlockPos>();
        this.lastBlockPlaced = null;
        this.blocksPlaced = 0;
        int index;
        String rand;
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            if (!this.timer.passed(10000.0)) {
                return;
            }
            else {
                if (this.lastBlockBroken != null && this.Break.getValue()) {
                    this.stringsToChose.add("I just mined " + String.valueOf(this.blocksBroken) + " " + this.lastBlockBroken + "!");
                    this.blocksBroken = 0;
                    this.lastBlockBroken = null;
                    this.blocksBrokenAtPos = null;
                }
                if (this.lastBlockPlaced != null && this.Place.getValue()) {
                    this.stringsToChose.add("I just placed " + String.valueOf(this.blocksPlaced) + " " + this.lastBlockPlaced + "!");
                    this.lastBlockPlaced = null;
                    this.blocksPlaced = 0;
                }
                if (!this.stringsToChose.isEmpty()) {
                    index = this.rand.nextInt(this.stringsToChose.size());
                    rand = this.stringsToChose.get(index);
                    this.stringsToChose.remove(rand);
                    this.timer.reset();
                    this.sendToChat(rand);
                }
                return;
            }
        });
        CPacketPlayerDigging packet;
        String block;
        CPacketPlayerTryUseItemOnBlock packet2;
        ItemStack stack;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof CPacketPlayerDigging && this.Break.getValue()) {
                    packet = (CPacketPlayerDigging)event.getPacket();
                    block = this.mc.world.getBlockState(packet.getPosition()).getBlock().getLocalizedName();
                    if (packet.getAction() == CPacketPlayerDigging.Action.START_DESTROY_BLOCK && (this.lastBlockBroken == null || this.lastBlockBroken.equals(block)) && !this.blocksBrokenAtPos.contains(packet.getPosition())) {
                        this.lastBlockBroken = block;
                        this.blocksBrokenAtPos.add(packet.getPosition());
                        ++this.blocksBroken;
                    }
                }
                else if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && this.Place.getValue()) {
                    packet2 = (CPacketPlayerTryUseItemOnBlock)event.getPacket();
                    stack = this.mc.player.getHeldItem(packet2.getHand());
                    if (!stack.isEmpty()) {
                        if (stack.getItem() instanceof ItemBlock && (this.lastBlockPlaced == null || stack.getDisplayName().equals(this.lastBlockPlaced))) {
                            this.lastBlockPlaced = stack.getDisplayName();
                            ++this.blocksPlaced;
                        }
                    }
                }
                return;
            }
        });
        final StringBuilder sb;
        this.onPlayerJoin = new Listener<EventPlayerJoin>(event -> {
            if (!this.timer.passed(2000.0)) {
                return;
            }
            else {
                this.stringsToChose.clear();
                this.timer.reset();
                if (FriendManager.Get().IsFriend(event.getName())) {
                    this.sendToChat("My friend " + event.getName() + " just joined the server!");
                }
                else {
                    new StringBuilder(this.joinMsgs[this.rand.nextInt(this.joinMsgs.length - 1)].replace("SERVERIP1D5A9E", (this.mc.getCurrentServerData() != null) ? this.mc.getCurrentServerData().serverIP : "127.0.0.1"));
                    this.sendToChat(sb.append(event.getName()).toString());
                }
                return;
            }
        });
        this.onPlayerLeave = new Listener<EventPlayerLeave>(event -> {
            if (!(!this.timer.passed(2000.0))) {
                this.stringsToChose.clear();
                this.timer.reset();
                this.sendToChat(this.leaveMsgs[this.rand.nextInt(this.joinMsgs.length - 1)] + event.getName());
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.stringsToChose.clear();
    }
    
    private void sendToChat(final String msg) {
        if (this.ClientSideOnly.getValue()) {
            this.SendMessage(msg);
        }
        else {
            this.mc.player.sendChatMessage(msg);
        }
    }
}
