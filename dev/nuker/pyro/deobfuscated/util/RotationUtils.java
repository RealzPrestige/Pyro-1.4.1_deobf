//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.util;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.network.Packet;
import dev.nuker.pyro.deobfuscated.PyroMod;
import net.minecraft.network.play.client.CPacketPlayer;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.Random;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import dev.nuker.pyro.deobfuscated.events.bus.EventListener;

@SideOnly(Side.CLIENT)
public final class RotationUtils extends MinecraftInstance implements EventListener
{
    private Random random;
    private int keepLength;
    public Rotation targetRotation;
    public Rotation serverRotation;
    public boolean keepCurrentRotation;
    private double x;
    private double y;
    private double z;
    @EventHandler
    private Listener<EventClientTick> OnClientTick;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public RotationUtils() {
        this.random = new Random();
        this.serverRotation = new Rotation(0.0f, 0.0f);
        this.keepCurrentRotation = false;
        this.x = this.random.nextDouble();
        this.y = this.random.nextDouble();
        this.z = this.random.nextDouble();
        this.OnClientTick = new Listener<EventClientTick>(event -> {
            if (this.targetRotation != null) {
                --this.keepLength;
                if (this.keepLength <= 0) {
                    this.reset();
                }
            }
            if (this.random.nextGaussian() > 0.8) {
                this.x = Math.random();
            }
            if (this.random.nextGaussian() > 0.8) {
                this.y = Math.random();
            }
            if (this.random.nextGaussian() > 0.8) {
                this.z = Math.random();
            }
            return;
        });
        final Packet<?> packet;
        CPacketPlayer packetPlayer;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                packetPlayer = (CPacketPlayer)packet;
                if (packetPlayer.rotating) {
                    this.serverRotation = new Rotation(packetPlayer.yaw, packetPlayer.pitch);
                }
            }
            return;
        });
        PyroMod.EVENT_BUS.subscribe(this);
    }
    
    public static RotationUtils getThis() {
        return Pyro.GetRotationUtils();
    }
    
    public VecRotation faceBlock(final BlockPos blockPos) {
        if (blockPos == null) {
            return null;
        }
        VecRotation vecRotation = null;
        for (double xSearch = 0.1; xSearch < 0.9; xSearch += 0.1) {
            for (double ySearch = 0.1; ySearch < 0.9; ySearch += 0.1) {
                for (double zSearch = 0.1; zSearch < 0.9; zSearch += 0.1) {
                    final Vec3d eyesPos = new Vec3d(RotationUtils.mc.player.posX, RotationUtils.mc.player.getEntityBoundingBox().minY + RotationUtils.mc.player.getEyeHeight(), RotationUtils.mc.player.posZ);
                    final Vec3d posVec = new Vec3d((Vec3i)blockPos).add(xSearch, ySearch, zSearch);
                    final double dist = eyesPos.distanceTo(posVec);
                    final double diffX = posVec.x - eyesPos.x;
                    final double diffY = posVec.y - eyesPos.y;
                    final double diffZ = posVec.z - eyesPos.z;
                    final double diffXZ = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
                    final Rotation rotation = new Rotation(MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
                    final Vec3d rotationVector = this.getVectorForRotation(rotation);
                    final Vec3d vector = eyesPos.add(rotationVector.x * dist, rotationVector.y * dist, rotationVector.z * dist);
                    final RayTraceResult obj = RotationUtils.mc.world.rayTraceBlocks(eyesPos, vector, false, false, true);
                    if (obj.typeOfHit == RayTraceResult.Type.BLOCK) {
                        final VecRotation currentVec = new VecRotation(posVec, rotation, obj.sideHit);
                        if (vecRotation == null || this.getRotationDifference(currentVec.getRotation()) < this.getRotationDifference(vecRotation.getRotation())) {
                            vecRotation = currentVec;
                        }
                    }
                }
            }
        }
        return vecRotation;
    }
    
    public void faceBow(final Entity target, final boolean silent, final boolean predict, final float predictSize) {
        final EntityPlayerSP player = RotationUtils.mc.player;
        final double posX = target.posX + (predict ? ((target.posX - target.prevPosX) * predictSize) : 0.0) - (player.posX + (predict ? (player.posX - player.prevPosX) : 0.0));
        final double posY = target.getEntityBoundingBox().minY + (predict ? ((target.getEntityBoundingBox().minY - target.prevPosY) * predictSize) : 0.0) + target.getEyeHeight() - 0.15 - (player.getEntityBoundingBox().minY + (predict ? (player.posY - player.prevPosY) : 0.0)) - player.getEyeHeight();
        final double posZ = target.posZ + (predict ? ((target.posZ - target.prevPosZ) * predictSize) : 0.0) - (player.posZ + (predict ? (player.posZ - player.prevPosZ) : 0.0));
        final double posSqrt = Math.sqrt(posX * posX + posZ * posZ);
        float velocity = player.getItemInUseMaxCount() / 20.0f;
        velocity = (velocity * velocity + velocity * 2.0f) / 3.0f;
        if (velocity > 1.0f) {
            velocity = 1.0f;
        }
        final Rotation rotation = new Rotation((float)(Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f, (float)(-Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(velocity * velocity * velocity * velocity - 0.006000000052154064 * (0.006000000052154064 * (posSqrt * posSqrt) + 2.0 * posY * (velocity * velocity)))) / (0.006000000052154064 * posSqrt)))));
        if (silent) {
            this.setTargetRotation(rotation);
        }
        else {
            this.limitAngleChange(new Rotation(player.rotationYaw, player.rotationPitch), rotation, (float)(10 + new Random().nextInt(6))).toPlayer(RotationUtils.mc.player);
        }
    }
    
    public Rotation toRotation(final Vec3d vec, final boolean predict) {
        final Vec3d eyesPos = new Vec3d(RotationUtils.mc.player.posX, RotationUtils.mc.player.getEntityBoundingBox().minY + RotationUtils.mc.player.getEyeHeight(), RotationUtils.mc.player.posZ);
        if (predict) {
            eyesPos.add(RotationUtils.mc.player.motionX, RotationUtils.mc.player.motionY, RotationUtils.mc.player.motionZ);
        }
        final double diffX = vec.x - eyesPos.x;
        final double diffY = vec.y - eyesPos.y;
        final double diffZ = vec.z - eyesPos.z;
        return new Rotation(MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f), MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(diffY, Math.sqrt(diffX * diffX + diffZ * diffZ))))));
    }
    
    public Vec3d getCenter(final AxisAlignedBB bb) {
        return new Vec3d(bb.minX + (bb.maxX - bb.minX) * 0.5, bb.minY + (bb.maxY - bb.minY) * 0.5, bb.minZ + (bb.maxZ - bb.minZ) * 0.5);
    }
    
    public VecRotation searchCenter(final AxisAlignedBB bb, final boolean outborder, final boolean random, final boolean predict, final boolean throughWalls) {
        if (outborder) {
            final Vec3d Vec3d = new Vec3d(bb.minX + (bb.maxX - bb.minX) * (this.x * 0.3 + 1.0), bb.minY + (bb.maxY - bb.minY) * (this.y * 0.3 + 1.0), bb.minZ + (bb.maxZ - bb.minZ) * (this.z * 0.3 + 1.0));
            return new VecRotation(Vec3d, this.toRotation(Vec3d, predict));
        }
        final Vec3d randomVec = new Vec3d(bb.minX + (bb.maxX - bb.minX) * this.x * 0.8, bb.minY + (bb.maxY - bb.minY) * this.y * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * this.z * 0.8);
        final Rotation randomRotation = this.toRotation(randomVec, predict);
        VecRotation vecRotation = null;
        for (double xSearch = 0.15; xSearch < 0.85; xSearch += 0.1) {
            for (double ySearch = 0.15; ySearch < 1.0; ySearch += 0.1) {
                for (double zSearch = 0.15; zSearch < 0.85; zSearch += 0.1) {
                    final Vec3d Vec3d2 = new Vec3d(bb.minX + (bb.maxX - bb.minX) * xSearch, bb.minY + (bb.maxY - bb.minY) * ySearch, bb.minZ + (bb.maxZ - bb.minZ) * zSearch);
                    final Rotation rotation = this.toRotation(Vec3d2, predict);
                    if (throughWalls || this.isVisible(Vec3d2)) {
                        final VecRotation currentVec = new VecRotation(Vec3d2, rotation);
                        if (vecRotation != null) {
                            if (random) {
                                if (this.getRotationDifference(currentVec.getRotation(), randomRotation) >= this.getRotationDifference(vecRotation.getRotation(), randomRotation)) {
                                    continue;
                                }
                            }
                            else if (this.getRotationDifference(currentVec.getRotation()) >= this.getRotationDifference(vecRotation.getRotation())) {
                                continue;
                            }
                        }
                        vecRotation = currentVec;
                    }
                }
            }
        }
        return vecRotation;
    }
    
    public double getRotationDifference(final Entity entity) {
        final Rotation rotation = this.toRotation(this.getCenter(entity.getEntityBoundingBox()), true);
        return this.getRotationDifference(rotation, new Rotation(RotationUtils.mc.player.rotationYaw, RotationUtils.mc.player.rotationPitch));
    }
    
    public double getRotationDifference(final Rotation rotation) {
        return (this.serverRotation == null) ? 0.0 : this.getRotationDifference(rotation, this.serverRotation);
    }
    
    public double getRotationDifference(final Rotation a, final Rotation b) {
        return Math.hypot(this.getAngleDifference(a.getYaw(), b.getYaw()), a.getPitch() - b.getPitch());
    }
    
    public Rotation limitAngleChange(final Rotation currentRotation, final Rotation targetRotation, final float turnSpeed) {
        final float yawDifference = this.getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
        final float pitchDifference = this.getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
        return new Rotation(currentRotation.getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
    }
    
    private float getAngleDifference(final float a, final float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }
    
    public Vec3d getVectorForRotation(final Rotation rotation) {
        final float yawCos = MathHelper.cos(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float yawSin = MathHelper.sin(-rotation.getYaw() * 0.017453292f - 3.1415927f);
        final float pitchCos = -MathHelper.cos(-rotation.getPitch() * 0.017453292f);
        final float pitchSin = MathHelper.sin(-rotation.getPitch() * 0.017453292f);
        return new Vec3d((double)(yawSin * pitchCos), (double)pitchSin, (double)(yawCos * pitchCos));
    }
    
    public boolean isFaced(final Entity targetEntity, final double blockReachDistance) {
        return RaycastUtils.raycastEntity(blockReachDistance, entity -> entity == targetEntity) != null;
    }
    
    public boolean isVisible(final Vec3d Vec3d) {
        final Vec3d eyesPos = new Vec3d(RotationUtils.mc.player.posX, RotationUtils.mc.player.getEntityBoundingBox().minY + RotationUtils.mc.player.getEyeHeight(), RotationUtils.mc.player.posZ);
        return RotationUtils.mc.world.rayTraceBlocks(eyesPos, Vec3d) == null;
    }
    
    public void setTargetRotation(final Rotation rotation) {
        this.setTargetRotation(rotation, 0);
    }
    
    public void setTargetRotation(final Rotation rotation, final int keepLength) {
        if (Double.isNaN(rotation.getYaw()) || Double.isNaN(rotation.getPitch()) || rotation.getPitch() > 90.0f || rotation.getPitch() < -90.0f) {
            return;
        }
        rotation.fixedSensitivity(RotationUtils.mc.gameSettings.mouseSensitivity);
        this.targetRotation = rotation;
        this.keepLength = keepLength;
    }
    
    public void reset() {
        this.keepLength = 0;
        this.targetRotation = null;
    }
}
