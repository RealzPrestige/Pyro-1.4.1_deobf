//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.storage.MapData;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.world.World;
import net.minecraft.item.ItemMap;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderTooltip;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.util.ResourceLocation;
import dev.nuker.pyro.deobfuscated.module.Module;

public class MapTooltip extends Module
{
    private static final ResourceLocation RES_MAP_BACKGROUND;
    @EventHandler
    private Listener<EventRenderTooltip> OnRenderTooltip;
    
    public MapTooltip() {
        super("MapTooltip", new String[] { "MT" }, "Displays a map preview", "NONE", -1, ModuleType.RENDER);
        MapData mapData;
        int xl;
        int yl;
        Tessellator tessellator;
        BufferBuilder bufferbuilder;
        this.OnRenderTooltip = new Listener<EventRenderTooltip>(event -> {
            if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() instanceof ItemMap) {
                mapData = ((ItemMap)event.getItemStack().getItem()).getMapData(event.getItemStack(), (World)this.mc.world);
                if (mapData != null) {
                    event.cancel();
                    GlStateManager.pushMatrix();
                    GlStateManager.color(1.0f, 1.0f, 1.0f);
                    xl = event.getX() + 6;
                    yl = event.getY() + 6;
                    tessellator = Tessellator.getInstance();
                    bufferbuilder = tessellator.getBuffer();
                    GlStateManager.translate((double)xl, (double)yl, 0.0);
                    GlStateManager.scale(1.0f, 1.0f, 0.0f);
                    this.mc.getTextureManager().bindTexture(MapTooltip.RES_MAP_BACKGROUND);
                    RenderHelper.disableStandardItemLighting();
                    GL11.glDepthRange(0.0, 0.01);
                    bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
                    bufferbuilder.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
                    bufferbuilder.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
                    bufferbuilder.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
                    bufferbuilder.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
                    tessellator.draw();
                    GL11.glDepthRange(0.0, 1.0);
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.disableDepth();
                    GL11.glDepthRange(0.0, 0.01);
                    this.mc.entityRenderer.getMapItemRenderer().renderMap(mapData, false);
                    GL11.glDepthRange(0.0, 1.0);
                    GlStateManager.enableDepth();
                    GlStateManager.popMatrix();
                }
            }
        });
    }
    
    static {
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    }
}
