//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.misc;

import me.leon.trinityplus.utils.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.util.text.*;

public class MessageUtil implements Util
{
    public static ChatFormatting reset;
    
    public static void sendClientMessage(final String message, final boolean prefix) {
        MessageUtil.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(prefix ? (ChatFormatting.DARK_PURPLE + "[Trinity+] " + MessageUtil.reset + message) : message));
    }
    
    static {
        MessageUtil.reset = ChatFormatting.RESET;
    }
}
