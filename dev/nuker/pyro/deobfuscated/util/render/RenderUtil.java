//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.render;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.GLAllocation;
import org.lwjgl.opengl.EXTFramebufferObject;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.gui.FontRenderer;
import java.awt.Color;
import org.lwjgl.BufferUtils;
import net.minecraft.util.math.MathHelper;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.client.renderer.culling.ICamera;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class RenderUtil
{
    private static final IntBuffer VIEWPORT;
    private static final FloatBuffer MODELVIEW;
    private static final FloatBuffer PROJECTION;
    public static ICamera camera;
    
    public static void init() {
    }
    
    public static boolean ShouldUseCustomFont() {
        return PyroStatic.HUD != null && PyroStatic.HUD.CustomFont.getValue();
    }
    
    public static void updateModelViewProjectionMatrix() {
        GL11.glGetFloat(2982, RenderUtil.MODELVIEW);
        GL11.glGetFloat(2983, RenderUtil.PROJECTION);
        GL11.glGetInteger(2978, RenderUtil.VIEWPORT);
        final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        GLUProjection.getInstance().updateMatrices(RenderUtil.VIEWPORT, RenderUtil.MODELVIEW, RenderUtil.PROJECTION, res.getScaledWidth() / (float)Minecraft.getMinecraft().displayWidth, res.getScaledHeight() / (float)Minecraft.getMinecraft().displayHeight);
    }
    
    public static void DrawPolygon(final double x, final double y, final int radius, final int sides, final int color) {
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        bufferbuilder.pos(x, y, 0.0).endVertex();
        final double TWICE_PI = 6.283185307179586;
        for (int i = 0; i <= sides; ++i) {
            final double angle = 6.283185307179586 * i / sides + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0.0).endVertex();
        }
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawRect(final float x, final float y, final float w, final float h, final int color, final float alpha) {
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawGradientRect(final float left, final float top, final float right, final float bottom, final int startColor, final int endColor) {
        final float f = (startColor >> 24 & 0xFF) / 255.0f;
        final float f2 = (startColor >> 16 & 0xFF) / 255.0f;
        final float f3 = (startColor >> 8 & 0xFF) / 255.0f;
        final float f4 = (startColor & 0xFF) / 255.0f;
        final float f5 = (endColor >> 24 & 0xFF) / 255.0f;
        final float f6 = (endColor >> 16 & 0xFF) / 255.0f;
        final float f7 = (endColor >> 8 & 0xFF) / 255.0f;
        final float f8 = (endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(f2, f3, f4, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(f6, f7, f8, f5).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public static void drawTriangle(final float x, final float y, final float size, final float theta, final int color) {
        GL11.glTranslated((double)x, (double)y, 0.0);
        GL11.glRotatef(180.0f + theta, 0.0f, 0.0f, 1.0f);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.0f);
        GL11.glBegin(6);
        GL11.glVertex2d(0.0, (double)(1.0f * size));
        GL11.glVertex2d((double)(1.0f * size / 2.0f), (double)(-(1.0f * size)));
        GL11.glVertex2d((double)(-(1.0f * size / 2.0f)), (double)(-(1.0f * size)));
        GL11.glEnd();
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glRotatef(-180.0f - theta, 0.0f, 0.0f, 1.0f);
        GL11.glTranslated((double)(-x), (double)(-y), 0.0);
    }
    
    public static void drawOutlineRect(final float x, final float y, final float w, final float h, final float thickness, final int c) {
        drawRect(x, y, x - thickness, h, c);
        drawRect(w + thickness, y, w, h, c);
        drawRect(x, y, w, y - thickness, c);
        drawRect(x, h + thickness, w, h, c);
    }
    
    public static void drawLine(final float x, final float y, final float x1, final float y1, final float thickness, final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glLineWidth(thickness);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static void drawLine3D(final float x, final float y, final float z, final float x1, final float y1, final float z1, final float thickness, final int hex) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        final float alpha = (hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glLineWidth(thickness);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GlStateManager.disableDepth();
        GL11.glEnable(34383);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, (double)z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, (double)z1).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.enableDepth();
        GL11.glDisable(34383);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final float red, final float green, final float blue, final float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glLineWidth(width);
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        tessellator.draw();
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        drawBoundingBox(bb, width, red, green, blue, alpha);
    }
    
    public static void drawPlane(final double x, final double y, final double z, final AxisAlignedBB bb, final float width, final int color) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        drawPlane(bb, width, color);
        GL11.glPopMatrix();
    }
    
    public static void drawPlane(final AxisAlignedBB axisalignedbb, final float width, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.glLineWidth(width);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        drawPlane(axisalignedbb, color);
        GL11.glDisable(2848);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void drawPlane(final AxisAlignedBB boundingBox, final int color) {
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final double minX = boundingBox.minX;
        final double minY = boundingBox.minY;
        final double minZ = boundingBox.minZ;
        final double maxX = boundingBox.maxX;
        final double maxY = boundingBox.maxY;
        final double maxZ = boundingBox.maxZ;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(minX, minY, maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(maxZ, minY, minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }
    
    public static void drawFilledBox(final AxisAlignedBB bb, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        final float alpha = (color >> 24 & 0xFF) / 255.0f;
        final float red = (color >> 16 & 0xFF) / 255.0f;
        final float green = (color >> 8 & 0xFF) / 255.0f;
        final float blue = (color & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
    
    public static void glScissor(final float x, final float y, final float x1, final float y1, final ScaledResolution sr) {
        GL11.glScissor((int)(x * sr.getScaleFactor()), (int)(Minecraft.getMinecraft().displayHeight - y1 * sr.getScaleFactor()), (int)((x1 - x) * sr.getScaleFactor()), (int)((y1 - y) * sr.getScaleFactor()));
    }
    
    public static void glBillboard(final float x, final float y, final float z) {
        final float scale = 0.02666667f;
        GlStateManager.translate(x - Minecraft.getMinecraft().getRenderManager().renderPosX, y - Minecraft.getMinecraft().getRenderManager().renderPosY, z - Minecraft.getMinecraft().getRenderManager().renderPosZ);
        GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch, (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-scale, -scale, scale);
    }
    
    public static void glBillboardDistanceScaled(final float x, final float y, final float z, final EntityPlayer player, final float scale) {
        glBillboard(x, y, z);
        final int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
    }
    
    public static void drawTexture(final float x, final float y, final float textureX, final float textureY, final float width, final float height) {
        final float f = 0.00390625f;
        final float f2 = 0.00390625f;
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)(textureX * f), (double)((textureY + height) * f2)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((textureX + width) * f), (double)((textureY + height) * f2)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)((textureX + width) * f), (double)(textureY * f2)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).tex((double)(textureX * f), (double)(textureY * f2)).endVertex();
        tessellator.draw();
    }
    
    public static void drawTexture(final float x, final float y, final float width, final float height, final float u, final float v, final float t, final float s) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(4, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)t, (double)v).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).tex((double)u, (double)v).endVertex();
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)u, (double)s).endVertex();
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)u, (double)s).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)t, (double)s).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)t, (double)v).endVertex();
        tessellator.draw();
    }
    
    public static final void DrawNodusBetterRect(final double x, final double y, final double x1, final double y1, final int color2, final int color) {
        GL11.glEnable(3042);
        GL11.glEnable(2848);
        drawRect((float)(int)x, (float)(int)y, (float)(int)x1, (float)(int)y1, color);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        drawRect((float)((int)x * 2 - 1), (float)((int)y * 2), (float)((int)x * 2), (float)((int)y1 * 2 - 1), color2);
        drawRect((float)((int)x * 2), (float)((int)y * 2 - 1), (float)((int)x1 * 2), (float)((int)y * 2), color2);
        drawRect((float)((int)x1 * 2), (float)((int)y * 2), (float)((int)x1 * 2 + 1), (float)((int)y1 * 2 - 1), color2);
        drawRect((float)((int)x * 2), (float)((int)y1 * 2 - 1), (float)((int)x1 * 2), (float)((int)y1 * 2), color2);
        GL11.glDisable(3042);
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    public static final void DrawNodusRect(float par0, float par1, float par2, float par3, final int par4) {
        if (par0 < par2) {
            final float var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            final float var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        final float var6 = (par4 >> 24 & 0xFF) / 255.0f;
        final float var7 = (par4 >> 16 & 0xFF) / 255.0f;
        final float var8 = (par4 >> 8 & 0xFF) / 255.0f;
        final float var9 = (par4 & 0xFF) / 255.0f;
        final Tessellator tessellator = Tessellator.getInstance();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(var7, var8, var9, var6);
        final BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)par0, (double)par3, 0.0).color(var7, var8, var9, var6).endVertex();
        bufferbuilder.pos((double)par2, (double)par3, 0.0).color(var7, var8, var9, var6).endVertex();
        bufferbuilder.pos((double)par2, (double)par1, 0.0).color(var7, var8, var9, var6).endVertex();
        bufferbuilder.pos((double)par0, (double)par1, 0.0).color(var7, var8, var9, var6).endVertex();
        tessellator.draw();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
    
    public static float drawStringWithShadow(final String p_Name, final float p_X, final float p_Y, final int p_Color) {
        if (!ShouldUseCustomFont()) {
            return (float)Wrapper.GetMC().fontRenderer.drawStringWithShadow(p_Name, p_X, p_Y, p_Color);
        }
        return (float) Pyro.GetFontManager().getGameFont().drawStringWithShadow(p_Name, p_X, p_Y, p_Color);
    }
    
    public static float getStringWidth(final String p_Name) {
        if (!ShouldUseCustomFont()) {
            return (float)Wrapper.GetMC().fontRenderer.getStringWidth(p_Name);
        }
        return Pyro.GetFontManager().GetStringWidth(p_Name);
    }
    
    public static float getStringHeight(final String name) {
        if (!ShouldUseCustomFont()) {
            return (float)Wrapper.GetMC().fontRenderer.FONT_HEIGHT;
        }
        return Pyro.GetFontManager().GetStringHeight(name);
    }
    
    public static void drawSplitString(final String p_Name, final int p_X, final int p_Y, final int p_K, final int p_Color) {
        Minecraft.getMinecraft().fontRenderer.drawSplitString(p_Name, p_X, p_Y, p_K, p_Color);
    }
    
    public static void drawBorderedRect(final int x, final int y, final int x1, final int y1, final int color, final float lineWidth, final int color1) {
        drawRect((float)x, (float)y, (float)x1, (float)y1, color);
        setupOverlayRendering();
        disableDefaults();
        GL11.glColor4d(getRedFromHex(color1), getGreenFromHex(color1), getBlueFromHex(color1), getAlphaFromHex(color1));
        GL11.glLineWidth(lineWidth);
        GL11.glBegin(1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        enableDefaults();
    }
    
    public static void drawRect(final int x, final int y, final int x1, final int y1, final int color, final int p_CustomAlpha) {
        setupOverlayRendering();
        disableDefaults();
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), (p_CustomAlpha > 0) ? ((double)p_CustomAlpha) : getAlphaFromHex(color));
        GL11.glBegin(7);
        GL11.glVertex2i(x1, y);
        GL11.glVertex2i(x, y);
        GL11.glVertex2i(x, y1);
        GL11.glVertex2i(x1, y1);
        GL11.glEnd();
        enableDefaults();
    }
    
    public static void drawRoundedRect(final int x, final int y, final int x1, final int y1, final int radius, final int color, final int p_CustomAlpha) {
        disableDefaults();
        final float newX = (float)Math.abs(x + radius);
        final float newY = (float)Math.abs(y + radius);
        final float newX2 = (float)Math.abs(x1 - radius);
        final float newY2 = (float)Math.abs(y1 - radius);
        drawRect(newX, newY, newX2, newY2, color);
        drawRect((float)x, newY, newX, newY2, color);
        drawRect(newX2, newY, (float)x1, newY2, color);
        drawRect(newX, (float)y, newX2, newY, color);
        drawRect(newX, newY2, newX2, (float)y1, color);
        drawQuarterCircle((int)newX, (int)newY, radius, 0, color, p_CustomAlpha);
        drawQuarterCircle((int)newX2, (int)newY, radius, 1, color, p_CustomAlpha);
        drawQuarterCircle((int)newX, (int)newY2, radius, 2, color, p_CustomAlpha);
        drawQuarterCircle((int)newX2, (int)newY2, radius, 3, color, p_CustomAlpha);
        enableDefaults();
    }
    
    public static void drawLine2D(final int x, final int y, final int x1, final int y1, final int color, final float lineWidth) {
        setupOverlayRendering();
        disableDefaults();
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), getAlphaFromHex(color));
        GL11.glBegin(1);
        GL11.glVertex2i(x, y);
        GL11.glVertex2i(x1, y1);
        GL11.glEnd();
        GL11.glDisable(2848);
        enableDefaults();
    }
    
    public static void drawLine(final double x, final double y, final double x1, final double y1, final float width) {
        GL11.glDisable(3553);
        GL11.glLineWidth(width);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(3553);
    }
    
    public static void drawBorderedCircle(final int x, final int y, final int radius, final int color, final float lineWidth, final int color1) {
        drawCircle((float)x, (float)y, (float)radius, color);
        drawUnfilledCircle(x, y, radius, lineWidth, color1);
    }
    
    public static void drawUnfilledCircle(final int x, final int y, final int radius, final float lineWidth, final int color) {
        setupOverlayRendering();
        disableDefaults();
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), getAlphaFromHex(color));
        GL11.glLineWidth(lineWidth);
        GL11.glEnable(2848);
        GL11.glBegin(2);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
        }
        GL11.glEnd();
        GL11.glDisable(2848);
        enableDefaults();
    }
    
    public static void drawCircle(final float x, final float y, final float radius, final int color) {
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), getAlphaFromHex(color));
        GL11.glBegin(9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)(x + MathHelper.sin(i * 3.141526f / 180.0f) * radius), (double)(y + MathHelper.cos(i * 3.141526f / 180.0f) * radius));
        }
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
        GL11.glEnd();
        GlStateManager.shadeModel(7424);
        GL11.glDisable(2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static void drawQuarterCircle(final int x, final int y, final int radius, final int mode, final int color, final int p_CustomAlpha) {
        disableDefaults();
        GL11.glColor4d(getRedFromHex(color), getGreenFromHex(color), getBlueFromHex(color), (p_CustomAlpha > 0) ? ((double)p_CustomAlpha) : getAlphaFromHex(color));
        GL11.glBegin(9);
        GL11.glVertex2d((double)x, (double)y);
        if (mode == 0) {
            for (int i = 0; i <= 90; ++i) {
                GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * (radius * -1), y + Math.cos(i * 3.141526 / 180.0) * (radius * -1));
            }
        }
        else if (mode == 1) {
            for (int i = 90; i <= 180; ++i) {
                GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
            }
        }
        else if (mode == 2) {
            for (int i = 90; i <= 180; ++i) {
                GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * (radius * -1), y + Math.cos(i * 3.141526 / 180.0) * (radius * -1));
            }
        }
        else if (mode == 3) {
            for (int i = 0; i <= 90; ++i) {
                GL11.glVertex2d(x + Math.sin(i * 3.141526 / 180.0) * radius, y + Math.cos(i * 3.141526 / 180.0) * radius);
            }
        }
        GL11.glEnd();
        enableDefaults();
    }
    
    public static double getAlphaFromHex(final int color) {
        return (color >> 24 & 0xFF) / 255.0f;
    }
    
    public static double getRedFromHex(final int color) {
        return (color >> 16 & 0xFF) / 255.0f;
    }
    
    public static double getGreenFromHex(final int color) {
        return (color >> 8 & 0xFF) / 255.0f;
    }
    
    public static double getBlueFromHex(final int color) {
        return (color & 0xFF) / 255.0f;
    }
    
    public static int getScreenWidth() {
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(2978, viewport);
        return Math.round((float)viewport.get(2));
    }
    
    public static int getScreenHeight() {
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        GL11.glGetInteger(2978, viewport);
        return Math.round((float)viewport.get(3));
    }
    
    public static void setupGradient() {
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glShadeModel(7425);
    }
    
    public static void unsetupGradient() {
        GL11.glShadeModel(7424);
        GL11.glDisable(3042);
        GL11.glEnable(3008);
        GL11.glEnable(3553);
    }
    
    public static void setupOverlayRendering() {
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, (double)getScreenWidth(), (double)getScreenHeight(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    public static void disableDefaults() {
        GL11.glEnable(3042);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
    }
    
    public static void enableDefaults() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
    }
    
    public static void disableLighting() {
        GL11.glDisable(2896);
    }
    
    public static String trimStringToWidth(final String substring, final int width) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(substring, width);
    }
    
    public static String trimStringToWidth(final String text, final int j, final boolean b) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(text, j, b);
    }
    
    public static void drawColorBox(final AxisAlignedBB axisalignedbb, final float red, final float green, final float blue, final float alpha) {
        final Tessellator ts = Tessellator.getInstance();
        final BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }
    
    public static void drawColorBox(final AxisAlignedBB axisalignedbb, final Color c) {
        final Tessellator ts = Tessellator.getInstance();
        final BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
    }
    
    public static void drawBox(final AxisAlignedBB boundingBox) {
        assert boundingBox != null;
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.maxY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
        GlStateManager.glBegin(7);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.minZ);
        GlStateManager.glVertex3f((float)boundingBox.minX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glVertex3f((float)boundingBox.maxX, (float)boundingBox.minY, (float)boundingBox.maxZ);
        GlStateManager.glEnd();
    }
    
    public static void drawOutlinedBox(final AxisAlignedBB bb) {
        GL11.glBegin(1);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
        GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
        GL11.glEnd();
    }
    
    public static void drawESPOutline(final AxisAlignedBB bb, final float red, final float green, final float blue, final float alpha, final float width) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glLineWidth(width);
        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
        drawOutlinedBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawESP(final AxisAlignedBB bb, final float red, final float green, final float blue, final float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4f(red / 255.0f, green / 255.0f, blue / 255.0f, alpha / 255.0f);
        drawBox(bb);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public static void drawSelectionBoundingBox(final AxisAlignedBB boundingBox) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }
    
    public static FontRenderer GetFontRenderer() {
        if (!ShouldUseCustomFont()) {
            return Wrapper.GetMC().fontRenderer;
        }
        return Wrapper.GetMC().fontRenderer;
    }
    
    public static void renderOne(final float width) {
        checkSetupFBO();
        GL11.glPushAttrib(1048575);
        GL11.glDisable(3008);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(width);
        GL11.glEnable(2848);
        GL11.glEnable(2960);
        GL11.glClear(1024);
        GL11.glClearStencil(15);
        GL11.glStencilFunc(512, 1, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderTwo() {
        GL11.glStencilFunc(512, 0, 15);
        GL11.glStencilOp(7681, 7681, 7681);
        GL11.glPolygonMode(1032, 6914);
    }
    
    public static void renderThree() {
        GL11.glStencilFunc(514, 1, 15);
        GL11.glStencilOp(7680, 7680, 7680);
        GL11.glPolygonMode(1032, 6913);
    }
    
    public static void renderFour() {
        setColor(new Color(255, 255, 255));
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(10754);
        GL11.glPolygonOffset(1.0f, -2000000.0f);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
    }
    
    public static void renderFive() {
        GL11.glPolygonOffset(1.0f, 2000000.0f);
        GL11.glDisable(10754);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(2960);
        GL11.glDisable(2848);
        GL11.glHint(3154, 4352);
        GL11.glEnable(3042);
        GL11.glEnable(2896);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        GL11.glPopAttrib();
    }
    
    public static void setColor(final Color c) {
        GL11.glColor4d((double)(c.getRed() / 255.0f), (double)(c.getGreen() / 255.0f), (double)(c.getBlue() / 255.0f), (double)(c.getAlpha() / 255.0f));
    }
    
    public static void checkSetupFBO() {
        final Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
        if (fbo != null && fbo.depthBuffer > -1) {
            setupFBO(fbo);
            fbo.depthBuffer = -1;
        }
    }
    
    public static void setupFBO(final Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
        final int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
    }
    
    static {
        VIEWPORT = GLAllocation.createDirectIntBuffer(16);
        MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
        PROJECTION = GLAllocation.createDirectFloatBuffer(16);
        RenderUtil.camera = (ICamera)new Frustum();
    }
}
