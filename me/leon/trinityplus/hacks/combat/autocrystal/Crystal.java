//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.hacks.combat.autocrystal;

import me.leon.trinityplus.utils.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.combat.*;

public class Crystal implements Util
{
    private float selfDamage;
    private float targetDamage;
    private EntityEnderCrystal crystal;
    private BlockPos base;
    
    public Crystal() {
    }
    
    public Crystal(final EntityEnderCrystal crystal) {
        this(Utility.calcDamage(crystal, (EntityLivingBase)Crystal.mc.player), Utility.calcDamage(crystal, AutoCrystal.target), crystal);
    }
    
    public Crystal(final float selfDamage, final float targetDamage, final EntityEnderCrystal crystal) {
        this.crystal = crystal;
        this.base = crystal.getPosition().down();
        this.selfDamage = selfDamage;
        this.targetDamage = targetDamage;
    }
    
    public float getSelfDamage() {
        return this.selfDamage;
    }
    
    public void setSelfDamage(final float selfDamage) {
        this.selfDamage = selfDamage;
    }
    
    public float getTargetDamage() {
        return this.targetDamage;
    }
    
    public void setTargetDamage(final float targetDamage) {
        this.targetDamage = targetDamage;
    }
    
    public EntityEnderCrystal getCrystal() {
        return this.crystal;
    }
    
    public void setCrystal(final EntityEnderCrystal crystal) {
        this.crystal = crystal;
    }
    
    public BlockPos getBase() {
        return this.base;
    }
    
    public void setBase(final BlockPos base) {
        this.base = base;
    }
}
