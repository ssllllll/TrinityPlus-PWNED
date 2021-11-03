//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.utils.rendering.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Tracers extends Module
{
    public static SliderSetting width;
    public static ColorSetting color;
    public static BooleanSetting extra;
    public static BooleanSetting targeting;
    public static SliderSetting range;
    public static BooleanSetting players;
    public static BooleanSetting mobs;
    public static BooleanSetting passive;
    public static BooleanSetting neutral;
    public static BooleanSetting vehicles;
    public static BooleanSetting items;
    public static BooleanSetting crystals;
    
    public Tracers() {
        super("Tracers", "Draws lines to entities", Category.RENDER);
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        for (final Entity entity : EntityUtils.getESPTargets(Tracers.players.getValue(), Tracers.neutral.getValue(), Tracers.mobs.getValue(), Tracers.vehicles.getValue(), Tracers.passive.getValue(), Tracers.items.getValue(), Tracers.crystals.getValue(), Tracers.range.getValue())) {
            Tessellator.drawTracerLine(entity, event, Tracers.color.getValue(), (float)Tracers.width.getValue(), Tracers.extra.getValue());
        }
    }
    
    static {
        Tracers.width = new SliderSetting("Width", 0.1, 0.5, 5.0, false);
        Tracers.color = new ColorSetting("Color", 255, 0, 0, 100, false);
        Tracers.extra = new BooleanSetting("Extra", true);
        Tracers.targeting = new BooleanSetting("Filters", true, false);
        Tracers.range = new SliderSetting("Range", Tracers.targeting, 10.0, 200.0, 300.0, true);
        Tracers.players = new BooleanSetting("Players", Tracers.targeting, true);
        Tracers.mobs = new BooleanSetting("Mobs", Tracers.targeting, true);
        Tracers.passive = new BooleanSetting("Passive", Tracers.targeting, true);
        Tracers.neutral = new BooleanSetting("Neutral", Tracers.targeting, true);
        Tracers.vehicles = new BooleanSetting("Vehicles", Tracers.targeting, true);
        Tracers.items = new BooleanSetting("Items", Tracers.targeting, true);
        Tracers.crystals = new BooleanSetting("Crystals", Tracers.targeting, true);
    }
}
