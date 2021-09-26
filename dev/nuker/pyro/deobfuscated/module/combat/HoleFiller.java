//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.client.renderer.RenderGlobal;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import dev.nuker.pyro.deobfuscated.util.Hole;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.block.Block;
import java.util.Iterator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.math.BlockPos;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class HoleFiller extends Module
{
    public final Value<Boolean> TogglesOff;
    public final Value<Integer> MaxHoles;
    public final Value<Float> Radius;
    public final Value<Boolean> Render;
    public final Value<String> Block;
    public final Value<Float> ObsidianRed;
    public final Value<Float> ObsidianGreen;
    public final Value<Float> ObsidianBlue;
    public final Value<Float> ObsidianAlpha;
    public final Value<String> HoleMode;
    private ArrayList<BlockPos> HolesToFill;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdates;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public HoleFiller() {
        super("HoleFiller", new String[] { "HoleFill" }, "Automatically fills up to x holes around you when enabled, if togglesoff is not enabled, it will continue to fill holes.", "NONE", -1, ModuleType.COMBAT);
        this.TogglesOff = new Value<Boolean>("TogglesOff", new String[] { "TogglesOff" }, "Toggles Off after filling all the holes around you", true);
        this.MaxHoles = new Value<Integer>("MaxHoles", new String[] { "MaxHoles" }, "Maximum number of holes to fill", 5, 1, 20, 1);
        this.Radius = new Value<Float>("Radius", new String[] { "Range" }, "Range to search for holes", 5.0f, 1.0f, 10.0f, 1.0f);
        this.Render = new Value<Boolean>("Visualize", new String[] { "Visualize" }, "Visualizes the holes that we are attempting to fill", true);
        this.Block = new Value<String>("Block", new String[] { "Block" }, "Allows you to fill with other blocks", "Obsidian");
        this.ObsidianRed = new Value<Float>("ObsidianRed", new String[] { "oRed" }, "Red for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianGreen = new Value<Float>("ObsidianGreen", new String[] { "oGreen" }, "Green for rendering", 1.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianBlue = new Value<Float>("ObsidianBlue", new String[] { "oBlue" }, "Blue for rendering", 0.0f, 0.0f, 1.0f, 0.1f);
        this.ObsidianAlpha = new Value<Float>("ObsidianAlpha", new String[] { "oAlpha" }, "Alpha for rendering", 0.5f, 0.0f, 1.0f, 0.1f);
        this.HoleMode = new Value<String>("HoleMode", new String[] { "HM" }, "Mode for rendering holes", "Full");
        this.HolesToFill = new ArrayList<BlockPos>();
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        BlockPos l_PosToFill;
        final Iterator<BlockPos> iterator;
        BlockPos l_Pos;
        BlockInteractionHelper.ValidResult l_Result;
        int slot;
        int lastSlot;
        float[] rotations;
        this.onMotionUpdates = new Listener<EventPlayerMotionUpdate>(p_Event -> {
            if (p_Event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (this.HolesToFill.isEmpty()) {
                    if (this.TogglesOff.getValue()) {
                        this.SendMessage("We are finished hole filling. toggling");
                        this.toggle();
                        return;
                    }
                    else {
                        this.FindNewHoles();
                    }
                }
                l_PosToFill = null;
                new ArrayList<BlockPos>(this.HolesToFill).iterator();
                while (iterator.hasNext()) {
                    l_Pos = iterator.next();
                    if (l_Pos == null) {
                        continue;
                    }
                    else {
                        l_Result = BlockInteractionHelper.valid(l_Pos);
                        if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                            this.HolesToFill.remove(l_Pos);
                        }
                        else {
                            l_PosToFill = l_Pos;
                            break;
                        }
                    }
                }
                slot = this.findStackHotbar(this.Block.getValue().equals("Obsidian") ? Blocks.OBSIDIAN : Blocks.WEB);
                if (l_PosToFill != null && slot != -1) {
                    lastSlot = this.mc.player.inventory.currentItem;
                    this.mc.player.inventory.currentItem = slot;
                    this.mc.playerController.updateController();
                    p_Event.cancel();
                    rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)l_PosToFill.getX(), (double)l_PosToFill.getY(), (double)l_PosToFill.getZ()));
                    p_Event.setPitch(rotations[1]);
                    p_Event.setYaw(rotations[0]);
                    if (BlockInteractionHelper.place(l_PosToFill, this.Radius.getValue(), false, false) == BlockInteractionHelper.PlaceResult.Placed) {
                        this.HolesToFill.remove(l_PosToFill);
                    }
                    this.Finish(lastSlot);
                }
                return;
            }
        });
        final Iterator<BlockPos> iterator2;
        BlockPos l_Pos2;
        AxisAlignedBB bb;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            if (this.mc.getRenderManager() == null || !this.Render.getValue()) {
                return;
            }
            else {
                new ArrayList<BlockPos>(this.HolesToFill).iterator();
                while (iterator2.hasNext()) {
                    l_Pos2 = iterator2.next();
                    if (l_Pos2 == null) {
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
                            this.Render(bb, this.ObsidianRed.getValue(), this.ObsidianGreen.getValue(), this.ObsidianBlue.getValue(), this.ObsidianAlpha.getValue());
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
        this.setMetaData(this.getMetaData());
        this.HoleMode.addString("FlatOutline");
        this.HoleMode.addString("Flat");
        this.HoleMode.addString("Outline");
        this.HoleMode.addString("Full");
        this.Block.addString("Obsidian");
        this.Block.addString("Web");
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.HolesToFill.size());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.FindNewHoles();
    }
    
    public boolean IsProcessing() {
        return !this.HolesToFill.isEmpty();
    }
    
    private void Finish(final int p_LastSlot) {
        if (!this.slotEqualsBlock(p_LastSlot, Blocks.OBSIDIAN)) {
            this.mc.player.inventory.currentItem = p_LastSlot;
        }
        this.mc.playerController.updateController();
    }
    
    public boolean hasStack(final Block type) {
        if (this.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getCurrentItem().getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private boolean slotEqualsBlock(final int slot, final Block type) {
        if (this.mc.player.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
            final ItemBlock block = (ItemBlock)this.mc.player.inventory.getStackInSlot(slot).getItem();
            return block.getBlock() == type;
        }
        return false;
    }
    
    private int findStackHotbar(final Block type) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock block = (ItemBlock)stack.getItem();
                if (block.getBlock() == type) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static Hole.HoleTypes isBlockValid(final Minecraft mc, final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (mc.world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (mc.world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (mc.world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        boolean l_Bedrock = true;
        boolean l_Obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = mc.world.getBlockState(touching);
            if (touchingState.getBlock() != Blocks.AIR && touchingState.isFullBlock()) {
                ++validHorizontalBlocks;
                if (touchingState.getBlock() != Blocks.BEDROCK && l_Bedrock) {
                    l_Bedrock = false;
                }
                if (!l_Bedrock && touchingState.getBlock() != Blocks.OBSIDIAN && touchingState.getBlock() != Blocks.BEDROCK) {
                    l_Obsidian = false;
                }
            }
        }
        if (validHorizontalBlocks < 4) {
            return Hole.HoleTypes.None;
        }
        if (l_Bedrock) {
            return Hole.HoleTypes.Bedrock;
        }
        if (l_Obsidian) {
            return Hole.HoleTypes.Obsidian;
        }
        return Hole.HoleTypes.Normal;
    }
    
    public void FindNewHoles() {
        this.HolesToFill.clear();
        final float l_Radius = this.Radius.getValue();
        int l_Holes = 0;
        for (final BlockPos l_Pos : BlockInteractionHelper.getSphere(PlayerUtil.GetLocalPlayerPosFloored(), l_Radius, (int)l_Radius, false, true, 0)) {
            final Hole.HoleTypes l_Type = isBlockValid(this.mc, this.mc.world.getBlockState(l_Pos), l_Pos);
            switch (l_Type) {
                case None: {
                    continue;
                }
                case Normal:
                case Obsidian:
                case Bedrock: {
                    this.HolesToFill.add(l_Pos);
                    if (++l_Holes >= this.MaxHoles.getValue()) {}
                    break;
                }
            }
        }
        this.VerifyHoles();
    }
    
    private void VerifyHoles() {
        for (final BlockPos l_Pos : new ArrayList<BlockPos>(this.HolesToFill)) {
            final BlockInteractionHelper.ValidResult l_Result = BlockInteractionHelper.valid(l_Pos);
            if (l_Result != BlockInteractionHelper.ValidResult.Ok) {
                this.HolesToFill.remove(l_Pos);
            }
        }
    }
    
    private void Render(final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        final String s = this.HoleMode.getValue();
        switch (s) {
            case "Flat": {
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "FlatOutline": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.minY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Full": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                RenderGlobal.renderFilledBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
            case "Outline": {
                RenderGlobal.drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, p_Red, p_Green, p_Blue, p_Alpha);
                break;
            }
        }
    }
}
