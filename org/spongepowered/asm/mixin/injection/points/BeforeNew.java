//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@InjectionPoint.AtCode("NEW")
public class BeforeNew extends InjectionPoint
{
    private final String target;
    private final String desc;
    private final int ordinal;
    
    public BeforeNew(final InjectionPointData data) {
        super(data);
        this.ordinal = data.getOrdinal();
        final String target = Strings.emptyToNull(data.get("class", data.get("target", "")).replace('.', '/'));
        final MemberInfo member = MemberInfo.parseAndValidate(target, data.getContext());
        this.target = member.toCtorType();
        this.desc = member.toCtorDesc();
    }
    
    public boolean hasDescriptor() {
        return this.desc != null;
    }
    
    public boolean find(final String desc, final InsnList insns, final Collection<AbstractInsnNode> nodes) {
        boolean found = false;
        int ordinal = 0;
        final Collection<TypeInsnNode> newNodes = new ArrayList<TypeInsnNode>();
        final Collection<AbstractInsnNode> candidates = (Collection<AbstractInsnNode>)((this.desc != null) ? newNodes : nodes);
        for (final AbstractInsnNode insn : insns) {
            if (insn instanceof TypeInsnNode && insn.getOpcode() == 187 && this.matchesOwner((TypeInsnNode)insn)) {
                if (this.ordinal == -1 || this.ordinal == ordinal) {
                    candidates.add(insn);
                    found = (this.desc == null);
                }
                ++ordinal;
            }
        }
        if (this.desc != null) {
            for (final TypeInsnNode newNode : newNodes) {
                if (this.findCtor(insns, newNode)) {
                    nodes.add((AbstractInsnNode)newNode);
                    found = true;
                }
            }
        }
        return found;
    }
    
    protected boolean findCtor(final InsnList insns, final TypeInsnNode newNode) {
        final int indexOf = insns.indexOf((AbstractInsnNode)newNode);
        final Iterator<AbstractInsnNode> iter = (Iterator<AbstractInsnNode>)insns.iterator(indexOf);
        while (iter.hasNext()) {
            final AbstractInsnNode insn = iter.next();
            if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
                final MethodInsnNode methodNode = (MethodInsnNode)insn;
                if ("<init>".equals(methodNode.name) && methodNode.owner.equals(newNode.desc) && methodNode.desc.equals(this.desc)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean matchesOwner(final TypeInsnNode insn) {
        return this.target == null || this.target.equals(insn.desc);
    }
}
