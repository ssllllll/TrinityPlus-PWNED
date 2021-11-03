//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LookupSwitchInsnNode extends AbstractInsnNode
{
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;
    
    public LookupSwitchInsnNode(final LabelNode dflt, final int[] keys, final LabelNode[] labels) {
        super(171);
        this.dflt = dflt;
        this.keys = new ArrayList<Integer>((keys == null) ? 0 : keys.length);
        this.labels = new ArrayList<LabelNode>((labels == null) ? 0 : labels.length);
        if (keys != null) {
            for (int i = 0; i < keys.length; ++i) {
                this.keys.add(keys[i]);
            }
        }
        if (labels != null) {
            this.labels.addAll(Arrays.asList(labels));
        }
    }
    
    public int getType() {
        return 12;
    }
    
    public void accept(final MethodVisitor mv) {
        final int[] keys = new int[this.keys.size()];
        for (int i = 0; i < keys.length; ++i) {
            keys[i] = this.keys.get(i);
        }
        final Label[] labels = new Label[this.labels.size()];
        for (int j = 0; j < labels.length; ++j) {
            labels[j] = this.labels.get(j).getLabel();
        }
        mv.visitLookupSwitchInsn(this.dflt.getLabel(), keys, labels);
        this.acceptAnnotations(mv);
    }
    
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> labels) {
        final LookupSwitchInsnNode clone = new LookupSwitchInsnNode(clone(this.dflt, (Map)labels), null, clone((List)this.labels, (Map)labels));
        clone.keys.addAll(this.keys);
        return clone.cloneAnnotations((AbstractInsnNode)this);
    }
}
