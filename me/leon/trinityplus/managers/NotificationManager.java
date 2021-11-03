//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import java.util.concurrent.*;
import me.leon.trinityplus.notification.*;
import me.leon.trinityplus.utils.world.*;

public class NotificationManager
{
    public static ConcurrentLinkedDeque<Notification> queue;
    
    public static void add(final Notification notification) {
        notification.timer = new Timer();
        notification.queuedTime = System.currentTimeMillis();
        NotificationManager.queue.addFirst(notification);
    }
    
    static {
        NotificationManager.queue = new ConcurrentLinkedDeque<Notification>();
    }
}
