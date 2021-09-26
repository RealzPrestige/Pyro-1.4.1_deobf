//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.mixin.client;

import net.minecraftforge.client.ForgeHooksClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.RenderHelper;
import dev.nuker.pyro.deobfuscated.module.render.ItemPhysics;
import org.lwjgl.opengl.GL11;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.texture.TextureMap;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.Minecraft;
import java.util.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.item.EntityItem;

@Mixin({ RenderEntityItem.class })
public abstract class MixinRenderEntityItem extends MixinRenderer<EntityItem>
{
    @Shadow
    @Final
    private RenderItem itemRenderer;
    @Shadow
    @Final
    private Random random;
    private Minecraft mc;
    private long tick;
    private double rotation;
    private final ResourceLocation RES_ITEM_GLINT;
    
    public MixinRenderEntityItem() {
        this.mc = Minecraft.getMinecraft();
        this.RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
    
    @Shadow
    public abstract int getModelCount(final ItemStack p0);
    
    @Shadow
    public abstract boolean shouldSpreadItems();
    
    @Shadow
    public abstract boolean shouldBob();
    
    @Shadow
    protected abstract ResourceLocation getEntityTexture(final EntityItem p0);
    
    private double formPositiv(final float rotationPitch) {
        return (rotationPitch > 0.0f) ? rotationPitch : ((double)(-rotationPitch));
    }
    
    @Overwrite
    private int transformModelCount(final EntityItem itemIn, final double x, final double y, final double z, final float p_177077_8_, final IBakedModel p_177077_9_) {
        if (PyroStatic.ITEMPHYSICS.isEnabled()) {
            final ItemStack itemstack = itemIn.getItem();
            final Item item2 = itemstack.getItem();
            if (item2 == null) {
                return 0;
            }
            final boolean flag = p_177077_9_.isAmbientOcclusion();
            final int i = this.getModelCount(itemstack);
            final float f2 = 0.0f;
            GlStateManager.translate((float)x, (float)y + 0.0f + 0.1f, (float)z);
            float f3 = 0.0f;
            if (flag || (this.mc.getRenderManager().options != null && this.mc.getRenderManager().options.fancyGraphics)) {
                GlStateManager.rotate(f3, 0.0f, 1.0f, 0.0f);
            }
            if (!flag) {
                f3 = -0.0f * (i - 1) * 0.5f;
                final float f4 = -0.0f * (i - 1) * 0.5f;
                final float f5 = -0.046875f * (i - 1) * 0.5f;
                GlStateManager.translate(f3, f4, f5);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            return i;
        }
        else {
            final ItemStack itemstack = itemIn.getItem();
            final Item item3 = itemstack.getItem();
            if (item3 == null) {
                return 0;
            }
            final boolean flag = p_177077_9_.isGui3d();
            final int i = this.getModelCount(itemstack);
            final float f6 = 0.25f;
            final float f7 = this.shouldBob() ? (MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0f + itemIn.hoverStart) * 0.1f + 0.1f) : 0.0f;
            final float f8 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float)x, (float)y + f7 + 0.25f * f8, (float)z);
            if (flag || this.renderManager.options != null) {
                final float f9 = ((itemIn.getAge() + p_177077_8_) / 20.0f + itemIn.hoverStart) * 57.295776f;
                GlStateManager.rotate(f9, 0.0f, 1.0f, 0.0f);
            }
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            return i;
        }
    }
    
    @Overwrite
    public void doRender(final EntityItem entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
        if (PyroStatic.ITEMPHYSICS.isEnabled()) {
            this.rotation = (System.nanoTime() - this.tick) / 3000000.0 * 1.0;
            if (!this.mc.inGameHasFocus) {
                this.rotation = 0.0;
            }
            final EntityItem entityitem = entity;
            final ItemStack itemstack = entityitem.getItem();
            if (itemstack.getItem() != null) {
                this.random.setSeed(187L);
                boolean flag = false;
                if (TextureMap.LOCATION_BLOCKS_TEXTURE != null) {
                    this.mc.getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                    this.mc.getRenderManager().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
                    flag = true;
                }
                GlStateManager.enableRescaleNormal();
                GlStateManager.alphaFunc(516, 0.1f);
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.pushMatrix();
                final IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
                final int i = this.transformModelCount(entityitem, x, y, z, partialTicks, ibakedmodel);
                final BlockPos blockpos = new BlockPos((Entity)entityitem);
                if (entityitem.rotationPitch > 360.0f) {
                    entityitem.rotationPitch = 0.0f;
                }
                if (entityitem != null && !Double.isNaN(entityitem.posX) && !Double.isNaN(entityitem.posY) && !Double.isNaN(entityitem.posZ) && entityitem.world != null) {
                    if (entityitem.onGround) {
                        if (entityitem.rotationPitch != 0.0f && entityitem.rotationPitch != 90.0f && entityitem.rotationPitch != 180.0f && entityitem.rotationPitch != 270.0f) {
                            final double d0 = this.formPositiv(entityitem.rotationPitch);
                            final double d2 = this.formPositiv(entityitem.rotationPitch - 90.0f);
                            final double d3 = this.formPositiv(entityitem.rotationPitch - 180.0f);
                            final double d4 = this.formPositiv(entityitem.rotationPitch - 270.0f);
                            if (d0 <= d2 && d0 <= d3 && d0 <= d4) {
                                if (entityitem.rotationPitch < 0.0f) {
                                    final EntityItem entityItem = entityitem;
                                    entityItem.rotationPitch += (float)this.rotation;
                                }
                                else {
                                    final EntityItem entityItem2 = entityitem;
                                    entityItem2.rotationPitch -= (float)this.rotation;
                                }
                            }
                            if (d2 < d0 && d2 <= d3 && d2 <= d4) {
                                if (entityitem.rotationPitch - 90.0f < 0.0f) {
                                    final EntityItem entityItem3 = entityitem;
                                    entityItem3.rotationPitch += (float)this.rotation;
                                }
                                else {
                                    final EntityItem entityItem4 = entityitem;
                                    entityItem4.rotationPitch -= (float)this.rotation;
                                }
                            }
                            if (d3 < d2 && d3 < d0 && d3 <= d4) {
                                if (entityitem.rotationPitch - 180.0f < 0.0f) {
                                    final EntityItem entityItem5 = entityitem;
                                    entityItem5.rotationPitch += (float)this.rotation;
                                }
                                else {
                                    final EntityItem entityItem6 = entityitem;
                                    entityItem6.rotationPitch -= (float)this.rotation;
                                }
                            }
                            if (d4 < d2 && d4 < d3 && d4 < d0) {
                                if (entityitem.rotationPitch - 270.0f < 0.0f) {
                                    final EntityItem entityItem7 = entityitem;
                                    entityItem7.rotationPitch += (float)this.rotation;
                                }
                                else {
                                    final EntityItem entityItem8 = entityitem;
                                    entityItem8.rotationPitch -= (float)this.rotation;
                                }
                            }
                        }
                    }
                    else {
                        final BlockPos blockpos2 = new BlockPos((Entity)entityitem);
                        blockpos2.add(0, 1, 0);
                        final Material material = entityitem.world.getBlockState(blockpos2).getMaterial();
                        final Material material2 = entityitem.world.getBlockState(blockpos).getMaterial();
                        final boolean flag2 = entityitem.isInsideOfMaterial(Material.WATER);
                        final boolean flag3 = entityitem.isInWater();
                        if (flag2 | material == Material.WATER | material2 == Material.WATER | flag3) {
                            final EntityItem entityItem9 = entityitem;
                            entityItem9.rotationPitch += (float)(this.rotation / 4.0);
                        }
                        else {
                            final EntityItem entityItem10 = entityitem;
                            entityItem10.rotationPitch += (float)(this.rotation * 2.0);
                        }
                    }
                }
                GL11.glRotatef(entityitem.rotationYaw, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(entityitem.rotationPitch + 90.0f, 1.0f, 0.0f, 0.0f);
                for (int j = 0; j < i; ++j) {
                    if (ibakedmodel.isAmbientOcclusion()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.scale((float)ItemPhysics.Scaling.getValue(), (float)ItemPhysics.Scaling.getValue(), (float)ItemPhysics.Scaling.getValue());
                        this.itemRenderer.renderItem(itemstack, ibakedmodel);
                        GlStateManager.popMatrix();
                    }
                    else {
                        GlStateManager.pushMatrix();
                        if (j > 0 && this.shouldSpreadItems()) {
                            GlStateManager.translate(0.0f, 0.0f, 0.046875f * j);
                        }
                        this.itemRenderer.renderItem(itemstack, ibakedmodel);
                        if (!this.shouldSpreadItems()) {
                            GlStateManager.translate(0.0f, 0.0f, 0.046875f);
                        }
                        GlStateManager.popMatrix();
                    }
                }
                GlStateManager.popMatrix();
                GlStateManager.disableRescaleNormal();
                GlStateManager.disableBlend();
                this.mc.getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                if (flag) {
                    this.mc.getRenderManager().renderEngine.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
                }
            }
            return;
        }
        final ItemStack itemstack2 = entity.getItem();
        final int k = itemstack2.isEmpty() ? 187 : (Item.getIdFromItem(itemstack2.getItem()) + itemstack2.getMetadata());
        this.random.setSeed(k);
        boolean flag = false;
        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.pushMatrix();
        final IBakedModel ibakedmodel = this.itemRenderer.getItemModelWithOverrides(itemstack2, entity.world, (EntityLivingBase)null);
        final int l = this.transformModelCount(entity, x, y, z, partialTicks, ibakedmodel);
        final boolean flag4 = ibakedmodel.isGui3d();
        if (!flag4) {
            final float f3 = -0.0f * (l - 1) * 0.5f;
            final float f4 = -0.0f * (l - 1) * 0.5f;
            final float f5 = -0.09375f * (l - 1) * 0.5f;
            GlStateManager.translate(f3, f4, f5);
        }
        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }
        for (int m = 0; m < l; ++m) {
            if (flag4) {
                GlStateManager.pushMatrix();
                if (m > 0) {
                    final float f6 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float f7 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    final float f8 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f;
                    GlStateManager.translate(this.shouldSpreadItems() ? f6 : 0.0f, this.shouldSpreadItems() ? f7 : 0.0f, f8);
                }
                final IBakedModel transformedModel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
                this.itemRenderer.renderItem(itemstack2, transformedModel);
                GlStateManager.popMatrix();
            }
            else {
                GlStateManager.pushMatrix();
                if (m > 0) {
                    final float f9 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    final float f10 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.15f * 0.5f;
                    GlStateManager.translate(f9, f10, 0.0f);
                }
                final IBakedModel transformedModel = ForgeHooksClient.handleCameraTransforms(ibakedmodel, ItemCameraTransforms.TransformType.GROUND, false);
                this.itemRenderer.renderItem(itemstack2, transformedModel);
                GlStateManager.popMatrix();
                GlStateManager.translate(0.0f, 0.0f, 0.09375f);
            }
        }
        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);
        if (flag) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }
    }
}
