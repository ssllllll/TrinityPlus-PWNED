//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.combat.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.notification.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.misc.*;
import java.util.*;

public class TotempopCounter extends Module
{
    public static final ModeSetting notif;
    public static final BooleanSetting self;
    public static final BooleanSetting death;
    public static final BooleanSetting caTarget;
    @EventHandler
    private final Listener<EventTotemPop> packetRecieveListener;
    
    public TotempopCounter() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "TotemPopCounter"
        //     3: ldc             "Counts totem pops"
        //     5: getstatic       me/leon/trinityplus/hacks/Category.MISC:Lme/leon/trinityplus/hacks/Category;
        //     8: invokespecial   me/leon/trinityplus/hacks/Module.<init>:(Ljava/lang/String;Ljava/lang/String;Lme/leon/trinityplus/hacks/Category;)V
        //    11: aload_0         /* this */
        //    12: new             Lme/zero/alpine/fork/listener/Listener;
        //    15: dup            
        //    16: invokedynamic   BootstrapMethod #0, invoke:()Lme/zero/alpine/fork/listener/EventHook;
        //    21: iconst_0       
        //    22: anewarray       Ljava/util/function/Predicate;
        //    25: invokespecial   me/zero/alpine/fork/listener/Listener.<init>:(Lme/zero/alpine/fork/listener/EventHook;[Ljava/util/function/Predicate;)V
        //    28: putfield        me/leon/trinityplus/hacks/misc/TotempopCounter.packetRecieveListener:Lme/zero/alpine/fork/listener/Listener;
        //    31: return         
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
    
    @Override
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        for (final Entity e : TotempopCounter.mc.world.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                if (e == TotempopCounter.mc.player && !TotempopCounter.self.getValue()) {
                    continue;
                }
                if (e != AutoCrystal.target && TotempopCounter.caTarget.getValue()) {
                    continue;
                }
                final EntityLivingBase living = (EntityLivingBase)e;
                if (!TotempopManager.totemMap.containsKey(e) || living.getHealth() > 0.0f) {
                    continue;
                }
                if (TotempopCounter.death.getValue()) {
                    final String message = ChatFormatting.DARK_RED + living.getName() + ChatFormatting.RESET + " Died after " + ChatFormatting.LIGHT_PURPLE + TotempopManager.getPops(living) + ChatFormatting.RESET + ((TotempopManager.getPops(living) == 1) ? " totem" : " totems");
                    final String value = TotempopCounter.notif.getValue();
                    switch (value) {
                        case "Notif": {
                            NotificationManager.add(new Notification("Death", message, NotifType.INFO));
                            break;
                        }
                        case "Chat": {
                            MessageUtil.sendClientMessage(message, true);
                            break;
                        }
                        case "Both": {
                            MessageUtil.sendClientMessage(message, true);
                            NotificationManager.add(new Notification("Death", message, NotifType.INFO));
                            break;
                        }
                    }
                }
                TotempopManager.totemMap.remove(living);
            }
        }
    }
    
    static {
        notif = new ModeSetting("Notification", "Chat", new String[] { "Chat", "Notif", "Both" });
        self = new BooleanSetting("Self", false);
        death = new BooleanSetting("Death", true);
        caTarget = new BooleanSetting("Only CA Target", false);
    }
}
