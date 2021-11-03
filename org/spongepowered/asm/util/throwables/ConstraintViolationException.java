//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.*;

public class ConstraintViolationException extends Exception
{
    private static final String MISSING_VALUE = "UNRESOLVED";
    private static final long serialVersionUID = 1L;
    private final ConstraintParser.Constraint constraint;
    private final String badValue;
    
    public ConstraintViolationException(final ConstraintParser.Constraint constraint) {
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final ConstraintParser.Constraint constraint, final int badValue) {
        this.constraint = constraint;
        this.badValue = String.valueOf(badValue);
    }
    
    public ConstraintViolationException(final String message, final ConstraintParser.Constraint constraint) {
        super(message);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String message, final ConstraintParser.Constraint constraint, final int badValue) {
        super(message);
        this.constraint = constraint;
        this.badValue = String.valueOf(badValue);
    }
    
    public ConstraintViolationException(final Throwable cause, final ConstraintParser.Constraint constraint) {
        super(cause);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final Throwable cause, final ConstraintParser.Constraint constraint, final int badValue) {
        super(cause);
        this.constraint = constraint;
        this.badValue = String.valueOf(badValue);
    }
    
    public ConstraintViolationException(final String message, final Throwable cause, final ConstraintParser.Constraint constraint) {
        super(message, cause);
        this.constraint = constraint;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String message, final Throwable cause, final ConstraintParser.Constraint constraint, final int badValue) {
        super(message, cause);
        this.constraint = constraint;
        this.badValue = String.valueOf(badValue);
    }
    
    public ConstraintParser.Constraint getConstraint() {
        return this.constraint;
    }
    
    public String getBadValue() {
        return this.badValue;
    }
}
