//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import java.util.concurrent.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class FriendManager
{
    private final ConcurrentHashMap<String, UUID> friends;
    
    public FriendManager() {
        this.friends = new ConcurrentHashMap<String, UUID>();
    }
    
    public boolean isFriend(final EntityPlayer player) {
        if (this.friends.get(player.getName()) != null) {
            final UUID uuid = this.friends.get(player.getName());
            return player.getPersistentID() == uuid;
        }
        return false;
    }
    
    public void add(final String name, final UUID uuid) {
        this.friends.put(name, uuid);
    }
    
    public void remove(final String name, final UUID uuid) {
        this.friends.remove(name, uuid);
    }
}
