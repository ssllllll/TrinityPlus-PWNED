//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.button;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.hacks.client.*;
import java.awt.*;
import me.leon.trinityplus.utils.rendering.*;

public abstract class IButton implements IComponent
{
    protected final FrameComponent parent;
    protected int offset;
    protected boolean open;
    
    protected IButton(final FrameComponent parent, final int offset) {
        this.parent = parent;
        this.offset = offset;
        this.open = false;
    }
    
    protected void drawBack(final Point p, final String name, final boolean enabled) {
        final float realY = this.parent.getY() + this.offset;
        this.drawRect(this.parent.getX(), realY, this.parent.getX() + this.getWidth(), realY + 14.0f, this.getColor(p, enabled));
        FontUtil.drawString(name, this.parent.getX() + this.xOffset(), realY + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.nameColorButton.getValue());
    }
    
    protected Color getColor(final Point point, final boolean enabled) {
        if (this.onButton(point)) {
            if (enabled) {
                return ClickGUI.enabledColor.getValue().brighter();
            }
            return ClickGUI.disabledColor.getValue().brighter();
        }
        else {
            if (enabled) {
                return ClickGUI.enabledColor.getValue();
            }
            return ClickGUI.disabledColor.getValue();
        }
    }
    
    protected boolean onButton(final Point point) {
        return GuiUtils.onButton(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.getWidth(), this.parent.getY() + this.offset + 14.0f, point);
    }
    
    @Override
    public FrameComponent parent() {
        return this.parent;
    }
    
    @Override
    public float xOffset() {
        return 3.0f;
    }
    
    public FrameComponent getParent() {
        return this.parent;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public void setOffset(final int offset) {
        this.offset = offset;
    }
}
