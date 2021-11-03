//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.hacks.client.*;
import me.leon.trinityplus.utils.rendering.skeet.*;
import me.leon.trinityplus.utils.math.*;
import java.awt.*;
import java.util.*;
import net.minecraft.util.*;
import me.leon.trinityplus.utils.rendering.*;
import org.apache.commons.lang3.*;

public class TextBoxComponent extends ISetting<TextBoxSetting>
{
    private boolean typing;
    private long aniEnd;
    private float strOffset;
    private boolean hasmoved;
    private int movetype;
    private char deletedChar;
    
    public TextBoxComponent(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        super(parent, superParent, set, offset);
        this.typing = false;
    }
    
    public void render(final Point point) {
        final float realY = this.superParent.parent().getY() + this.offset;
        final float realX = this.superParent.parent().getX();
        this.drawRect(realX, realY, realX + this.getWidth(), realY + 28.0f, this.getColor(point, false));
        FontUtil.drawString(((TextBoxSetting)this.set).getName(), realX + this.xOffset() + (this.getWidth() - this.xOffset() - FontUtil.getStringWidth(((TextBoxSetting)this.set).getName())) / 2.0f, realY + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
        RenderUtils.scissor(new Quad(realX + this.xOffset() - 1.0f, realY, realX + this.getWidth(), realY + 29.0f));
        final String one = ((TextBoxSetting)this.set).getValue().substring(0, (int)MathUtils.clamp(0.0, ((TextBoxSetting)this.set).getValue().length(), ((TextBoxSetting)this.set).typeSpace));
        final String two = ((TextBoxSetting)this.set).getValue().substring((int)MathUtils.clamp(0.0, ((TextBoxSetting)this.set).getValue().length(), ((TextBoxSetting)this.set).typeSpace));
        this.updateStrOff();
        this.strOffset = (float)MathUtils.clamp(-1.0, 2.147483647E9, this.strOffset);
        FontUtil.drawString(one, realX + this.xOffset() - this.strOffset, realY + 14.0f + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
        final float one_ = FontUtil.getStringWidth(one);
        if (this.typing && System.currentTimeMillis() % 800L > 400L) {
            RenderUtils.drawLine(realX + this.xOffset() + one_ - this.strOffset, realY + 14.0f, realX + this.xOffset() + one_ - this.strOffset, realY + 28.0f, 1.0f, Color.WHITE);
        }
        FontUtil.drawString(two, realX + this.xOffset() - this.strOffset + one_ + 1.0f, realY + 14.0f + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
        float progress = (float)MathUtils.clamp(0.0, 1.0, (System.currentTimeMillis() - this.aniEnd) / 500.0f);
        final float half = (this.getWidth() - this.xOffset()) / 2.0f;
        if (!this.typing) {
            progress = 1.0f - progress;
        }
        if (progress != 0.0f) {
            RenderUtils.drawLine(realX + this.xOffset() + half - progress * half, realY + 27.0f, realX + this.xOffset() + half + progress * half, realY + 27.0f, 1.0f, new Color(255, 255, 255, 255));
        }
        RenderUtils.restoreScissor();
        if (this.open) {
            this.getSets().forEach(e -> e.render(point));
        }
    }
    
    public void update(final Point point) {
    }
    
    public boolean buttonClick(final int button, final Point point) {
        if (this.onButton(point)) {
            switch (button) {
                case 0: {
                    if (!this.typing) {
                        this.typing = true;
                        this.aniEnd = System.currentTimeMillis();
                    }
                    return true;
                }
                case 1: {
                    this.open = !this.open;
                    return true;
                }
            }
        }
        else if (this.typing) {
            this.typing = false;
            this.aniEnd = System.currentTimeMillis();
        }
        for (final ISetting<?> sub : this.getSets()) {
            if (sub.buttonClick(button, point)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean buttonRelease(final int button, final Point point) {
        for (final ISetting<?> sub : this.getSets()) {
            if (sub.buttonRelease(button, point)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean keyTyped(final char chr, final int code) {
        if (this.typing) {
            final int typeSpace = ((TextBoxSetting)this.set).typeSpace;
            switch (code) {
                case 14: {
                    if (typeSpace >= 1) {
                        final String one = ((TextBoxSetting)this.set).getValue().substring(0, typeSpace - 1);
                        final String two = ((TextBoxSetting)this.set).getValue().substring(typeSpace);
                        final TextBoxSetting textBoxSetting = (TextBoxSetting)this.set;
                        --textBoxSetting.typeSpace;
                        this.hasmoved = true;
                        this.movetype = 1;
                        this.deletedChar = ((TextBoxSetting)this.set).getValue().charAt(typeSpace - 1);
                        ((TextBoxSetting)this.set).setValue(one + two);
                        return true;
                    }
                    return true;
                }
                case 205: {
                    if (typeSpace < ((TextBoxSetting)this.set).getValue().length()) {
                        final TextBoxSetting textBoxSetting2 = (TextBoxSetting)this.set;
                        ++textBoxSetting2.typeSpace;
                        this.hasmoved = true;
                        this.movetype = 0;
                        return true;
                    }
                    return true;
                }
                case 203: {
                    if (typeSpace != 0) {
                        final TextBoxSetting textBoxSetting3 = (TextBoxSetting)this.set;
                        --textBoxSetting3.typeSpace;
                        this.hasmoved = true;
                        this.movetype = 0;
                        return true;
                    }
                    return true;
                }
                case 28: {
                    this.typing = false;
                    this.aniEnd = System.currentTimeMillis();
                    break;
                }
            }
            if (ChatAllowedCharacters.isAllowedCharacter(chr)) {
                final String one = ((TextBoxSetting)this.set).getValue().substring(0, typeSpace);
                final String two = ((TextBoxSetting)this.set).getValue().substring(typeSpace);
                ((TextBoxSetting)this.set).setValue(one + chr + two);
                final TextBoxSetting textBoxSetting4 = (TextBoxSetting)this.set;
                ++textBoxSetting4.typeSpace;
                this.hasmoved = true;
                this.movetype = 0;
            }
            return true;
        }
        return false;
    }
    
    public float height() {
        if (this.open) {
            int h = 28;
            for (final ISetting<?> s : this.getSets()) {
                h += (int)s.height();
            }
            return (float)h;
        }
        return 28.0f;
    }
    
    protected boolean onButton(final Point point) {
        return GuiUtils.onButton(this.superParent.parent().getX(), this.superParent.parent().getY() + this.offset, this.superParent.parent().getX() + this.getWidth(), this.superParent.parent().getY() + this.offset + 28.0f, point);
    }
    
    private void updateStrOff() {
        final String before = this.split(((TextBoxSetting)this.set).typeSpace)[0];
        final float widthBefore = FontUtil.getStringWidth(before);
        final float width = this.getWidth() - this.xOffset() - 2.0f;
        if (((TextBoxSetting)this.set).typeSpace == 0) {
            this.strOffset = 0.0f;
            return;
        }
        if (this.hasmoved) {
            if (this.movetype == 0) {
                if (widthBefore > this.strOffset + width) {
                    this.strOffset = widthBefore - width;
                }
                else if (widthBefore < this.strOffset) {
                    final StringBuilder b = new StringBuilder();
                    for (int a = before.length() - 1; a > 0 && this.strOffset - FontUtil.getStringWidth(b.toString()) > widthBefore; --a) {
                        b.append(before.charAt(a));
                    }
                    this.strOffset -= FontUtil.getStringWidth(StringUtils.reverse(b.toString()));
                }
            }
            else if (this.movetype == 1) {
                this.strOffset -= FontUtil.getStringWidth(Character.toString(this.deletedChar));
            }
            this.hasmoved = false;
            this.movetype = -1;
        }
    }
    
    private String[] split(final int c) {
        return new String[] { ((TextBoxSetting)this.set).getValue().substring(0, (int)MathUtils.clamp(0.0, ((TextBoxSetting)this.set).getValue().length(), c)), ((TextBoxSetting)this.set).getValue().substring((int)MathUtils.clamp(0.0, ((TextBoxSetting)this.set).getValue().length(), c)) };
    }
    
    public boolean isTyping() {
        return this.typing;
    }
    
    public void setTyping(final boolean typing) {
        this.typing = typing;
    }
}
