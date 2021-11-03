//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidInjectionException extends InvalidMixinException
{
    private static final long serialVersionUID = 2L;
    private final InjectionInfo info;
    
    public InvalidInjectionException(final IMixinContext context, final String message) {
        super(context, message);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final String message) {
        super(info.getContext(), message);
        this.info = info;
    }
    
    public InvalidInjectionException(final IMixinContext context, final Throwable cause) {
        super(context, cause);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final Throwable cause) {
        super(info.getContext(), cause);
        this.info = info;
    }
    
    public InvalidInjectionException(final IMixinContext context, final String message, final Throwable cause) {
        super(context, message, cause);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo info, final String message, final Throwable cause) {
        super(info.getContext(), message, cause);
        this.info = info;
    }
    
    public InjectionInfo getInjectionInfo() {
        return this.info;
    }
}
