//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityBoat;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.util.text.Style;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.chunk.Chunk;
import java.util.Iterator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Blocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.client.Minecraft;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketJoinGame;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import dev.nuker.pyro.deobfuscated.events.world.EventLoadWorld;
import dev.nuker.pyro.deobfuscated.events.world.EventChunkLoad;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.Vec3d;
import java.util.List;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Tracers extends Module
{
    public final Value<Boolean> Players;
    public final Value<Boolean> Friends;
    public final Value<Boolean> Invisibles;
    public final Value<Boolean> Monsters;
    public final Value<Boolean> Animals;
    public final Value<Boolean> Vehicles;
    public final Value<Boolean> Items;
    public final Value<Boolean> Others;
    public final Value<Boolean> Portals;
    public final Value<Integer> Width;
    private final List<Vec3d> portals;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    @EventHandler
    private Listener<EventChunkLoad> onChunkLoad;
    @EventHandler
    private Listener<EventLoadWorld> onLoadWorld;
    
    public Tracers() {
        super("Tracers", new String[] { "Tracers" }, "Draws tracer to a given entity", "NONE", -1, ModuleType.RENDER);
        this.Players = new Value<Boolean>("Players", new String[] { "Players" }, "Traces players", true);
        this.Friends = new Value<Boolean>("Friends", new String[] { "Friends" }, "Traces friends", true);
        this.Invisibles = new Value<Boolean>("Invisibles", new String[] { "Invisibles" }, "Traces invisibles", true);
        this.Monsters = new Value<Boolean>("Monsters", new String[] { "Monsters" }, "Traces monsters", false);
        this.Animals = new Value<Boolean>("Animals", new String[] { "Animals" }, "Traces animals", false);
        this.Vehicles = new Value<Boolean>("Vehicles", new String[] { "Vehicles" }, "Traces Vehicles", false);
        this.Items = new Value<Boolean>("Items", new String[] { "Items" }, "Traces items", true);
        this.Others = new Value<Boolean>("Others", new String[] { "Others" }, "Traces others", false);
        this.Portals = new Value<Boolean>("Portals", new String[] { "Portals" }, "Traces Portals", true);
        this.Width = new Value<Integer>("Width", new String[] { "Width" }, "Width", 1, 1, 30, 1);
        this.portals = new CopyOnWriteArrayList<Vec3d>();
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketJoinGame) {
                    this.portals.clear();
                }
                return;
            }
        });
        final Iterator<Entity> iterator;
        Entity entity;
        Vec3d pos;
        boolean bobbing;
        Vec3d forward;
        final Iterator<Vec3d> iterator2;
        Vec3d portal;
        boolean bobbing2;
        Vec3d forward2;
        this.OnRenderEvent = new Listener<RenderEvent>(event -> {
            if (this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
                return;
            }
            else {
                this.mc.world.loadedEntityList.iterator();
                while (iterator.hasNext()) {
                    entity = iterator.next();
                    if (this.shouldRenderTracer(entity)) {
                        pos = MathUtil.interpolateEntity(entity, event.getPartialTicks()).subtract(this.mc.getRenderManager().renderPosX, this.mc.getRenderManager().renderPosY, this.mc.getRenderManager().renderPosZ);
                        if (pos != null) {
                            bobbing = this.mc.gameSettings.viewBobbing;
                            this.mc.gameSettings.viewBobbing = false;
                            this.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                            forward = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
                            RenderUtil.drawLine3D((float)forward.x, (float)forward.y + this.mc.player.getEyeHeight(), (float)forward.z, (float)pos.x, (float)pos.y, (float)pos.z, this.Width.getValue(), this.getColor(entity));
                            this.mc.gameSettings.viewBobbing = bobbing;
                            this.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                        }
                        else {
                            continue;
                        }
                    }
                }
                if (this.Portals.getValue()) {
                    this.portals.iterator();
                    while (iterator2.hasNext()) {
                        portal = iterator2.next();
                        GlStateManager.pushMatrix();
                        bobbing2 = this.mc.gameSettings.viewBobbing;
                        this.mc.gameSettings.viewBobbing = false;
                        this.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                        forward2 = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationPitch)).rotateYaw(-(float)Math.toRadians(Minecraft.getMinecraft().player.rotationYaw));
                        RenderUtil.drawLine3D((float)forward2.x, (float)forward2.y + this.mc.player.getEyeHeight(), (float)forward2.z, (float)(portal.x - this.mc.getRenderManager().renderPosX), (float)(portal.y - this.mc.getRenderManager().renderPosY), (float)(portal.z - this.mc.getRenderManager().renderPosZ), this.Width.getValue(), 16777215);
                        RenderUtil.glBillboardDistanceScaled((float)portal.x, (float)portal.y, (float)portal.z, (EntityPlayer)this.mc.player, 1.0f);
                        GlStateManager.disableDepth();
                        this.drawPortalInfoText(portal, 0.0f, 0.0f);
                        GlStateManager.enableDepth();
                        this.mc.gameSettings.viewBobbing = bobbing2;
                        this.mc.entityRenderer.setupCameraTransform(event.getPartialTicks(), 0);
                        GlStateManager.popMatrix();
                    }
                }
                return;
            }
        });
        final Chunk chunk;
        ExtendedBlockStorage[] blockStoragesLoad;
        int i;
        ExtendedBlockStorage extendedBlockStorage;
        int x;
        int y;
        int z;
        IBlockState blockState;
        int worldY;
        BlockPos position;
        Vec3d portal2;
        BlockPos position2;
        Vec3d portal3;
        final Iterator<Vec3d> iterator3;
        Vec3d portal4;
        this.onChunkLoad = new Listener<EventChunkLoad>(event -> {
            switch (event.getType()) {
                case LOAD: {
                    chunk = event.getChunk();
                    for (blockStoragesLoad = chunk.getBlockStorageArray(), i = 0; i < blockStoragesLoad.length; ++i) {
                        extendedBlockStorage = blockStoragesLoad[i];
                        if (extendedBlockStorage != null) {
                            for (x = 0; x < 16; ++x) {
                                for (y = 0; y < 16; ++y) {
                                    for (z = 0; z < 16; ++z) {
                                        blockState = extendedBlockStorage.get(x, y, z);
                                        worldY = y + extendedBlockStorage.getYLocation();
                                        if (blockState.getBlock().equals(Blocks.PORTAL)) {
                                            position = new BlockPos(event.getChunk().getPos().getXStart() + x, worldY, event.getChunk().getPos().getZStart() + z);
                                            if (!this.isPortalCached(position.getX(), position.getY(), position.getZ(), 0.0f)) {
                                                portal2 = new Vec3d((double)position.getX(), (double)position.getY(), (double)position.getZ());
                                                this.portals.add(portal2);
                                                if (this.Portals.getValue()) {
                                                    this.printPortalToChat(portal2);
                                                }
                                                return;
                                            }
                                        }
                                        if (blockState.getBlock().equals(Blocks.END_PORTAL)) {
                                            position2 = new BlockPos(event.getChunk().getPos().getXStart() + x, worldY, event.getChunk().getPos().getZStart() + z);
                                            if (!this.isPortalCached(position2.getX(), position2.getY(), position2.getZ(), 3.0f)) {
                                                portal3 = new Vec3d((double)position2.getX(), (double)position2.getY(), (double)position2.getZ());
                                                this.portals.add(portal3);
                                                if (this.Portals.getValue()) {
                                                    this.printEndPortalToChat(portal3);
                                                }
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
                case UNLOAD: {
                    this.portals.iterator();
                    while (iterator3.hasNext()) {
                        portal4 = iterator3.next();
                        if (this.mc.player.getDistance(portal4.x, portal4.y, portal4.z) > 200.0) {
                            this.portals.remove(portal4);
                        }
                    }
                    break;
                }
            }
            return;
        });
        this.onLoadWorld = new Listener<EventLoadWorld>(event -> this.portals.clear());
    }
    
    private boolean isPortalCached(final int x, final int y, final int z, final float dist) {
        for (int i = this.portals.size() - 1; i >= 0; --i) {
            final Vec3d searchPortal = this.portals.get(i);
            if (searchPortal.distanceTo(new Vec3d((double)x, (double)y, (double)z)) <= dist) {
                return true;
            }
            if (searchPortal.x == x && searchPortal.y == y && searchPortal.z == z) {
                return true;
            }
        }
        return false;
    }
    
    private void printEndPortalToChat(final Vec3d portal) {
        final TextComponentString portalTextComponent = new TextComponentString("End Portal found!");
        final String coords = String.format("X: %s, Y: %s, Z: %s", (int)portal.x, (int)portal.y, (int)portal.z);
        final int playerDistance = (int)Minecraft.getMinecraft().player.getDistance(portal.x, portal.y, portal.z);
        final String distance = ChatFormatting.GRAY + "" + playerDistance + "m away";
        final String hoverText = coords + "\n" + distance;
        portalTextComponent.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(hoverText))));
        Pyro.SendMessage(portalTextComponent);
    }
    
    private void printPortalToChat(final Vec3d portal) {
        final TextComponentString portalTextComponent = new TextComponentString("Portal found!");
        String overworld = "";
        String nether = "";
        if (Minecraft.getMinecraft().player.dimension == 0) {
            overworld = String.format("Overworld: X: %s, Y: %s, Z: %s", (int)portal.x, (int)portal.y, (int)portal.z);
            nether = String.format("Nether: X: %s, Y: %s, Z: %s", (int)portal.x / 8, (int)portal.y, (int)portal.z / 8);
        }
        else if (Minecraft.getMinecraft().player.dimension == -1) {
            overworld = String.format("Overworld: X: %s, Y: %s, Z: %s", (int)portal.x * 8, (int)portal.y, (int)portal.z * 8);
            nether = String.format("Nether: X: %s, Y: %s, Z: %s", (int)portal.x, (int)portal.y, (int)portal.z);
        }
        final int playerDistance = (int)Minecraft.getMinecraft().player.getDistance(portal.x, portal.y, portal.z);
        final String distance = ChatFormatting.GRAY + "" + playerDistance + "m away";
        final String hoverText = overworld + "\n" + nether + "\n" + distance;
        portalTextComponent.setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString(hoverText))));
        Pyro.SendMessage(portalTextComponent);
    }
    
    private void drawPortalInfoText(final Vec3d portal, final float x, final float y) {
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)Minecraft.getMinecraft().player.getDistance(portal.x, portal.y, portal.z) + "m", x, y, -5592406);
    }
    
    public List<Vec3d> getPortals() {
        return this.portals;
    }
    
    public boolean shouldRenderTracer(final Entity e) {
        if (e == Minecraft.getMinecraft().player) {
            return false;
        }
        if (e instanceof EntityPlayer) {
            return this.Players.getValue();
        }
        if (EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e)) {
            return this.Monsters.getValue();
        }
        if (EntityUtil.isPassive(e)) {
            return this.Animals.getValue();
        }
        if (e instanceof EntityBoat || e instanceof EntityMinecart) {
            return this.Vehicles.getValue();
        }
        if (e instanceof EntityItem) {
            return this.Items.getValue();
        }
        return this.Others.getValue();
    }
    
    private int getColor(final Entity e) {
        if (e instanceof EntityPlayer) {
            if (FriendManager.Get().IsFriend(e)) {
                return -16711698;
            }
            return -16711936;
        }
        else {
            if (e.isInvisible()) {
                return -16777216;
            }
            if (EntityUtil.isHostileMob(e) || EntityUtil.isNeutralMob(e)) {
                return -65536;
            }
            if (EntityUtil.isPassive(e)) {
                return -29440;
            }
            if (e instanceof EntityBoat || e instanceof EntityMinecart) {
                return -256;
            }
            if (e instanceof EntityItem) {
                return -5635841;
            }
            return -1;
        }
    }
}
