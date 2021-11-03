//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;

public class ModifyArgsInjector extends InvokeInjector
{
    private final ArgsClassGenerator argsClassGenerator;
    
    public ModifyArgsInjector(final InjectionInfo info) {
        super(info, "@ModifyArgs");
        this.argsClassGenerator = info.getContext().getExtensions().getGenerator((Class<? extends IClassGenerator>)ArgsClassGenerator.class);
    }
    
    protected void checkTarget(final Target target) {
        this.checkTargetModifiers(target, false);
    }
    
    protected void inject(final Target target, final InjectionNodes.InjectionNode node) {
        this.checkTargetForNode(target, node);
        super.inject(target, node);
    }
    
    protected void injectAtInvoke(final Target target, final InjectionNodes.InjectionNode node) {
        final MethodInsnNode targetMethod = (MethodInsnNode)node.getCurrentTarget();
        final Type[] args = Type.getArgumentTypes(targetMethod.desc);
        if (args.length == 0) {
            throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + targetMethod.name + targetMethod.desc + " with no arguments!");
        }
        final String clArgs = this.argsClassGenerator.getClassRef(targetMethod.desc);
        final boolean withArgs = this.verifyTarget(target);
        final InsnList insns = new InsnList();
        target.addToStack(1);
        this.packArgs(insns, clArgs, targetMethod);
        if (withArgs) {
            target.addToStack(Bytecode.getArgsSize(target.arguments));
            Bytecode.loadArgs(target.arguments, insns, target.isStatic ? 0 : 1);
        }
        this.invokeHandler(insns);
        this.unpackArgs(insns, clArgs, args);
        target.insns.insertBefore((AbstractInsnNode)targetMethod, insns);
    }
    
    private boolean verifyTarget(final Target target) {
        final String shortDesc = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
        if (this.methodNode.desc.equals(shortDesc)) {
            return false;
        }
        final String targetDesc = Bytecode.changeDescriptorReturnType(target.method.desc, "V");
        final String longDesc = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, targetDesc.substring(1));
        if (this.methodNode.desc.equals(longDesc)) {
            return true;
        }
        throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + shortDesc + " or " + longDesc);
    }
    
    private void packArgs(final InsnList insns, final String clArgs, final MethodInsnNode targetMethod) {
        final String factoryDesc = Bytecode.changeDescriptorReturnType(targetMethod.desc, "L" + clArgs + ";");
        insns.add((AbstractInsnNode)new MethodInsnNode(184, clArgs, "of", factoryDesc, false));
        insns.add((AbstractInsnNode)new InsnNode(89));
        if (!this.isStatic) {
            insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
            insns.add((AbstractInsnNode)new InsnNode(95));
        }
    }
    
    private void unpackArgs(final InsnList insns, final String clArgs, final Type[] args) {
        for (int i = 0; i < args.length; ++i) {
            if (i < args.length - 1) {
                insns.add((AbstractInsnNode)new InsnNode(89));
            }
            insns.add((AbstractInsnNode)new MethodInsnNode(182, clArgs, "$" + i, "()" + args[i].getDescriptor(), false));
            if (i < args.length - 1) {
                if (args[i].getSize() == 1) {
                    insns.add((AbstractInsnNode)new InsnNode(95));
                }
                else {
                    insns.add((AbstractInsnNode)new InsnNode(93));
                    insns.add((AbstractInsnNode)new InsnNode(88));
                }
            }
        }
    }
}
