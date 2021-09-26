//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.block.BlockObsidian;
import net.minecraft.client.Minecraft;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import java.util.Iterator;
import dev.nuker.pyro.deobfuscated.util.Pair;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoBuilder extends Module
{
    public final Value<String> Mode;
    public final Value<String> BuildingMode;
    public final Value<Integer> BlocksPerTick;
    public final Value<Float> Delay;
    public final Value<Boolean> Visualize;
    private Timer timer;
    private Timer NetherPortalTimer;
    private boolean SentPacket;
    ArrayList<BlockPos> BlockArray;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public AutoBuilder() {
        super("AutoBuilder", new String[] { "AutoSwastika" }, "Builds cool things at your facing block", "NONE", 9886500, ModuleType.WORLD);
        this.Mode = new Value<String>("Mode", new String[] { "" }, "Mode", "Highway");
        this.BuildingMode = new Value<String>("BuildingMode", new String[] { "" }, "Dynamic will update source block while walking, static keeps same position and resets on toggle", "Dynamic");
        this.BlocksPerTick = new Value<Integer>("BlocksPerTick", new String[] { "BPT" }, "Blocks per tick", 4, 1, 10, 1);
        this.Delay = new Value<Float>("Delay", new String[] { "Delay" }, "Delay of the place", 0.0f, 0.0f, 1.0f, 0.1f);
        this.Visualize = new Value<Boolean>("Visualize", new String[] { "Render" }, "Visualizes where blocks are to be placed", true);
        this.timer = new Timer();
        this.NetherPortalTimer = new Timer();
        this.SentPacket = false;
        this.BlockArray = new ArrayList<BlockPos>();
        Vec3d pos;
        BlockPos orignPos;
        Pair<Integer, Block> l_Pair;
        int slot;
        double l_Offset;
        boolean l_NeedPlace;
        float[] rotations;
        int lastSlot;
        int l_BlocksPerTick;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.PlaceResult l_Place;
        int l_I;
        ItemStack l_Stack;
        boolean l_IsSprinting;
        boolean l_IsSneaking;
        this.OnPlayerUpdate = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            this.setMetaData(this.Mode.getValue().toString() + " - " + this.BuildingMode.getValue().toString());
            if (p_Event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else if (!this.timer.passed(this.Delay.getValue() * 1000.0f)) {
                return;
            }
            else {
                this.timer.reset();
                pos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                orignPos = new BlockPos(pos.x, pos.y + 0.5, pos.z);
                l_Pair = this.findStackHotbar();
                slot = -1;
                l_Offset = pos.y - orignPos.getY();
                if (l_Pair != null) {
                    slot = l_Pair.getFirst();
                    if (l_Pair.getSecond() instanceof BlockSlab && l_Offset == 0.5) {
                        orignPos = new BlockPos(pos.x, pos.y + 0.5, pos.z);
                    }
                }
                if (this.BuildingMode.getValue().equals("Dynamic")) {
                    this.BlockArray.clear();
                }
                if (this.BlockArray.isEmpty()) {
                    this.FillBlockArrayAsNeeded(pos, orignPos, l_Pair);
                }
                l_NeedPlace = false;
                rotations = null;
                if (slot != -1 && this.mc.player.onGround) {
                    lastSlot = this.mc.player.inventory.currentItem;
                    this.mc.player.inventory.currentItem = slot;
                    this.mc.playerController.updateController();
                    l_BlocksPerTick = this.BlocksPerTick.getValue();
                    this.BlockArray.iterator();
                    while (iterator.hasNext()) {
                        l_Pos = iterator.next();
                        l_Place = BlockInteractionHelper.place(l_Pos, 5.0f, false, l_Offset == -0.5);
                        if (l_Place != BlockInteractionHelper.PlaceResult.Placed) {
                            continue;
                        }
                        else {
                            l_NeedPlace = true;
                            rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_Pos.getX(), (double)l_Pos.getY(), (double)l_Pos.getZ()));
                            if (--l_BlocksPerTick <= 0) {
                                break;
                            }
                            else {
                                continue;
                            }
                        }
                    }
                    if (!this.slotEqualsBlock(lastSlot, l_Pair.getSecond())) {
                        this.mc.player.inventory.currentItem = lastSlot;
                    }
                    this.mc.playerController.updateController();
                }
                if (!l_NeedPlace && this.Mode.getValue().equals("Portal")) {
                    if (this.mc.world.getBlockState(this.BlockArray.get(0).up()).getBlock() == Blocks.PORTAL || !this.VerifyPortalFrame(this.BlockArray)) {
                        return;
                    }
                    else {
                        if (this.mc.player.getHeldItemMainhand().getItem() != Items.FLINT_AND_STEEL) {
                            for (l_I = 0; l_I < 9; ++l_I) {
                                l_Stack = this.mc.player.inventory.getStackInSlot(l_I);
                                if (!l_Stack.isEmpty()) {
                                    if (l_Stack.getItem() == Items.FLINT_AND_STEEL) {
                                        this.mc.player.inventory.currentItem = l_I;
                                        this.mc.playerController.updateController();
                                        this.NetherPortalTimer.reset();
                                        break;
                                    }
                                }
                            }
                        }
                        if (!this.NetherPortalTimer.passed(500.0)) {
                            if (this.SentPacket) {
                                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                                this.mc.getConnection().sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock((BlockPos)this.BlockArray.get(0), EnumFacing.UP, EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                            }
                            rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)this.BlockArray.get(0).getX(), (double)(this.BlockArray.get(0).getY() + 0.5f), (double)this.BlockArray.get(0).getZ()));
                            l_NeedPlace = true;
                        }
                        else {
                            return;
                        }
                    }
                }
                else if (l_NeedPlace && this.Mode.getValue().equals("Portal")) {
                    this.NetherPortalTimer.reset();
                }
                if (!l_NeedPlace || rotations == null) {
                    this.SentPacket = false;
                    return;
                }
                else {
                    p_Event.cancel();
                    l_IsSprinting = this.mc.player.isSprinting();
                    if (l_IsSprinting != this.mc.player.serverSprintState) {
                        if (l_IsSprinting) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                        }
                        else {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                        }
                        this.mc.player.serverSprintState = l_IsSprinting;
                    }
                    l_IsSneaking = this.mc.player.isSneaking();
                    if (l_IsSneaking != this.mc.player.serverSneakState) {
                        if (l_IsSneaking) {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                        }
                        else {
                            this.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        this.mc.player.serverSneakState = l_IsSneaking;
                    }
                    p_Event.setYaw(rotations[0]);
                    p_Event.setPitch(rotations[1]);
                    this.SentPacket = true;
                    return;
                }
            }
        });
        Iterator l_Itr;
        BlockPos l_Pos2;
        IBlockState l_State;
        AxisAlignedBB bb;
        double dist;
        int l_Color;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (!this.Visualize.getValue()) {
                return;
            }
            else {
                l_Itr = this.BlockArray.iterator();
                while (l_Itr.hasNext()) {
                    l_Pos2 = l_Itr.next();
                    l_State = this.mc.world.getBlockState(l_Pos2);
                    if (l_State != null && l_State.getBlock() != Blocks.AIR && l_State.getBlock() != Blocks.WATER) {
                        continue;
                    }
                    else {
                        bb = new AxisAlignedBB(l_Pos2.getX() - this.mc.getRenderManager().viewerPosX, l_Pos2.getY() - this.mc.getRenderManager().viewerPosY, l_Pos2.getZ() - this.mc.getRenderManager().viewerPosZ, l_Pos2.getX() + 1 - this.mc.getRenderManager().viewerPosX, l_Pos2.getY() + 1 - this.mc.getRenderManager().viewerPosY, l_Pos2.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
                        RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                        if (RenderUtil.camera.isBoundingBoxInFrustum(new AxisAlignedBB(bb.minX + this.mc.getRenderManager().viewerPosX, bb.minY + this.mc.getRenderManager().viewerPosY, bb.minZ + this.mc.getRenderManager().viewerPosZ, bb.maxX + this.mc.getRenderManager().viewerPosX, bb.maxY + this.mc.getRenderManager().viewerPosY, bb.maxZ + this.mc.getRenderManager().viewerPosZ))) {
                            GlStateManager.pushMatrix();
                            GlStateManager.enableBlend();
                            GlStateManager.disableDepth();
                            GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                            GlStateManager.disableTexture2D();
                            GlStateManager.depthMask(false);
                            GL11.glEnable(2848);
                            GL11.glHint(3154, 4354);
                            GL11.glLineWidth(1.5f);
                            dist = this.mc.player.getDistance((double)(l_Pos2.getX() + 0.5f), (double)(l_Pos2.getY() + 0.5f), (double)(l_Pos2.getZ() + 0.5f)) * 0.75;
                            MathUtil.clamp((float)(dist * 255.0 / 5.0 / 255.0), 0.0f, 0.3f);
                            l_Color = -1878982657;
                            RenderUtil.drawBoundingBox(bb, 1.0f, l_Color);
                            RenderUtil.drawFilledBox(bb, l_Color);
                            GL11.glDisable(2848);
                            GlStateManager.depthMask(true);
                            GlStateManager.enableDepth();
                            GlStateManager.enableTexture2D();
                            GlStateManager.disableBlend();
                            GlStateManager.popMatrix();
                        }
                        else {
                            continue;
                        }
                    }
                }
                return;
            }
        });
        this.setMetaData(this.Mode.getValue().toString() + " - " + this.BuildingMode.getValue().toString());
        this.Mode.addString("Highway");
        this.Mode.addString("Swastika");
        this.Mode.addString("HighwayTunnel");
        this.Mode.addString("Portal");
        this.Mode.addString("Flat");
        this.Mode.addString("Tower");
        this.Mode.addString("Cover");
        this.Mode.addString("Wall");
        this.Mode.addString("HighwayWall");
        this.Mode.addString("Stair");
        this.BuildingMode.addString("Dynamic");
        this.BuildingMode.addString("Static");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            this.toggle();
            return;
        }
        this.timer.reset();
        this.BlockArray.clear();
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private void FillBlockArrayAsNeeded(final Vec3d pos, final BlockPos orignPos, final Pair<Integer, Block> p_Pair) {
        BlockPos interpPos = null;
        final String s = this.Mode.getValue();
        int n = -1;
        switch (s.hashCode()) {
            case -1714812435: {
                if (s.equals("Highway")) {
                    n = 0;
                    break;
                }
                break;
            }
            case 1098883253: {
                if (s.equals("HighwayTunnel")) {
                    n = 1;
                    break;
                }
                break;
            }
            case -250081983: {
                if (s.equals("Swastika")) {
                    n = 2;
                    break;
                }
                break;
            }
            case -1898613620: {
                if (s.equals("Portal")) {
                    n = 3;
                    break;
                }
                break;
            }
            case 2192281: {
                if (s.equals("Flat")) {
                    n = 4;
                    break;
                }
                break;
            }
            case 65299351: {
                if (s.equals("Cover")) {
                    n = 5;
                    break;
                }
                break;
            }
            case 81000169: {
                if (s.equals("Tower")) {
                    n = 6;
                    break;
                }
                break;
            }
            case 2688490: {
                if (s.equals("Wall")) {
                    n = 7;
                    break;
                }
                break;
            }
            case 819089751: {
                if (s.equals("HighwayWall")) {
                    n = 8;
                    break;
                }
                break;
            }
            case 80204585: {
                if (s.equals("Stair")) {
                    n = 9;
                    break;
                }
                break;
            }
        }
        Label_6479: {
            switch (n) {
                case 0: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            this.BlockArray.add(orignPos.down());
                            this.BlockArray.add(orignPos.down().east());
                            this.BlockArray.add(orignPos.down().east().north());
                            this.BlockArray.add(orignPos.down().east().south());
                            this.BlockArray.add(orignPos.down().east().north().north());
                            this.BlockArray.add(orignPos.down().east().south().south());
                            this.BlockArray.add(orignPos.down().east().north().north().north());
                            this.BlockArray.add(orignPos.down().east().south().south().south());
                            this.BlockArray.add(orignPos.down().east().north().north().north().up());
                            this.BlockArray.add(orignPos.down().east().south().south().south().up());
                            break Label_6479;
                        }
                        case North: {
                            this.BlockArray.add(orignPos.down());
                            this.BlockArray.add(orignPos.down().north());
                            this.BlockArray.add(orignPos.down().north().east());
                            this.BlockArray.add(orignPos.down().north().west());
                            this.BlockArray.add(orignPos.down().north().east().east());
                            this.BlockArray.add(orignPos.down().north().west().west());
                            this.BlockArray.add(orignPos.down().north().east().east().east());
                            this.BlockArray.add(orignPos.down().north().west().west().west());
                            this.BlockArray.add(orignPos.down().north().east().east().east().up());
                            this.BlockArray.add(orignPos.down().north().west().west().west().up());
                            break Label_6479;
                        }
                        case South: {
                            this.BlockArray.add(orignPos.down());
                            this.BlockArray.add(orignPos.down().south());
                            this.BlockArray.add(orignPos.down().south().east());
                            this.BlockArray.add(orignPos.down().south().west());
                            this.BlockArray.add(orignPos.down().south().east().east());
                            this.BlockArray.add(orignPos.down().south().west().west());
                            this.BlockArray.add(orignPos.down().south().east().east().east());
                            this.BlockArray.add(orignPos.down().south().west().west().west());
                            this.BlockArray.add(orignPos.down().south().east().east().east().up());
                            this.BlockArray.add(orignPos.down().south().west().west().west().up());
                            break Label_6479;
                        }
                        case West: {
                            this.BlockArray.add(orignPos.down());
                            this.BlockArray.add(orignPos.down().west());
                            this.BlockArray.add(orignPos.down().west().north());
                            this.BlockArray.add(orignPos.down().west().south());
                            this.BlockArray.add(orignPos.down().west().north().north());
                            this.BlockArray.add(orignPos.down().west().south().south());
                            this.BlockArray.add(orignPos.down().west().north().north().north());
                            this.BlockArray.add(orignPos.down().west().south().south().south());
                            this.BlockArray.add(orignPos.down().west().north().north().north().up());
                            this.BlockArray.add(orignPos.down().west().south().south().south().up());
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
                case 1: {
                    this.BlockArray.add(orignPos.down());
                    this.BlockArray.add(orignPos.down().north());
                    this.BlockArray.add(orignPos.down().north().east());
                    this.BlockArray.add(orignPos.down().north().west());
                    this.BlockArray.add(orignPos.down().north().east().east());
                    this.BlockArray.add(orignPos.down().north().west().west());
                    this.BlockArray.add(orignPos.down().north().east().east().east());
                    this.BlockArray.add(orignPos.down().north().west().west().west());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up().up());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up().up());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up().up().up());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up().up().up());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up().up().up().west());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up().up().up().east());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up().up().up().west().west());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up().up().up().east().east());
                    this.BlockArray.add(orignPos.down().north().east().east().east().up().up().up().up().west().west().west());
                    this.BlockArray.add(orignPos.down().north().west().west().west().up().up().up().up().east().east().east());
                    break;
                }
                case 2: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).east().east();
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.north());
                            this.BlockArray.add(interpPos.north().north());
                            this.BlockArray.add(interpPos.up());
                            this.BlockArray.add(interpPos.up().up());
                            this.BlockArray.add(interpPos.up().up().north());
                            this.BlockArray.add(interpPos.up().up().north().north());
                            this.BlockArray.add(interpPos.up().up().north().north().up());
                            this.BlockArray.add(interpPos.up().up().north().north().up().up());
                            this.BlockArray.add(interpPos.up().up().south());
                            this.BlockArray.add(interpPos.up().up().south().south());
                            this.BlockArray.add(interpPos.up().up().south().south().down());
                            this.BlockArray.add(interpPos.up().up().south().south().down().down());
                            this.BlockArray.add(interpPos.up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up().south());
                            this.BlockArray.add(interpPos.up().up().up().up().south().south());
                            break Label_6479;
                        }
                        case North: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).north().north();
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.west());
                            this.BlockArray.add(interpPos.west().west());
                            this.BlockArray.add(interpPos.up());
                            this.BlockArray.add(interpPos.up().up());
                            this.BlockArray.add(interpPos.up().up().west());
                            this.BlockArray.add(interpPos.up().up().west().west());
                            this.BlockArray.add(interpPos.up().up().west().west().up());
                            this.BlockArray.add(interpPos.up().up().west().west().up().up());
                            this.BlockArray.add(interpPos.up().up().east());
                            this.BlockArray.add(interpPos.up().up().east().east());
                            this.BlockArray.add(interpPos.up().up().east().east().down());
                            this.BlockArray.add(interpPos.up().up().east().east().down().down());
                            this.BlockArray.add(interpPos.up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up().east());
                            this.BlockArray.add(interpPos.up().up().up().up().east().east());
                            break Label_6479;
                        }
                        case South: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).south().south();
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.east());
                            this.BlockArray.add(interpPos.east().east());
                            this.BlockArray.add(interpPos.up());
                            this.BlockArray.add(interpPos.up().up());
                            this.BlockArray.add(interpPos.up().up().east());
                            this.BlockArray.add(interpPos.up().up().east().east());
                            this.BlockArray.add(interpPos.up().up().east().east().up());
                            this.BlockArray.add(interpPos.up().up().east().east().up().up());
                            this.BlockArray.add(interpPos.up().up().west());
                            this.BlockArray.add(interpPos.up().up().west().west());
                            this.BlockArray.add(interpPos.up().up().west().west().down());
                            this.BlockArray.add(interpPos.up().up().west().west().down().down());
                            this.BlockArray.add(interpPos.up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up().west());
                            this.BlockArray.add(interpPos.up().up().up().up().west().west());
                            break Label_6479;
                        }
                        case West: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).west().west();
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.south());
                            this.BlockArray.add(interpPos.south().south());
                            this.BlockArray.add(interpPos.up());
                            this.BlockArray.add(interpPos.up().up());
                            this.BlockArray.add(interpPos.up().up().south());
                            this.BlockArray.add(interpPos.up().up().south().south());
                            this.BlockArray.add(interpPos.up().up().south().south().up());
                            this.BlockArray.add(interpPos.up().up().south().south().up().up());
                            this.BlockArray.add(interpPos.up().up().north());
                            this.BlockArray.add(interpPos.up().up().north().north());
                            this.BlockArray.add(interpPos.up().up().north().north().down());
                            this.BlockArray.add(interpPos.up().up().north().north().down().down());
                            this.BlockArray.add(interpPos.up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up());
                            this.BlockArray.add(interpPos.up().up().up().up().north());
                            this.BlockArray.add(interpPos.up().up().up().up().north().north());
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).east().east();
                            this.BlockArray.add(interpPos.south());
                            this.BlockArray.add(interpPos.south().south());
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.south().south().up());
                            this.BlockArray.add(interpPos.south().south().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down().down().down());
                            break Label_6479;
                        }
                        case North: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).north().north();
                            this.BlockArray.add(interpPos.east());
                            this.BlockArray.add(interpPos.east().east());
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.east().east().up());
                            this.BlockArray.add(interpPos.east().east().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down().down().down());
                            break Label_6479;
                        }
                        case South: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).south().south();
                            this.BlockArray.add(interpPos.east());
                            this.BlockArray.add(interpPos.east().east());
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.east().east().up());
                            this.BlockArray.add(interpPos.east().east().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down().down());
                            this.BlockArray.add(interpPos.east().east().up().up().up().up().west().west().west().down().down().down().down());
                            break Label_6479;
                        }
                        case West: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).west().west();
                            this.BlockArray.add(interpPos.south());
                            this.BlockArray.add(interpPos.south().south());
                            this.BlockArray.add(interpPos);
                            this.BlockArray.add(interpPos.south().south().up());
                            this.BlockArray.add(interpPos.south().south().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down().down());
                            this.BlockArray.add(interpPos.south().south().up().up().up().up().north().north().north().down().down().down().down());
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
                case 4: {
                    for (int l_X = -3; l_X <= 3; ++l_X) {
                        for (int l_Y = -3; l_Y <= 3; ++l_Y) {
                            this.BlockArray.add(orignPos.down().add(l_X, 0, l_Y));
                        }
                    }
                    break;
                }
                case 5: {
                    if (p_Pair == null) {
                        return;
                    }
                    for (int l_X = -3; l_X < 3; ++l_X) {
                        for (int l_Y = -3; l_Y < 3; ++l_Y) {
                            int l_Tries = 5;
                            BlockPos l_Pos = orignPos.down().add(l_X, 0, l_Y);
                            if (this.mc.world.getBlockState(l_Pos).getBlock() != p_Pair.getSecond() && this.mc.world.getBlockState(l_Pos.down()).getBlock() != Blocks.AIR) {
                                if (this.mc.world.getBlockState(l_Pos.down()).getBlock() != p_Pair.getSecond()) {
                                    while (this.mc.world.getBlockState(l_Pos).getBlock() != Blocks.AIR && this.mc.world.getBlockState(l_Pos).getBlock() != Blocks.FIRE) {
                                        if (this.mc.world.getBlockState(l_Pos).getBlock() == p_Pair.getSecond()) {
                                            break;
                                        }
                                        l_Pos = l_Pos.up();
                                        if (--l_Tries <= 0) {
                                            break;
                                        }
                                    }
                                    this.BlockArray.add(l_Pos);
                                }
                            }
                        }
                    }
                    break;
                }
                case 6: {
                    this.BlockArray.add(orignPos.up());
                    this.BlockArray.add(orignPos);
                    this.BlockArray.add(orignPos.down());
                    break;
                }
                case 7: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).east().east();
                            for (int l_X = -3; l_X <= 3; ++l_X) {
                                for (int l_Y = -3; l_Y <= 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(0, l_Y, l_X));
                                }
                            }
                            break Label_6479;
                        }
                        case North: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).north().north();
                            for (int l_X = -3; l_X <= 3; ++l_X) {
                                for (int l_Y = -3; l_Y <= 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(l_X, l_Y, 0));
                                }
                            }
                            break Label_6479;
                        }
                        case South: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).south().south();
                            for (int l_X = -3; l_X <= 3; ++l_X) {
                                for (int l_Y = -3; l_Y <= 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(l_X, l_Y, 0));
                                }
                            }
                            break Label_6479;
                        }
                        case West: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).west().west();
                            for (int l_X = -3; l_X <= 3; ++l_X) {
                                for (int l_Y = -3; l_Y <= 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(0, l_Y, l_X));
                                }
                            }
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
                case 8: {
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).east().east();
                            for (int l_X = -2; l_X <= 3; ++l_X) {
                                for (int l_Y = 0; l_Y < 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(0, l_Y, l_X));
                                }
                            }
                            break Label_6479;
                        }
                        case North: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).north().north();
                            for (int l_X = -2; l_X <= 3; ++l_X) {
                                for (int l_Y = 0; l_Y < 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(l_X, l_Y, 0));
                                }
                            }
                            break Label_6479;
                        }
                        case South: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).south().south();
                            for (int l_X = -2; l_X <= 3; ++l_X) {
                                for (int l_Y = 0; l_Y < 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(l_X, l_Y, 0));
                                }
                            }
                            break Label_6479;
                        }
                        case West: {
                            interpPos = new BlockPos(pos.x, pos.y, pos.z).west().west();
                            for (int l_X = -2; l_X <= 3; ++l_X) {
                                for (int l_Y = 0; l_Y < 3; ++l_Y) {
                                    this.BlockArray.add(interpPos.add(0, l_Y, l_X));
                                }
                            }
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
                case 9: {
                    interpPos = orignPos.down();
                    switch (PlayerUtil.GetFacing()) {
                        case East: {
                            this.BlockArray.add(interpPos.east());
                            this.BlockArray.add(interpPos.east().up());
                            break Label_6479;
                        }
                        case North: {
                            this.BlockArray.add(interpPos.north());
                            this.BlockArray.add(interpPos.north().up());
                            break Label_6479;
                        }
                        case South: {
                            this.BlockArray.add(interpPos.south());
                            this.BlockArray.add(interpPos.south().up());
                            break Label_6479;
                        }
                        case West: {
                            this.BlockArray.add(interpPos.west());
                            this.BlockArray.add(interpPos.west().up());
                            break Label_6479;
                        }
                        default: {
                            break Label_6479;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    private Pair<Integer, Block> findStackHotbar() {
        if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) {
            return new Pair<Integer, Block>(this.mc.player.inventory.currentItem, ((ItemBlock)this.mc.player.getHeldItemMainhand().getItem()).getBlock());
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                return new Pair<Integer, Block>(i, block.getBlock());
            }
        }
        return null;
    }
    
    private boolean VerifyPortalFrame(final ArrayList<BlockPos> p_Blocks) {
        for (final BlockPos l_Pos : p_Blocks) {
            final IBlockState l_State = this.mc.world.getBlockState(l_Pos);
            if (l_State == null || !(l_State.getBlock() instanceof BlockObsidian)) {
                return false;
            }
        }
        return true;
    }
    
    public enum Modes
    {
        Highway, 
        Swastika, 
        HighwayTunnel, 
        Portal, 
        Flat, 
        Tower, 
        Cover, 
        Wall, 
        HighwayWall, 
        Stair;
    }
    
    public enum BuildingModes
    {
        Dynamic, 
        Static;
    }
}
