//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.*;

public class MixinClassWriter extends ClassWriter
{
    public MixinClassWriter(final int flags) {
        super(flags);
    }
    
    public MixinClassWriter(final ClassReader classReader, final int flags) {
        super(classReader, flags);
    }
    
    protected String getCommonSuperClass(final String type1, final String type2) {
        return ClassInfo.getCommonSuperClass(type1, type2).getName();
    }
}
