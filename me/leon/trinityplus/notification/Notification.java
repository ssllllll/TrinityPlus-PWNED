//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.notification;

import me.leon.trinityplus.utils.world.*;
import me.leon.trinityplus.hud.components.*;

public class Notification
{
    private String name;
    private String description;
    private NotifType type;
    private int mode;
    public Timer timer;
    public long queuedTime;
    
    public Notification() {
        this.mode = 0;
    }
    
    public Notification(final String name, final String description, final NotifType type) {
        this.mode = 0;
        this.name = name;
        this.description = description;
        this.type = type;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public NotifType getType() {
        return this.type;
    }
    
    public void setType(final NotifType type) {
        this.type = type;
    }
    
    public int getMode() {
        return this.mode;
    }
    
    public void setMode(final int mode) {
        this.mode = mode;
    }
    
    public int getDeathTime() {
        if (this.mode != 2) {
            return 0;
        }
        return (int)(System.currentTimeMillis() - (this.queuedTime + NotificationComponent.time.getValue() + NotificationComponent.fadeInTime.getValue()));
    }
    
    public int getFadeTime() {
        if (this.mode != 0) {
            return 0;
        }
        return (int)(System.currentTimeMillis() - (this.queuedTime + NotificationComponent.fadeInTime.getValue()));
    }
    
    public boolean isOverTime() {
        return this.timer.hasPassed((int)NotificationComponent.fadeInTime.getValue());
    }
    
    public boolean isOverTime1() {
        return this.timer.hasPassed((int)(NotificationComponent.fadeInTime.getValue() + NotificationComponent.time.getValue()));
    }
    
    public boolean isOverTime2() {
        return this.timer.hasPassed((int)(NotificationComponent.dieTime.getValue() + NotificationComponent.time.getValue() + NotificationComponent.fadeInTime.getValue()));
    }
}
