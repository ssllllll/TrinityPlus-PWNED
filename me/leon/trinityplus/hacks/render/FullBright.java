//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import me.leon.trinityplus.events.*;
import net.minecraft.init.*;
import java.util.function.*;
import net.minecraft.potion.*;

public class FullBright extends Module
{
    private final ModeSetting mode;
    @EventHandler
    private final Listener<EventModeChange> onModeChange;
    private float gamma;
    
    public FullBright() {
        super("FullBright", "Makes your world bright", Category.RENDER);
        this.mode = new ModeSetting("Mode", "Gamma", new String[] { "Gamma", "Potion" });
        this.onModeChange = new Listener<EventModeChange>(event -> {
            if (event.getStage() != EventStage.POST) {
                return;
            }
            else {
                if (event.getSet() == this.mode && this.mode.getValue().equalsIgnoreCase("Gamma")) {
                    FullBright.mc.player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
                }
                return;
            }
        }, (Predicate<EventModeChange>[])new Predicate[0]);
        this.gamma = 0.0f;
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        final String value = this.mode.getValue();
        switch (value) {
            case "Gamma": {
                FullBright.mc.gameSettings.gammaSetting = 999.0f;
                break;
            }
            case "Potion": {
                FullBright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 80950, 1, false, false));
                break;
            }
        }
    }
    
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        FullBright.mc.gameSettings.gammaSetting = this.gamma;
        FullBright.mc.player.removeActivePotionEffect(MobEffects.NIGHT_VISION);
    }
    
    public void onEnable() {
        this.gamma = FullBright.mc.gameSettings.gammaSetting;
    }
}
