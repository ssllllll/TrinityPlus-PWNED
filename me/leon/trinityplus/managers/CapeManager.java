//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import java.util.*;
import net.minecraft.util.*;
import me.leon.trinityplus.utils.client.*;

public class CapeManager
{
    public static final String LIGHT_CAPE = "capes/capewhite.png";
    public static final String DARK_CAPE = "capes/capeblack.png";
    public static final String RAINBOW_CAPE = "capes/caperainbow.png";
    private final String capeURL = "https://pastebin.com/raw/ThVd8BHR";
    private HashMap<UUID, ResourceLocation> capes;
    
    public CapeManager() {
        this.capes = new HashMap<UUID, ResourceLocation>();
        try {
            this.capes = CapeUtil.getCapes("https://pastebin.com/raw/ThVd8BHR");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean hasCape(final UUID uuid) {
        return this.capes.containsKey(uuid);
    }
    
    public ResourceLocation getCapeForUUID(final UUID uuid) {
        return this.capes.get(uuid);
    }
}
