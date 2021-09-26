//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.util.render.GLUProjection;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.play.server.SPacketWindowItems;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGameOverlay;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.util.math.BlockPos;
import java.util.HashMap;
import dev.nuker.pyro.deobfuscated.module.Module;

public class ContainerPreview extends Module
{
    private HashMap<BlockPos, ArrayList<ItemStack>> PosItems;
    private int TotalSlots;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventRenderGameOverlay> OnRenderGameOverlay;
    
    public ContainerPreview() {
        super("ContainerPreview", new String[] { "" }, "Shows you what's in a container", "NONE", 11787044, ModuleType.RENDER);
        this.PosItems = new HashMap<BlockPos, ArrayList<ItemStack>>();
        this.TotalSlots = 0;
        RayTraceResult ray;
        IBlockState l_State;
        SPacketWindowItems l_Packet;
        BlockPos blockpos;
        ArrayList<ItemStack> l_List;
        int i;
        ItemStack itemStack;
        SPacketOpenWindow l_Packet2;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketWindowItems) {
                    ray = this.mc.objectMouseOver;
                    if (ray != null) {
                        if (ray.typeOfHit == RayTraceResult.Type.BLOCK) {
                            l_State = this.mc.world.getBlockState(ray.getBlockPos());
                            if (l_State != null) {
                                if (l_State.getBlock() == Blocks.CHEST || l_State.getBlock() instanceof BlockShulkerBox) {
                                    l_Packet = (SPacketWindowItems)event.getPacket();
                                    blockpos = ray.getBlockPos();
                                    if (this.PosItems.containsKey(blockpos)) {
                                        this.PosItems.remove(blockpos);
                                    }
                                    l_List = new ArrayList<ItemStack>();
                                    for (i = 0; i < l_Packet.getItemStacks().size(); ++i) {
                                        itemStack = l_Packet.getItemStacks().get(i);
                                        if (itemStack != null) {
                                            if (i >= this.TotalSlots) {
                                                break;
                                            }
                                            else {
                                                l_List.add(itemStack);
                                            }
                                        }
                                    }
                                    this.PosItems.put(blockpos, l_List);
                                }
                            }
                        }
                    }
                }
                else if (event.getPacket() instanceof SPacketOpenWindow) {
                    l_Packet2 = (SPacketOpenWindow)event.getPacket();
                    this.TotalSlots = l_Packet2.getSlotCount();
                }
                return;
            }
        });
        final RayTraceResult ray2;
        BlockPos l_Pos;
        ArrayList<ItemStack> l_Items;
        float[] bounds;
        int l_I;
        int l_Y;
        int x;
        final Iterator<ItemStack> iterator;
        ItemStack stack;
        this.OnRenderGameOverlay = new Listener<EventRenderGameOverlay>(p_Event -> {
            ray2 = this.mc.objectMouseOver;
            if (ray2 != null) {
                if (ray2.typeOfHit == RayTraceResult.Type.BLOCK) {
                    if (!(!this.PosItems.containsKey(ray2.getBlockPos()))) {
                        l_Pos = ray2.getBlockPos();
                        l_Items = this.PosItems.get(l_Pos);
                        if (l_Items != null) {
                            bounds = this.convertBounds(l_Pos, p_Event.getPartialTicks(), p_Event.getScaledResolution().getScaledWidth(), p_Event.getScaledResolution().getScaledHeight());
                            if (bounds != null) {
                                l_I = 0;
                                l_Y = -20;
                                x = 0;
                                l_Items.iterator();
                                while (iterator.hasNext()) {
                                    stack = iterator.next();
                                    if (stack != null) {
                                        GlStateManager.pushMatrix();
                                        GlStateManager.enableBlend();
                                        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                                        RenderHelper.enableGUIStandardItemLighting();
                                        GlStateManager.translate(bounds[0] + (bounds[2] - bounds[0]) / 2.0f + x, l_Y + bounds[1] + (bounds[3] - bounds[1]) - this.mc.fontRenderer.FONT_HEIGHT - 19.0f, 0.0f);
                                        this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, 0, 0);
                                        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, stack, 0, 0);
                                        RenderHelper.disableStandardItemLighting();
                                        GlStateManager.disableBlend();
                                        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                                        GlStateManager.popMatrix();
                                        x += 16;
                                    }
                                    if (++l_I % 9 == 0) {
                                        x = 0;
                                        l_Y += 15;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }
    
    private float[] convertBounds(final BlockPos e, final float partialTicks, final int width, final int height) {
        float x = -1.0f;
        float y = -1.0f;
        float w = (float)(width + 1);
        float h = (float)(height + 1);
        final Vec3d pos = new Vec3d((double)e.getX(), (double)e.getY(), (double)e.getZ());
        AxisAlignedBB bb = new AxisAlignedBB(e.getX() - this.mc.getRenderManager().viewerPosX, e.getY() - this.mc.getRenderManager().viewerPosY, e.getZ() - this.mc.getRenderManager().viewerPosZ, e.getX() + 1 - this.mc.getRenderManager().viewerPosX, e.getY() + 1 - this.mc.getRenderManager().viewerPosY, e.getZ() + 1 - this.mc.getRenderManager().viewerPosZ);
        bb = bb.expand(0.15000000596046448, 0.10000000149011612, 0.15000000596046448);
        RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
        if (!RenderUtil.camera.isBoundingBoxInFrustum(bb)) {}
        final Vec3d[] array;
        final Vec3d[] corners = array = new Vec3d[] { new Vec3d(bb.minX - bb.maxX + 0.0, 0.0, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, 0.0, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, 0.0, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, 0.0, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, bb.maxY - bb.minY, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, bb.maxY - bb.minY, bb.minZ - bb.maxZ + 0.0), new Vec3d(bb.minX - bb.maxX + 0.0, bb.maxY - bb.minY, bb.maxZ - bb.minZ - 0.0), new Vec3d(bb.maxX - bb.minX - 0.0, bb.maxY - bb.minY, bb.maxZ - bb.minZ - 0.0) };
        for (final Vec3d vec : array) {
            final GLUProjection.Projection projection = GLUProjection.getInstance().project(pos.x + vec.x - this.mc.getRenderManager().viewerPosX, pos.y + vec.y - this.mc.getRenderManager().viewerPosY, pos.z + vec.z - this.mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, false);
            if (projection == null) {
                return null;
            }
            x = Math.max(x, (float)projection.getX());
            y = Math.max(y, (float)projection.getY());
            w = Math.min(w, (float)projection.getX());
            h = Math.min(h, (float)projection.getY());
        }
        if (x != -1.0f && y != -1.0f && w != width + 1 && h != height + 1) {
            return new float[] { x, y, w, h };
        }
        return null;
    }
}
