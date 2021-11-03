//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.utils.world;

import me.leon.trinityplus.utils.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import me.leon.trinityplus.utils.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.math.*;

public class DamageUtils implements Util
{
    public static boolean isArmorLow(final EntityPlayer player, final int durability) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece == null) {
                return true;
            }
            if (getItemDamage(piece) >= durability) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    public static boolean isNaked(final EntityPlayer player) {
        for (final ItemStack piece : player.inventory.armorInventory) {
            if (piece != null) {
                if (piece.isEmpty()) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public static int getItemDamage(final ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }
    
    public static float getDamageInPercent(final ItemStack stack) {
        return getItemDamage(stack) / (float)stack.getMaxDamage() * 100.0f;
    }
    
    public static int getRoundedDamage(final ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }
    
    public static boolean hasDurability(final ItemStack stack) {
        final Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static boolean canBreakWeakness(final EntityPlayer player) {
        int strengthAmp = 0;
        final PotionEffect effect = DamageUtils.mc.player.getActivePotionEffect(MobEffects.STRENGTH);
        if (effect != null) {
            strengthAmp = effect.getAmplifier();
        }
        return !DamageUtils.mc.player.isPotionActive(MobEffects.WEAKNESS) || strengthAmp >= 1 || DamageUtils.mc.player.getHeldItemMainhand().getItem() instanceof ItemSword || DamageUtils.mc.player.getHeldItemMainhand().getItem() instanceof ItemPickaxe || DamageUtils.mc.player.getHeldItemMainhand().getItem() instanceof ItemAxe || DamageUtils.mc.player.getHeldItemMainhand().getItem() instanceof ItemSpade;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity, final boolean predict, final int predictTicks) {
        Vec3d entityPos;
        AxisAlignedBB bb;
        if (predict) {
            entityPos = MathUtils.extrapolatePlayerPosition(entity, predictTicks);
            bb = entity.boundingBox.offset(-entity.posX, -entity.posY, -entity.posZ).offset(entityPos);
        }
        else {
            bb = entity.boundingBox;
            entityPos = entity.getPositionVector();
        }
        final float doubleExplosionSize = 12.0f;
        final Vec3d vec3d = new Vec3d(posX, posY, posZ);
        final double distancedsize = entityPos.distanceTo(vec3d) / 12.0;
        double blockDensity = 0.0;
        try {
            blockDensity = entity.world.getBlockDensity(vec3d, bb);
        }
        catch (Exception ex) {}
        final double v = (1.0 - distancedsize) * blockDensity;
        final float damage = (float)((v * v + v) / 2.0 * 7.0 * 12.0 + 1.0);
        double finald = 1.0;
        if (entity instanceof EntityLivingBase) {
            finald = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(damage), new Explosion((World)DamageUtils.mc.world, (Entity)null, posX, posY, posZ, 6.0f, false, true));
        }
        return (float)finald;
    }
    
    public static float calculateDamage(final double posX, final double posY, final double posZ, final Entity entity) {
        return calculateDamage(posX, posY, posZ, entity, false, 0);
    }
    
    public static float getBlastReduction(final EntityLivingBase entity, final float damageI, final Explosion explosion) {
        float damage = damageI;
        if (entity instanceof EntityPlayer) {
            final EntityPlayer ep = (EntityPlayer)entity;
            final DamageSource ds = DamageSource.causeExplosionDamage(explosion);
            damage = CombatRules.getDamageAfterAbsorb(damage, (float)ep.getTotalArmorValue(), (float)ep.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            int k = 0;
            try {
                k = EnchantmentHelper.getEnchantmentModifierDamage(ep.getArmorInventoryList(), ds);
            }
            catch (Exception ex) {}
            final float f = MathHelper.clamp((float)k, 0.0f, 20.0f);
            damage *= 1.0f - f / 25.0f;
            if (entity.isPotionActive(MobEffects.RESISTANCE)) {
                damage -= damage / 4.0f;
            }
            damage = Math.max(damage, 0.0f);
            return damage;
        }
        damage = CombatRules.getDamageAfterAbsorb(damage, (float)entity.getTotalArmorValue(), (float)entity.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return damage;
    }
    
    public static float getDamageMultiplied(final float damage) {
        final int diff = DamageUtils.mc.world.getDifficulty().getId();
        return damage * ((diff == 0) ? 0.0f : ((diff == 2) ? 1.0f : ((diff == 1) ? 0.5f : 1.5f)));
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity, final boolean predict, final int ticks) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity, predict, ticks);
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity, final boolean predict, final int ticks) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity, predict, ticks);
    }
    
    public static float calculateDamage(final Vec3d pos, final Entity entity, final boolean predict, final int ticks) {
        return calculateDamage(pos.x, pos.y, pos.z, entity, predict, ticks);
    }
    
    public static float calculateDamage(final Entity crystal, final Entity entity) {
        return calculateDamage(crystal.posX, crystal.posY, crystal.posZ, entity);
    }
    
    public static float calculateDamage(final BlockPos pos, final Entity entity) {
        return calculateDamage(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, entity);
    }
    
    public static float calculateDamage(final Vec3d pos, final Entity entity) {
        return calculateDamage(pos.x, pos.y, pos.z, entity);
    }
    
    public static boolean canTakeDamage(final boolean suicide) {
        return !DamageUtils.mc.player.capabilities.isCreativeMode && !suicide;
    }
}
