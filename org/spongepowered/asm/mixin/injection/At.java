//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
public @interface At {
    String id() default "";
    
    String value();
    
    String slice() default "";
    
    Shift shift() default Shift.NONE;
    
    int by() default 0;
    
    String[] args() default {};
    
    String target() default "";
    
    int ordinal() default -1;
    
    int opcode() default -1;
    
    boolean remap() default true;
    
    public enum Shift
    {
        NONE, 
        BEFORE, 
        AFTER, 
        BY;
    }
}
