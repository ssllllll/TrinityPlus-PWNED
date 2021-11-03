//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.misc;

import java.io.*;
import com.google.gson.*;

public class FileUtils
{
    public static Gson gson;
    
    public static String prettyPrint(final String s) {
        return FileUtils.gson.toJson(new JsonParser().parse(s));
    }
    
    public static void makeIfDoesntExist(final String file) {
        final File file2 = new File(file);
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }
    
    public static void makeIfDoesntExist(final File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    
    public static PrintWriter writer(final String path, final String name) {
        try {
            makeIfDoesntExist(path);
            return new PrintWriter(new FileOutputStream(new File(path.endsWith("/") ? (path + name) : (path + "/" + name))));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static PrintWriter writer(final File path, final String name) {
        try {
            makeIfDoesntExist(path);
            return new PrintWriter(new FileOutputStream(new File(path.getAbsolutePath().endsWith("/") ? (path + name) : (path + "/" + name))));
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static File[] list(final String path) {
        return new File(path).listFiles();
    }
    
    static {
        FileUtils.gson = new GsonBuilder().setPrettyPrinting().create();
    }
}
