//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.client;

import java.util.*;
import net.minecraft.util.*;
import java.io.*;
import java.net.*;
import net.minecraft.client.*;
import javax.imageio.*;
import net.minecraft.client.renderer.texture.*;

public class CapeUtil
{
    public static HashMap<UUID, ResourceLocation> getCapes(final String pastebinURL) throws Exception {
        final HashMap<UUID, ResourceLocation> outMap = new HashMap<UUID, ResourceLocation>();
        final URL url = new URL(pastebinURL);
        final URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            final String[] splitLine = line.split(" ");
            if (splitLine.length != 2) {
                throw new IllegalStateException("Incorrect cape line found");
            }
            final String s = splitLine[1];
            switch (s) {
                case "LIGHT": {
                    outMap.put(UUID.fromString(splitLine[0]), new ResourceLocation("trinity", "capes/capewhite.png"));
                    continue;
                }
                case "DARK": {
                    outMap.put(UUID.fromString(splitLine[0]), new ResourceLocation("trinity", "capes/capeblack.png"));
                    continue;
                }
                case "RAINBOW": {
                    outMap.put(UUID.fromString(splitLine[0]), new ResourceLocation("trinity", "capes/caperainbow.png"));
                    continue;
                }
                default: {
                    final URL capeURL = new URL(splitLine[1]);
                    outMap.put(UUID.fromString(splitLine[0]), downloadFromURL(capeURL));
                    continue;
                }
            }
        }
        return outMap;
    }
    
    public static ResourceLocation downloadFromURL(final URL url) throws Exception {
        final ResourceLocation r = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("capes", new DynamicTexture(ImageIO.read(url)));
        r.getClass().getDeclaredField("namespace").set(r, "trinity");
        return r;
    }
}
