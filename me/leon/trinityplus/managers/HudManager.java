//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.managers;

import me.leon.trinityplus.hud.*;
import me.leon.trinityplus.hud.components.*;
import java.util.*;
import me.leon.trinityplus.setting.rewrite.*;
import java.lang.reflect.*;

public class HudManager
{
    public ArrayList<Component> comps;
    
    public HudManager() {
        this.comps = new ArrayList<Component>();
        this.addComp((Component)new RagDollComponent());
        this.addComp((Component)new FPSComponent());
        this.addComp((Component)new TPSComponent());
        this.addComp((Component)new ModuleListComponent());
        this.addComp((Component)new CoordComponent());
        this.addComp((Component)new NotificationComponent());
    }
    
    public Component getComponentByName(final String name) {
        for (final Component comp : this.comps) {
            if (comp.name.equalsIgnoreCase(name)) {
                return comp;
            }
        }
        return null;
    }
    
    private void addComp(final Component comp) {
        try {
            for (final Field field : comp.getClass().getDeclaredFields()) {
                if (Setting.class.isAssignableFrom(field.getType())) {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    final Setting val = (Setting)field.get(comp);
                    if (val.getParent() == null) {
                        val.setParent(comp);
                    }
                    val.setSuperParent(comp);
                    comp.addSetting(val);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.comps.add(comp);
    }
}
