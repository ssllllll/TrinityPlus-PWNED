//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.misc;

import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.hacks.*;
import net.minecraftforge.client.event.*;
import me.leon.trinityplus.main.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChatSuffix extends Module
{
    private static final TextBoxSetting text;
    
    public ChatSuffix() {
        super("Suffix", "Adds the client name to the end of your messages", Category.MISC);
    }
    
    @Override
    public void onEnable() {
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatEvent event) {
        Trinity.dispatcher.post(event);
        if (this.isEnabled()) {
            for (final String s : Arrays.asList("/", ".", "-", ",", ":", ";", "'", "+", "\\", "@")) {
                if (event.getMessage().startsWith(s)) {
                    return;
                }
            }
            event.setMessage(event.getMessage() + " \u23d0" + this.toUnicode(" " + ChatSuffix.text.getValue()));
        }
    }
    
    public String toUnicode(final String s) {
        return s.toLowerCase().replace("a", "\u1d00").replace("b", "\u0299").replace("c", "\u1d04").replace("d", "\u1d05").replace("e", "\u1d07").replace("f", "\ua730").replace("g", "\u0262").replace("h", "\u029c").replace("i", "\u026a").replace("j", "\u1d0a").replace("k", "\u1d0b").replace("l", "\u029f").replace("m", "\u1d0d").replace("n", "\u0274").replace("o", "\u1d0f").replace("p", "\u1d18").replace("q", "\u01eb").replace("r", "\u0280").replace("s", "\ua731").replace("t", "\u1d1b").replace("u", "\u1d1c").replace("v", "\u1d20").replace("w", "\u1d21").replace("x", "\u02e3").replace("y", "\u028f").replace("z", "\u1d22");
    }
    
    static {
        text = new TextBoxSetting("Suffix Text", "Trinity+");
    }
}
