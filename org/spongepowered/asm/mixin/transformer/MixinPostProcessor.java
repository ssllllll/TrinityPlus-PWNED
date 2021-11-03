//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

class MixinPostProcessor extends TreeTransformer implements MixinConfig.IListener
{
    private final Set<String> syntheticInnerClasses;
    private final Map<String, MixinInfo> accessorMixins;
    private final Set<String> loadable;
    
    MixinPostProcessor() {
        this.syntheticInnerClasses = new HashSet<String>();
        this.accessorMixins = new HashMap<String, MixinInfo>();
        this.loadable = new HashSet<String>();
    }
    
    public void onInit(final MixinInfo mixin) {
        for (final String innerClass : mixin.getSyntheticInnerClasses()) {
            this.registerSyntheticInner(innerClass.replace('/', '.'));
        }
    }
    
    public void onPrepare(final MixinInfo mixin) {
        final String className = mixin.getClassName();
        if (mixin.isLoadable()) {
            this.registerLoadable(className);
        }
        if (mixin.isAccessor()) {
            this.registerAccessor(mixin);
        }
    }
    
    void registerSyntheticInner(final String className) {
        this.syntheticInnerClasses.add(className);
    }
    
    void registerLoadable(final String className) {
        this.loadable.add(className);
    }
    
    void registerAccessor(final MixinInfo mixin) {
        this.registerLoadable(mixin.getClassName());
        this.accessorMixins.put(mixin.getClassName(), mixin);
    }
    
    boolean canTransform(final String className) {
        return this.syntheticInnerClasses.contains(className) || this.loadable.contains(className);
    }
    
    public String getName() {
        return this.getClass().getName();
    }
    
    public boolean isDelegationExcluded() {
        return true;
    }
    
    public byte[] transformClassBytes(final String name, final String transformedName, final byte[] bytes) {
        if (this.syntheticInnerClasses.contains(transformedName)) {
            return this.processSyntheticInner(bytes);
        }
        if (this.accessorMixins.containsKey(transformedName)) {
            final MixinInfo mixin = this.accessorMixins.get(transformedName);
            return this.processAccessor(bytes, mixin);
        }
        return bytes;
    }
    
    private byte[] processSyntheticInner(final byte[] bytes) {
        final ClassReader cr = new ClassReader(bytes);
        final ClassWriter cw = new MixinClassWriter(cr, 0);
        final ClassVisitor visibilityVisitor = new ClassVisitor(327680, cw) {
            public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
                super.visit(version, access | 0x1, name, signature, superName, interfaces);
            }
            
            public FieldVisitor visitField(int access, final String name, final String desc, final String signature, final Object value) {
                if ((access & 0x6) == 0x0) {
                    access |= 0x1;
                }
                return super.visitField(access, name, desc, signature, value);
            }
            
            public MethodVisitor visitMethod(int access, final String name, final String desc, final String signature, final String[] exceptions) {
                if ((access & 0x6) == 0x0) {
                    access |= 0x1;
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        cr.accept(visibilityVisitor, 8);
        return cw.toByteArray();
    }
    
    private byte[] processAccessor(final byte[] bytes, final MixinInfo mixin) {
        if (!MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8)) {
            return bytes;
        }
        boolean transformed = false;
        final MixinInfo.MixinClassNode classNode = mixin.getClassNode(0);
        final ClassInfo targetClass = mixin.getTargets().get(0);
        for (final MixinInfo.MixinMethodNode methodNode : classNode.mixinMethods) {
            if (!Bytecode.hasFlag((MethodNode)methodNode, 8)) {
                continue;
            }
            final AnnotationNode accessor = methodNode.getVisibleAnnotation((Class)Accessor.class);
            final AnnotationNode invoker = methodNode.getVisibleAnnotation((Class)Invoker.class);
            if (accessor == null && invoker == null) {
                continue;
            }
            final ClassInfo.Method method = getAccessorMethod(mixin, (MethodNode)methodNode, targetClass);
            createProxy((MethodNode)methodNode, targetClass, method);
            transformed = true;
        }
        if (transformed) {
            return this.writeClass((ClassNode)classNode);
        }
        return bytes;
    }
    
    private static ClassInfo.Method getAccessorMethod(final MixinInfo mixin, final MethodNode methodNode, final ClassInfo targetClass) throws MixinTransformerError {
        final ClassInfo.Method method = mixin.getClassInfo().findMethod(methodNode, 10);
        if (!method.isRenamed()) {
            throw new MixinTransformerError("Unexpected state: " + mixin + " loaded before " + targetClass + " was conformed");
        }
        return method;
    }
    
    private static void createProxy(final MethodNode methodNode, final ClassInfo targetClass, final ClassInfo.Method method) {
        methodNode.instructions.clear();
        final Type[] args = Type.getArgumentTypes(methodNode.desc);
        final Type returnType = Type.getReturnType(methodNode.desc);
        Bytecode.loadArgs(args, methodNode.instructions, 0);
        methodNode.instructions.add((AbstractInsnNode)new MethodInsnNode(184, targetClass.getName(), method.getName(), methodNode.desc, false));
        methodNode.instructions.add((AbstractInsnNode)new InsnNode(returnType.getOpcode(172)));
        methodNode.maxStack = Bytecode.getFirstNonArgLocalIndex(args, false);
        methodNode.maxLocals = 0;
    }
}
