/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.mixins;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.impl.ParticleCullingMod;
import dev.frostclient.helpers.render.Helper3D;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFX.class)
public abstract class EntityFXMixin {

    @Shadow public double posX;
    @Shadow public double posY;
    @Shadow public double posZ;

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    private void cullOffscreenParticles(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (!Frost.INSTANCE.modManager.getMod("ParticleCulling").isToggled()) {
            return;
        }

        if (Helper3D.isOutsideView(posX, posY, posZ, ParticleCullingMod.getCullAngle())) {
            ci.cancel();
        }
    }
}
