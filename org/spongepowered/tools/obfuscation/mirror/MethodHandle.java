//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.mapping.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.*;

public class MethodHandle extends MemberHandle<MappingMethod>
{
    private final ExecutableElement element;
    private final TypeHandle ownerHandle;
    
    public MethodHandle(final TypeHandle owner, final ExecutableElement element) {
        this(owner, element, TypeUtils.getName(element), TypeUtils.getDescriptor(element));
    }
    
    public MethodHandle(final TypeHandle owner, final String name, final String desc) {
        this(owner, null, name, desc);
    }
    
    private MethodHandle(final TypeHandle owner, final ExecutableElement element, final String name, final String desc) {
        super((owner != null) ? owner.getName() : null, name, desc);
        this.element = element;
        this.ownerHandle = owner;
    }
    
    public boolean isImaginary() {
        return this.element == null;
    }
    
    public ExecutableElement getElement() {
        return this.element;
    }
    
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }
    
    public MappingMethod asMapping(final boolean includeOwner) {
        if (!includeOwner) {
            return new MappingMethod((String)null, this.getName(), this.getDesc());
        }
        if (this.ownerHandle != null) {
            return (MappingMethod)new ResolvableMappingMethod(this.ownerHandle, this.getName(), this.getDesc());
        }
        return new MappingMethod(this.getOwner(), this.getName(), this.getDesc());
    }
    
    public String toString() {
        final String owner = (this.getOwner() != null) ? ("L" + this.getOwner() + ";") : "";
        final String name = Strings.nullToEmpty(this.getName());
        final String desc = Strings.nullToEmpty(this.getDesc());
        return String.format("%s%s%s", owner, name, desc);
    }
}
