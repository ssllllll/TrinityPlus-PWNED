//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.service;

public interface ILegacyClassTransformer extends ITransformer
{
    String getName();
    
    boolean isDelegationExcluded();
    
    byte[] transformClassBytes(final String p0, final String p1, final byte[] p2);
}
