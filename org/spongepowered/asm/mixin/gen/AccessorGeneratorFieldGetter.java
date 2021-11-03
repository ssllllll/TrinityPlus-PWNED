//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.lib.tree.*;

public class AccessorGeneratorFieldGetter extends AccessorGeneratorField
{
    public AccessorGeneratorFieldGetter(final AccessorInfo info) {
        super(info);
    }
    
    public MethodNode generate() {
        final MethodNode method = this.createMethod(this.targetType.getSize(), this.targetType.getSize());
        if (this.isInstanceField) {
            method.instructions.add((AbstractInsnNode)new VarInsnNode(25, 0));
        }
        final int opcode = this.isInstanceField ? 180 : 178;
        method.instructions.add((AbstractInsnNode)new FieldInsnNode(opcode, this.info.getClassNode().name, this.targetField.name, this.targetField.desc));
        method.instructions.add((AbstractInsnNode)new InsnNode(this.targetType.getOpcode(172)));
        return method;
    }
}
