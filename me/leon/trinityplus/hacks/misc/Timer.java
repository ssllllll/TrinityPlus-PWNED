//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import java.math.*;

public class Timer extends Module
{
    private static final SliderSetting speed;
    
    public Timer() {
        super("Timer", "Increases your client tick speed", Category.MISC);
    }
    
    @Override
    public void onDisable() {
        Timer.mc.timer.tickLength = 50.0f;
    }
    
    @Override
    public String getHudInfo() {
        return BigDecimal.valueOf(Timer.speed.getValue()).setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    @Override
    public void onUpdate() {
        Timer.mc.timer.tickLength = 50.0f / (((float)Timer.speed.getValue() == 0.0f) ? 0.1f : ((float)Timer.speed.getValue()));
    }
    
    static {
        speed = new SliderSetting("Speed", 0.0, 3.0, 20.0, false);
    }
}
