//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.ForgeModContainer;
import dev.nuker.pyro.deobfuscated.module.ValueListeners;
import net.minecraft.util.BlockRenderLayer;
import java.util.Arrays;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderPutColorMultiplier;
import dev.nuker.pyro.deobfuscated.events.blocks.EventBlockGetRenderLayer;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.block.Block;
import dev.nuker.pyro.deobfuscated.util.BlockListValue;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Wallhack extends Module
{
    public final Value<String> Mode;
    public final Value<Float> Opacity;
    public final Value<Boolean> HandBlock;
    private BlockListValue blocks;
    public final Value<Boolean> SoftReload;
    private Block _block;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventBlockGetRenderLayer> OnGetRenderLayer;
    @EventHandler
    private Listener<EventRenderPutColorMultiplier> OnPutColorMultiplier;
    
    public Wallhack() {
        super("Wallhack", new String[] { "JadeVision", "Xray" }, "Allows visiblity through blocks", "NONE", -1, ModuleType.RENDER);
        this.Mode = new Value<String>("Mode", new String[] { "M" }, "Mode to use", "Normal");
        this.Opacity = new Value<Float>("Opacity", new String[] { "O" }, "Opacity level", 128.0f, 0.0f, 255.0f, 10.0f);
        this.HandBlock = new Value<Boolean>("HandBlock", new String[] { "H" }, "Only display hand block", false);
        this.blocks = new BlockListValue("Blocks", Arrays.asList("minecraft:gold_ore", "minecraft:iron_ore", "minecraft:coal_ore", "minecraft:lapis_ore", "minecraft:diamond_ore", "minecraft:redstone_ore", "minecraft:lit_redstone_ore", "minecraft:tnt", "minecraft:emerald_ore", "minecraft:furnace", "minecraft:lit_furnace", "minecraft:diamond_block", "minecraft:iron_block", "minecraft:gold_block", "minecraft:quartz_ore", "minecraft:beacon", "minecraft:mob_spawner"));
        this.SoftReload = new Value<Boolean>("SoftReload", new String[] { "SR" }, "Reloads softly", false);
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        this.OnGetRenderLayer = new Listener<EventBlockGetRenderLayer>(event -> {
            if (!this.containsBlock(event.getBlock())) {
                event.cancel();
                event.setLayer(BlockRenderLayer.TRANSLUCENT);
            }
            return;
        });
        this.OnPutColorMultiplier = new Listener<EventRenderPutColorMultiplier>(event -> {
            event.cancel();
            event.setOpacity(this.Opacity.getValue() / 255.0f);
            return;
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Normal");
        this.Mode.addString("Circuits");
        this.Opacity.SetListener(new ValueListeners() {
            @Override
            public void OnValueChange(final Value p_Val) {
                Wallhack.this.reloadWorld();
            }
        });
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public String getMetaData() {
        return String.valueOf(this.Mode.getValue());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.mc.renderChunksMany = false;
        this.reloadWorld();
        ForgeModContainer.forgeLightPipelineEnabled = false;
        if (this.HandBlock.getValue()) {
            final ItemStack stack = this.mc.player.getHeldItemMainhand();
            if (stack.getItem() instanceof ItemBlock) {
                final ItemBlock item = (ItemBlock)stack.getItem();
                this._block = item.getBlock();
                this.SendMessage("Only displaying " + stack.getDisplayName());
            }
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.renderChunksMany = false;
        this.reloadWorld();
        ForgeModContainer.forgeLightPipelineEnabled = true;
    }
    
    private void reloadWorld() {
        if (this.mc.world == null || this.mc.renderGlobal == null) {
            return;
        }
        if (this.SoftReload.getValue()) {
            final int x;
            final int y;
            final int z;
            final int distance;
            this.mc.addScheduledTask(() -> {
                x = (int)this.mc.player.posX;
                y = (int)this.mc.player.posY;
                z = (int)this.mc.player.posZ;
                distance = this.mc.gameSettings.renderDistanceChunks * 16;
                this.mc.renderGlobal.markBlockRangeForRenderUpdate(x - distance, y - distance, z - distance, x + distance, y + distance, z + distance);
            });
        }
        else {
            this.mc.renderGlobal.loadRenderers();
        }
    }
    
    private boolean containsBlock(final Block block) {
        if (this.HandBlock.getValue() && this._block != null) {
            return block == this._block;
        }
        if (this.Mode.getValue().equals("Normal") && block != null) {
            return this.blocks.containsBlock(block);
        }
        return block == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE || block == Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE || block == Blocks.STONE_PRESSURE_PLATE || block == Blocks.WOODEN_PRESSURE_PLATE || block == Blocks.STONE_BUTTON || block == Blocks.WOODEN_BUTTON || block == Blocks.LEVER || block == Blocks.COMMAND_BLOCK || block == Blocks.CHAIN_COMMAND_BLOCK || block == Blocks.REPEATING_COMMAND_BLOCK || block == Blocks.DAYLIGHT_DETECTOR || block == Blocks.DAYLIGHT_DETECTOR_INVERTED || block == Blocks.DISPENSER || block == Blocks.DROPPER || block == Blocks.HOPPER || block == Blocks.OBSERVER || block == Blocks.TRAPDOOR || block == Blocks.IRON_TRAPDOOR || block == Blocks.REDSTONE_BLOCK || block == Blocks.REDSTONE_LAMP || block == Blocks.REDSTONE_TORCH || block == Blocks.UNLIT_REDSTONE_TORCH || block == Blocks.REDSTONE_WIRE || block == Blocks.POWERED_REPEATER || block == Blocks.UNPOWERED_REPEATER || block == Blocks.POWERED_COMPARATOR || block == Blocks.UNPOWERED_COMPARATOR || block == Blocks.LIT_REDSTONE_LAMP || block == Blocks.REDSTONE_ORE || block == Blocks.LIT_REDSTONE_ORE || block == Blocks.ACACIA_DOOR || block == Blocks.DARK_OAK_DOOR || block == Blocks.BIRCH_DOOR || block == Blocks.JUNGLE_DOOR || block == Blocks.OAK_DOOR || block == Blocks.SPRUCE_DOOR || block == Blocks.DARK_OAK_DOOR || block == Blocks.IRON_DOOR || block == Blocks.OAK_FENCE || block == Blocks.SPRUCE_FENCE || block == Blocks.BIRCH_FENCE || block == Blocks.JUNGLE_FENCE || block == Blocks.DARK_OAK_FENCE || block == Blocks.ACACIA_FENCE || block == Blocks.OAK_FENCE_GATE || block == Blocks.SPRUCE_FENCE_GATE || block == Blocks.BIRCH_FENCE_GATE || block == Blocks.JUNGLE_FENCE_GATE || block == Blocks.DARK_OAK_FENCE_GATE || block == Blocks.ACACIA_FENCE_GATE || block == Blocks.JUKEBOX || block == Blocks.NOTEBLOCK || block == Blocks.PISTON || block == Blocks.PISTON_EXTENSION || block == Blocks.PISTON_HEAD || block == Blocks.STICKY_PISTON || block == Blocks.TNT || block == Blocks.SLIME_BLOCK || block == Blocks.TRIPWIRE || block == Blocks.TRIPWIRE_HOOK || block == Blocks.RAIL || block == Blocks.ACTIVATOR_RAIL || block == Blocks.DETECTOR_RAIL || block == Blocks.GOLDEN_RAIL;
    }
    
    public void processShouldSideBeRendered(final Block block, final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side, final CallbackInfoReturnable<Boolean> callback) {
        if (this.containsBlock(block)) {
            callback.setReturnValue((Object)true);
        }
    }
    
    public void processGetLightValue(final Block block, final CallbackInfoReturnable<Integer> callback) {
        if (this.containsBlock(block)) {
            callback.setReturnValue((Object)1);
        }
    }
}
