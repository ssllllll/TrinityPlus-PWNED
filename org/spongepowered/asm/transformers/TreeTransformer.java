//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.transformers;

import org.spongepowered.asm.service.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;

public abstract class TreeTransformer implements ILegacyClassTransformer
{
    private ClassReader classReader;
    private ClassNode classNode;
    
    protected final ClassNode readClass(final byte[] basicClass) {
        return this.readClass(basicClass, true);
    }
    
    protected final ClassNode readClass(final byte[] basicClass, final boolean cacheReader) {
        final ClassReader classReader = new ClassReader(basicClass);
        if (cacheReader) {
            this.classReader = classReader;
        }
        final ClassNode classNode = new ClassNode();
        classReader.accept((ClassVisitor)classNode, 8);
        return classNode;
    }
    
    protected final byte[] writeClass(final ClassNode classNode) {
        if (this.classReader != null && this.classNode == classNode) {
            this.classNode = null;
            final ClassWriter writer = (ClassWriter)new MixinClassWriter(this.classReader, 3);
            this.classReader = null;
            classNode.accept((ClassVisitor)writer);
            return writer.toByteArray();
        }
        this.classNode = null;
        final ClassWriter writer = (ClassWriter)new MixinClassWriter(3);
        classNode.accept((ClassVisitor)writer);
        return writer.toByteArray();
    }
}
