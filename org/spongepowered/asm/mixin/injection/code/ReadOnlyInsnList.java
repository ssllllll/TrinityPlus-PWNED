//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

class ReadOnlyInsnList extends InsnList
{
    private InsnList insnList;
    
    public ReadOnlyInsnList(final InsnList insns) {
        this.insnList = insns;
    }
    
    void dispose() {
        this.insnList = null;
    }
    
    public final void set(final AbstractInsnNode location, final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public final void add(final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public final void add(final InsnList insns) {
        throw new UnsupportedOperationException();
    }
    
    public final void insert(final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public final void insert(final InsnList insns) {
        throw new UnsupportedOperationException();
    }
    
    public final void insert(final AbstractInsnNode location, final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public final void insert(final AbstractInsnNode location, final InsnList insns) {
        throw new UnsupportedOperationException();
    }
    
    public final void insertBefore(final AbstractInsnNode location, final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public final void insertBefore(final AbstractInsnNode location, final InsnList insns) {
        throw new UnsupportedOperationException();
    }
    
    public final void remove(final AbstractInsnNode insn) {
        throw new UnsupportedOperationException();
    }
    
    public AbstractInsnNode[] toArray() {
        return this.insnList.toArray();
    }
    
    public int size() {
        return this.insnList.size();
    }
    
    public AbstractInsnNode getFirst() {
        return this.insnList.getFirst();
    }
    
    public AbstractInsnNode getLast() {
        return this.insnList.getLast();
    }
    
    public AbstractInsnNode get(final int index) {
        return this.insnList.get(index);
    }
    
    public boolean contains(final AbstractInsnNode insn) {
        return this.insnList.contains(insn);
    }
    
    public int indexOf(final AbstractInsnNode insn) {
        return this.insnList.indexOf(insn);
    }
    
    public ListIterator<AbstractInsnNode> iterator() {
        return (ListIterator<AbstractInsnNode>)this.insnList.iterator();
    }
    
    public ListIterator<AbstractInsnNode> iterator(final int index) {
        return (ListIterator<AbstractInsnNode>)this.insnList.iterator(index);
    }
    
    public final void resetLabels() {
        this.insnList.resetLabels();
    }
}
