//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.misc;

import me.leon.trinityplus.main.*;
import org.apache.commons.lang3.reflect.*;
import java.lang.reflect.*;

public class ReflectionUtils
{
    public static Object readPrivateField(final Object object, final String name, final String obfName) {
        Object ret = null;
        try {
            ret = FieldUtils.getField((Class)object.getClass(), Trinity.isObfEnv() ? name : obfName, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static boolean invokeMethod(final Object object, final String name, final String obfName, final Object... params) {
        boolean done = false;
        try {
            final Method method = object.getClass().getMethod(Trinity.isObfEnv() ? name : obfName, params.getClass());
            method.setAccessible(true);
            method.invoke(object, params);
            done = true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return done;
    }
}
