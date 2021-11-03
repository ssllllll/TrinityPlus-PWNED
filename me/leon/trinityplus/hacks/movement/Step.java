//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.movement;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.events.settings.*;
import me.zero.alpine.fork.listener.*;
import me.leon.trinityplus.hacks.*;
import java.util.function.*;
import me.leon.trinityplus.utils.entity.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Step extends Module
{
    public static ModeSetting mode;
    public static SliderSetting height;
    public static ModeSetting disable;
    public static BooleanSetting useTimer;
    public static SliderSetting timerTicks;
    public static BooleanSetting entityStep;
    public static BooleanSetting pause;
    public static BooleanSetting sneakPause;
    public static BooleanSetting waterPause;
    @EventHandler
    private final Listener<EventModeChange> changeListener;
    double[] forwardStep;
    double originalHeight;
    private int ticks;
    
    public Step() {
        super("Step", "Increases player step height", Category.MOVEMENT);
        this.changeListener = new Listener<EventModeChange>(event -> {
            if (event.getSet() == Step.mode && !Step.mode.getValue().equalsIgnoreCase("Vanilla")) {
                Step.mc.player.stepHeight = 0.5f;
            }
            return;
        }, (Predicate<EventModeChange>[])new Predicate[0]);
        this.ticks = 0;
    }
    
    public void onEnable() {
        if (this.nullCheck()) {
            return;
        }
        this.originalHeight = Step.mc.player.posY;
        if (Step.mode.getValue().equalsIgnoreCase("Vanilla")) {
            Step.mc.player.stepHeight = (float)Step.height.getValue();
        }
    }
    
    public void onDisable() {
        Step.mc.player.stepHeight = 0.5f;
    }
    
    public void onUpdate() {
        if (this.nullCheck()) {
            return;
        }
        if (!Step.mc.player.collidedHorizontally) {
            return;
        }
        if (Step.mc.player.isOnLadder() || Step.mc.player.movementInput.jump) {
            return;
        }
        if ((Step.mc.player.isInWater() || Step.mc.player.isInLava()) && Step.waterPause.getValue()) {
            return;
        }
        if (Step.useTimer.getValue()) {
            Step.mc.timer.tickLength = (float)(50.0 / Step.timerTicks.getValue());
        }
        if (Step.entityStep.getValue() && Step.mc.player.isRiding()) {
            Step.mc.player.ridingEntity.stepHeight = (float)Step.height.getValue();
        }
        if (Step.mc.player.isSneaking() && Step.sneakPause.getValue()) {
            return;
        }
        this.forwardStep = MotionUtils.getMotion(0.1f);
        if (this.getStepHeight().equals(StepHeight.Unsafe)) {
            if (Step.disable.getValue().equalsIgnoreCase("Unsafe")) {
                this.setEnabled(false);
            }
            return;
        }
        final String value = Step.mode.getValue();
        switch (value) {
            case "Teleport": {
                this.stepTeleport();
                break;
            }
            case "Spider": {
                this.stepSpider();
                break;
            }
            case "Vanilla": {
                this.stepVanilla();
                break;
            }
            case "Normal": {
                this.stepNormal();
                break;
            }
        }
        if (Step.mc.player.posY > this.originalHeight + this.getStepHeight().height && Step.disable.getValue().equalsIgnoreCase("Completion")) {
            this.setEnabled(false);
        }
    }
    
    public void stepTeleport() {
        this.updateStepPackets(this.getStepHeight().stepArray);
        Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + this.getStepHeight().height, Step.mc.player.posZ);
    }
    
    public void stepSpider() {
        this.updateStepPackets(this.getStepHeight().stepArray);
        Step.mc.player.motionY = 0.2;
        Step.mc.player.fallDistance = 0.0f;
    }
    
    public void stepNormal() {
        if (Step.useTimer.getValue()) {
            if (this.ticks == 0) {
                EntityUtils.resetTimer();
            }
            else {
                --this.ticks;
            }
        }
        final double[] dir = MotionUtils.forward(0.1);
        boolean twofive = false;
        boolean two = false;
        boolean onefive = false;
        boolean one = false;
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.4, dir[1])).isEmpty()) {
            twofive = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 2.1, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.9, dir[1])).isEmpty()) {
            two = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.6, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.4, dir[1])).isEmpty()) {
            onefive = true;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 1.0, dir[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(dir[0], 0.6, dir[1])).isEmpty()) {
            one = true;
        }
        if (Step.mc.player.collidedHorizontally && (Step.mc.player.moveForward != 0.0f || Step.mc.player.moveStrafing != 0.0f) && Step.mc.player.onGround) {
            if (one && Step.height.getValue() >= 1.0) {
                final double[] array;
                final double[] oneOffset = array = new double[] { 0.42, 0.753 };
                for (final double v : array) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + v, Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (Step.useTimer.getValue()) {
                    EntityUtils.setTimer(0.6f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.0, Step.mc.player.posZ);
                this.ticks = 1;
            }
            if (onefive && Step.height.getValue() >= 1.5) {
                final double[] oneFiveOffset = { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 };
                for (int i = 0; i < oneFiveOffset.length; ++i) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + oneFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (Step.useTimer.getValue()) {
                    EntityUtils.setTimer(0.35f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 1.5, Step.mc.player.posZ);
                this.ticks = 1;
            }
            if (two && Step.height.getValue() >= 2.0) {
                final double[] twoOffset = { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 };
                for (int i = 0; i < twoOffset.length; ++i) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (Step.useTimer.getValue()) {
                    EntityUtils.setTimer(0.25f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.0, Step.mc.player.posZ);
                this.ticks = 2;
            }
            if (twofive && Step.height.getValue() >= 2.5) {
                final double[] twoFiveOffset = { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 };
                for (int i = 0; i < twoFiveOffset.length; ++i) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + twoFiveOffset[i], Step.mc.player.posZ, Step.mc.player.onGround));
                }
                if (Step.useTimer.getValue()) {
                    EntityUtils.setTimer(0.15f);
                }
                Step.mc.player.setPosition(Step.mc.player.posX, Step.mc.player.posY + 2.5, Step.mc.player.posZ);
                this.ticks = 2;
            }
        }
    }
    
    public void stepVanilla() {
        Step.mc.player.stepHeight = (float)Step.height.getValue();
    }
    
    public void updateStepPackets(final double[] stepArray) {
        for (final double v : stepArray) {
            Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Step.mc.player.posX, Step.mc.player.posY + v, Step.mc.player.posZ, Step.mc.player.onGround));
        }
    }
    
    public StepHeight getStepHeight() {
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.0, this.forwardStep[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 0.6, this.forwardStep[1])).isEmpty()) {
            return StepHeight.One;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.6, this.forwardStep[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.4, this.forwardStep[1])).isEmpty()) {
            return StepHeight.OneHalf;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.1, this.forwardStep[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 1.9, this.forwardStep[1])).isEmpty()) {
            return StepHeight.Two;
        }
        if (Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.6, this.forwardStep[1])).isEmpty() && !Step.mc.world.getCollisionBoxes((Entity)Step.mc.player, Step.mc.player.getEntityBoundingBox().offset(this.forwardStep[0], 2.4, this.forwardStep[1])).isEmpty()) {
            return StepHeight.TwoHalf;
        }
        return StepHeight.Unsafe;
    }
    
    static {
        Step.mode = new ModeSetting("Mode", "Vanilla", new String[] { "Vanilla", "Normal", "Teleport", "Spider" });
        Step.height = new SliderSetting("Height", 0.0, 2.0, 2.5, true);
        Step.disable = new ModeSetting("Disable", "Never", new String[] { "Never", "Completion", "Unsafe" });
        Step.useTimer = new BooleanSetting("Use Timer", false, true);
        Step.timerTicks = new SliderSetting("Timer Speed", Step.useTimer, 0.1, 0.5, 2.0, false);
        Step.entityStep = new BooleanSetting("Entity Step", false);
        Step.pause = new BooleanSetting("Pause", true, false);
        Step.sneakPause = new BooleanSetting("When Sneaking", Step.pause, false);
        Step.waterPause = new BooleanSetting("When in Liquid", Step.pause, true);
    }
    
    public enum StepHeight
    {
        One(1.0, new double[] { 0.42, 0.753 }), 
        OneHalf(1.5, new double[] { 0.42, 0.75, 1.0, 1.16, 1.23, 1.2 }), 
        Two(2.0, new double[] { 0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43 }), 
        TwoHalf(2.5, new double[] { 0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907 }), 
        Unsafe(3.0, new double[] { 0.0 });
        
        double[] stepArray;
        double height;
        
        private StepHeight(final double height, final double[] stepArray) {
            this.height = height;
            this.stepArray = stepArray;
        }
    }
}
