//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;

public final class ResolvableMappingMethod extends MappingMethod
{
    private final TypeHandle ownerHandle;
    
    public ResolvableMappingMethod(final TypeHandle owner, final String name, final String desc) {
        super(owner.getName(), name, desc);
        this.ownerHandle = owner;
    }
    
    public MappingMethod getSuper() {
        if (this.ownerHandle == null) {
            return super.getSuper();
        }
        final String name = this.getSimpleName();
        final String desc = this.getDesc();
        final String signature = TypeUtils.getJavaSignature(desc);
        final TypeHandle superClass = this.ownerHandle.getSuperclass();
        if (superClass != null && superClass.findMethod(name, signature) != null) {
            return superClass.getMappingMethod(name, desc);
        }
        for (final TypeHandle iface : this.ownerHandle.getInterfaces()) {
            if (iface.findMethod(name, signature) != null) {
                return iface.getMappingMethod(name, desc);
            }
        }
        if (superClass != null) {
            return superClass.getMappingMethod(name, desc).getSuper();
        }
        return super.getSuper();
    }
    
    public MappingMethod move(final TypeHandle newOwner) {
        return new ResolvableMappingMethod(newOwner, this.getSimpleName(), this.getDesc());
    }
    
    public MappingMethod remap(final String newName) {
        return new ResolvableMappingMethod(this.ownerHandle, newName, this.getDesc());
    }
    
    public MappingMethod transform(final String newDesc) {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), newDesc);
    }
    
    public MappingMethod copy() {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), this.getDesc());
    }
}
