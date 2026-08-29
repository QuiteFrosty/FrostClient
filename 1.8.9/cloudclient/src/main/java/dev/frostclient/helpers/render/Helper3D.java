/*
 * Copyright (c) 2022 DupliCAT
 * GNU Lesser General Public License v3.0
 */

package dev.frostclient.helpers.render;

import dev.frostclient.Frost;
import dev.frostclient.feature.mod.impl.FreelookMod;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Helper3D {

    /**
     * Checks whether a world position falls outside the player's field of view, so
     * callers can skip rendering things the camera can't actually see. Anything
     * within 2 blocks of the eye is always considered visible, since the angle test
     * becomes unstable at very short range.
     *
     * @param x World-space X of the point being tested
     * @param y World-space Y of the point being tested
     * @param z World-space Z of the point being tested
     * @param maxAngle Half-angle, in degrees, of the cone considered "in view"
     * @return True if the point lies outside the view cone and can be culled
     */

    public static boolean isOutsideView(double x, double y, double z, float maxAngle) {
        EntityPlayer player = Frost.INSTANCE.mc.thePlayer;
        if (player == null) {
            return false;
        }

        Vec3 eyePos = player.getPositionEyes(1.0F);
        double dx = x - eyePos.xCoord;
        double dy = y - eyePos.yCoord;
        double dz = z - eyePos.zCoord;

        if (dx * dx + dy * dy + dz * dz < 4.0D) {
            return false;
        }

        Vec3 toPoint = new Vec3(dx, dy, dz).normalize();
        Vec3 look = player.getLook(1.0F);

        double dot = MathHelper.clamp_double(toPoint.dotProduct(look), -1.0D, 1.0D);
        double angle = Math.toDegrees(Math.acos(dot));

        return angle > maxAngle;
    }

    /**
     * Draws a filled box over a given Axis Aligned Bounding Box in the world
     *
     * @param boundingBox The Axis Aligned Bounding Box in the world
     */

    public static void drawFilledBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();

        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldRenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
    }

    public static double calculateCameraDistance(double d0, double d1, double d2, double d3) {
        float f1 = FreelookMod.cameraYaw;
        float f2 = FreelookMod.cameraPitch;

        if (Frost.INSTANCE.mc.gameSettings.thirdPersonView == 2) {
            f2 += 180.0F;
        }

        double d4 = (double) (MathHelper.sin(f1 / 180.0F * (float) Math.PI) * MathHelper.cos(f2 / 180.0F * (float) Math.PI)) * d3;
        double d5 = (double) (-MathHelper.cos(f1 / 180.0F * (float) Math.PI) * MathHelper.cos(f2 / 180.0F * (float) Math.PI)) * d3;
        double d6 = (double) (-MathHelper.sin(f2 / 180.0F * (float) Math.PI)) * d3;

        for (int i = 0; i < 8; ++i) {
            float f3 = (float) ((i & 1) * 2 - 1);
            float f4 = (float) ((i >> 1 & 1) * 2 - 1);
            float f5 = (float) ((i >> 2 & 1) * 2 - 1);
            f3 = f3 * 0.1F;
            f4 = f4 * 0.1F;
            f5 = f5 * 0.1F;
            MovingObjectPosition movingobjectposition = Frost.INSTANCE.mc.theWorld.rayTraceBlocks(
                    new Vec3(d0 + (double) f3, d1 + (double) f4, d2 + (double) f5),
                    new Vec3(d0 - d4 + (double) f3 + (double) f5, d1 - d6 + (double) f4, d2 - d5 + (double) f5)
            );

            if (movingobjectposition != null) {
                double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));

                if (d7 < d3) {
                    d3 = d7;
                }
            }
        }
        return d3;
    }
}
