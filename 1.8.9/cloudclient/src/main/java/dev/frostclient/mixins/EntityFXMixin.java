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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFX.class)
public abstract class EntityFXMixin {

    @Inject(method = "renderParticle", at = @At("HEAD"), cancellable = true)
    private void cullOffscreenParticles(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ, CallbackInfo ci) {
        if (!Frost.INSTANCE.modManager.getMod("ParticleCulling").isToggled()) {
            return;
        }

        // posX/posY/posZ are declared on Entity, not EntityFX itself, so Mixin's
        // @Shadow can't resolve them on this target (it only looks at fields
        // declared directly on the target class). Read them via a plain cast
        // instead -- that goes through the normal FML deobfuscating remapper
        // like every other non-mixin file in this codebase, rather than
        // Mixin's separate (and here, unsupported) shadow-field resolution.
        Entity particle = (Entity) (Object) this;
        if (Helper3D.isOutsideView(particle.posX, particle.posY, particle.posZ, ParticleCullingMod.getCullAngle())) {
            ci.cancel();
        }
    }
}
