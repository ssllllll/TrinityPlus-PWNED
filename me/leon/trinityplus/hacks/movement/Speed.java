//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.events.main.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.block.*;

public class Speed extends Module
{
    public static ModeSetting mode;
    public static BooleanSetting useTimer;
    public static SliderSetting timerTime;
    public static SliderSetting speed;
    public static SliderSetting slowSpeed;
    public static SliderSetting groundSpeed;
    public static SliderSetting jumpheight;
    public static BooleanSetting vStep;
    public static SliderSetting height;
    private static boolean slowDown;
    private static double strafeSpeed;
    @EventHandler
    private final Listener<EventModeChange> changeListener;
    @EventHandler
    private final Listener<MoveEvent> mainListener;
    
    public Speed() {
        super("Speed", "Make yourself faster", Category.MOVEMENT);
        this.changeListener = new Listener<EventModeChange>(event -> {
            if (event.getSet() == Speed.mode) {
                this.toggle();
                this.toggle();
            }
            return;
        }, (Predicate<EventModeChange>[])new Predicate[0]);
        double speedY;
        EntityPlayerSP player;
        final double n;
        ArrayList<Block> blocks;
        ArrayList<Block> blocks2;
        double strafeSpeed;
        Object o;
        final Object o2;
        final Object o3;
        this.mainListener = new Listener<MoveEvent>(event -> {
            if (!this.nullCheck()) {
                if (Speed.vStep.getValue() && !ModuleManager.getMod(Step.class).isEnabled()) {
                    Speed.mc.player.stepHeight = (float)Speed.height.getValue();
                }
                if (Speed.mode.getValue().equalsIgnoreCase("Normal")) {
                    speedY = Speed.jumpheight.getValue();
                    if (Speed.mc.player.onGround && MotionUtils.isMoving()) {
                        if (Speed.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                            speedY += (Speed.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                        }
                        player = Speed.mc.player;
                        player.motionY = n;
                        event.y = n;
                        blocks = EntityUtils.isColliding(0.0, -0.5, 0.0, (Entity)Speed.mc.player);
                        Speed.strafeSpeed = MotionUtils.getBaseMoveSpeed() * (((blocks.contains(Blocks.WATER) || blocks.contains(Blocks.FLOWING_LAVA) || blocks.contains(Blocks.FLOWING_WATER) || blocks.contains(Blocks.LAVA)) && !EntityUtils.isInLiquid()) ? 0.9 : Speed.groundSpeed.getValue());
                        Speed.slowDown = true;
                    }
                    else {
                        EntityUtils.resetTimer();
                        if (Speed.slowDown || Speed.mc.player.collidedHorizontally) {
                            blocks2 = EntityUtils.isColliding(0.0, -0.5, 0.0, (Entity)Speed.mc.player);
                            strafeSpeed = Speed.strafeSpeed;
                            if (blocks2.contains(Blocks.WATER) || blocks2.contains(Blocks.FLOWING_LAVA) || blocks2.contains(Blocks.FLOWING_WATER) || (blocks2.contains(Blocks.LAVA) && !EntityUtils.isInLiquid())) {
                                o = 0.4;
                            }
                            else {
                                Speed.strafeSpeed = MotionUtils.getBaseMoveSpeed();
                                o = o2 * o3;
                            }
                            Speed.strafeSpeed = (double)(strafeSpeed - o);
                            Speed.slowDown = false;
                        }
                        else {
                            Speed.strafeSpeed -= Speed.strafeSpeed / Speed.slowSpeed.getValue();
                        }
                    }
                    Speed.strafeSpeed = Math.max(Speed.strafeSpeed, MotionUtils.getBaseMoveSpeed());
                }
                if (Speed.useTimer.getValue()) {
                    EntityUtils.setTimer((float)Speed.timerTime.getValue());
                }
                MotionUtils.doStrafe(event, (float)(Speed.strafeSpeed * Speed.speed.getValue()));
                event.cancel();
            }
        }, (Predicate<MoveEvent>[])new Predicate[0]);
    }
    
    public String getHudInfo() {
        return Speed.mode.getValue();
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        if (Speed.vStep.getValue()) {
            Speed.mc.player.stepHeight = (float)Speed.height.getValue();
        }
    }
    
    public void onDisable() {
        if (this.nullCheck()) {
            return;
        }
        EntityUtils.setTimer(1.0f);
        if (!ModuleManager.getMod(Step.class).isEnabled()) {
            Speed.mc.player.stepHeight = 0.5f;
        }
    }
    
    static {
        Speed.mode = new ModeSetting("Mode", "Normal", new String[] { "Normal" });
        Speed.useTimer = new BooleanSetting("UseTimer", false, true);
        Speed.timerTime = new SliderSetting("Speed", Speed.useTimer, 0.0, 1.15, 2.0, false);
        Speed.speed = new SliderSetting("Speed", 0.0, 1.0, 1.5, false);
        Speed.slowSpeed = new SliderSetting("SlowSpeed", 50.0, 159.0, 300.0, true);
        Speed.groundSpeed = new SliderSetting("GroundFactor", 0.0, 1.9, 3.0, false);
        Speed.jumpheight = new SliderSetting("JumpHeight", 0.0, 0.42, 0.5, false);
        Speed.vStep = new BooleanSetting("VStep", false, true);
        Speed.height = new SliderSetting("Height", Speed.vStep, 0.0, 2.0, 3.0, false);
        Speed.slowDown = false;
    }
}
