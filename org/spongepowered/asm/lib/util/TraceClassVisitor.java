//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.lib.util;

import java.io.*;
import org.spongepowered.asm.lib.*;

public final class TraceClassVisitor extends ClassVisitor
{
    private final PrintWriter pw;
    public final Printer p;
    
    public TraceClassVisitor(final PrintWriter pw) {
        this(null, pw);
    }
    
    public TraceClassVisitor(final ClassVisitor cv, final PrintWriter pw) {
        this(cv, (Printer)new Textifier(), pw);
    }
    
    public TraceClassVisitor(final ClassVisitor cv, final Printer p, final PrintWriter pw) {
        super(327680, cv);
        this.pw = pw;
        this.p = p;
    }
    
    public void visit(final int version, final int access, final String name, final String signature, final String superName, final String[] interfaces) {
        this.p.visit(version, access, name, signature, superName, interfaces);
        super.visit(version, access, name, signature, superName, interfaces);
    }
    
    public void visitSource(final String file, final String debug) {
        this.p.visitSource(file, debug);
        super.visitSource(file, debug);
    }
    
    public void visitOuterClass(final String owner, final String name, final String desc) {
        this.p.visitOuterClass(owner, name, desc);
        super.visitOuterClass(owner, name, desc);
    }
    
    public AnnotationVisitor visitAnnotation(final String desc, final boolean visible) {
        final Printer p = this.p.visitClassAnnotation(desc, visible);
        final AnnotationVisitor av = (this.cv == null) ? null : this.cv.visitAnnotation(desc, visible);
        return (AnnotationVisitor)new TraceAnnotationVisitor(av, p);
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int typeRef, final TypePath typePath, final String desc, final boolean visible) {
        final Printer p = this.p.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
        final AnnotationVisitor av = (this.cv == null) ? null : this.cv.visitTypeAnnotation(typeRef, typePath, desc, visible);
        return (AnnotationVisitor)new TraceAnnotationVisitor(av, p);
    }
    
    public void visitAttribute(final Attribute attr) {
        this.p.visitClassAttribute(attr);
        super.visitAttribute(attr);
    }
    
    public void visitInnerClass(final String name, final String outerName, final String innerName, final int access) {
        this.p.visitInnerClass(name, outerName, innerName, access);
        super.visitInnerClass(name, outerName, innerName, access);
    }
    
    public FieldVisitor visitField(final int access, final String name, final String desc, final String signature, final Object value) {
        final Printer p = this.p.visitField(access, name, desc, signature, value);
        final FieldVisitor fv = (this.cv == null) ? null : this.cv.visitField(access, name, desc, signature, value);
        return new TraceFieldVisitor(fv, p);
    }
    
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature, final String[] exceptions) {
        final Printer p = this.p.visitMethod(access, name, desc, signature, exceptions);
        final MethodVisitor mv = (this.cv == null) ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
        return new TraceMethodVisitor(mv, p);
    }
    
    public void visitEnd() {
        this.p.visitClassEnd();
        if (this.pw != null) {
            this.p.print(this.pw);
            this.pw.flush();
        }
        super.visitEnd();
    }
}
