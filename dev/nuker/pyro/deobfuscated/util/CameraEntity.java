//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.entity.MoverType;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.StatisticsManager;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.world.World;
import net.minecraft.client.Minecraft;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.client.entity.EntityPlayerSP;

public class CameraEntity extends EntityPlayerSP
{
    @Nullable
    private static Entity originalRenderViewEntity;
    @Nullable
    private static CameraEntity camera;
    private static boolean cullChunksOriginal;
    private static float forwardRamped;
    private static float strafeRamped;
    private static float verticalRamped;
    private static boolean sprinting;
    
    public CameraEntity(final Minecraft mc, final World world, final NetHandlerPlayClient nethandler, final StatisticsManager stats, final RecipeBook recipeBook) {
        super(mc, world, nethandler, stats, recipeBook);
    }
    
    public boolean isSpectator() {
        return true;
    }
    
    public static void movementTick(final boolean sneak, final boolean jump) {
        final CameraEntity camera = getCamera();
        if (camera != null) {
            final Minecraft mc = Minecraft.getMinecraft();
            camera.updateLastTickPosition();
            float forward = 0.0f;
            float vertical = 0.0f;
            float strafe = 0.0f;
            final GameSettings options = mc.gameSettings;
            if (options.keyBindForward.isKeyDown()) {
                ++forward;
            }
            if (options.keyBindBack.isKeyDown()) {
                --forward;
            }
            if (options.keyBindLeft.isKeyDown()) {
                ++strafe;
            }
            if (options.keyBindRight.isKeyDown()) {
                --strafe;
            }
            if (options.keyBindJump.isKeyDown()) {
                ++vertical;
            }
            if (options.keyBindSneak.isKeyDown()) {
                --vertical;
            }
            if (options.keyBindSprint.isKeyDown()) {
                CameraEntity.sprinting = true;
            }
            else if (forward == 0.0f) {
                CameraEntity.sprinting = false;
            }
            final float rampAmount = 0.15f;
            float speed = strafe * strafe + forward * forward;
            if (forward != 0.0f && strafe != 0.0f) {
                speed = (float)Math.sqrt(speed * 0.6);
            }
            else {
                speed = 1.0f;
            }
            CameraEntity.forwardRamped = getRampedMotion(CameraEntity.forwardRamped, forward, rampAmount) / speed;
            CameraEntity.verticalRamped = getRampedMotion(CameraEntity.verticalRamped, vertical, rampAmount);
            CameraEntity.strafeRamped = getRampedMotion(CameraEntity.strafeRamped, strafe, rampAmount) / speed;
            forward = (CameraEntity.sprinting ? (CameraEntity.forwardRamped * 3.0f) : CameraEntity.forwardRamped);
            camera.handleMotion(forward, CameraEntity.verticalRamped, CameraEntity.strafeRamped);
        }
    }
    
    private static float getRampedMotion(float current, final float input, float rampAmount) {
        if (input != 0.0f) {
            if (input < 0.0f) {
                rampAmount *= -1.0f;
            }
            if (input < 0.0f != current < 0.0f) {
                current = 0.0f;
            }
            current = MathHelper.clamp(current + rampAmount, -1.0f, 1.0f);
        }
        else {
            current *= 0.5f;
        }
        return current;
    }
    
    private static double getMoveSpeed() {
        double base = 0.07;
        if (PyroStatic.FREECAM != null) {
            base = PyroStatic.FREECAM.speed.getValue() / 10.0f;
        }
        return base * 10.0;
    }
    
    private void handleMotion(final float forward, final float up, final float strafe) {
        final double xFactor = Math.sin(this.rotationYaw * 3.141592653589793 / 180.0);
        final double zFactor = Math.cos(this.rotationYaw * 3.141592653589793 / 180.0);
        final double scale = getMoveSpeed();
        this.motionX = (strafe * zFactor - forward * xFactor) * scale;
        this.motionY = up * scale;
        this.motionZ = (forward * zFactor + strafe * xFactor) * scale;
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.chunkCoordX = (int)Math.floor(this.posX) >> 4;
        this.chunkCoordY = (int)Math.floor(this.posY) >> 4;
        this.chunkCoordZ = (int)Math.floor(this.posZ) >> 4;
    }
    
    private void updateLastTickPosition() {
        final double posX = this.posX;
        this.lastTickPosX = posX;
        this.prevPosX = posX;
        final double posY = this.posY;
        this.lastTickPosY = posY;
        this.prevPosY = posY;
        final double posZ = this.posZ;
        this.lastTickPosZ = posZ;
        this.prevPosZ = posZ;
    }
    
    public void setCameraRotations(final float yaw, final float pitch) {
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
        this.setRotationYawHead(this.rotationYaw);
        this.setRenderYawOffset(this.rotationYaw);
    }
    
    public void updateCameraRotations(final float yawChange, final float pitchChange) {
        this.rotationYaw += yawChange * 0.15f;
        this.rotationPitch = MathHelper.clamp(this.rotationPitch - pitchChange * 0.15f, -90.0f, 90.0f);
        this.setCameraRotations(this.rotationYaw, this.rotationPitch);
    }
    
    private static CameraEntity createCameraEntity(final Minecraft mc) {
        final CameraEntity camera = new CameraEntity(mc, (World)mc.world, mc.player.connection, mc.player.getStatFileWriter(), mc.player.getRecipeBook());
        camera.noClip = true;
        final Entity player = (Entity)mc.player;
        if (player != null) {
            camera.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
            camera.prevRotationYaw = camera.rotationYaw;
            camera.prevRotationPitch = camera.rotationPitch;
            camera.setRotationYawHead(camera.rotationYaw);
            camera.setRenderYawOffset(camera.rotationYaw);
        }
        return camera;
    }
    
    @Nullable
    public static CameraEntity getCamera() {
        return CameraEntity.camera;
    }
    
    public static void setCameraState(final boolean enabled) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (enabled) {
            createAndSetCamera(mc);
        }
        else {
            removeCamera(mc);
        }
    }
    
    private static void createAndSetCamera(final Minecraft mc) {
        CameraEntity.camera = createCameraEntity(mc);
        CameraEntity.originalRenderViewEntity = mc.getRenderViewEntity();
        CameraEntity.cullChunksOriginal = mc.renderChunksMany;
        mc.setRenderViewEntity((Entity)CameraEntity.camera);
        mc.renderChunksMany = false;
    }
    
    private static void removeCamera(final Minecraft mc) {
        mc.setRenderViewEntity(CameraEntity.originalRenderViewEntity);
        mc.renderChunksMany = CameraEntity.cullChunksOriginal;
        if (mc.world != null && CameraEntity.camera != null) {
            CameraUtils.markChunksForRebuildOnDeactivation(CameraEntity.camera.chunkCoordX, CameraEntity.camera.chunkCoordZ);
        }
        CameraEntity.originalRenderViewEntity = null;
        CameraEntity.camera = null;
    }
}
