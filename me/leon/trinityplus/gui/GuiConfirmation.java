//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui;

import net.minecraft.client.gui.*;

public class GuiConfirmation extends GuiScreen
{
    private GuiButton b;
    
    public GuiConfirmation() {
        final ScaledResolution c = new ScaledResolution(this.mc);
        this.b = new GuiButton(100, c.getScaledWidth() / 2, c.getScaledHeight() / 2, "Test");
    }
}
