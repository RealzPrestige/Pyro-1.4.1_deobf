//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev.nuker.pyro.deobfuscated.module.combat;

import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.inventory.ClickType;
import net.minecraft.init.Items;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.network.play.server.SPacketSoundEffect;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.ItemTool;
import dev.nuker.pyro.deobfuscated.main.Wrapper;
import dev.nuker.pyro.deobfuscated.main.PyroStatic;
import dev.nuker.pyro.deobfuscated.util.entity.PlayerUtil;
import java.util.Comparator;

import dev.nuker.pyro.deobfuscated.managers.FriendManager;
import dev.nuker.pyro.deobfuscated.util.entity.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import dev.nuker.pyro.deobfuscated.util.CrystalUtils;
import net.minecraft.entity.Entity;
import dev.nuker.pyro.deobfuscated.events.render.RenderEvent;
import dev.nuker.pyro.deobfuscated.events.network.EventServerPacket;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerMotionUpdate;
import dev.nuker.pyro.deobfuscated.events.player.EventPlayerUpdate;
import dev.nuker.pyro.deobfuscated.events.client.EventClientTick;
import dev.nuker.pyro.deobfuscated.events.bus.EventHandler;
import dev.nuker.pyro.deobfuscated.events.entity.EventEntityRemoved;
import dev.nuker.pyro.deobfuscated.events.bus.Listener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.math.BlockPos;
import java.util.concurrent.ConcurrentLinkedQueue;
import dev.nuker.pyro.deobfuscated.util.Timer;
import dev.nuker.pyro.deobfuscated.module.Value;
import dev.nuker.pyro.deobfuscated.module.Module;

public class AutoCrystal extends Module
{
    public static final Value<String> BreakMode;
    public static final Value<String> PlaceMode;
    public static final Value<Float> PlaceRadius;
    public static final Value<Float> BreakRadius;
    public static final Value<Float> WallsRangePlace;
    public static final Value<Float> WallsRangeBreak;
    public static final Value<Boolean> MultiPlace;
    public static final Value<Integer> Ticks;
    public static final Value<Float> MinDMG;
    public static final Value<Float> MaxSelfDMG;
    public static final Value<Float> FacePlace;
    public static final Value<String> SwapMode;
    public static final Value<Boolean> PauseIfHittingBlock;
    public static final Value<Boolean> PauseWhileEating;
    public static final Value<Boolean> NoSuicide;
    public static final Value<Boolean> AntiWeakness;
    public static final Value<Boolean> Players;
    public static final Value<Boolean> Hostile;
    public static final Value<Boolean> Render;
    public static final Value<Integer> Red;
    public static final Value<Integer> Green;
    public static final Value<Integer> Blue;
    public static final Value<Integer> Alpha;
    public static Timer _removeVisualTimer;
    private Timer _rotationResetTimer;
    private ConcurrentLinkedQueue<BlockPos> _placedCrystals;
    private ConcurrentHashMap<BlockPos, Float> _placedCrystalsDamage;
    private double[] _rotations;
    private ConcurrentHashMap<EntityEnderCrystal, Integer> _attackedEnderCrystals;
    private final Minecraft mc;
    private String _lastTarget;
    private int _remainingTicks;
    private BlockPos _lastPlaceLocation;
    private int prevSlot;
    @EventHandler
    private Listener<EventEntityRemoved> OnEntityRemove;
    @EventHandler
    private Listener<EventClientTick> OnClientTick;
    @EventHandler
    private Listener<EventPlayerUpdate> onPlayerUpdate;
    @EventHandler
    private Listener<EventPlayerMotionUpdate> OnPlayerUpdate;
    @EventHandler
    private Listener<EventServerPacket> onServerPacket;
    @EventHandler
    private Listener<RenderEvent> OnRenderEvent;
    
    public AutoCrystal() {

        //WHY FOR THIS MODULE DEOBF DONT WORK idiot shit
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "AutoCrystal"
        //     3: iconst_1       
        //     4: anewarray       Ljava/lang/String;
        //     7: dup            
        //     8: iconst_0       
        //     9: ldc             "CrystalAura"
        //    11: aastore        
        //    12: ldc             "Automatically places and destroys crystals"
        //    14: ldc             "NONE"
        //    16: ldc             14819840
        //    18: getstatic       com/Pyro/pyro/module/Module$ModuleType.COMBAT:Lcom/Pyro/pyro/module/Module$ModuleType;
        //    21: invokespecial   com/Pyro/pyro/module/Module.<init>:(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILcom/Pyro/pyro/module/Module$ModuleType;)V
        //    24: aload_0         /* this */
        //    25: new             Lcom/Pyro/pyro/util/Timer;
        //    28: dup            
        //    29: invokespecial   com/Pyro/pyro/util/Timer.<init>:()V
        //    32: putfield        com/Pyro/pyro/module/combat/AutoCrystal._rotationResetTimer:Lcom/Pyro/pyro/util/Timer;
        //    35: aload_0         /* this */
        //    36: new             Ljava/util/concurrent/ConcurrentLinkedQueue;
        //    39: dup            
        //    40: invokespecial   java/util/concurrent/ConcurrentLinkedQueue.<init>:()V
        //    43: putfield        com/Pyro/pyro/module/combat/AutoCrystal._placedCrystals:Ljava/util/concurrent/ConcurrentLinkedQueue;
        //    46: aload_0         /* this */
        //    47: new             Ljava/util/concurrent/ConcurrentHashMap;
        //    50: dup            
        //    51: invokespecial   java/util/concurrent/ConcurrentHashMap.<init>:()V
        //    54: putfield        com/Pyro/pyro/module/combat/AutoCrystal._placedCrystalsDamage:Ljava/util/concurrent/ConcurrentHashMap;
        //    57: aload_0         /* this */
        //    58: aconst_null    
        //    59: putfield        com/Pyro/pyro/module/combat/AutoCrystal._rotations:[D
        //    62: aload_0         /* this */
        //    63: new             Ljava/util/concurrent/ConcurrentHashMap;
        //    66: dup            
        //    67: invokespecial   java/util/concurrent/ConcurrentHashMap.<init>:()V
        //    70: putfield        com/Pyro/pyro/module/combat/AutoCrystal._attackedEnderCrystals:Ljava/util/concurrent/ConcurrentHashMap;
        //    73: aload_0         /* this */
        //    74: invokestatic    net/minecraft/client/Minecraft.getMinecraft:()Lnet/minecraft/client/Minecraft;
        //    77: putfield        com/Pyro/pyro/module/combat/AutoCrystal.mc:Lnet/minecraft/client/Minecraft;
        //    80: aload_0         /* this */
        //    81: aconst_null    
        //    82: putfield        com/Pyro/pyro/module/combat/AutoCrystal._lastTarget:Ljava/lang/String;
        //    85: aload_0         /* this */
        //    86: getstatic       net/minecraft/util/math/BlockPos.ORIGIN:Lnet/minecraft/util/math/BlockPos;
        //    89: putfield        com/Pyro/pyro/module/combat/AutoCrystal._lastPlaceLocation:Lnet/minecraft/util/math/BlockPos;
        //    92: aload_0         /* this */
        //    93: iconst_m1      
        //    94: putfield        com/Pyro/pyro/module/combat/AutoCrystal.prevSlot:I
        //    97: aload_0         /* this */
        //    98: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   101: dup            
        //   102: aload_0         /* this */
        //   103: invokedynamic   BootstrapMethod #0, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   108: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   111: putfield        com/Pyro/pyro/module/combat/AutoCrystal.OnEntityRemove:Lcom/Pyro/pyro/events/bus/Listener;
        //   114: aload_0         /* this */
        //   115: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   118: dup            
        //   119: aload_0         /* this */
        //   120: invokedynamic   BootstrapMethod #1, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   125: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   128: putfield        com/Pyro/pyro/module/combat/AutoCrystal.OnClientTick:Lcom/Pyro/pyro/events/bus/Listener;
        //   131: aload_0         /* this */
        //   132: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   135: dup            
        //   136: aload_0         /* this */
        //   137: invokedynamic   BootstrapMethod #2, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   142: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   145: putfield        com/Pyro/pyro/module/combat/AutoCrystal.onPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   148: aload_0         /* this */
        //   149: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   152: dup            
        //   153: aload_0         /* this */
        //   154: invokedynamic   BootstrapMethod #3, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   159: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   162: putfield        com/Pyro/pyro/module/combat/AutoCrystal.OnPlayerUpdate:Lcom/Pyro/pyro/events/bus/Listener;
        //   165: aload_0         /* this */
        //   166: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   169: dup            
        //   170: aload_0         /* this */
        //   171: invokedynamic   BootstrapMethod #4, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   176: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   179: putfield        com/Pyro/pyro/module/combat/AutoCrystal.onServerPacket:Lcom/Pyro/pyro/events/bus/Listener;
        //   182: aload_0         /* this */
        //   183: new             Lcom/Pyro/pyro/events/bus/Listener;
        //   186: dup            
        //   187: aload_0         /* this */
        //   188: invokedynamic   BootstrapMethod #5, accept:(Lcom/Pyro/pyro/module/combat/AutoCrystal;)Ljava/util/function/Consumer;
        //   193: invokespecial   com/Pyro/pyro/events/bus/Listener.<init>:(Ljava/util/function/Consumer;)V
        //   196: putfield        com/Pyro/pyro/module/combat/AutoCrystal.OnRenderEvent:Lcom/Pyro/pyro/events/bus/Listener;
        //   199: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.BreakMode:Lcom/Pyro/pyro/module/Value;
        //   202: ldc             "Always"
        //   204: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   207: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.BreakMode:Lcom/Pyro/pyro/module/Value;
        //   210: ldc             "Smart"
        //   212: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   215: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.BreakMode:Lcom/Pyro/pyro/module/Value;
        //   218: ldc             "OnlyOwn"
        //   220: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   223: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.PlaceMode:Lcom/Pyro/pyro/module/Value;
        //   226: ldc             "Most"
        //   228: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   231: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.PlaceMode:Lcom/Pyro/pyro/module/Value;
        //   234: ldc             "Lethal"
        //   236: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   239: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.SwapMode:Lcom/Pyro/pyro/module/Value;
        //   242: ldc             "Hotbar"
        //   244: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   247: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.SwapMode:Lcom/Pyro/pyro/module/Value;
        //   250: ldc             "Inventory"
        //   252: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   255: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.SwapMode:Lcom/Pyro/pyro/module/Value;
        //   258: ldc             "GoBack"
        //   260: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   263: getstatic       com/Pyro/pyro/module/combat/AutoCrystal.SwapMode:Lcom/Pyro/pyro/module/Value;
        //   266: ldc             "None"
        //   268: invokevirtual   com/Pyro/pyro/module/Value.addString:(Ljava/lang/String;)V
        //   271: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.generateNameForVariable(NameVariables.java:264)
        //     at com.strobel.decompiler.languages.java.ast.NameVariables.assignNamesToVariables(NameVariables.java:198)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:276)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:782)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createConstructor(AstBuilder.java:713)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:549)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:519)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:161)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:150)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:125)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:330)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:251)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:126)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public void toggleNoSave() {
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this._placedCrystals.clear();
        this._placedCrystalsDamage.clear();
        this._remainingTicks = 0;
        this._lastPlaceLocation = BlockPos.ORIGIN;
    }
    
    private boolean ValidateCrystal(final EntityEnderCrystal e) {
        if (e == null || e.isDead) {
            return false;
        }
        if (this._attackedEnderCrystals.containsKey(e) && this._attackedEnderCrystals.get(e) > 15) {
            return false;
        }
        if (e.getDistance((Entity)this.mc.player) > (this.mc.player.canEntityBeSeen((Entity)e) ? AutoCrystal.BreakRadius.getValue() : AutoCrystal.WallsRangeBreak.getValue())) {
            return false;
        }
        final String s = AutoCrystal.BreakMode.getValue();
        switch (s) {
            case "OnlyOwn": {
                for (final BlockPos pos : this._placedCrystals) {
                    if (e.getDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= 3.0) {
                        return true;
                    }
                }
            }
            case "Smart": {
                final float selfDamage = CrystalUtils.calculateDamage((World)this.mc.world, e.posX, e.posY, e.posZ, (Entity)this.mc.player, 0);
                if (selfDamage > AutoCrystal.MaxSelfDMG.getValue()) {
                    return false;
                }
                if (AutoCrystal.NoSuicide.getValue() && selfDamage >= this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount()) {
                    return false;
                }
                for (final Entity en : this.mc.world.loadedEntityList) {
                    if (!(en instanceof EntityLivingBase)) {
                        continue;
                    }
                    final EntityLivingBase living = (EntityLivingBase)en;
                    if (living instanceof EntityPlayer && !AutoCrystal.Players.getValue()) {
                        continue;
                    }
                    if (EntityUtil.isHostileMob((Entity)living) && !AutoCrystal.Hostile.getValue()) {
                        continue;
                    }
                    if (!(living instanceof EntityPlayer) && !EntityUtil.isHostileMob((Entity)living)) {
                        continue;
                    }
                    if (living == this.mc.player || FriendManager.Get().IsFriend((Entity)living) || living.isDead) {
                        continue;
                    }
                    if (living.getHealth() + living.getAbsorptionAmount() <= 0.0f) {
                        continue;
                    }
                    float minDamage = AutoCrystal.MinDMG.getValue();
                    if (living.getHealth() + living.getAbsorptionAmount() <= AutoCrystal.FacePlace.getValue()) {
                        minDamage = 1.0f;
                    }
                    final float calculatedDamage = CrystalUtils.calculateDamage((World)this.mc.world, e.posX, e.posY, e.posZ, (Entity)living, 0);
                    if (calculatedDamage > minDamage) {
                        return true;
                    }
                }
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public EntityEnderCrystal GetNearestCrystalTo(final Entity entity) {
        return (EntityEnderCrystal)this.mc.world.getLoadedEntityList().stream().filter(e -> e instanceof EntityEnderCrystal && this.ValidateCrystal(e)).map(e -> e).min(Comparator.comparing(e -> entity.getDistance(e))).orElse(null);
    }
    
    public void AddAttackedCrystal(final EntityEnderCrystal crystal) {
        if (this._attackedEnderCrystals.containsKey(crystal)) {
            final int value = this._attackedEnderCrystals.get(crystal);
            this._attackedEnderCrystals.put(crystal, value + 1);
        }
        else {
            this._attackedEnderCrystals.put(crystal, 1);
        }
    }
    
    public static boolean VerifyCrystalBlocks(final Minecraft mc, final BlockPos pos) {
        if (mc.player.getDistanceSq(pos) > AutoCrystal.PlaceRadius.getValue() * AutoCrystal.PlaceRadius.getValue()) {
            return false;
        }
        if (AutoCrystal.WallsRangePlace.getValue() > 0.0f && !PlayerUtil.CanSeeBlock(pos)) {
            final double dist = pos.getDistance((int)mc.player.posX, (int)mc.player.posY, (int)mc.player.posZ);
            if (dist > AutoCrystal.WallsRangePlace.getValue()) {
                return false;
            }
        }
        final float selfDamage = CrystalUtils.calculateDamage((World)mc.world, pos.getX() + 0.5, pos.getY() + 1.0, pos.getZ() + 0.5, (Entity)mc.player, 0);
        return selfDamage <= AutoCrystal.MaxSelfDMG.getValue() && (!AutoCrystal.NoSuicide.getValue() || selfDamage < mc.player.getHealth() + mc.player.getAbsorptionAmount());
    }
    
    public static boolean NeedPause() {
        if (PyroStatic.SURROUND.isEnabled() && !PyroStatic.SURROUND.IsSurrounded() && PyroStatic.SURROUND.HasObsidian()) {
            if (!PyroStatic.SURROUND.ActivateOnlyOnShift.getValue()) {
                return true;
            }
            if (!Wrapper.GetMC().gameSettings.keyBindSneak.isKeyDown()) {
                return true;
            }
        }
        return (PyroStatic.AUTOTRAPFEET.isEnabled() && !PyroStatic.AUTOTRAPFEET.IsCurrentTargetTrapped() && PyroStatic.AUTOTRAPFEET.HasObsidian()) || PyroStatic.AUTOMEND.isEnabled() || (PyroStatic.SELFTRAP.isEnabled() && !PyroStatic.SELFTRAP.IsSelfTrapped() && PyroStatic.SURROUND.HasObsidian()) || (PyroStatic.HOLEFILLER.isEnabled() && PyroStatic.HOLEFILLER.IsProcessing()) || (AutoCrystal.PauseIfHittingBlock.getValue() && Wrapper.GetMC().playerController.isHittingBlock && Wrapper.GetMC().player.getHeldItemMainhand().getItem() instanceof ItemTool) || PyroStatic.AUTOCITY.isEnabled() || (AutoCrystal.PauseWhileEating.getValue() && PlayerUtil.IsEating());
    }
    
    public String getTarget() {
        return this._lastTarget;
    }
    
    public boolean isCrystalling() {
        return !this._rotationResetTimer.passed(1000.0);
    }
    
    static {
        BreakMode = new Value<String>("BreakMode", new String[] { "BM" }, "Mode of breaking to use", "Always");
        PlaceMode = new Value<String>("PlaceMode", new String[] { "BM" }, "Mode of placing to use", "Most");
        PlaceRadius = new Value<Float>("PlaceRadius", new String[] { "" }, "Radius for placing", 4.0f, 0.0f, 5.0f, 0.5f);
        BreakRadius = new Value<Float>("BreakRadius", new String[] { "" }, "Radius for BreakRadius", 4.0f, 0.0f, 5.0f, 0.5f);
        WallsRangePlace = new Value<Float>("WallsRangePlace", new String[] { "" }, "Max distance through walls", 3.5f, 0.0f, 5.0f, 0.5f);
        WallsRangeBreak = new Value<Float>("WallsRangeBreak", new String[] { "" }, "Max distance through walls", 3.5f, 0.0f, 5.0f, 0.5f);
        MultiPlace = new Value<Boolean>("MultiPlace", new String[] { "MultiPlaces" }, "Tries to multiplace", false);
        Ticks = new Value<Integer>("Ticks", new String[] { "IgnoreTicks" }, "The number of ticks to ignore on client update", 2, 0, 20, 1);
        MinDMG = new Value<Float>("MinDMG", new String[] { "" }, "Minimum damage to do to your opponent", 4.0f, 0.0f, 20.0f, 1.0f);
        MaxSelfDMG = new Value<Float>("MaxSelfDMG", new String[] { "" }, "Max self dmg for breaking crystals that will deal tons of dmg", 4.0f, 0.0f, 20.0f, 1.0f);
        FacePlace = new Value<Float>("FacePlace", new String[] { "" }, "Required target health for faceplacing", 8.0f, 0.0f, 20.0f, 0.5f);
        SwapMode = new Value<String>("Swap", new String[] { "" }, "Swap Mode", "Hotbar");
        PauseIfHittingBlock = new Value<Boolean>("PauseIfHittingBlock", new String[] { "" }, "Pauses when your hitting a block with a pickaxe", false);
        PauseWhileEating = new Value<Boolean>("PauseWhileEating", new String[] { "PauseWhileEating" }, "Pause while eating", false);
        NoSuicide = new Value<Boolean>("NoSuicide", new String[] { "NS" }, "Doesn't commit suicide/pop if you are going to take fatal damage from self placed crystal", true);
        AntiWeakness = new Value<Boolean>("AntiWeakness", new String[] { "AW" }, "Switches to a sword to try and break crystals", true);
        Players = new Value<Boolean>("Players", new String[] { "Players" }, "Target players", true);
        Hostile = new Value<Boolean>("Hostile", new String[] { "Hostile", "Withers" }, "Target hostile", false);
        Render = new Value<Boolean>("Render", new String[] { "Render" }, "Allows for rendering of block placements", true);
        Red = new Value<Integer>("Red", new String[] { "Red" }, "Red for rendering", 51, 0, 255, 5);
        Green = new Value<Integer>("Green", new String[] { "Green" }, "Green for rendering", 255, 0, 255, 5);
        Blue = new Value<Integer>("Blue", new String[] { "Blue" }, "Blue for rendering", 243, 0, 255, 5);
        Alpha = new Value<Integer>("Alpha", new String[] { "Alpha" }, "Alpha for rendering", 153, 0, 255, 5);
        AutoCrystal._removeVisualTimer = new Timer();
    }
}
