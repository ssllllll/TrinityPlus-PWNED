//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper
{
    boolean isDefault();
    
    String getResourceName();
    
    String getStatus();
    
    String getContext();
    
    void setContext(final String p0);
    
    String remap(final String p0, final String p1);
    
    String remapWithContext(final String p0, final String p1, final String p2);
}
