//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.struct.*;

public class InvalidInjectionPointException extends InvalidInjectionException
{
    private static final long serialVersionUID = 2L;
    
    public InvalidInjectionPointException(final IMixinContext context, final String format, final Object... args) {
        super(context, String.format(format, args));
    }
    
    public InvalidInjectionPointException(final InjectionInfo info, final String format, final Object... args) {
        super(info, String.format(format, args));
    }
    
    public InvalidInjectionPointException(final IMixinContext context, final Throwable cause, final String format, final Object... args) {
        super(context, String.format(format, args), cause);
    }
    
    public InvalidInjectionPointException(final InjectionInfo info, final Throwable cause, final String format, final Object... args) {
        super(info, String.format(format, args), cause);
    }
}
