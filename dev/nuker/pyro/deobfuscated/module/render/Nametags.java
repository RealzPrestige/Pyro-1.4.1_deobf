//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.render;

import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import dev.nuker.pyro.deobfuscated.util.ColourUtilities;
import java.awt.Color;
import net.minecraft.init.Items;
import net.minecraft.enchantment.EnchantmentHelper;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import java.util.List;
import java.util.Collections;
import net.minecraft.item.ItemStack;
import dev.nuker.pyro.deobfuscated.managers.FontManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.util.MathUtil;
import dev.nuker.pyro.deobfuscated.util.render.RenderUtil;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import java.util.ArrayList;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderEntityName;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class Nametags extends Module
{
    public final Value<Boolean> Armor;
    public final Value<Boolean> Durability;
    public final Value<Boolean> ItemName;
    public final Value<Boolean> Health;
    public final Value<Boolean> Invisibles;
    public final Value<Boolean> EntityID;
    public final Value<Boolean> GameMode;
    public final Value<Boolean> Ping;
    public final Value<Float> Scaling;
    @EventHandler
    private Listener<RenderEvent> OnRenderGameOverlay;
    @EventHandler
    private Listener<EventRenderEntityName> OnRenderEntityName;
    
    public Nametags() {
        super("Nametags", new String[] { "Nametag" }, "Improves nametags of players around you", "NONE", 5983697, ModuleType.RENDER);
        this.Armor = new Value<Boolean>("Armor", new String[] { "" }, "", true);
        this.Durability = new Value<Boolean>("Durability", new String[] { "" }, "", true);
        this.ItemName = new Value<Boolean>("ItemName", new String[] { "" }, "", true);
        this.Health = new Value<Boolean>("Health", new String[] { "" }, "", true);
        this.Invisibles = new Value<Boolean>("Invisibles", new String[] { "" }, "", false);
        this.EntityID = new Value<Boolean>("EntityID", new String[] { "" }, "", false);
        this.GameMode = new Value<Boolean>("GameMode", new String[] { "" }, "", false);
        this.Ping = new Value<Boolean>("Ping", new String[] { "" }, "", true);
        this.Scaling = new Value<Float>("Scaling", new String[] { "" }, "Scaling", 3.0f, 1.0f, 10.0f, 1.0f);
        ArrayList<EntityPlayer> players;
        final List<EntityPlayer> list;
        final Iterator<EntityPlayer> iterator;
        EntityPlayer player;
        Entity entity2;
        Vec3d pos;
        double n;
        double distance;
        double n2;
        double n3;
        Vec3d pos2;
        double posX;
        double posY;
        double posZ;
        double distance2;
        double scale;
        float n4;
        float n5;
        float n6;
        String nameTag;
        float width;
        float height;
        Iterator<ItemStack> items;
        ArrayList<ItemStack> stacks;
        ItemStack stack;
        int x;
        int y;
        int z;
        final Iterator<ItemStack> iterator2;
        ItemStack stack2;
        this.OnRenderGameOverlay = new Listener<RenderEvent>(event -> {
            if (this.mc.world == null || this.mc.renderEngine == null || this.mc.getRenderManager() == null || this.mc.getRenderManager().options == null) {
                return;
            }
            else {
                players = new ArrayList<EntityPlayer>();
                this.mc.world.playerEntities.stream().filter(entity -> entity instanceof EntityPlayer && EntityUtil.isLiving(entity) && entity != this.mc.getRenderViewEntity()).forEach(e -> {
                    RenderUtil.camera.setPosition(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY, this.mc.getRenderViewEntity().posZ);
                    if (RenderUtil.camera.isBoundingBoxInFrustum(e.getEntityBoundingBox())) {
                        list.add(e);
                    }
                    return;
                });
                players.sort((p1, p2) -> Double.compare(p2.getDistance(this.mc.getRenderViewEntity()), p1.getDistance(this.mc.getRenderViewEntity())));
                players.iterator();
                while (iterator.hasNext()) {
                    player = iterator.next();
                    entity2 = this.mc.getRenderViewEntity();
                    pos = MathUtil.interpolateEntityClose((Entity)player, event.getPartialTicks());
                    n = pos.x;
                    distance = pos.y + 0.65;
                    n2 = pos.z;
                    n3 = distance + (player.isSneaking() ? 0.0 : 0.07999999821186066);
                    pos2 = MathUtil.interpolateEntityClose(entity2, event.getPartialTicks());
                    posX = entity2.posX;
                    posY = entity2.posY;
                    posZ = entity2.posZ;
                    entity2.posX = pos2.x;
                    entity2.posY = pos2.y;
                    entity2.posZ = pos2.z;
                    distance2 = entity2.getDistance(n, distance, n2);
                    scale = 0.04;
                    if (distance2 > 0.0) {
                        scale = 0.02 + this.Scaling.getValue() / 1000.0f * distance2;
                    }
                    GlStateManager.pushMatrix();
                    RenderHelper.enableStandardItemLighting();
                    GlStateManager.enablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, -1500000.0f);
                    GlStateManager.disableLighting();
                    GlStateManager.translate((float)n, (float)n3 + 1.4f, (float)n2);
                    n4 = -this.mc.getRenderManager().playerViewY;
                    n5 = 1.0f;
                    n6 = 0.0f;
                    GlStateManager.rotate(n4, 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate(this.mc.getRenderManager().playerViewX, (this.mc.gameSettings.thirdPersonView == 2) ? -1.0f : 1.0f, 0.0f, 0.0f);
                    GlStateManager.scale(-scale, -scale, scale);
                    GlStateManager.disableDepth();
                    GlStateManager.enableBlend();
                    nameTag = this.generateNameTag(player);
                    width = (float)(FontManager.Get().getGameFont().getStringWidth(nameTag) / 2);
                    height = (float)FontManager.Get().getGameFont().getHeight();
                    GlStateManager.enableBlend();
                    RenderUtil.drawRect(-width - 1.0f, -(height + 1.0f), width + 2.0f, 2.0f, 1594493450);
                    GlStateManager.disableBlend();
                    FontManager.Get().getGameFont().drawString(nameTag, -width + 1.0f, -height + 3.0f, this.getColorByHealth(player.getMaxHealth(), player.getHealth()));
                    GlStateManager.pushMatrix();
                    items = player.getArmorInventoryList().iterator();
                    stacks = new ArrayList<ItemStack>();
                    stacks.add(player.getHeldItemOffhand());
                    while (items.hasNext()) {
                        stack = items.next();
                        if (!stack.isEmpty()) {
                            stacks.add(stack);
                        }
                    }
                    stacks.add(player.getHeldItemMainhand());
                    Collections.reverse(stacks);
                    x = (int)(-width);
                    y = -32;
                    z = 0;
                    stacks.iterator();
                    while (iterator2.hasNext()) {
                        stack2 = iterator2.next();
                        this.RenderItemStack(stack2, x, y, z);
                        this.RenderItemEnchantments(stack2, x, -62);
                        x += 16;
                    }
                    GlStateManager.popMatrix();
                    GlStateManager.enableDepth();
                    GlStateManager.disableBlend();
                    GlStateManager.disablePolygonOffset();
                    GlStateManager.doPolygonOffset(1.0f, 1500000.0f);
                    GlStateManager.popMatrix();
                    entity2.posX = posX;
                    entity2.posY = posY;
                    entity2.posZ = posZ;
                }
                return;
            }
        });
        this.OnRenderEntityName = new Listener<EventRenderEntityName>(event -> event.cancel());
    }
    
    private String GetEnchantName(final Enchantment enchantment, final int n) {
        if (enchantment.getTranslatedName(n).contains("Vanish")) {
            return ChatFormatting.RED + "Van";
        }
        if (enchantment.getTranslatedName(n).contains("Bind")) {
            return ChatFormatting.RED + "Bind";
        }
        String substring = enchantment.getTranslatedName(n);
        final int n2 = (n > 1) ? 2 : 3;
        if (substring.length() > n2) {
            substring = substring.substring(0, n2);
        }
        final StringBuilder sb = new StringBuilder();
        final String s = substring;
        final int n3 = 0;
        String s2 = sb.insert(0, s.substring(0, 1).toUpperCase()).append(substring.substring(1)).toString();
        if (n > 1) {
            s2 = new StringBuilder().insert(0, s2).append(n).toString();
        }
        return s2;
    }
    
    private void RenderItemEnchantments(final ItemStack itemStack, final int n, int n2) {
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        final int n3 = -1;
        Iterator<Enchantment> iterator3;
        for (Iterator<Enchantment> iterator2 = iterator3 = EnchantmentHelper.getEnchantments(itemStack).keySet().iterator(); iterator3.hasNext(); iterator3 = iterator2) {
            final Enchantment enchantment;
            if ((enchantment = iterator2.next()) != null) {
                RenderUtil.drawStringWithShadow(this.GetEnchantName(enchantment, EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack)), (float)(n * 2), (float)n2, -1);
                n2 += 8;
            }
        }
        if (itemStack.getItem().equals(Items.GOLDEN_APPLE) && itemStack.hasEffect()) {
            RenderUtil.drawStringWithShadow(ChatFormatting.DARK_RED + "God", (float)(n * 2), (float)n2, -1);
        }
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void RenderItemDamage(final ItemStack itemStack, final int n, final int n2) {
        final float n3 = (itemStack.getMaxDamage() - itemStack.getItemDamage()) / (float)itemStack.getMaxDamage() * 100.0f;
        int color = 2096896;
        if (n3 > 30.0f && n3 < 70.0f) {
            color = 16776960;
        }
        else if (n3 <= 30.0f) {
            color = 16711680;
        }
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        RenderUtil.drawStringWithShadow(new StringBuilder().insert(0, String.valueOf((int)n3)).append('%').toString(), (float)(n * 2), (float)n2, color);
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
    }
    
    private void RenderItemStack(final ItemStack itemStack, final int n, final int n2, final int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().zLevel = -150.0f;
        GlStateManager.disableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.disableCull();
        final int n4 = (n3 > 4) ? ((n3 - 4) * 8 / 2) : 0;
        this.mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, n, n2 + n4);
        this.mc.getRenderItem().renderItemOverlays(this.mc.fontRenderer, itemStack, n, n2 + n4);
        this.mc.getRenderItem().zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableCull();
        GlStateManager.enableAlpha();
        final float n5 = 0.5f;
        final float n6 = 0.5f;
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        GlStateManager.disableDepth();
        if (itemStack.getMaxDamage() > 1) {
            this.RenderItemDamage(itemStack, n * 2, n2 - 100);
        }
        GlStateManager.enableDepth();
        final float n7 = 2.0f;
        final int n8 = 2;
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }
    
    private int getColorByHealth(final float maxHealth, final float health) {
        final Color green = new Color(72, 255, 94);
        final Color yellow = new Color(255, 250, 57);
        final Color red = new Color(255, 35, 40);
        final float middleHealth = maxHealth / 2.0f;
        if (health <= middleHealth) {
            return ColourUtilities.blend(yellow, red, health / middleHealth).getRGB();
        }
        if (health <= middleHealth * 2.0f) {
            return ColourUtilities.blend(green, yellow, (health - middleHealth) / middleHealth).getRGB();
        }
        return green.getRGB();
    }
    
    private String generateNameTag(final EntityPlayer player) {
        String string = player.getName();
        if (FriendManager.Get().IsFriend((Entity)player)) {
            string = ChatFormatting.AQUA + string + ChatFormatting.RESET;
        }
        else if (player.isSneaking()) {
            string = ChatFormatting.GOLD + string + ChatFormatting.RESET;
        }
        else {
            string = ChatFormatting.WHITE + string + ChatFormatting.RESET;
        }
        int responseTime = -1;
        try {
            responseTime = (int)MathUtil.clamp((float)this.mc.getConnection().getPlayerInfo(player.getUniqueID()).getResponseTime(), 0.0f, 10000.0f);
        }
        catch (NullPointerException ex) {}
        if (responseTime > 200) {
            string += ChatFormatting.RED;
        }
        else if (responseTime <= 200 && responseTime >= 100) {
            string += ChatFormatting.YELLOW;
        }
        else if (responseTime < 100) {
            string += ChatFormatting.GREEN;
        }
        string = string + "  " + responseTime + "ms" + ChatFormatting.RESET + "  ";
        return string + Math.floor(player.getHealth() + player.getAbsorptionAmount());
    }
}
