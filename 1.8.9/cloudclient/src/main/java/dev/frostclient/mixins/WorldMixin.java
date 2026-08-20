package dev.frostclient.mixins;

import dev.frostclient.Frost;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public abstract class WorldMixin {

    @Redirect(method = "getCelestialAngle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/storage/WorldInfo;getWorldTime()J"))
    public long setCelestialAngle(WorldInfo instance) {
        if (Frost.INSTANCE.modManager.getMod("TimeChanger").isToggled()) {
            return (long) (instance.getWorldTime() *
                    Frost.INSTANCE.settingManager.getSettingByModAndName("TimeChanger", "Speed").getCurrentNumber() +
                    Frost.INSTANCE.settingManager.getSettingByModAndName("TimeChanger", "Offset").getCurrentNumber());
        }
        return instance.getWorldTime();
    }
}
