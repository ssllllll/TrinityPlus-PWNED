//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hud.components;

import me.leon.trinityplus.hud.*;
import com.mojang.realmsclient.gui.*;
import me.leon.trinityplus.managers.*;
import me.leon.trinityplus.utils.misc.*;

public class TPSComponent extends Component
{
    public TPSComponent() {
        super("TPS");
        this.visible = true;
        this.x = 0.0f;
        this.y = this.res.getScaledHeight() / 2.0f - 10.0f;
    }
    
    public void render() {
        this.drawBackground();
        FontUtil.drawString("TPS: " + ChatFormatting.WHITE + TickrateManager.getTPS(), this.x + 1.0f, this.y, this.getTextColor());
    }
    
    public float width() {
        return FontUtil.getStringWidth("TPS: " + ChatFormatting.WHITE + TickrateManager.getTPS()) + 3.0f;
    }
    
    public float height() {
        return (float)FontUtil.getFontHeight();
    }
    
    public boolean ButtonCheck(final float x, final float y, final float w, final float h, final int mX, final int mY) {
        return mX >= x && mX <= x + w && mY >= y - 1.0f && mY <= y + h;
    }
}
