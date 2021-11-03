//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

@InjectionPoint.AtCode("JUMP")
public class JumpInsnPoint extends InjectionPoint
{
    private final int opCode;
    private final int ordinal;
    
    public JumpInsnPoint(final InjectionPointData data) {
        this.opCode = data.getOpcode(-1, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1);
        this.ordinal = data.getOrdinal();
    }
    
    public boolean find(final String desc, final InsnList insns, final Collection<AbstractInsnNode> nodes) {
        boolean found = false;
        int ordinal = 0;
        for (final AbstractInsnNode insn : insns) {
            if (insn instanceof JumpInsnNode && (this.opCode == -1 || insn.getOpcode() == this.opCode)) {
                if (this.ordinal == -1 || this.ordinal == ordinal) {
                    nodes.add(insn);
                    found = true;
                }
                ++ordinal;
            }
        }
        return found;
    }
}
