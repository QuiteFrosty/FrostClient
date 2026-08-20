/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.feature.mod.impl;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.Mod;
import dev.frostclient.feature.mod.Type;
import dev.frostclient.feature.setting.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ParticleMultiplierMod extends Mod {

    public ParticleMultiplierMod() {
        super(
                "ParticleMultiplier",
                "Multiplies or adds Particles by a given amount.",
                Type.Visual
        );

        Frost.INSTANCE.settingManager.addSetting(new Setting("Particle Amount", this, 15, 5));
    }

    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (e.target instanceof EntityLivingBase) {
            boolean doCriticalDamage =
                    Frost.INSTANCE.mc.thePlayer.fallDistance > 0.0F &&
                    !Frost.INSTANCE.mc.thePlayer.onGround &&
                    !Frost.INSTANCE.mc.thePlayer.isOnLadder() &&
                    !Frost.INSTANCE.mc.thePlayer.isInWater() &&
                    !Frost.INSTANCE.mc.thePlayer.isPotionActive(Potion.blindness) &&
                            Frost.INSTANCE.mc.thePlayer.ridingEntity == null;

            for (int i = 0; i < getAmount(); i++) {
                Frost.INSTANCE.mc.thePlayer.onEnchantmentCritical(e.target);
                if (doCriticalDamage) {
                    Frost.INSTANCE.mc.thePlayer.onCriticalHit(e.target);
                }
            }

        }
    }

    private float getAmount() {
        return Frost.INSTANCE.settingManager.getSettingByModAndName(getName(), "Particle Amount").getCurrentNumber();
    }
}
