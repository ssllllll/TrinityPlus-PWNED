//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.mixins.mixins;

import me.leon.trinityplus.mixins.*;
import net.minecraft.entity.*;
import me.leon.trinityplus.hacks.movement.*;
import me.leon.trinityplus.managers.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import me.leon.trinityplus.hacks.render.*;
import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.injection.*;
import me.leon.trinityplus.events.*;
import me.leon.trinityplus.events.main.*;
import net.minecraft.util.math.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Entity.class })
public abstract class MixinEntity implements IMixin
{
    @Redirect(method = { "applyEntityCollision" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;addVelocity(DDD)V"))
    public void velocity(final Entity entity, final double x, final double y, final double z) {
        if (!ModuleManager.getMod((Class)Velocity.class).isEnabled()) {
            entity.motionX += x;
            entity.motionY += y;
            entity.motionZ += z;
            entity.isAirBorne = true;
        }
    }
    
    @Inject(method = { "shouldRenderInPass" }, at = { @At("HEAD") }, cancellable = true, remap = false)
    public void shouldRenderInPass(final CallbackInfoReturnable<Boolean> info) {
        if (Chams.shouldRender((Entity)this) && !(((Entity)this) instanceof EntityBoat)) {
            info.setReturnValue((Object)true);
            info.cancel();
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Nullable
    @Overwrite
    public RayTraceResult rayTrace(final double blockReachDistance, final float partialTicks) {
        final EventGetBlockReachDistance event = new EventGetBlockReachDistance(EventStage.PRE, blockReachDistance);
        final Vec3d vec3d = this.getPositionEyes(partialTicks);
        final Vec3d vec3d2 = this.getLook(partialTicks);
        Vec3d vec3d3;
        if (event.isCancelled()) {
            vec3d3 = vec3d.add(vec3d2.x * event.reach, vec3d2.y * event.reach, vec3d2.z * event.reach);
        }
        else {
            vec3d3 = vec3d.add(vec3d2.x * blockReachDistance, vec3d2.y * blockReachDistance, vec3d2.z * blockReachDistance);
        }
        return MixinEntity.mc.world.rayTraceBlocks(vec3d, vec3d3, false, false, true);
    }
    
    @Shadow
    public abstract Vec3d getPositionEyes(final float p0);
    
    @Shadow
    public abstract Vec3d getLook(final float p0);
}
