//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.misc;

import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketSpawnMob;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class GlobalLocation extends Module
{
    public final Value<Boolean> thunder;
    public final Value<Boolean> slimes;
    public final Value<Boolean> Wither;
    public final Value<Boolean> EndPortal;
    public final Value<Boolean> EnderDragon;
    public final Value<Boolean> Donkey;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    
    public GlobalLocation() {
        super("GlobalLocation", new String[] { "WitherLocationModule" }, "Logs in chat where a global sound happened (Warning can send current location if server changed the packet!)", "NONE", 14397476, ModuleType.MISC);
        this.thunder = new Value<Boolean>("Thunder", new String[] { "thund" }, "Logs positions of thunder/lightning sounds.", true);
        this.slimes = new Value<Boolean>("Slimes", new String[] { "slime" }, "Logs positions of slime spawns.", false);
        this.Wither = new Value<Boolean>("Wither", new String[] { "Wither" }, "Logs positions of Wither spawns.", false);
        this.EndPortal = new Value<Boolean>("End Portal", new String[] { "EndPortal" }, "Logs positions of EndPortal spawns.", false);
        this.EnderDragon = new Value<Boolean>("Ender Dragon", new String[] { "ED" }, "Logs positions of EnderDragon spawns.", false);
        this.Donkey = new Value<Boolean>("Donkey", new String[] { "Donkey" }, "logs location of donkey spawns", false);
        SPacketSpawnMob packet;
        Minecraft mc;
        BlockPos pos;
        SPacketSoundEffect packet2;
        float yaw;
        double difX;
        double difZ;
        float yaw2;
        SPacketEffect packet3;
        double theta;
        double theta2;
        double angle;
        double angle2;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() == MinecraftEvent.Stage.Pre) {
                if (event.getPacket() instanceof SPacketSpawnMob) {
                    packet = (SPacketSpawnMob)event.getPacket();
                    if (this.slimes.getValue()) {
                        mc = Minecraft.getMinecraft();
                        if (packet.getEntityType() == 55 && packet.getY() <= 40.0 && !mc.world.getBiome(mc.player.getPosition()).getBiomeName().toLowerCase().contains("swamp")) {
                            pos = new BlockPos(packet.getX(), packet.getY(), packet.getZ());
                            Pyro.SendMessage("Slime Spawned in chunk X:" + mc.world.getChunk(pos).x + " Z:" + mc.world.getChunk(pos).z);
                        }
                    }
                    if (this.Donkey.getValue() && packet.getEntityType() == 31) {
                        Pyro.SendMessage(String.format("Donkey spawned at %s %s %s", packet.getX(), packet.getY(), packet.getZ()));
                    }
                }
                else if (event.getPacket() instanceof SPacketSoundEffect) {
                    packet2 = (SPacketSoundEffect)event.getPacket();
                    if (this.thunder.getValue() && packet2.getCategory() == SoundCategory.WEATHER && packet2.getSound() == SoundEvents.ENTITY_LIGHTNING_THUNDER) {
                        yaw = 0.0f;
                        difX = packet2.getX() - Minecraft.getMinecraft().player.posX;
                        difZ = packet2.getZ() - Minecraft.getMinecraft().player.posZ;
                        yaw2 = (float)(yaw + MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0 - yaw));
                        Pyro.SendMessage("Lightning spawned X:" + Minecraft.getMinecraft().player.posX + " Z:" + Minecraft.getMinecraft().player.posZ + " Angle:" + yaw2);
                    }
                }
                else if (event.getPacket() instanceof SPacketEffect) {
                    packet3 = (SPacketEffect)event.getPacket();
                    if (packet3.getSoundType() == 1023 && this.Wither.getValue()) {
                        theta = Math.atan2(packet3.getSoundPos().getZ() - Minecraft.getMinecraft().player.posZ, packet3.getSoundPos().getX() - Minecraft.getMinecraft().player.posX);
                        theta2 = theta + 1.5707963267948966;
                        angle = Math.toDegrees(theta2);
                        if (angle < 0.0) {
                            angle += 360.0;
                        }
                        angle2 = angle - 180.0;
                        Pyro.SendMessage("Wither spawned in direction " + angle2 + " with y position: " + packet3.getSoundPos().getY());
                    }
                    else if (packet3.getSoundType() == 1038 && this.EndPortal.getValue()) {
                        Pyro.SendMessage("End portal spawned at " + packet3.getSoundPos().toString());
                    }
                    else if (packet3.getSoundType() == 1028 && this.EnderDragon.getValue()) {
                        Pyro.SendMessage("Ender dragon died at " + packet3.getSoundPos().toString());
                    }
                }
            }
        });
    }
}
