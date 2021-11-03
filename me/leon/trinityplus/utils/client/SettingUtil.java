//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.client;

import java.util.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.lang.reflect.*;

public class SettingUtil
{
    public static ArrayList list;
    
    public static <T> void addMod(final T mod) {
        addMod(mod, SettingUtil.list);
    }
    
    public static <T> void addMod(final T mod, final ArrayList<T> arrayList) {
        try {
            for (final Field field : mod.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Setting val = (Setting)field.get(mod);
                    val.setSuperParent((Object)mod);
                    if (val.getParent() == null) {
                        val.setParent((Object)mod);
                        mod.getClass().getMethod("addSetting", Setting.class).invoke(mod, val);
                    }
                    else {
                        ((Setting)val.getParent()).addSetting(val);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        arrayList.add(mod);
    }
}
