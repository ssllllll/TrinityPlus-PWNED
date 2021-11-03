//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world;

public enum Priority
{
    Highest(200), 
    High(100), 
    Normal(0), 
    Low(-100), 
    Lowest(-200);
    
    int priority;
    
    private Priority(final int priority) {
        this.priority = priority;
    }
    
    public int getPriority() {
        return this.priority;
    }
}
