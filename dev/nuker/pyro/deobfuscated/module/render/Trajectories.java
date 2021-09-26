//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.BufferBuilder;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import java.util.concurrent.ConcurrentLinkedQueue;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import net.minecraft.util.math.Vec3d;
import java.util.Queue;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class Trajectories extends Module
{
    private final Queue<Vec3d> flightPoint;
    public final Value<Float> width;
    public final Value<Float> red;
    public final Value<Float> green;
    public final Value<Float> blue;
    public final Value<Float> alpha;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public Trajectories() {
        super("Trajectories", new String[] { "Proj" }, "Projects the possible path of an entity that was fired.", "NONE", 6033873, ModuleType.RENDER);
        this.flightPoint = new ConcurrentLinkedQueue<Vec3d>();
        this.width = new Value<Float>("Width", new String[] { "W", "Width" }, "Pixel width of the projectile path.", 1.0f, 0.0f, 5.0f, 0.1f);
        this.red = new Value<Float>("Red", new String[] { "R" }, "Red value for the projectile path.", 255.0f, 0.0f, 255.0f, 1.0f);
        this.green = new Value<Float>("Green", new String[] { "G" }, "Green value for the projectile path.", 255.0f, 0.0f, 255.0f, 1.0f);
        this.blue = new Value<Float>("Blue", new String[] { "B" }, "Blue value for the projectile path.", 255.0f, 0.0f, 255.0f, 1.0f);
        this.alpha = new Value<Float>("Alpha", new String[] { "A" }, "Alpha value for the projectile path.", 255.0f, 0.0f, 255.0f, 1.0f);
        final ThrowableType throwingType;
        FlightPath flightPath;
        boolean bobbing;
        Tessellator tessellator;
        BufferBuilder bufferbuilder;
        Vec3d head;
        Vec3d point;
        RayTraceResult hit;
        AxisAlignedBB bb;
        BlockPos blockpos;
        IBlockState iblockstate;
        Vec3d interp;
        AxisAlignedBB entityBB;
        this.OnRenderEvent = new Listener<RenderEvent>(p_Event -> {
            throwingType = this.getTypeFromCurrentItem(this.mc.player);
            if (throwingType != ThrowableType.NONE) {
                flightPath = new FlightPath(this.mc.player, throwingType);
                while (!flightPath.isCollided()) {
                    flightPath.onUpdate();
                    this.flightPoint.offer(new Vec3d(flightPath.position.x - this.mc.getRenderManager().viewerPosX, flightPath.position.y - this.mc.getRenderManager().viewerPosY, flightPath.position.z - this.mc.getRenderManager().viewerPosZ));
                }
                bobbing = this.mc.gameSettings.viewBobbing;
                this.mc.gameSettings.viewBobbing = false;
                this.mc.entityRenderer.setupCameraTransform(p_Event.getPartialTicks(), 0);
                GlStateManager.pushMatrix();
                GlStateManager.disableTexture2D();
                GlStateManager.enableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.shadeModel(7425);
                GL11.glLineWidth((float)this.width.getValue());
                GL11.glEnable(2848);
                GL11.glHint(3154, 4354);
                GlStateManager.disableDepth();
                GL11.glEnable(34383);
                tessellator = Tessellator.getInstance();
                bufferbuilder = tessellator.getBuffer();
                while (!this.flightPoint.isEmpty()) {
                    bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
                    head = this.flightPoint.poll();
                    bufferbuilder.pos(head.x, head.y, head.z).color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f).endVertex();
                    if (this.flightPoint.peek() != null) {
                        point = this.flightPoint.peek();
                        bufferbuilder.pos(point.x, point.y, point.z).color(this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f).endVertex();
                    }
                    tessellator.draw();
                }
                GlStateManager.shadeModel(7424);
                GL11.glDisable(2848);
                GlStateManager.enableDepth();
                GL11.glDisable(34383);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
                this.mc.gameSettings.viewBobbing = bobbing;
                this.mc.entityRenderer.setupCameraTransform(p_Event.getPartialTicks(), 0);
                if (flightPath.collided) {
                    hit = flightPath.target;
                    bb = null;
                    if (hit != null) {
                        if (hit.typeOfHit == RayTraceResult.Type.BLOCK) {
                            blockpos = hit.getBlockPos();
                            iblockstate = this.mc.world.getBlockState(blockpos);
                            if (iblockstate.getMaterial() != Material.AIR && this.mc.world.getWorldBorder().contains(blockpos)) {
                                interp = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                                bb = iblockstate.getSelectedBoundingBox((World)this.mc.world, blockpos).grow(0.0020000000949949026).offset(-interp.x, -interp.y, -interp.z);
                            }
                        }
                        else if (hit.typeOfHit == RayTraceResult.Type.ENTITY && hit.entityHit != null) {
                            entityBB = hit.entityHit.getEntityBoundingBox();
                            if (entityBB != null) {
                                bb = new AxisAlignedBB(entityBB.minX - this.mc.getRenderManager().viewerPosX, entityBB.minY - this.mc.getRenderManager().viewerPosY, entityBB.minZ - this.mc.getRenderManager().viewerPosZ, entityBB.maxX - this.mc.getRenderManager().viewerPosX, entityBB.maxY - this.mc.getRenderManager().viewerPosY, entityBB.maxZ - this.mc.getRenderManager().viewerPosZ);
                            }
                        }
                        if (bb != null) {
                            RenderUtil.drawBoundingBox(bb, this.width.getValue(), this.red.getValue() / 255.0f, this.green.getValue() / 255.0f, this.blue.getValue() / 255.0f, this.alpha.getValue() / 255.0f);
                        }
                    }
                }
            }
        });
    }
    
    private ThrowableType getTypeFromCurrentItem(final EntityPlayerSP player) {
        if (player.getHeldItemMainhand() == null) {
            return ThrowableType.NONE;
        }
        final ItemStack itemStack = player.getHeldItem(EnumHand.MAIN_HAND);
        switch (Item.getIdFromItem(itemStack.getItem())) {
            case 261: {
                if (player.isHandActive()) {
                    return ThrowableType.ARROW;
                }
                break;
            }
            case 346: {
                return ThrowableType.FISHING_ROD;
            }
            case 438:
            case 441: {
                return ThrowableType.POTION;
            }
            case 384: {
                return ThrowableType.EXPERIENCE;
            }
            case 332:
            case 344:
            case 368: {
                return ThrowableType.NORMAL;
            }
        }
        return ThrowableType.NONE;
    }
    
    enum ThrowableType
    {
        NONE(0.0f, 0.0f), 
        ARROW(1.5f, 0.05f), 
        POTION(0.5f, 0.05f), 
        EXPERIENCE(0.7f, 0.07f), 
        FISHING_ROD(1.5f, 0.04f), 
        NORMAL(1.5f, 0.03f);
        
        private final float velocity;
        private final float gravity;
        
        private ThrowableType(final float velocity, final float gravity) {
            this.velocity = velocity;
            this.gravity = gravity;
        }
        
        public float getVelocity() {
            return this.velocity;
        }
        
        public float getGravity() {
            return this.gravity;
        }
    }
    
    final class FlightPath
    {
        private EntityPlayerSP shooter;
        private Vec3d position;
        private Vec3d motion;
        private float yaw;
        private float pitch;
        private AxisAlignedBB boundingBox;
        private boolean collided;
        private RayTraceResult target;
        private ThrowableType throwableType;
        
        FlightPath(final EntityPlayerSP player, final ThrowableType throwableType) {
            this.shooter = player;
            this.throwableType = throwableType;
            this.setLocationAndAngles(this.shooter.posX, this.shooter.posY + this.shooter.getEyeHeight(), this.shooter.posZ, this.shooter.rotationYaw, this.shooter.rotationPitch);
            final Vec3d startingOffset = new Vec3d((double)(MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * 0.16f), 0.1, (double)(MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * 0.16f));
            this.setPosition(this.position = this.position.subtract(startingOffset));
            this.setThrowableHeading(this.motion = new Vec3d((double)(-MathHelper.sin(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f)), (double)(-MathHelper.sin(this.pitch / 180.0f * 3.1415927f)), (double)(MathHelper.cos(this.yaw / 180.0f * 3.1415927f) * MathHelper.cos(this.pitch / 180.0f * 3.1415927f))), this.getInitialVelocity());
        }
        
        public void onUpdate() {
            Vec3d prediction = this.position.add(this.motion);
            final RayTraceResult blockCollision = this.shooter.getEntityWorld().rayTraceBlocks(this.position, prediction, this.throwableType == ThrowableType.FISHING_ROD, !this.collidesWithNoBoundingBox(), false);
            if (blockCollision != null) {
                prediction = blockCollision.hitVec;
            }
            this.onCollideWithEntity(prediction, blockCollision);
            if (this.target != null) {
                this.collided = true;
                this.setPosition(this.target.hitVec);
                return;
            }
            if (this.position.y <= 0.0) {
                this.collided = true;
                return;
            }
            this.position = this.position.add(this.motion);
            float motionModifier = 0.99f;
            if (this.shooter.getEntityWorld().isMaterialInBB(this.boundingBox, Material.WATER)) {
                motionModifier = ((this.throwableType == ThrowableType.ARROW) ? 0.6f : 0.8f);
            }
            if (this.throwableType == ThrowableType.FISHING_ROD) {
                motionModifier = 0.92f;
            }
            this.motion = MathUtil.mult(this.motion, motionModifier);
            this.motion = this.motion.subtract(0.0, (double)this.getGravityVelocity(), 0.0);
            this.setPosition(this.position);
        }
        
        private boolean collidesWithNoBoundingBox() {
            switch (this.throwableType) {
                case FISHING_ROD:
                case NORMAL: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        
        private void onCollideWithEntity(final Vec3d prediction, final RayTraceResult blockCollision) {
            Entity collidingEntity = null;
            RayTraceResult collidingPosition = null;
            double currentDistance = 0.0;
            final List<Entity> collisionEntities = (List<Entity>)Minecraft.getMinecraft().world.getEntitiesWithinAABBExcludingEntity((Entity)this.shooter, this.boundingBox.expand(this.motion.x, this.motion.y, this.motion.z).grow(1.0, 1.0, 1.0));
            for (final Entity entity : collisionEntities) {
                if (!entity.canBeCollidedWith()) {
                    continue;
                }
                final float collisionSize = entity.getCollisionBorderSize();
                final AxisAlignedBB expandedBox = entity.getEntityBoundingBox().expand((double)collisionSize, (double)collisionSize, (double)collisionSize);
                final RayTraceResult objectPosition = expandedBox.calculateIntercept(this.position, prediction);
                if (objectPosition == null) {
                    continue;
                }
                final double distanceTo = this.position.distanceTo(objectPosition.hitVec);
                if (distanceTo >= currentDistance && currentDistance != 0.0) {
                    continue;
                }
                collidingEntity = entity;
                collidingPosition = objectPosition;
                currentDistance = distanceTo;
            }
            if (collidingEntity != null) {
                this.target = new RayTraceResult(collidingEntity, collidingPosition.hitVec);
            }
            else {
                this.target = blockCollision;
            }
        }
        
        private float getInitialVelocity() {
            switch (this.throwableType) {
                case ARROW: {
                    final int useDuration = this.shooter.getHeldItem(EnumHand.MAIN_HAND).getItem().getMaxItemUseDuration(this.shooter.getHeldItem(EnumHand.MAIN_HAND)) - this.shooter.getItemInUseCount();
                    float velocity = useDuration / 20.0f;
                    velocity = (velocity * velocity + velocity * 2.0f) / 3.0f;
                    if (velocity > 1.0f) {
                        velocity = 1.0f;
                    }
                    return velocity * 2.0f * this.throwableType.getVelocity();
                }
                default: {
                    return this.throwableType.getVelocity();
                }
            }
        }
        
        private float getGravityVelocity() {
            return this.throwableType.getGravity();
        }
        
        private void setLocationAndAngles(final double x, final double y, final double z, final float yaw, final float pitch) {
            this.position = new Vec3d(x, y, z);
            this.yaw = yaw;
            this.pitch = pitch;
        }
        
        private void setPosition(final Vec3d position) {
            this.position = new Vec3d(position.x, position.y, position.z);
            final double entitySize = ((this.throwableType == ThrowableType.ARROW) ? 0.5 : 0.25) / 2.0;
            this.boundingBox = new AxisAlignedBB(position.x - entitySize, position.y - entitySize, position.z - entitySize, position.x + entitySize, position.y + entitySize, position.z + entitySize);
        }
        
        private void setThrowableHeading(final Vec3d motion, final float velocity) {
            this.motion = MathUtil.div(motion, (float)motion.length());
            this.motion = MathUtil.mult(this.motion, velocity);
        }
        
        public boolean isCollided() {
            return this.collided;
        }
        
        public RayTraceResult getCollidingTarget() {
            return this.target;
        }
    }
}
