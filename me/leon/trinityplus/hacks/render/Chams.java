//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import io.netty.util.internal.*;
import me.leon.trinityplus.managers.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.leon.trinityplus.events.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import me.leon.trinityplus.utils.rendering.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.client.renderer.entity.*;
import java.awt.*;
import me.leon.trinityplus.setting.rewrite.*;
import net.minecraft.entity.*;

public class Chams extends Module
{
    public static ModeSetting mode;
    public static ModeSetting wallsMode;
    public static SliderSetting width;
    public static BooleanSetting cancel;
    public static ColorSetting color;
    public static ColorSetting xqzColor;
    public static BooleanSetting targeting;
    public static BooleanSetting players;
    public static BooleanSetting crystals;
    public static BooleanSetting pop;
    public static BooleanSetting extra;
    public static ModeSetting extraRender;
    public static ModeSetting popExtraMode;
    public static SliderSetting height;
    public static BooleanSetting popExtraFade;
    public static BooleanSetting popExtraFreeze;
    public static BooleanSetting syncTime;
    public static SliderSetting popExtraTime;
    public static ColorSetting popExtraColor;
    public static SliderSetting popTime;
    public static ModeSetting popMode;
    public static ModeSetting popWallsMode;
    public static SliderSetting popWidth;
    public static ModeSetting popCancel;
    public static BooleanSetting popFade;
    public static ColorSetting popColor;
    public static ColorSetting popXqzColor;
    private static Set<ExtraInfo> extraMap;
    private static Set<PopInfo> popMap;
    private static Set<PlayerRenderInfo> infoMap;
    private boolean isExtra;
    private ExtraInfo info;
    private ChamsUtil util;
    @EventHandler
    private final Listener<EventRenderEntityCrystal> renderEntityCrystalListener;
    @EventHandler
    private final Listener<EventRenderEntityModel> renderEntityModelListener;
    @EventHandler
    private final Listener<EventTotemPop> totemListener;
    
    public Chams() {
        super("Chams", "Render entities through walls", Category.RENDER);
        this.isExtra = false;
        this.renderEntityCrystalListener = new Listener<EventRenderEntityCrystal>(event -> this.chams(event, (ModelBase)((EventRenderEntityCrystal)event).modelBase, (Entity)((EventRenderEntityCrystal)event).entityIn, ((EventRenderEntityCrystal)event).limbSwing, ((EventRenderEntityCrystal)event).limbSwingAmount, ((EventRenderEntityCrystal)event).ageInTicks, ((EventRenderEntityCrystal)event).netHeadYaw, ((EventRenderEntityCrystal)event).headPitch, ((EventRenderEntityCrystal)event).scale, false, 0.0f, false), (Predicate<EventRenderEntityCrystal>[])new Predicate[0]);
        final PopInfo info;
        final float progress;
        this.renderEntityModelListener = new Listener<EventRenderEntityModel>(event -> {
            this.updatePop();
            info = this.getPop(((EventRenderEntityModel)event).entityIn);
            progress = (this.isPop(((EventRenderEntityModel)event).entityIn) ? (1.0f - (info.time + Chams.popTime.intValue() - System.currentTimeMillis()) / Chams.popTime.floatValue()) : 0.0f);
            if (Chams.popCancel.is("None", "Skin") && !this.isExtra && this.isPop(((EventRenderEntityModel)event).entityIn)) {
                this.chams(event, ((EventRenderEntityModel)event).modelBase, ((EventRenderEntityModel)event).entityIn, ((EventRenderEntityModel)event).limbSwing, ((EventRenderEntityModel)event).limbSwingAmount, ((EventRenderEntityModel)event).ageInTicks, ((EventRenderEntityModel)event).netHeadYaw, ((EventRenderEntityModel)event).headPitch, ((EventRenderEntityModel)event).scale, false, 0.0f, false);
                this.chams(event, ((EventRenderEntityModel)event).modelBase, ((EventRenderEntityModel)event).entityIn, ((EventRenderEntityModel)event).limbSwing, ((EventRenderEntityModel)event).limbSwingAmount, ((EventRenderEntityModel)event).ageInTicks, ((EventRenderEntityModel)event).netHeadYaw, ((EventRenderEntityModel)event).headPitch, ((EventRenderEntityModel)event).scale, true, progress, false);
            }
            else {
                this.chams(event, ((EventRenderEntityModel)event).modelBase, ((EventRenderEntityModel)event).entityIn, ((EventRenderEntityModel)event).limbSwing, ((EventRenderEntityModel)event).limbSwingAmount, ((EventRenderEntityModel)event).ageInTicks, ((EventRenderEntityModel)event).netHeadYaw, ((EventRenderEntityModel)event).headPitch, ((EventRenderEntityModel)event).scale, this.isPop(((EventRenderEntityModel)event).entityIn), progress, this.isExtra);
            }
            return;
        }, (Predicate<EventRenderEntityModel>[])new Predicate[0]);
        final PlayerRenderInfo info2;
        this.totemListener = new Listener<EventTotemPop>(event -> {
            info2 = this.getInfo((Entity)event.getEntity());
            if (info2 != null && Chams.extra.getValue()) {
                Chams.extraMap.add(new ExtraInfo(event.getEntity(), info2.model, info2.limbSwing, info2.limbSwingAmount, info2.ageInTicks, info2.headPitch, info2.yaw, info2.lastYaw, info2.yawOffset, info2.lastYawOffset));
            }
            if (Chams.pop.getValue()) {
                if (this.isPop((Entity)event.getEntity())) {
                    Chams.popMap.remove(this.getPop((Entity)event.getEntity()));
                }
                Chams.popMap.add(new PopInfo((Entity)event.getEntity()));
            }
            return;
        }, (Predicate<EventTotemPop>[])new Predicate[0]);
        Chams.extraMap = (Set<ExtraInfo>)new ConcurrentSet();
        Chams.popMap = (Set<PopInfo>)new ConcurrentSet();
        Chams.infoMap = (Set<PlayerRenderInfo>)new ConcurrentSet();
    }
    
    public String getHudInfo() {
        return Chams.mode.getValue();
    }
    
    public static boolean shouldRender(final Entity e) {
        if (ModuleManager.getMod("Chams").isEnabled()) {
            if (e instanceof EntityEnderCrystal) {
                return Chams.crystals.getValue();
            }
            if (e instanceof EntityPlayer) {
                return Chams.players.getValue();
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (this.util == null) {
            this.util = new ChamsUtil();
        }
        this.renderExtra();
    }
    
    public void chams(final TrinityEvent event, final ModelBase model, final Entity entity, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale, final boolean pop, final float progress, final boolean extra) {
        if (!shouldRender(entity)) {
            return;
        }
        final boolean shadows = Chams.mc.gameSettings.entityShadows;
        Chams.mc.renderManager.setRenderShadow(false);
        if (entity instanceof EntityPlayer) {
            final PlayerRenderInfo info = this.getInfo(entity);
            final EntityPlayer en = (EntityPlayer)entity;
            if (info != null) {
                Chams.infoMap.remove(info);
            }
            Chams.infoMap.add(new PlayerRenderInfo(en, (ModelPlayer)model, limbSwing, limbSwingAmount, ageInTicks, headPitch, en.rotationYawHead, en.prevRotationYawHead, en.renderYawOffset, en.prevRenderYawOffset));
        }
        final String renderMode = extra ? Chams.extraRender.getValue() : (pop ? Chams.popMode.getValue() : Chams.mode.getValue());
        final String hiddenMode = (extra || pop) ? Chams.popWallsMode.getValue() : Chams.wallsMode.getValue();
        final float renderWidth = (extra || pop) ? Chams.popWidth.floatValue() : Chams.width.floatValue();
        final String cancelModel = (extra || pop) ? Chams.popCancel.getValue() : String.valueOf(Chams.cancel.getValue());
        final float extraProgress = extra ? (1.0f - (this.info.time + Chams.popExtraTime.intValue() - System.currentTimeMillis()) / Chams.popExtraTime.floatValue()) : 0.0f;
        GL11.glPushMatrix();
        GL11.glAlphaFunc(516, 0.015686274f);
        final boolean cancel = cancelModel.equalsIgnoreCase("All") || cancelModel.equalsIgnoreCase("true") || cancelModel.equalsIgnoreCase("Skin");
        final String s = renderMode;
        switch (s) {
            case "Wireframe": {
                GL11.glPushMatrix();
                GL11.glPushAttrib(1048575);
                GL11.glPolygonMode(1032, 6913);
                GL11.glDisable(3553);
                GL11.glEnable(2848);
                GL11.glEnable(3042);
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GlStateManager.glLineWidth(renderWidth);
                if (hiddenMode.equalsIgnoreCase("XQZ")) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(false);
                    GL11.glDepthRange(0.1, 1.0);
                    GL11.glDepthFunc(516);
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, true));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glDepthFunc(513);
                    GL11.glDepthRange(0.0, 1.0);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(false);
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, false));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                }
                else {
                    GL11.glEnable(2929);
                    if (hiddenMode.equalsIgnoreCase("Normal")) {
                        GL11.glDisable(2929);
                    }
                    GL11.glDepthMask(false);
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, false));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    if (hiddenMode.equalsIgnoreCase("Normal")) {
                        GL11.glEnable(2929);
                    }
                }
                GL11.glPopAttrib();
                GL11.glPopMatrix();
                if (cancel) {
                    event.cancel();
                    break;
                }
                break;
            }
            case "Textured": {
                GL11.glPushMatrix();
                GL11.glPushAttrib(-1);
                GL11.glEnable(3008);
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                GL11.glLineWidth(1.5f);
                if (hiddenMode.equalsIgnoreCase("XQZ")) {
                    GL11.glEnable(2929);
                    GL11.glDepthMask(false);
                    GL11.glDepthRange(0.01, 1.0);
                    GL11.glDepthFunc(516);
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, true));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    GL11.glDepthFunc(513);
                    GL11.glDepthRange(0.0, 1.0);
                    GL11.glEnable(2929);
                    GL11.glDepthMask(false);
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, false));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                }
                else {
                    if (hiddenMode.equalsIgnoreCase("Normal")) {
                        GL11.glDisable(2929);
                        GL11.glDepthMask(false);
                    }
                    Tessellator.color(this.getColor(progress, extraProgress, pop, extra, false));
                    model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                    if (hiddenMode.equalsIgnoreCase("Normal")) {
                        GL11.glEnable(2929);
                        GL11.glDepthMask(true);
                    }
                }
                GL11.glDisable(3008);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glPopAttrib();
                GL11.glPopMatrix();
                if (cancel) {
                    event.cancel();
                    break;
                }
                break;
            }
            case "Normal": {
                GL11.glEnable(32823);
                GL11.glPolygonOffset(1.0f, -1100000.0f);
                model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GL11.glPolygonOffset(1.0f, 1000000.0f);
                GL11.glDisable(32823);
                event.cancel();
                break;
            }
        }
        GL11.glPopMatrix();
        Chams.mc.renderManager.setRenderShadow(shadows);
    }
    
    private void renderExtra() {
        for (final ExtraInfo extraInfo : Chams.extraMap) {
            if (extraInfo.time + Chams.popExtraTime.intValue() < System.currentTimeMillis()) {
                Chams.extraMap.remove(extraInfo);
                return;
            }
            final Entity entity = (Entity)extraInfo.entity;
            final Render<Entity> renderer = (Render<Entity>)Chams.mc.renderManager.getEntityRenderObject(entity);
            if (renderer == null) {
                continue;
            }
            final float partialTicks = Chams.mc.getRenderPartialTicks();
            final float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks;
            final Vec3d pos = new Vec3d(extraInfo.x, extraInfo.y + (1.0f - (extraInfo.time + Chams.popExtraTime.intValue() - System.currentTimeMillis()) / Chams.popExtraTime.floatValue()) * (Chams.height.floatValue() * (Chams.popExtraMode.is("Heaven") ? 1 : -1)), extraInfo.z).subtract(Chams.mc.renderManager.renderPosX, Chams.mc.renderManager.renderPosY, Chams.mc.renderManager.renderPosZ);
            this.isExtra = true;
            this.info = extraInfo;
            if (Chams.popExtraFreeze.getValue()) {
                this.util.doRender(this.info, pos.x, pos.y, pos.z, Chams.mc.getRenderPartialTicks());
            }
            else {
                renderer.doRender(entity, pos.x, pos.y, pos.z, yaw, Chams.mc.getRenderPartialTicks());
            }
            this.info = null;
            this.isExtra = false;
        }
    }
    
    private void updatePop() {
        final long time = System.currentTimeMillis();
        Chams.popMap.removeIf(e -> e.time + Chams.popTime.intValue() < time);
    }
    
    private Color getColor(final float progress, final float progress0, final boolean flag, final boolean flag2, final boolean hidden) {
        if (flag2) {
            if (hidden) {
                return Chams.popExtraFade.getValue() ? this.lower(Chams.popXqzColor.getValue(), progress0) : Chams.popXqzColor.getValue();
            }
            return Chams.popExtraFade.getValue() ? this.lower(Chams.popExtraColor.getValue(), progress0) : Chams.popExtraColor.getValue();
        }
        else {
            if (flag) {
                return Chams.popFade.getValue() ? this.lower(hidden ? Chams.popXqzColor.getValue() : Chams.popColor.getValue(), progress) : (hidden ? Chams.popXqzColor.getValue() : Chams.popColor.getValue());
            }
            return hidden ? Chams.xqzColor.getValue() : Chams.color.getValue();
        }
    }
    
    private Color lower(final Color color, final float progress) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(color.getAlpha() * (1.0f - progress)));
    }
    
    private boolean isPop(final Entity entity) {
        return this.getPop(entity) != null;
    }
    
    private boolean isExtra(final Entity entity) {
        for (final ExtraInfo info : Chams.extraMap) {
            if (info.entity == entity) {
                return true;
            }
        }
        return false;
    }
    
    public PopInfo getPop(final Entity entity) {
        for (final PopInfo info : Chams.popMap) {
            if (info.entity == entity) {
                return info;
            }
        }
        return null;
    }
    
    public PlayerRenderInfo getInfo(final Entity entity) {
        for (final PlayerRenderInfo info : Chams.infoMap) {
            if (info.entity == entity) {
                return info;
            }
        }
        return null;
    }
    
    static {
        Chams.mode = new ModeSetting("Mode", "Textured", new String[] { "Textured", "Normal", "Wireframe", "None" });
        Chams.wallsMode = new ModeSetting("Walls", "XQZ", new String[] { "XQZ", "Normal", "None" });
        Chams.width = new SliderSetting("Width", 0.1, 1.0, 4.0, false);
        Chams.cancel = new BooleanSetting("Cancel", true);
        Chams.color = new ColorSetting("Color", 119, 0, 255, 200, false);
        Chams.xqzColor = new ColorSetting("XQZ Color", 119, 0, 255, 200, false);
        Chams.targeting = new BooleanSetting("Targets", true, false);
        Chams.players = new BooleanSetting("Players", Chams.targeting, true);
        Chams.crystals = new BooleanSetting("Crystals", Chams.targeting, true);
        Chams.pop = new BooleanSetting("PopChams", true);
        Chams.extra = new BooleanSetting("Extra", Chams.pop, true);
        Chams.extraRender = new ModeSetting("Rendering", Chams.extra, "Textured", new String[] { "Textured", "Wireframe", "Normal" });
        Chams.popExtraMode = new ModeSetting("Mode", Chams.extra, "Heaven", new String[] { "Hell", "Heaven" });
        Chams.height = new SliderSetting("Height", Chams.extra, 0.0, 10.0, 50.0, true);
        Chams.popExtraFade = new BooleanSetting("Fade", Chams.extra, true);
        Chams.popExtraFreeze = new BooleanSetting("Freeze", Chams.extra, true);
        Chams.syncTime = new BooleanSetting("SyncTime", Chams.extra, false);
        Chams.popExtraTime = new SliderSetting("Time", Chams.extra, 0.0, 3000.0, 10000.0, true, s -> !Chams.syncTime.getValue());
        Chams.popExtraColor = new ColorSetting("Color", Chams.extra, 119, 0, 255, 200, false);
        Chams.popTime = new SliderSetting("Time", Chams.pop, 0.0, 3000.0, 10000.0, true);
        Chams.popMode = new ModeSetting("Mode", Chams.pop, "Textured", new String[] { "Normal", "Textured", "Wireframe" });
        Chams.popWallsMode = new ModeSetting("Walls", Chams.pop, "XQZ", new String[] { "XQZ", "Normal", "None" });
        Chams.popWidth = new SliderSetting("Width", Chams.pop, 0.1, 1.0, 4.0, false);
        Chams.popCancel = new ModeSetting("Cancel", Chams.pop, "Skin", new String[] { "Skin", "All", "None" });
        Chams.popFade = new BooleanSetting("Fade", Chams.pop, true);
        Chams.popColor = new ColorSetting("Color", Chams.pop, 119, 0, 255, 200, false);
        Chams.popXqzColor = new ColorSetting("XQZ Color", Chams.pop, 119, 0, 255, 200, false);
    }
    
    public static class ExtraInfo
    {
        public double x;
        public double y;
        public double z;
        public EntityLivingBase entity;
        public long time;
        public ModelPlayer model;
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float ageInTicks;
        public final float headPitch;
        public final float yaw;
        public final float lastYaw;
        public final float yawOffset;
        public final float lastYawOffset;
        
        public ExtraInfo(final EntityLivingBase entity, final ModelPlayer model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float headPitch, final float yaw, final float lastYaw, final float yawOffset, final float lastYawOffset) {
            this.entity = entity;
            this.model = model;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.headPitch = headPitch;
            this.yaw = yaw;
            this.lastYaw = lastYaw;
            this.yawOffset = yawOffset;
            this.lastYawOffset = lastYawOffset;
            this.time = System.currentTimeMillis();
            this.x = entity.posX;
            this.y = entity.posY;
            this.z = entity.posZ;
        }
    }
    
    private static class PopInfo
    {
        public Entity entity;
        public long time;
        
        private PopInfo(final Entity entity) {
            this.entity = entity;
            this.time = System.currentTimeMillis();
        }
    }
    
    private static class PlayerRenderInfo
    {
        public final EntityPlayer entity;
        public final ModelPlayer model;
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float ageInTicks;
        public final float headPitch;
        public final float yaw;
        public final float lastYaw;
        public final float yawOffset;
        public final float lastYawOffset;
        
        public PlayerRenderInfo(final EntityPlayer entity, final ModelPlayer model, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float headPitch, final float yaw, final float lastYaw, final float yawOffset, final float lastYawOffset) {
            this.entity = entity;
            this.model = model;
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.ageInTicks = ageInTicks;
            this.headPitch = headPitch;
            this.yaw = yaw;
            this.lastYaw = lastYaw;
            this.yawOffset = yawOffset;
            this.lastYawOffset = lastYawOffset;
        }
    }
}
