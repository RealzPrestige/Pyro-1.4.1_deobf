//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.main;

import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import dev.nuker.pyro.deobfuscated.events.render.EventRenderGetFOVModifier;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import dev.nuker.pyro.deobfuscated.events.minecraft.EventKeyInput;
import dev.nuker.pyro.deobfuscated.managers.MacroManager;
import dev.nuker.pyro.deobfuscated.managers.ModuleManager;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import dev.nuker.pyro.deobfuscated.util.render.AWTFontRenderer;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.PyroMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.image.BufferedImage;
import net.minecraftforge.client.event.ScreenshotEvent;
import dev.nuker.pyro.deobfuscated.util.imgur.ImgurUploader;

public class ForgeEventProcessor
{
    private ImgurUploader imgurUploader;
    
    public ForgeEventProcessor() {
        this.imgurUploader = new ImgurUploader();
    }
    
    @SubscribeEvent
    public void onScreenshot(final ScreenshotEvent event) {
        if (PyroStatic.IMGURUPLOADER != null && PyroStatic.IMGURUPLOADER.isEnabled()) {
            final BufferedImage screenshot = event.getImage();
            this.imgurUploader.uploadImage(screenshot);
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0f);
        PyroMod.EVENT_BUS.post(new RenderEvent(event.getPartialTicks()));
        GlStateManager.glLineWidth(1.0f);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        AWTFontRenderer.garbageCollectionTick();
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (Wrapper.GetMC().player == null) {
            return;
        }
        PyroMod.EVENT_BUS.post(new EventClientTick());
    }
    
    @SubscribeEvent
    public void onEntitySpawn(final EntityJoinWorldEvent event) {
        if (event.isCanceled()) {
            return;
        }
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            final String key = Keyboard.getKeyName(Keyboard.getEventKey());
            ModuleManager.Get().OnKeyPress(key);
            MacroManager.Get().OnKeyPress(key);
            if (!key.equals("NONE") && !key.isEmpty()) {
                PyroMod.EVENT_BUS.post(new EventKeyInput(key));
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState()) {
            final String button = Mouse.getButtonName(Mouse.getEventButton());
            ModuleManager.Get().OnKeyPress(button);
            MacroManager.Get().OnKeyPress(button);
            PyroMod.EVENT_BUS.post(event);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Pre event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDrawn(final RenderPlayerEvent.Post event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkLoaded(final ChunkEvent.Load event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onChunkUnLoaded(final ChunkEvent.Unload event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onInputUpdate(final InputUpdateEvent event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLivingEntityUseItemEventTick(final LivingEntityUseItemEvent.Start entityUseItemEvent) {
        PyroMod.EVENT_BUS.post(entityUseItemEvent);
    }
    
    @SubscribeEvent
    public void onLivingDamageEvent(final LivingDamageEvent event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onEntityJoinWorldEvent(final EntityJoinWorldEvent entityJoinWorldEvent) {
        PyroMod.EVENT_BUS.post(entityJoinWorldEvent);
    }
    
    @SubscribeEvent
    public void onPlayerPush(final PlayerSPPushOutOfBlocksEvent event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onLeftClickBlock(final PlayerInteractEvent.LeftClickBlock event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent entityEvent) {
        PyroMod.EVENT_BUS.post(entityEvent);
    }
    
    @SubscribeEvent
    public void onRenderBlockOverlay(final RenderBlockOverlayEvent event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void onClientChat(final ClientChatReceivedEvent event) {
        PyroMod.EVENT_BUS.post(event);
    }
    
    @SubscribeEvent
    public void getFOVModifier(final EntityViewRenderEvent.FOVModifier p_Event) {
        final EventRenderGetFOVModifier l_Event = new EventRenderGetFOVModifier((float)p_Event.getRenderPartialTicks(), true);
        PyroMod.EVENT_BUS.post(l_Event);
        if (l_Event.isCancelled()) {
            p_Event.setFOV(l_Event.GetFOV());
        }
    }
    
    @SubscribeEvent
    public void LivingAttackEvent(final LivingAttackEvent p_Event) {
        PyroMod.EVENT_BUS.post(p_Event);
    }
    
    @SubscribeEvent
    public void OnWorldChange(final WorldEvent p_Event) {
        PyroMod.EVENT_BUS.post(p_Event);
    }
}
