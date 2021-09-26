//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util.render;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.AxisAlignedBB;
import dev.nuker.pyro.deobfuscated.util.Hole;
import net.minecraft.init.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import net.minecraft.client.renderer.culling.ICamera;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.item.EntityEnderCrystal;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.module.render.EntityESP;
import org.lwjgl.opengl.GL11;
import java.awt.Color;

public class ESPUtil
{
    public static void ColorToGL(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    public static void RenderCSGOShader(final EntityESP ESP, final Entity entity) {
        if (EntityUtil.IsVehicle(entity) && ESP.Vehicles.getValue()) {
            ColorToGL(new Color(200, 100, 0, 255));
        }
        if (EntityUtil.isPassive(entity) && ESP.Animals.getValue()) {
            final int n = 200;
            final int n2 = 0;
            ColorToGL(new Color(0, 200, 0, 255));
        }
        if ((EntityUtil.isHostileMob(entity) || EntityUtil.isNeutralMob(entity)) && ESP.Monsters.getValue()) {
            ColorToGL(new Color(200, 60, 60, 255));
        }
        if (entity instanceof EntityEnderCrystal && ESP.Others.getValue()) {
            final int n3 = 100;
            final int n4 = 200;
            ColorToGL(new Color(200, 100, 200, 255));
        }
        if (entity instanceof EntityPlayer && ESP.Players.getValue()) {
            final EntityPlayer entityPlayer;
            if ((entityPlayer = (EntityPlayer)entity).isInvisible()) {
                ColorToGL(new Color(133, 200, 178, 255));
            }
            final float distance = Wrapper.GetMC().player.getDistance((Entity)entityPlayer);
            int n5 = 0;
            if (distance >= 60.0f) {
                n5 = 120;
            }
            else {
                final int n6 = (int)distance;
                n5 = n6 + n6;
            }
            ColorToGL(new Color(n5, 100, 50, 128));
        }
    }
    
    public static void RenderCSGO(final ICamera camera, final EntityESP ESP, final RenderEvent p_Event) {
        GL11.glPushMatrix();
        double d3;
        double d4;
        double d5;
        Wrapper.GetMC().world.loadedEntityList.forEach(p_Entity -> {
            if (p_Entity != null && !((Entity)p_Entity).isDead && p_Entity != Wrapper.GetMC().player) {
                d3 = Wrapper.GetMC().player.lastTickPosX + (Wrapper.GetMC().player.posX - Wrapper.GetMC().player.lastTickPosX) * p_Event.getPartialTicks();
                d4 = Wrapper.GetMC().player.lastTickPosY + (Wrapper.GetMC().player.posY - Wrapper.GetMC().player.lastTickPosY) * p_Event.getPartialTicks();
                d5 = Wrapper.GetMC().player.lastTickPosZ + (Wrapper.GetMC().player.posZ - Wrapper.GetMC().player.lastTickPosZ) * p_Event.getPartialTicks();
                camera.setPosition(d3, d4, d5);
                if (camera.isBoundingBoxInFrustum(((Entity)p_Entity).getEntityBoundingBox())) {
                    RenderCSGOShader(ESP, (Entity)p_Entity);
                    Wrapper.GetMC().getRenderManager().renderEntityStatic((Entity)p_Entity, p_Event.getPartialTicks(), false);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
            return;
        });
        GL11.glPopMatrix();
    }
    
    public static void RenderOutline(final ICamera camera, final RenderEvent p_Event) {
        GL11.glPushMatrix();
        double d3;
        double d4;
        double d5;
        Wrapper.GetMC().world.loadedEntityList.forEach(p_Entity -> {
            if (p_Entity != null && !((Entity)p_Entity).isDead && p_Entity != Wrapper.GetMC().player) {
                d3 = Wrapper.GetMC().player.lastTickPosX + (Wrapper.GetMC().player.posX - Wrapper.GetMC().player.lastTickPosX) * p_Event.getPartialTicks();
                d4 = Wrapper.GetMC().player.lastTickPosY + (Wrapper.GetMC().player.posY - Wrapper.GetMC().player.lastTickPosY) * p_Event.getPartialTicks();
                d5 = Wrapper.GetMC().player.lastTickPosZ + (Wrapper.GetMC().player.posZ - Wrapper.GetMC().player.lastTickPosZ) * p_Event.getPartialTicks();
                camera.setPosition(d3, d4, d5);
                if (camera.isBoundingBoxInFrustum(((Entity)p_Entity).getEntityBoundingBox())) {
                    RenderOutline((Entity)p_Entity, 1.0, 1.0, 1.0, 255);
                    Wrapper.GetMC().getRenderManager().renderEntityStatic((Entity)p_Entity, p_Event.getPartialTicks(), false);
                }
            }
            return;
        });
        GL11.glPopMatrix();
    }
    
    public static void RenderShader(final RenderEvent p_Event) {
    }
    
    public static void RenderOutline(final Entity entity, final double n, final double n2, final double n3, final int n4) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)n, (float)n2 + entity.height + 0.5f, (float)n3);
        final float n5 = 1.0f;
        final float n6 = 0.0f;
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        final float n7 = Wrapper.GetMC().getRenderManager().playerViewY;
        final float n8 = 1.0f;
        final float n9 = 0.0f;
        GL11.glRotatef(n7, 0.0f, 1.0f, 0.0f);
        GL11.glScalef(-0.017f, -0.017f, 0.017f);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        int n10 = 0;
        if (entity.isSneaking()) {
            n10 = 4;
        }
        GL11.glDisable(3553);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glEnable(2848);
        GlStateManager.disableLighting();
        RenderBoundingBox(0.0, n10 + 19, 0.0, n10 + 21, -16777216);
        RenderBoundingBox(0.0, n10 + 21, 0.0, n10 + 46, -16777216);
        RenderBoundingBox(0.0, n10 + 21, 0.0, n10 + 25, n4);
        RenderBoundingBox(0.0, n10 + 25, 0.0, n10 + 48, n4);
        RenderBoundingBox(0.0, n10 + 19, 0.0, n10 + 21, -16777216);
        RenderBoundingBox(0.0, n10 + 21, 0.0, n10 + 46, -16777216);
        RenderBoundingBox(0.0, n10 + 21, 0.0, n10 + 25, n4);
        RenderBoundingBox(0.0, n10 + 25, 0.0, n10 + 48, n4);
        RenderBoundingBox(0.0, n10 + 140, 0.0, n10 + 142, -16777216);
        RenderBoundingBox(0.0, n10 + 115, 0.0, n10 + 140, -16777216);
        RenderBoundingBox(0.0, n10 + 136, 0.0, n10 + 140, n4);
        RenderBoundingBox(0.0, n10 + 113, 0.0, n10 + 140, n4);
        RenderBoundingBox(0.0, n10 + 140, 0.0, n10 + 142, -16777216);
        RenderBoundingBox(0.0, n10 + 115, 0.0, n10 + 140, -16777216);
        RenderBoundingBox(0.0, n10 + 136, 0.0, n10 + 140, n4);
        RenderBoundingBox(0.0, n10 + 113, 0.0, n10 + 140, n4);
        GlStateManager.enableLighting();
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        final float n11 = 1.0f;
        final int n12 = 1;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
    
    public static void RenderBoundingBox(final double n, final double n2, final double n3, final double n4, final int n5) {
        final float n6 = (n5 >> 24 & 0xFF) / 255.0f;
        final float n7 = (n5 >> 16 & 0xFF) / 255.0f;
        final float n8 = (n5 >> 8 & 0xFF) / 255.0f;
        final float n9 = (n5 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(n7, n8, n9, n6);
        GL11.glBegin(7);
        GL11.glVertex2d(n, n4);
        GL11.glVertex2d(n3, n4);
        GL11.glVertex2d(n3, n2);
        GL11.glVertex2d(n, n2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }
    
    public static boolean IsVoidHole(final BlockPos blockPos, final IBlockState blockState) {
        if (blockPos.getY() > 4 || blockPos.getY() <= 0) {
            return false;
        }
        BlockPos l_Pos = blockPos;
        for (int l_I = blockPos.getY(); l_I >= 0; --l_I) {
            if (Wrapper.GetMC().world.getBlockState(l_Pos).getBlock() != Blocks.AIR) {
                return false;
            }
            l_Pos = l_Pos.down();
        }
        return true;
    }
    
    public static Hole.HoleTypes isBlockValid(final IBlockState blockState, final BlockPos blockPos) {
        if (blockState.getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().world.getBlockState(blockPos.up()).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().world.getBlockState(blockPos.up(2)).getBlock() != Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        if (Wrapper.GetMC().world.getBlockState(blockPos.down()).getBlock() == Blocks.AIR) {
            return Hole.HoleTypes.None;
        }
        final BlockPos[] touchingBlocks = { blockPos.north(), blockPos.south(), blockPos.east(), blockPos.west() };
        boolean l_Bedrock = true;
        boolean l_Obsidian = true;
        int validHorizontalBlocks = 0;
        for (final BlockPos touching : touchingBlocks) {
            final IBlockState touchingState = Wrapper.GetMC().world.getBlockState(touching);
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
    
    public static void Render(final String p_Mode, final AxisAlignedBB bb, final float p_Red, final float p_Green, final float p_Blue, final float p_Alpha) {
        switch (p_Mode) {
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
    
    public static void RenderOutline(final RenderEvent p_Event, final BlockPos p_Pos, final float red, final float green, final float blue, final float alpha) {
    }
}
