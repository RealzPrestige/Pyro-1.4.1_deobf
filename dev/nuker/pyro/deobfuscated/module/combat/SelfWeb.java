//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import java.util.function.Consumer;
import net.minecraft.block.state.IBlockState;
import java.util.Iterator;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.util.math.Vec3d;
import dev.nuker.pyro.deobfuscated.util.BlockInteractionHelper;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import net.minecraft.init.Blocks;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class SelfWeb extends Module
{
    private Value<String> Mode;
    private Value<Float> validDistance;
    public final Value<Boolean> Double;
    public final Value<Boolean> Toggles;
    public final Value<Boolean> ToggleCA;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> onMotionUpdate;
    
    public SelfWeb() {
        super("SelfWeb", new String[] { "SelfWeb" }, "Places webs on you automatically", "NONE", -1, ModuleType.COMBAT);
        this.Mode = new Value<String>("Mode", new String[] { "Modes" }, "Choose the mode!", "Distance");
        this.validDistance = new Value<Float>("ValidDistance", new String[] { "ValidDistance" }, "The range at which other players will trigger SelfWeb.", 2.0f, 0.0f, 10.0f, 1.0f);
        this.Double = new Value<Boolean>("Double", new String[] { "Double" }, "Places two blocks", true);
        this.Toggles = new Value<Boolean>("Toggles", new String[] { "Toggles" }, "Toggles after a place", true);
        this.ToggleCA = new Value<Boolean>("ToggleCA", new String[] { "ToggleCA" }, "Toggles CA on after webs are placed.", true);
        boolean shouldTrigger;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        int webSlot;
        Vec3d pos;
        BlockPos blockPos;
        IBlockState state;
        boolean valid;
        IBlockState state2;
        BlockPos interpPos;
        int prevSlot;
        float[] rotations;
        final BlockPos pos2;
        final int currentItem;
        Consumer<EntityPlayerSP> post;
        this.onMotionUpdate = new Listener<EventPlayerMotionUpdate>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre || event.isCancelled()) {
                return;
            }
            else {
                if (PlayerUtil.isPlayerInHole(Blocks.OBSIDIAN)) {
                    if (this.Mode.getValue().equals("Distance")) {
                        shouldTrigger = false;
                        this.mc.world.playerEntities.iterator();
                        while (iterator.hasNext()) {
                            player = iterator.next();
                            if (!(player instanceof EntityPlayerSP)) {
                                if (FriendManager.Get().IsFriend((Entity)player)) {
                                    continue;
                                }
                                else if (this.mc.player.getDistance((Entity)player) < this.validDistance.getValue()) {
                                    shouldTrigger = true;
                                    break;
                                }
                                else {
                                    continue;
                                }
                            }
                        }
                        if (!shouldTrigger) {
                            return;
                        }
                    }
                    webSlot = PlayerUtil.GetItemSlotInHotbar(Blocks.WEB);
                    if (webSlot != -1) {
                        pos = MathUtil.interpolateEntity((Entity)this.mc.player, this.mc.getRenderPartialTicks());
                        blockPos = new BlockPos(pos.x, pos.y, pos.z);
                        state = this.mc.world.getBlockState(blockPos);
                        if (state.getBlock() == Blocks.WEB) {
                            valid = true;
                            if (this.Double.getValue()) {
                                blockPos = blockPos.up();
                                state2 = this.mc.world.getBlockState(blockPos);
                                if (state2.getBlock() != Blocks.WEB) {
                                    valid = false;
                                }
                            }
                            if (valid) {
                                if (this.ToggleCA.getValue() && !PyroStatic.AUTOCRYSTAL.isEnabled()) {
                                    PyroStatic.AUTOCRYSTAL.toggle();
                                }
                                if (this.Toggles.getValue()) {
                                    this.SendMessage("Finished");
                                    this.toggle();
                                    return;
                                }
                            }
                        }
                        interpPos = blockPos;
                        if (BlockInteractionHelper.valid(interpPos, true) == BlockInteractionHelper.ValidResult.Ok) {
                            prevSlot = this.mc.player.inventory.currentItem;
                            this.mc.player.inventory.currentItem = webSlot;
                            this.mc.playerController.updateController();
                            event.cancel();
                            rotations = BlockInteractionHelper.getLegitRotations(new Vec3d((double)(interpPos.getX() - 0.5f), (double)(interpPos.getY() - 0.5f), (double)(interpPos.getZ() - 0.5f)));
                            event.setYaw(rotations[0]);
                            event.setPitch(90.0f);
                            post = (p -> {
                                BlockInteractionHelper.place(pos2, 5.0f, false, false);
                                this.mc.player.inventory.currentItem = currentItem;
                                this.mc.playerController.updateController();
                                return;
                            });
                            event.setFunct(post);
                        }
                    }
                }
                else {
                    this.SendMessage(ChatFormatting.RED + "You're not in a hole! Toggling...");
                    this.toggle();
                }
                return;
            }
        });
        this.Mode.addString("Distance");
        this.Mode.addString("Toggle");
    }
}
