//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class NoRender extends Module
{
    public static BooleanSetting items;
    public static BooleanSetting hurtCam;
    public static BooleanSetting bosslist;
    public static BooleanSetting fire;
    public static BooleanSetting water;
    public static BooleanSetting signs;
    public static BooleanSetting armor;
    public static BooleanSetting weather;
    public static BooleanSetting noCluster;
    public static BooleanSetting storage;
    public static BooleanSetting shulkers;
    public static BooleanSetting chests;
    public static BooleanSetting EChests;
    
    public NoRender() {
        super("NoRender", "Prevents rendering of certain stuff", Category.RENDER);
    }
    
    @SubscribeEvent
    public void onRenderBlock(final RenderBlockOverlayEvent event) {
        if (NoRender.fire.getValue() && event.getOverlayType().equals((Object)RenderBlockOverlayEvent.OverlayType.FIRE)) {
            event.setCanceled(true);
        }
        if (NoRender.water.getValue() && event.getOverlayType().equals((Object)RenderBlockOverlayEvent.OverlayType.WATER)) {
            event.setCanceled(true);
        }
    }
    
    static {
        NoRender.items = new BooleanSetting("Items", false);
        NoRender.hurtCam = new BooleanSetting("HurtCam", true);
        NoRender.bosslist = new BooleanSetting("BossList", false);
        NoRender.fire = new BooleanSetting("Fire", true);
        NoRender.water = new BooleanSetting("Water", true);
        NoRender.signs = new BooleanSetting("Signs", false);
        NoRender.armor = new BooleanSetting("Armor", false);
        NoRender.weather = new BooleanSetting("Weather", false);
        NoRender.noCluster = new BooleanSetting("NoCluster", true);
        NoRender.storage = new BooleanSetting("Storage", true, false);
        NoRender.shulkers = new BooleanSetting("Shulkers", NoRender.storage, false);
        NoRender.chests = new BooleanSetting("Chests", NoRender.storage, false);
        NoRender.EChests = new BooleanSetting("Ender Chests", NoRender.storage, false);
    }
}
