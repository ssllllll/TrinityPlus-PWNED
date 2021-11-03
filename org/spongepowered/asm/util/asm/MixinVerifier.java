//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.tree.analysis.*;
import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.*;

public class MixinVerifier extends SimpleVerifier
{
    private Type currentClass;
    private Type currentSuperClass;
    private List<Type> currentClassInterfaces;
    private boolean isInterface;
    
    public MixinVerifier(final Type currentClass, final Type currentSuperClass, final List<Type> currentClassInterfaces, final boolean isInterface) {
        super(currentClass, currentSuperClass, (List)currentClassInterfaces, isInterface);
        this.currentClass = currentClass;
        this.currentSuperClass = currentSuperClass;
        this.currentClassInterfaces = currentClassInterfaces;
        this.isInterface = isInterface;
    }
    
    protected boolean isInterface(final Type type) {
        if (this.currentClass != null && type.equals((Object)this.currentClass)) {
            return this.isInterface;
        }
        return ClassInfo.forType(type).isInterface();
    }
    
    protected Type getSuperClass(final Type type) {
        if (this.currentClass != null && type.equals((Object)this.currentClass)) {
            return this.currentSuperClass;
        }
        final ClassInfo c = ClassInfo.forType(type).getSuperClass();
        return (c == null) ? null : Type.getType("L" + c.getName() + ";");
    }
    
    protected boolean isAssignableFrom(final Type type, final Type other) {
        if (type.equals((Object)other)) {
            return true;
        }
        if (this.currentClass != null && type.equals((Object)this.currentClass)) {
            if (this.getSuperClass(other) == null) {
                return false;
            }
            if (this.isInterface) {
                return other.getSort() == 10 || other.getSort() == 9;
            }
            return this.isAssignableFrom(type, this.getSuperClass(other));
        }
        else if (this.currentClass != null && other.equals((Object)this.currentClass)) {
            if (this.isAssignableFrom(type, this.currentSuperClass)) {
                return true;
            }
            if (this.currentClassInterfaces != null) {
                for (int i = 0; i < this.currentClassInterfaces.size(); ++i) {
                    final Type v = this.currentClassInterfaces.get(i);
                    if (this.isAssignableFrom(type, v)) {
                        return true;
                    }
                }
            }
            return false;
        }
        else {
            ClassInfo typeInfo = ClassInfo.forType(type);
            if (typeInfo == null) {
                return false;
            }
            if (typeInfo.isInterface()) {
                typeInfo = ClassInfo.forName("java/lang/Object");
            }
            return ClassInfo.forType(other).hasSuperClass(typeInfo);
        }
    }
}
