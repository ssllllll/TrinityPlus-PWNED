//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\charl\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package me.leon.trinityplus.events.main;

import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import me.leon.trinityplus.events.*;

public class EventRenderEntityCrystal extends TrinityEvent
{
    public final ModelEnderCrystal modelBase;
    public EntityEnderCrystal entityIn;
    public float limbSwing;
    public float limbSwingAmount;
    public float ageInTicks;
    public float netHeadYaw;
    public float headPitch;
    public float scale;
    
    public EventRenderEntityCrystal(final EventStage stage, final EntityEnderCrystal crystal, final ModelEnderCrystal modelBase, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
        super(stage);
        this.modelBase = modelBase;
        this.entityIn = crystal;
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
        this.ageInTicks = ageInTicks;
        this.netHeadYaw = netHeadYaw;
        this.headPitch = headPitch;
        this.scale = scale;
    }
}
