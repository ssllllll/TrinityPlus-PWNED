//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.render;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.main.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import java.util.function.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ViewModel extends Module
{
    public static BooleanSetting leftPosition;
    public static SliderSetting leftX;
    public static SliderSetting leftY;
    public static SliderSetting leftZ;
    public static BooleanSetting rightPosition;
    public static SliderSetting rightX;
    public static SliderSetting rightY;
    public static SliderSetting rightZ;
    public static BooleanSetting leftRotation;
    public static SliderSetting leftYaw;
    public static SliderSetting leftPitch;
    public static SliderSetting leftRoll;
    public static BooleanSetting rightRotation;
    public static SliderSetting rightYaw;
    public static SliderSetting rightPitch;
    public static SliderSetting rightRoll;
    public static BooleanSetting Animation;
    public static BooleanSetting leftAnimation;
    public static SliderSetting leftAnimationSpeed;
    public static BooleanSetting rightAnimation;
    public static SliderSetting rightAnimationSpeed;
    public static BooleanSetting customfov;
    public static SliderSetting fov;
    public static BooleanSetting cancelEating;
    @EventHandler
    private final Listener<TransformFirstPersonEvent> listener;
    @EventHandler
    private final Listener<TransformFirstPersonEvent> listener2;
    
    public ViewModel() {
        super("ViewModel", "ViewModel", Category.RENDER);
        this.listener = new Listener<TransformFirstPersonEvent>(event -> {
            if (this.nullCheck()) {
                return;
            }
            else {
                if (ViewModel.leftPosition.getValue() && event.getEnumHandSide() == EnumHandSide.LEFT) {
                    GlStateManager.translate(ViewModel.leftX.getValue(), ViewModel.leftY.getValue(), ViewModel.leftZ.getValue());
                }
                if (ViewModel.rightPosition.getValue() && event.getEnumHandSide() == EnumHandSide.RIGHT) {
                    GlStateManager.translate(ViewModel.rightX.getValue(), ViewModel.rightY.getValue(), ViewModel.rightZ.getValue());
                }
                return;
            }
        }, (Predicate<TransformFirstPersonEvent>[])new Predicate[0]);
        this.listener2 = new Listener<TransformFirstPersonEvent>(event -> {
            if (!this.nullCheck()) {
                if (ViewModel.leftRotation.getValue() && event.getEnumHandSide() == EnumHandSide.LEFT) {
                    GlStateManager.rotate((float)ViewModel.leftYaw.getValue(), 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate((float)ViewModel.leftPitch.getValue(), 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate((float)ViewModel.leftRoll.getValue(), 0.0f, 0.0f, 1.0f);
                }
                if (ViewModel.rightRotation.getValue() && event.getEnumHandSide() == EnumHandSide.RIGHT) {
                    GlStateManager.rotate((float)ViewModel.rightYaw.getValue(), 0.0f, 1.0f, 0.0f);
                    GlStateManager.rotate((float)ViewModel.rightPitch.getValue(), 1.0f, 0.0f, 0.0f);
                    GlStateManager.rotate((float)ViewModel.rightRoll.getValue(), 0.0f, 0.0f, 1.0f);
                }
            }
        }, (Predicate<TransformFirstPersonEvent>[])new Predicate[0]);
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (ViewModel.leftAnimation.getValue()) {
            ViewModel.leftYaw.setValue(ViewModel.leftYaw.getValue() + ViewModel.leftAnimationSpeed.getValue() % 360.0);
        }
        if (ViewModel.rightAnimation.getValue()) {
            ViewModel.rightYaw.setValue(ViewModel.rightYaw.getValue() + ViewModel.rightAnimationSpeed.getValue() % 360.0);
        }
    }
    
    @SubscribeEvent
    public void fov_event(final EntityViewRenderEvent.FOVModifier m) {
        if (ViewModel.customfov.getValue()) {
            m.setFOV((float)ViewModel.fov.getValue());
        }
    }
    
    static {
        ViewModel.leftPosition = new BooleanSetting("LeftPosition", true, true);
        ViewModel.leftX = new SliderSetting("LeftX", ViewModel.leftPosition, -2.0, 0.0, 2.0, false);
        ViewModel.leftY = new SliderSetting("LeftY", ViewModel.leftPosition, -2.0, 0.2, 2.0, false);
        ViewModel.leftZ = new SliderSetting("LeftZ", ViewModel.leftPosition, -2.0, -1.2, 2.0, false);
        ViewModel.rightPosition = new BooleanSetting("RightPosition", true, true);
        ViewModel.rightX = new SliderSetting("RightX", ViewModel.rightPosition, -2.0, 0.0, 2.0, false);
        ViewModel.rightY = new SliderSetting("RightY", ViewModel.rightPosition, -2.0, 0.2, 2.0, false);
        ViewModel.rightZ = new SliderSetting("RightZ", ViewModel.rightPosition, -2.0, -1.2, 2.0, false);
        ViewModel.leftRotation = new BooleanSetting("LeftRotation", true, true);
        ViewModel.leftYaw = new SliderSetting("Yaw", ViewModel.leftRotation, -100.0, 0.0, 100.0, true);
        ViewModel.leftPitch = new SliderSetting("Pitch", ViewModel.leftRotation, -100.0, 0.0, 100.0, true);
        ViewModel.leftRoll = new SliderSetting("Roll", ViewModel.leftRotation, -100.0, 0.0, 100.0, true);
        ViewModel.rightRotation = new BooleanSetting("RightRotation", true, true);
        ViewModel.rightYaw = new SliderSetting("Yaw", ViewModel.rightRotation, -100.0, 0.0, 100.0, true);
        ViewModel.rightPitch = new SliderSetting("Pitch", ViewModel.rightRotation, -100.0, 0.0, 100.0, true);
        ViewModel.rightRoll = new SliderSetting("Roll", ViewModel.rightRotation, -100.0, 0.0, 100.0, true);
        ViewModel.Animation = new BooleanSetting("Animation", true, false);
        ViewModel.leftAnimation = new BooleanSetting("LeftAnimation", ViewModel.Animation, false, true);
        ViewModel.leftAnimationSpeed = new SliderSetting("AnimationSpeed", ViewModel.leftAnimation, 1.0, 5.0, 10.0, false);
        ViewModel.rightAnimation = new BooleanSetting("RightAnimation", ViewModel.Animation, false, true);
        ViewModel.rightAnimationSpeed = new SliderSetting("AnimationSpeed", ViewModel.rightAnimation, 1.0, 5.0, 10.0, false);
        ViewModel.customfov = new BooleanSetting("CustomFOV", true, true);
        ViewModel.fov = new SliderSetting("FOV", ViewModel.customfov, 110.0, 130.0, 170.0, true);
        ViewModel.cancelEating = new BooleanSetting("CancelEating", false);
    }
}
