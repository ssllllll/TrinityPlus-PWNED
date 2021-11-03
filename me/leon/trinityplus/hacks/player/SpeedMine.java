//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.player;

import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.util.math.*;
import me.leon.trinityplus.utils.rendering.*;
import java.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.leon.trinityplus.events.*;

public class SpeedMine extends Module
{
    public static ModeSetting mode;
    public static ModeSetting haste;
    public static SliderSetting startPercent;
    public static SliderSetting endPercent;
    public static SliderSetting range;
    public static BooleanSetting render;
    public static ModeSetting renderMode;
    public static SliderSetting width;
    public static SliderSetting length;
    public static ColorSetting mainColor;
    public static ColorSetting secondaryColor;
    public static BooleanSetting renderDamage;
    private boolean started;
    private BlockPos curPos;
    @EventHandler
    private final Listener<EventModeChange> modeChangeListener;
    @EventHandler
    private final Listener<EventClickBlock> destroyBlockListener;
    @EventHandler
    private final Listener<EventDamageBlock> damageBlockListener;
    
    public String getHudInfo() {
        return SpeedMine.mode.getValue();
    }
    
    public void onUpdate() {
        if (this.curPos != null && SpeedMine.mc.player.getDistanceSq(this.curPos) > SpeedMine.range.getValue() * SpeedMine.range.getValue()) {
            this.curPos = null;
        }
    }
    
    public SpeedMine() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "SpeedMine"
        //     3: ldc             "Mine Blocks Faster"
        //     5: getstatic       me/leon/trinityplus/hacks/Category.PLAYER:Lme/leon/trinityplus/hacks/Category;
        //     8: invokespecial   me/leon/trinityplus/hacks/Module.<init>:(Ljava/lang/String;Ljava/lang/String;Lme/leon/trinityplus/hacks/Category;)V
        //    11: aload_0         /* this */
        //    12: iconst_0       
        //    13: putfield        me/leon/trinityplus/hacks/player/SpeedMine.started:Z
        //    16: aload_0         /* this */
        //    17: aconst_null    
        //    18: putfield        me/leon/trinityplus/hacks/player/SpeedMine.curPos:Lnet/minecraft/util/math/BlockPos;
        //    21: aload_0         /* this */
        //    22: new             Lme/zero/alpine/fork/listener/Listener;
        //    25: dup            
        //    26: aload_0         /* this */
        //    27: invokedynamic   BootstrapMethod #0, invoke:(Lme/leon/trinityplus/hacks/player/SpeedMine;)Lme/zero/alpine/fork/listener/EventHook;
        //    32: iconst_0       
        //    33: anewarray       Ljava/util/function/Predicate;
        //    36: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //    39: putfield        me/leon/trinityplus/hacks/player/SpeedMine.modeChangeListener:Lme/zero/alpine/fork/listener/Listener;
        //    42: aload_0         /* this */
        //    43: new             Lme/zero/alpine/fork/listener/Listener;
        //    46: dup            
        //    47: aload_0         /* this */
        //    48: invokedynamic   BootstrapMethod #1, invoke:(Lme/leon/trinityplus/hacks/player/SpeedMine;)Lme/zero/alpine/fork/listener/EventHook;
        //    53: iconst_0       
        //    54: anewarray       Ljava/util/function/Predicate;
        //    57: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //    60: putfield        me/leon/trinityplus/hacks/player/SpeedMine.destroyBlockListener:Lme/zero/alpine/fork/listener/Listener;
        //    63: aload_0         /* this */
        //    64: new             Lme/zero/alpine/fork/listener/Listener;
        //    67: dup            
        //    68: aload_0         /* this */
        //    69: invokedynamic   BootstrapMethod #2, invoke:(Lme/leon/trinityplus/hacks/player/SpeedMine;)Lme/zero/alpine/fork/listener/EventHook;
        //    74: iconst_0       
        //    75: anewarray       Ljava/util/function/Predicate;
        //    78: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //    81: putfield        me/leon/trinityplus/hacks/player/SpeedMine.damageBlockListener:Lme/zero/alpine/fork/listener/Listener;
        //    84: return         
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
        //     at com.strobel.decompiler.Decompiler.decompile(Decompiler.java:70)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompile(Deobfuscator3000.java:536)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.decompileAndDeobfuscate(Deobfuscator3000.java:550)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.processMod(Deobfuscator3000.java:508)
        //     at org.ugp.mc.deobfuscator.Deobfuscator3000.lambda$18(Deobfuscator3000.java:328)
        //     at java.lang.Thread.run(Thread.java:748)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void onEnable() {
        this.addEffect();
    }
    
    public void onDisable() {
        this.removeEffect();
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.curPos == null) {
            return;
        }
        if (!SpeedMine.render.getValue()) {
            return;
        }
        if (BlockUtils.getBlockResistance(this.curPos) == BlockUtils.BlockResistance.Blank || (SpeedMine.mode.getValue().equalsIgnoreCase("Normal") && !SpeedMine.mc.playerController.isHittingBlock)) {
            this.curPos = null;
            return;
        }
        final AxisAlignedBB pos = new AxisAlignedBB(this.curPos);
        if (SpeedMine.renderMode.getValue().equalsIgnoreCase("Outline")) {
            Tessellator.drawBBOutline(pos, (float)SpeedMine.width.getValue(), SpeedMine.mainColor.getValue());
        }
        if (SpeedMine.renderMode.getValue().equalsIgnoreCase("Fill")) {
            Tessellator.drawBBFill(pos, SpeedMine.mainColor.getValue());
        }
        if (SpeedMine.renderMode.getValue().equalsIgnoreCase("Claw")) {
            Tessellator.drawBBClaw(pos, (float)SpeedMine.width.getValue(), (float)SpeedMine.length.getValue(), SpeedMine.mainColor.getValue());
        }
        if (SpeedMine.renderMode.getValue().equalsIgnoreCase("Slab")) {
            Tessellator.drawBBSlab(pos, (float)SpeedMine.length.getValue(), SpeedMine.mainColor.getValue());
        }
        if (SpeedMine.renderMode.getValue().equalsIgnoreCase("Both")) {
            Tessellator.drawBBFill(pos, SpeedMine.mainColor.getValue());
            Tessellator.drawBBOutline(pos, (float)SpeedMine.width.getValue(), SpeedMine.secondaryColor.getValue());
        }
        if (SpeedMine.renderDamage.getValue()) {
            final DecimalFormat format = new DecimalFormat("##");
            final String string = format.format(SpeedMine.mc.playerController.curBlockDamageMP * 100.0f);
            Tessellator.drawTextFromBlockWithBackground(this.curPos, string, SpeedMine.mainColor.getValue().getRGB(), 1.0f, SpeedMine.secondaryColor.getValue(), true, (float)SpeedMine.width.getValue());
        }
    }
    
    private void removeEffect() {
        if (SpeedMine.mc.player.isPotionActive(MobEffects.HASTE)) {
            SpeedMine.mc.player.removeActivePotionEffect(MobEffects.HASTE);
        }
    }
    
    private void addEffect() {
        if (SpeedMine.haste.getValue().equalsIgnoreCase("I")) {
            SpeedMine.mc.player.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.HASTE, 10000, 0, false, false)));
        }
        if (SpeedMine.haste.getValue().equalsIgnoreCase("II")) {
            SpeedMine.mc.player.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.HASTE, 10000, 1, false, false)));
        }
        if (SpeedMine.haste.getValue().equalsIgnoreCase("Max")) {
            SpeedMine.mc.player.addPotionEffect(new PotionEffect(new PotionEffect(MobEffects.HASTE, 10000, 100, false, false)));
        }
    }
    
    static {
        SpeedMine.mode = new ModeSetting("Mode", "Packet", new String[] { "Packet", "Normal", "Instant" });
        SpeedMine.haste = new ModeSetting("Haste", "None", new String[] { "None", "I", "II", "Max" });
        SpeedMine.startPercent = new SliderSetting("Start Percent", 0.0, 0.0, 100.0, true);
        SpeedMine.endPercent = new SliderSetting("End Percent", 0.0, 70.0, 100.0, true);
        SpeedMine.range = new SliderSetting("Range", 0.0, 10.0, 20.0, true);
        SpeedMine.render = new BooleanSetting("Render", true, true);
        SpeedMine.renderMode = new ModeSetting("Mode", SpeedMine.render, "Both", new String[] { "Both", "Outline", "Claw", "Fill" });
        SpeedMine.width = new SliderSetting("Width", SpeedMine.render, 0.0, 0.5, 5.0, false);
        SpeedMine.length = new SliderSetting("Length", SpeedMine.render, 0.0, 0.2, 1.0, false);
        SpeedMine.mainColor = new ColorSetting("Main", SpeedMine.render, 119, 0, 255, 150, false);
        SpeedMine.secondaryColor = new ColorSetting("Secondary", SpeedMine.render, 119, 0, 255, 255, false);
        SpeedMine.renderDamage = new BooleanSetting("Damage", SpeedMine.render, false);
    }
}
