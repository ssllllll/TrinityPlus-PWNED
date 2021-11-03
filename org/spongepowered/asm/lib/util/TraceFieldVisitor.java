//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public final class TraceFieldVisitor extends FieldVisitor
{
    public final Printer p;
    
    public TraceFieldVisitor(final Printer p) {
        this(null, p);
    }
    
    public TraceFieldVisitor(final FieldVisitor fv, final Printer p) {
        super(327680, fv);
        this.p = p;
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        final Printer p = this.p.visitFieldAnnotation(desc, visible);
        final AnnotationVisitor av = (this.fv == null) ? null : this.fv.visitAnnotation(desc, visible);
        return (AnnotationVisitor)new TraceAnnotationVisitor(av, p);
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int typeRef, final TypePath typePath, final String desc, final boolean visible) {
        final Printer p = this.p.visitFieldTypeAnnotation(typeRef, typePath, desc, visible);
        final AnnotationVisitor av = (this.fv == null) ? null : this.fv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return (AnnotationVisitor)new TraceAnnotationVisitor(av, p);
    }
    
    public void visitAttribute(final Attribute attr) {
        this.p.visitFieldAttribute(attr);
        super.visitAttribute(attr);
    }
    
    public void visitEnd() {
        this.p.visitFieldEnd();
        super.visitEnd();
    }
}
