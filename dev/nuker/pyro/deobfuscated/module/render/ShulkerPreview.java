//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.util.NonNullList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.inventory.IInventory;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.InventoryBasic;
import org.lwjgl.input.Mouse;
import net.minecraft.client.renderer.RenderHelper;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketWindowItems;
import dev.nuker.pyro.deobfuscated.events.MinecraftEvent;
import net.minecraft.nbt.NBTTagCompound;
import dev.nuker.pyro.deobfuscated.main.Pyro;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.item.ItemShulkerBox;
import java.util.TimerTask;
import net.minecraft.entity.item.EntityItem;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderTooltip;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import java.util.Timer;
import java.util.List;
import java.util.HashMap;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public final class ShulkerPreview extends Module
{
    public final Value<Boolean> middleClick;
    public final Value<Boolean> EnderChest;
    public final Value<String> Mode;
    private boolean clicked;
    private final ArrayList<ItemStack> EnderChestItems;
    private final HashMap<String, List<ItemStack>> SavedShulkerItems;
    private int EnderChestWindowId;
    private int ShulkerWindowId;
    private Timer timer;
    private String LastWindowTitle;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EntityJoinWorldEvent> OnEntityJoinWorld;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<EventRenderTooltip> OnRenderTooltip;
    
    public ShulkerPreview() {
        super("ShulkerPreview", new String[] { "SPreview", "ShulkerView" }, "Hover over a shulker box to the items inside.", "NONE", 14367780, ModuleType.RENDER);
        this.middleClick = new Value<Boolean>("MiddleClick", new String[] { "MC", "Mid" }, "Allows you to middle click shulkers and view their contents.", true);
        this.EnderChest = new Value<Boolean>("EnderChest", new String[] { "EC", "Ender" }, "Previews your enderchest (Requires you to open your ender chest first", true);
        this.Mode = new Value<String>("Mode", new String[] { "Server Mode" }, "Server mode, like 2b2t has custom preview settings", "Normal");
        this.EnderChestItems = new ArrayList<ItemStack>();
        this.SavedShulkerItems = new HashMap<String, List<ItemStack>>();
        this.EnderChestWindowId = -1;
        this.ShulkerWindowId = -1;
        this.timer = new Timer();
        this.LastWindowTitle = "";
        this.onPlayerUpdate = new Listener<EventPlayerUpdate>(event -> this.setMetaData(this.getMetaData()));
        this.OnEntityJoinWorld = new Listener<EntityJoinWorldEvent>(p_Event -> {
            if (!this.Mode.getValue().equals("DropPacket")) {
                return;
            }
            else if (p_Event.getEntity() == null || !(p_Event.getEntity() instanceof EntityItem)) {
                return;
            }
            else {
                this.timer.schedule(new TimerTask() {
                    final /* synthetic */ EntityJoinWorldEvent val$p_Event;
                    
                    @Override
                    public void run() {
                        final EntityItem l_Item = (EntityItem)this.val$p_Event.getEntity();
                        if (!(l_Item.getItem().getItem() instanceof ItemShulkerBox)) {
                            return;
                        }
                        final ItemStack shulker = l_Item.getItem();
                        final NBTTagCompound shulkerNBT = ShulkerPreview.this.getShulkerNBT(shulker);
                        if (shulkerNBT != null) {
                            final TileEntityShulkerBox fakeShulker = new TileEntityShulkerBox();
                            fakeShulker.loadFromNbt(shulkerNBT);
                            final String customName = shulker.getDisplayName();
                            final ArrayList l_Items = new ArrayList();
                            int l_SlotsNotEmpty = 0;
                            for (int i = 0; i < 27; ++i) {
                                l_Items.add(fakeShulker.getStackInSlot(i));
                                if (fakeShulker.getStackInSlot(i) != ItemStack.EMPTY) {
                                    ++l_SlotsNotEmpty;
                                }
                            }
                            if (ShulkerPreview.this.SavedShulkerItems.containsKey(customName)) {
                                ShulkerPreview.this.SavedShulkerItems.remove(customName);
                            }
                            else {
                                Pyro.SendMessage("New shulker found with name " + customName + " it contains " + l_SlotsNotEmpty + " slots NOT empty");
                            }
                            ShulkerPreview.this.SavedShulkerItems.put(customName, l_Items);
                        }
                    }
                }, 5000L);
                return;
            }
        });
        SPacketWindowItems l_Packet;
        int i;
        ItemStack itemStack;
        ArrayList<ItemStack> l_List;
        int j;
        ItemStack itemStack2;
        SPacketOpenWindow l_Packet2;
        SPacketSetSlot l_Packet3;
        this.onServerPacket = new Listener<EventServerPacket>(event -> {
            if (event.getStage() != MinecraftEvent.Stage.Pre) {
                return;
            }
            else {
                if (event.getPacket() instanceof SPacketWindowItems) {
                    l_Packet = (SPacketWindowItems)event.getPacket();
                    if (l_Packet.getWindowId() == this.EnderChestWindowId) {
                        this.EnderChestItems.clear();
                        for (i = 0; i < l_Packet.getItemStacks().size(); ++i) {
                            itemStack = l_Packet.getItemStacks().get(i);
                            if (itemStack != null) {
                                if (i > 26) {
                                    break;
                                }
                                else {
                                    this.EnderChestItems.add(itemStack);
                                }
                            }
                        }
                    }
                    else if (l_Packet.getWindowId() == this.ShulkerWindowId) {
                        if (this.SavedShulkerItems.containsKey(this.LastWindowTitle)) {
                            this.SavedShulkerItems.remove(this.LastWindowTitle);
                        }
                        l_List = new ArrayList<ItemStack>();
                        for (j = 0; j < l_Packet.getItemStacks().size(); ++j) {
                            itemStack2 = l_Packet.getItemStacks().get(j);
                            if (itemStack2 != null) {
                                if (j > 26) {
                                    break;
                                }
                                else {
                                    l_List.add(itemStack2);
                                }
                            }
                        }
                        this.SavedShulkerItems.put(this.LastWindowTitle, l_List);
                    }
                }
                else if (event.getPacket() instanceof SPacketOpenWindow) {
                    l_Packet2 = (SPacketOpenWindow)event.getPacket();
                    if (l_Packet2.getWindowTitle().getFormattedText().startsWith("Ender")) {
                        this.EnderChestWindowId = l_Packet2.getWindowId();
                    }
                    else {
                        this.ShulkerWindowId = l_Packet2.getWindowId();
                        this.LastWindowTitle = l_Packet2.getWindowTitle().getUnformattedText();
                    }
                }
                else if (event.getPacket() instanceof SPacketSetSlot) {
                    l_Packet3 = (SPacketSetSlot)event.getPacket();
                    if (l_Packet3.getWindowId() == this.EnderChestWindowId) {}
                }
                return;
            }
        });
        Minecraft mc;
        int x;
        int y;
        int k;
        ItemStack itemStack3;
        int offsetX;
        int offsetY;
        InventoryBasic l_Inventory;
        int l;
        ItemStack itemStack4;
        this.OnRenderTooltip = new Listener<EventRenderTooltip>(p_Event -> {
            if (p_Event.getItemStack() == null) {
                return;
            }
            else {
                mc = Minecraft.getMinecraft();
                if (Item.getIdFromItem(p_Event.getItemStack().getItem()) == 130) {
                    x = p_Event.getX();
                    y = p_Event.getY();
                    GlStateManager.translate((float)(x + 10), (float)(y - 5), 0.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.disableDepth();
                    RenderUtil.drawRect(-3.0f, (float)(-mc.fontRenderer.FONT_HEIGHT - 4), 147.0f, 51.0f, -1727000560);
                    RenderUtil.drawRect(-2.0f, (float)(-mc.fontRenderer.FONT_HEIGHT - 3), 146.0f, 50.0f, -14671840);
                    RenderUtil.drawRect(0.0f, 0.0f, 144.0f, 48.0f, -15724528);
                    RenderUtil.drawStringWithShadow("Pyro EnderChest Viewer", 0.0f, (float)(-mc.fontRenderer.FONT_HEIGHT - 1), -1);
                    GlStateManager.enableDepth();
                    mc.getRenderItem().zLevel = 150.0f;
                    RenderHelper.enableGUIStandardItemLighting();
                    for (k = 0; k < this.EnderChestItems.size(); ++k) {
                        itemStack3 = this.EnderChestItems.get(k);
                        if (itemStack3 != null) {
                            offsetX = k % 9 * 16;
                            offsetY = k / 9 * 16;
                            mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack3, offsetX, offsetY);
                            mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, itemStack3, offsetX, offsetY, (String)null);
                        }
                    }
                    p_Event.cancel();
                    RenderHelper.disableStandardItemLighting();
                    mc.getRenderItem().zLevel = 0.0f;
                    GlStateManager.enableLighting();
                    GlStateManager.translate((float)(-(x + 10)), (float)(-(y - 5)), 0.0f);
                    if (this.middleClick.getValue()) {
                        if (Mouse.isButtonDown(2)) {
                            if (!this.clicked) {
                                l_Inventory = new InventoryBasic("Pyro EnderChest Viewer", true, 27);
                                for (l = 0; l < this.EnderChestItems.size(); ++l) {
                                    itemStack4 = this.EnderChestItems.get(l);
                                    if (itemStack4 != null) {
                                        l_Inventory.addItem(itemStack4);
                                    }
                                }
                                mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)mc.player.inventory, (IInventory)l_Inventory));
                            }
                            this.clicked = true;
                        }
                        else {
                            this.clicked = false;
                        }
                    }
                }
                else if (p_Event.getItemStack().getItem() instanceof ItemShulkerBox) {
                    if (this.Mode.getValue().equals("Normal")) {
                        this.RenderLegacyShulkerPreview(p_Event);
                    }
                    else {
                        this.Render2b2tShulkerPreview(p_Event);
                    }
                }
                return;
            }
        });
        this.setMetaData(this.getMetaData());
        this.Mode.addString("Normal");
        this.Mode.addString("DropPacket");
        this.Mode.addString("Inventory");
    }
    
    public NBTTagCompound getShulkerNBT(final ItemStack stack) {
        final NBTTagCompound compound = stack.getTagCompound();
        if (compound != null && compound.hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound tags = compound.getCompoundTag("BlockEntityTag");
            if (tags.hasKey("Items", 9)) {
                return tags;
            }
        }
        return null;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public String getMetaData() {
        return this.Mode.getValue().toString();
    }
    
    public void RenderLegacyShulkerPreview(final EventRenderTooltip event) {
        final ItemStack shulker = event.getItemStack();
        final NBTTagCompound tagCompound = shulker.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10)) {
            final NBTTagCompound blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag");
            if (blockEntityTag.hasKey("Items", 9)) {
                event.cancel();
                final NonNullList<ItemStack> nonnulllist = (NonNullList<ItemStack>)NonNullList.withSize(27, (Object)ItemStack.EMPTY);
                ItemStackHelper.loadAllItems(blockEntityTag, (NonNullList)nonnulllist);
                final int x = event.getX();
                final int y = event.getY();
                GlStateManager.translate((float)(x + 10), (float)(y - 5), 0.0f);
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                RenderUtil.drawRect(-3.0f, (float)(-this.mc.fontRenderer.FONT_HEIGHT - 4), 147.0f, 51.0f, -1727000560);
                RenderUtil.drawRect(-2.0f, (float)(-this.mc.fontRenderer.FONT_HEIGHT - 3), 146.0f, 50.0f, -14671840);
                RenderUtil.drawRect(0.0f, 0.0f, 144.0f, 48.0f, -15724528);
                RenderUtil.drawStringWithShadow(shulker.getDisplayName(), 0.0f, (float)(-this.mc.fontRenderer.FONT_HEIGHT - 1), -1);
                GlStateManager.enableDepth();
                this.mc.getRenderItem().zLevel = 150.0f;
                RenderHelper.enableGUIStandardItemLighting();
                for (int i = 0; i < nonnulllist.size(); ++i) {
                    final ItemStack itemStack = (ItemStack)nonnulllist.get(i);
                    final int offsetX = i % 9 * 16;
                    final int offsetY = i / 9 * 16;
                    this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
                    this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX, offsetY, (String)null);
                }
                RenderHelper.disableStandardItemLighting();
                this.mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.enableLighting();
                GlStateManager.translate((float)(-(x + 10)), (float)(-(y - 5)), 0.0f);
            }
        }
        if (this.middleClick.getValue()) {
            if (Mouse.isButtonDown(2)) {
                if (!this.clicked) {
                    final BlockShulkerBox shulkerBox = (BlockShulkerBox)Block.getBlockFromItem(shulker.getItem());
                    if (shulkerBox != null) {
                        final NBTTagCompound tag = shulker.getTagCompound();
                        if (tag != null && tag.hasKey("BlockEntityTag", 10)) {
                            final NBTTagCompound entityTag = tag.getCompoundTag("BlockEntityTag");
                            final TileEntityShulkerBox te = new TileEntityShulkerBox();
                            te.setWorld((World)this.mc.world);
                            te.readFromNBT(entityTag);
                            this.mc.displayGuiScreen((GuiScreen)new GuiShulkerBox(this.mc.player.inventory, (IInventory)te));
                        }
                    }
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
    
    public void Render2b2tShulkerPreview(final EventRenderTooltip event) {
        if (!this.SavedShulkerItems.containsKey(event.getItemStack().getDisplayName())) {
            return;
        }
        final List<ItemStack> l_Items = this.SavedShulkerItems.get(event.getItemStack().getDisplayName());
        final int x = event.getX();
        final int y = event.getY();
        GlStateManager.translate((float)(x + 10), (float)(y - 5), 0.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderUtil.drawRect(-2.0f, -13.0f, 146.0f, 50.0f, -14671840);
        RenderUtil.drawRect(0.0f, 0.0f, 144.0f, 48.0f, -15724528);
        RenderUtil.drawStringWithShadow(event.getItemStack().getDisplayName(), 0.0f, -12.0f, -1);
        GlStateManager.enableDepth();
        this.mc.getRenderItem().zLevel = 150.0f;
        RenderHelper.enableGUIStandardItemLighting();
        for (int i = 0; i < l_Items.size(); ++i) {
            final ItemStack itemStack = l_Items.get(i);
            if (itemStack != null) {
                final int offsetX = i % 9 * 16;
                final int offsetY = i / 9 * 16;
                this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, offsetX, offsetY);
                this.mc.getRenderItem().renderItemOverlayIntoGUI(this.mc.fontRenderer, itemStack, offsetX, offsetY, (String)null);
            }
        }
        event.cancel();
        RenderHelper.disableStandardItemLighting();
        this.mc.getRenderItem().zLevel = 0.0f;
        GlStateManager.enableLighting();
        GlStateManager.translate((float)(-(x + 10)), (float)(-(y - 5)), 0.0f);
        if (this.middleClick.getValue()) {
            if (Mouse.isButtonDown(2)) {
                if (!this.clicked) {
                    final InventoryBasic l_Inventory = new InventoryBasic(event.getItemStack().getDisplayName(), true, 27);
                    for (int j = 0; j < l_Items.size(); ++j) {
                        final ItemStack itemStack2 = l_Items.get(j);
                        if (itemStack2 != null) {
                            l_Inventory.addItem(itemStack2);
                        }
                    }
                    this.mc.displayGuiScreen((GuiScreen)new GuiChest((IInventory)this.mc.player.inventory, (IInventory)l_Inventory));
                }
                this.clicked = true;
            }
            else {
                this.clicked = false;
            }
        }
    }
}
