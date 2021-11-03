//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes
{
    public SourceInterpreter() {
        super(327680);
    }
    
    protected SourceInterpreter(final int api) {
        super(api);
    }
    
    public SourceValue newValue(final Type type) {
        if (type == Type.VOID_TYPE) {
            return null;
        }
        return new SourceValue((type == null) ? 1 : type.getSize());
    }
    
    public SourceValue newOperation(final AbstractInsnNode insn) {
        int size = 0;
        switch (insn.getOpcode()) {
            case 9:
            case 10:
            case 14:
            case 15: {
                size = 2;
                break;
            }
            case 18: {
                final Object cst = ((LdcInsnNode)insn).cst;
                size = ((cst instanceof Long || cst instanceof Double) ? 2 : 1);
                break;
            }
            case 178: {
                size = Type.getType(((FieldInsnNode)insn).desc).getSize();
                break;
            }
            default: {
                size = 1;
                break;
            }
        }
        return new SourceValue(size, insn);
    }
    
    public SourceValue copyOperation(final AbstractInsnNode insn, final SourceValue value) {
        return new SourceValue(value.getSize(), insn);
    }
    
    public SourceValue unaryOperation(final AbstractInsnNode insn, final SourceValue value) {
        int size = 0;
        switch (insn.getOpcode()) {
            case 117:
            case 119:
            case 133:
            case 135:
            case 138:
            case 140:
            case 141:
            case 143: {
                size = 2;
                break;
            }
            case 180: {
                size = Type.getType(((FieldInsnNode)insn).desc).getSize();
                break;
            }
            default: {
                size = 1;
                break;
            }
        }
        return new SourceValue(size, insn);
    }
    
    public SourceValue binaryOperation(final AbstractInsnNode insn, final SourceValue value1, final SourceValue value2) {
        int size = 0;
        switch (insn.getOpcode()) {
            case 47:
            case 49:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131: {
                size = 2;
                break;
            }
            default: {
                size = 1;
                break;
            }
        }
        return new SourceValue(size, insn);
    }
    
    public SourceValue ternaryOperation(final AbstractInsnNode insn, final SourceValue value1, final SourceValue value2, final SourceValue value3) {
        return new SourceValue(1, insn);
    }
    
    public SourceValue naryOperation(final AbstractInsnNode insn, final List<? extends SourceValue> values) {
        final int opcode = insn.getOpcode();
        int size;
        if (opcode == 197) {
            size = 1;
        }
        else {
            final String desc = (opcode == 186) ? ((InvokeDynamicInsnNode)insn).desc : ((MethodInsnNode)insn).desc;
            size = Type.getReturnType(desc).getSize();
        }
        return new SourceValue(size, insn);
    }
    
    public void returnOperation(final AbstractInsnNode insn, final SourceValue value, final SourceValue expected) {
    }
    
    public SourceValue merge(final SourceValue d, final SourceValue w) {
        if (d.insns instanceof SmallSet && w.insns instanceof SmallSet) {
            final Set<AbstractInsnNode> s = (Set<AbstractInsnNode>)((SmallSet)d.insns).union((SmallSet)w.insns);
            if (s == d.insns && d.size == w.size) {
                return d;
            }
            return new SourceValue(Math.min(d.size, w.size), s);
        }
        else {
            if (d.size != w.size || !d.insns.containsAll(w.insns)) {
                final HashSet<AbstractInsnNode> s2 = new HashSet<AbstractInsnNode>();
                s2.addAll((Collection<?>)d.insns);
                s2.addAll((Collection<?>)w.insns);
                return new SourceValue(Math.min(d.size, w.size), s2);
            }
            return d;
        }
    }
}
