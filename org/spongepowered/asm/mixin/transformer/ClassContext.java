//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.struct.*;

abstract class ClassContext
{
    private final Set<ClassInfo.Method> upgradedMethods;
    
    ClassContext() {
        this.upgradedMethods = new HashSet<ClassInfo.Method>();
    }
    
    abstract String getClassRef();
    
    abstract ClassNode getClassNode();
    
    abstract ClassInfo getClassInfo();
    
    void addUpgradedMethod(final MethodNode method) {
        final ClassInfo.Method md = this.getClassInfo().findMethod(method);
        if (md == null) {
            throw new IllegalStateException("Meta method for " + method.name + " not located in " + this);
        }
        this.upgradedMethods.add(md);
    }
    
    protected void upgradeMethods() {
        for (final MethodNode method : this.getClassNode().methods) {
            this.upgradeMethod(method);
        }
    }
    
    private void upgradeMethod(final MethodNode method) {
        for (final AbstractInsnNode insn : method.instructions) {
            if (!(insn instanceof MethodInsnNode)) {
                continue;
            }
            final MemberRef methodRef = (MemberRef)new MemberRef.Method((MethodInsnNode)insn);
            if (!methodRef.getOwner().equals(this.getClassRef())) {
                continue;
            }
            final ClassInfo.Method md = this.getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
            this.upgradeMethodRef(method, methodRef, md);
        }
    }
    
    protected void upgradeMethodRef(final MethodNode containingMethod, final MemberRef methodRef, final ClassInfo.Method method) {
        if (methodRef.getOpcode() != 183) {
            return;
        }
        if (this.upgradedMethods.contains(method)) {
            methodRef.setOpcode(182);
        }
    }
}
