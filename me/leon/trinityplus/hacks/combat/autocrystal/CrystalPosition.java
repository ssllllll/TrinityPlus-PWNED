//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat.autocrystal;

import net.minecraft.util.math.*;

public class CrystalPosition
{
    private float targetDamage;
    private float selfDamage;
    private BlockPos base;
    
    public CrystalPosition() {
    }
    
    public CrystalPosition(final float targetDamage, final float selfDamage, final BlockPos base) {
        this.targetDamage = targetDamage;
        this.selfDamage = selfDamage;
        this.base = base;
    }
    
    public float getTargetDamage() {
        return this.targetDamage;
    }
    
    public void setTargetDamage(final float targetDamage) {
        this.targetDamage = targetDamage;
    }
    
    public float getSelfDamage() {
        return this.selfDamage;
    }
    
    public void setSelfDamage(final float selfDamage) {
        this.selfDamage = selfDamage;
    }
    
    public BlockPos getBase() {
        return this.base;
    }
    
    public void setBase(final BlockPos base) {
        this.base = base;
    }
    
    public Vec3d getCenterVec() {
        return new Vec3d(this.base.x + 0.5, this.base.y + 0.5, this.base.z + 0.5);
    }
    
    public Vec3d getCrystalVec() {
        return new Vec3d(this.base.x + 0.5, this.base.y + 1.0, this.base.z + 0.5);
    }
}
