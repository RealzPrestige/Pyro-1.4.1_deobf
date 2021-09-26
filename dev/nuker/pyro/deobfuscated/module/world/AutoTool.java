//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.world;

import net.minecraft.init.Enchantments;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.Packet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import dev.nuker.pyro.deobfuscated.events.network.EventClientPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerDamageBlock;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.util.Timer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class AutoTool extends Module
{
    public final Value<Boolean> silent;
    public final Value<Boolean> GoBack;
    private boolean send;
    public BlockPos position;
    public EnumFacing facing;
    private int _previousSlot;
    private Timer _timer;
    @EventHandler
    private Listener<EventPlayerDamageBlock> DamageBlock;
    @EventHandler
    private Listener<EventPlayerUpdate> PlayerUpdate;
    @EventHandler
    private Listener<EventClientPacket> onClientPacket;
    
    public AutoTool() {
        super("AutoTool", new String[] { "Tool" }, "Automatically switches to the best tool", "NONE", 7396132, ModuleType.WORLD);
        this.silent = new Value<Boolean>("Silent", new String[] { "Sil" }, "Hold any item and spoof your mining tool.", true);
        this.GoBack = new Value<Boolean>("GoBack", new String[] { "ToolBack" }, "Replaces back to your original hotbar item, if you finish mining something", false);
        this._previousSlot = -1;
        this._timer = new Timer();
        int slot;
        PlayerControllerMP playerController;
        int hotbar;
        PlayerControllerMP playerController2;
        int slot2;
        this.DamageBlock = new Listener<EventPlayerDamageBlock>(p_Event -> {
            if (this.silent.getValue()) {
                slot = this.getToolInventory(p_Event.getPos());
                if (slot != -1) {
                    playerController = this.mc.playerController;
                    playerController.curBlockDamageMP += this.blockStrength(p_Event.getPos(), this.mc.player.inventoryContainer.getSlot(slot).getStack());
                }
                else {
                    hotbar = this.getToolHotbar(p_Event.getPos());
                    if (hotbar != -1) {
                        playerController2 = this.mc.playerController;
                        playerController2.curBlockDamageMP += this.blockStrength(p_Event.getPos(), this.mc.player.inventory.getStackInSlot(hotbar));
                    }
                }
            }
            else {
                this._timer.reset();
                slot2 = this.getToolHotbar(p_Event.getPos());
                if (slot2 != -1 && this.mc.player.inventory.currentItem != slot2) {
                    if (this._previousSlot != slot2) {
                        this._previousSlot = this.mc.player.inventory.currentItem;
                    }
                    this.mc.player.inventory.currentItem = slot2;
                    this.mc.playerController.updateController();
                }
            }
            return;
        });
        this.PlayerUpdate = new Listener<EventPlayerUpdate>(event -> {
            this.setMetaData(((boolean)this.silent.getValue()) ? "Silent" : "Normal");
            if (this._previousSlot != -1 && this._timer.passed(250.0) && !this.mc.playerController.isHittingBlock) {
                this._timer.reset();
                if (this.GoBack.getValue()) {
                    this.mc.player.inventory.currentItem = this._previousSlot;
                    this.mc.playerController.updateController();
                }
                this._previousSlot = -1;
            }
            return;
        });
        CPacketPlayerDigging packet;
        int slot3;
        int hotbar2;
        this.onClientPacket = new Listener<EventClientPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre || this.mc.world == null) {
                return;
            }
            else if (this.send) {
                this.send = false;
                return;
            }
            else {
                if (event.getPacket() instanceof CPacketPlayerDigging && this.silent.getValue()) {
                    packet = (CPacketPlayerDigging)event.getPacket();
                    if (packet.getAction() == CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                        this.position = packet.getPosition();
                        this.facing = packet.getFacing();
                        if (this.position != null && this.facing != null) {
                            slot3 = this.getToolInventory(packet.getPosition());
                            if (slot3 != -1) {
                                event.cancel();
                                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, slot3, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                                this.send = true;
                                this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.position, this.facing));
                                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, slot3, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                            }
                            else {
                                hotbar2 = this.getToolHotbar(packet.getPosition());
                                if (hotbar2 != -1) {
                                    event.cancel();
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, hotbar2, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                                    this.send = true;
                                    this.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.position, this.facing));
                                    this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, hotbar2, this.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)this.mc.player);
                                }
                            }
                        }
                    }
                }
                return;
            }
        });
        this.setMetaData(((boolean)this.silent.getValue()) ? "Silent" : "Normal");
    }
    
    private float blockStrength(final BlockPos pos, final ItemStack stack) {
        final float hardness = this.mc.world.getBlockState(pos).getBlockHardness((World)this.mc.world, pos);
        if (hardness < 0.0f) {
            return 0.0f;
        }
        if (!this.canHarvestBlock(this.mc.world.getBlockState(pos).getBlock(), pos, stack)) {
            return this.getDigSpeed(this.mc.world.getBlockState(pos), pos, stack) / hardness / 100.0f;
        }
        return this.getDigSpeed(this.mc.world.getBlockState(pos), pos, stack) / hardness / 30.0f;
    }
    
    private boolean canHarvestBlock(final Block block, final BlockPos pos, final ItemStack stack) {
        IBlockState state = this.mc.world.getBlockState(pos);
        state = state.getBlock().getActualState(state, (IBlockAccess)this.mc.world, pos);
        if (state.getMaterial().isToolNotRequired()) {
            return true;
        }
        final String tool = block.getHarvestTool(state);
        if (stack.isEmpty() || tool == null) {
            return this.mc.player.canHarvestBlock(state);
        }
        final int toolLevel = stack.getItem().getHarvestLevel(stack, tool, (EntityPlayer)this.mc.player, state);
        if (toolLevel < 0) {
            return this.mc.player.canHarvestBlock(state);
        }
        return toolLevel >= block.getHarvestLevel(state);
    }
    
    private float getDestroySpeed(final IBlockState state, final ItemStack stack) {
        float f = 1.0f;
        f *= stack.getDestroySpeed(state);
        return f;
    }
    
    private float getDigSpeed(final IBlockState state, final BlockPos pos, final ItemStack stack) {
        float f = this.getDestroySpeed(state, stack);
        if (f > 1.0f) {
            final int i = EnchantmentHelper.getEfficiencyModifier((EntityLivingBase)this.mc.player);
            if (i > 0 && !stack.isEmpty()) {
                f += i * i + 1;
            }
        }
        if (this.mc.player.isPotionActive(MobEffects.HASTE)) {
            f *= 1.0f + (this.mc.player.getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2f;
        }
        if (this.mc.player.isPotionActive(MobEffects.MINING_FATIGUE)) {
            float f2 = 0.0f;
            switch (this.mc.player.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
                case 0: {
                    f2 = 0.3f;
                    break;
                }
                case 1: {
                    f2 = 0.09f;
                    break;
                }
                case 2: {
                    f2 = 0.0027f;
                    break;
                }
                default: {
                    f2 = 8.1E-4f;
                    break;
                }
            }
            f *= f2;
        }
        if (this.mc.player.isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier((EntityLivingBase)this.mc.player)) {
            f /= 5.0f;
        }
        if (!this.mc.player.onGround) {
            f /= 5.0f;
        }
        f = ForgeEventFactory.getBreakSpeed((EntityPlayer)this.mc.player, state, f, pos);
        return (f < 0.0f) ? 0.0f : f;
    }
    
    private int getToolInventory(final BlockPos pos) {
        int index = -1;
        float speed = 1.0f;
        for (int i = 9; i < 36; ++i) {
            final ItemStack stack = this.mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack != ItemStack.EMPTY) {
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(this.mc.world.getBlockState(pos));
                if (digSpeed + destroySpeed > speed) {
                    speed = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
    
    private int getToolHotbar(final BlockPos pos) {
        int index = -1;
        float speed = 1.0f;
        for (int i = 0; i <= 9; ++i) {
            final ItemStack stack = this.mc.player.inventory.getStackInSlot(i);
            if (stack != null && stack != ItemStack.EMPTY) {
                final float digSpeed = (float)EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack);
                final float destroySpeed = stack.getDestroySpeed(this.mc.world.getBlockState(pos));
                if (digSpeed + destroySpeed > speed) {
                    speed = digSpeed + destroySpeed;
                    index = i;
                }
            }
        }
        return index;
    }
}
