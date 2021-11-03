//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.gui.setting;

import me.leon.trinityplus.gui.*;
import me.leon.trinityplus.gui.button.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.util.*;
import me.leon.trinityplus.utils.misc.*;
import me.leon.trinityplus.hacks.client.*;
import java.awt.*;
import me.leon.trinityplus.gui.frame.*;
import me.leon.trinityplus.utils.rendering.*;

public abstract class ISetting<T extends Setting> implements IComponent
{
    protected final ArrayList<ISetting<?>> subs;
    protected final IComponent parent;
    protected final IButton superParent;
    protected final T set;
    protected int offset;
    protected boolean open;
    protected long aniEnd;
    protected float p1;
    protected float p2;
    
    protected ISetting(final IComponent parent, final IButton superParent, final Setting set, final int offset) {
        this.open = false;
        this.parent = parent;
        this.superParent = superParent;
        this.set = (T)set;
        this.offset = offset;
        this.subs = new ArrayList<ISetting<?>>();
        int off = offset + 14;
        if (set != null) {
            for (final Setting s : set.getSubSettings()) {
                if (s instanceof ColorSetting) {
                    this.subs.add((ISetting<?>)new ColorComponent((IComponent)this, superParent, s, off));
                }
                if (s instanceof BooleanSetting) {
                    this.subs.add((ISetting<?>)new BooleanComponent((IComponent)this, superParent, s, off));
                }
                if (s instanceof ModeSetting) {
                    this.subs.add(new ModeComponent((IComponent)this, superParent, s, off));
                }
                if (s instanceof SliderSetting) {
                    this.subs.add(new SliderComponent((IComponent)this, superParent, s, off));
                }
                if (s instanceof KeybindSetting) {
                    this.subs.add(new KeybindComponent((IComponent)this, superParent, s, off));
                }
                if (s instanceof TextBoxSetting) {
                    this.subs.add(new TextBoxComponent((IComponent)this, superParent, s, off));
                }
                off += 14;
            }
        }
    }
    
    protected void drawBack(final Point p, final String name, final boolean enabled) {
        final float realY = this.superParent.parent().getY() + this.offset;
        final float realX = this.superParent.parent().getX();
        this.drawRect(realX, realY, realX + this.getWidth(), realY + 14.0f, this.getColor(p, enabled));
        FontUtil.drawString(name, realX + this.xOffset(), realY + (14 - FontUtil.getFontHeight()) / 2.0f, ClickGUI.settingNameColor.getValue());
    }
    
    protected void drawArrow() {
        final float progress = 1.0f - (this.aniEnd - System.currentTimeMillis()) / 500.0f;
        final float y = this.superParent.getParent().getY() + this.offset;
        final float x = this.superParent.getParent().getX() + this.getWidth();
        if (progress < 1.0f) {
            if (this.open) {
                this.p1 = y + 9.0f - progress * 4.0f;
                this.p2 = y + 5.0f + progress * 4.0f;
            }
            else {
                this.p1 = y + 5.0f + progress * 4.0f;
                this.p2 = y + 9.0f - progress * 4.0f;
            }
        }
        else if (this.open) {
            this.p1 = y + 5.0f;
            this.p2 = y + 9.0f;
        }
        else {
            this.p1 = y + 9.0f;
            this.p2 = y + 5.0f;
        }
        RenderUtils.drawLine(x - 7.0f, this.p1, x - 5.0f, this.p2, 1.0f, Color.WHITE);
        RenderUtils.drawLine(x - 5.0f, this.p2, x - 3.0f, this.p1, 1.0f, Color.WHITE);
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
    
    protected FrameComponent getFrame() {
        return this.superParent.parent();
    }
    
    protected boolean onButton(final Point point) {
        return GuiUtils.onButton(this.superParent.parent().getX(), this.superParent.parent().getY() + this.offset, this.superParent.parent().getX() + this.getWidth(), this.superParent.parent().getY() + this.offset + 14.0f, point);
    }
    
    protected void updateOffset() {
        int offset = 14 + this.offset;
        for (final ISetting<?> button : this.getSets()) {
            button.setOffset(offset);
            offset += (int)button.height();
        }
    }
    
    public void unload() {
        this.subs.forEach(IComponent::unload);
    }
    
    public IComponent parent() {
        return this.parent;
    }
    
    public String description() {
        return null;
    }
    
    public float height() {
        int h = 14;
        if (this.open && !this.getSets().isEmpty()) {
            for (final IComponent c : this.getSets()) {
                h += (int)c.height();
            }
        }
        return (float)h;
    }
    
    public float xOffset() {
        return this.parent.xOffset() + 3.0f;
    }
    
    public ArrayList<ISetting<?>> getSubs() {
        return this.subs;
    }
    
    public IComponent getParent() {
        return this.parent;
    }
    
    public IButton getSuperParent() {
        return this.superParent;
    }
    
    public T getSet() {
        return this.set;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    public boolean isOpen() {
        return this.open;
    }
    
    public void setOpen(final boolean open) {
        this.open = open;
    }
    
    protected ArrayList<ISetting<?>> getSets() {
        final ArrayList<ISetting<?>> toReturn = new ArrayList<ISetting<?>>();
        for (final ISetting<?> s : this.subs) {
            if (((Setting)s.getSet()).test()) {
                toReturn.add(s);
            }
        }
        return toReturn;
    }
}
