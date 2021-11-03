//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.service;

import java.net.*;

public interface IClassProvider
{
    URL[] getClassPath();
    
    Class<?> findClass(final String p0) throws ClassNotFoundException;
    
    Class<?> findClass(final String p0, final boolean p1) throws ClassNotFoundException;
    
    Class<?> findAgentClass(final String p0, final boolean p1) throws ClassNotFoundException;
}
