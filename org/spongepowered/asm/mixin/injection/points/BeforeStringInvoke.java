//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.struct.*;

@InjectionPoint.AtCode("INVOKE_STRING")
public class BeforeStringInvoke extends BeforeInvoke
{
    private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
    private final String ldcValue;
    private boolean foundLdc;
    
    public BeforeStringInvoke(final InjectionPointData data) {
        super(data);
        this.ldcValue = data.get("ldc", null);
        if (this.ldcValue == null) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
        }
        if (!"(Ljava/lang/String;)V".equals(this.target.desc)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires target method with with signature " + "(Ljava/lang/String;)V");
        }
    }
    
    public boolean find(final String desc, final InsnList insns, final Collection<AbstractInsnNode> nodes) {
        this.foundLdc = false;
        return super.find(desc, insns, (Collection)nodes);
    }
    
    protected void inspectInsn(final String desc, final InsnList insns, final AbstractInsnNode insn) {
        if (insn instanceof LdcInsnNode) {
            final LdcInsnNode node = (LdcInsnNode)insn;
            if (node.cst instanceof String && this.ldcValue.equals(node.cst)) {
                this.log("{} > found a matching LDC with value {}", new Object[] { this.className, node.cst });
                this.foundLdc = true;
                return;
            }
        }
        this.foundLdc = false;
    }
    
    protected boolean matchesInsn(final MemberInfo nodeInfo, final int ordinal) {
        this.log("{} > > found LDC \"{}\" = {}", new Object[] { this.className, this.ldcValue, this.foundLdc });
        return this.foundLdc && super.matchesInsn(nodeInfo, ordinal);
    }
}
