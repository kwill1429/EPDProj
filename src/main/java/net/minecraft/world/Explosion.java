package net.minecraft.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class Explosion
{
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming;
    /** whether or not this explosion spawns smoke particles */
    public boolean isSmoking = true;
    private int value16 = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    /** A list of ChunkPositions of blocks affected by this explosion */
    public List affectedBlockPositions = new ArrayList();
    private Map field_77288_k = new HashMap();
    private static final String __OBFID = "CL_00000134";

    public Explosion(World world, Entity entity, double xPos, double yPos, double zPos, float explosionSize)
    {
        this.worldObj = world;
        this.exploder = entity;
        this.explosionSize = explosionSize;
        this.explosionX = xPos;
        this.explosionY = yPos;
        this.explosionZ = zPos;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float explosionSize = this.explosionSize;
        HashSet hashset = new HashSet();
        int explosionRadiusXN;
        int explosionRadiusXP;
        int explosionRadiusYN;
        double differenceInX;
        double differenceInY;
        double differenceInZ;

        for (explosionRadiusXN = 0; explosionRadiusXN < this.value16; ++explosionRadiusXN)
        {
            for (explosionRadiusXP = 0; explosionRadiusXP < this.value16; ++explosionRadiusXP)
            {
                for (explosionRadiusYN = 0; explosionRadiusYN < this.value16; ++explosionRadiusYN)
                {
                    if (explosionRadiusXN == 0 || explosionRadiusXN == this.value16 - 1 || explosionRadiusXP == 0 || explosionRadiusXP == this.value16 - 1 || explosionRadiusYN == 0 || explosionRadiusYN == this.value16 - 1)
                    {
                        double d0 = (double)((float)explosionRadiusXN / ((float)this.value16 - 1.0F) * 2.0F - 1.0F);
                        double d1 = (double)((float)explosionRadiusXP / ((float)this.value16 - 1.0F) * 2.0F - 1.0F);
                        double d2 = (double)((float)explosionRadiusYN / ((float)this.value16 - 1.0F) * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        differenceInX = this.explosionX;
                        differenceInY = this.explosionY;
                        differenceInZ = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
                        {
                            int j1 = MathHelper.floor_double(differenceInX);
                            int k1 = MathHelper.floor_double(differenceInY);
                            int l1 = MathHelper.floor_double(differenceInZ);
                            Block block = this.worldObj.getBlock(j1, k1, l1);

                            if (block.getMaterial() != Material.air)
                            {
                                float f3 = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, j1, k1, l1, block) : block.getExplosionResistance(this.exploder, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                f1 -= (f3 + 0.3F) * f2;
                            }

                            if (f1 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, j1, k1, l1, block, f1)))
                            {
                                hashset.add(new ChunkPosition(j1, k1, l1));
                            }

                            differenceInX += d0 * (double)f2;
                            differenceInY += d1 * (double)f2;
                            differenceInZ += d2 * (double)f2;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        this.explosionSize *= 2.0F;
        explosionRadiusXN = MathHelper.floor_double(this.explosionX - (double)this.explosionSize - 1.0D);
        explosionRadiusXP = MathHelper.floor_double(this.explosionX + (double)this.explosionSize + 1.0D);
        explosionRadiusYN = MathHelper.floor_double(this.explosionY - (double)this.explosionSize - 1.0D);
        int explosionRadiusYP = MathHelper.floor_double(this.explosionY + (double)this.explosionSize + 1.0D);
        int explosionRadiusZN = MathHelper.floor_double(this.explosionZ - (double)this.explosionSize - 1.0D);
        int explosionRadiusZP = MathHelper.floor_double(this.explosionZ + (double)this.explosionSize + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double) explosionRadiusXN, (double) explosionRadiusYN, (double) explosionRadiusZN, (double) explosionRadiusXP, (double) explosionRadiusYP, (double) explosionRadiusZP));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, this.explosionSize);
        Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

        for (int entityIndex = 0; entityIndex < list.size(); ++entityIndex)
        {
            Entity entity = (Entity)list.get(entityIndex);
            double distanceFromExplosion = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;

            if (distanceFromExplosion <= 1.0D) //entity within explosion radius
            {
                differenceInX = entity.posX - this.explosionX;
                differenceInY = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
                differenceInZ = entity.posZ - this.explosionZ;
                double differenceInDistance = (double)MathHelper.sqrt_double(differenceInX * differenceInX + differenceInY * differenceInY + differenceInZ * differenceInZ);

                if (differenceInDistance != 0.0D)
                {
                    differenceInX /= differenceInDistance;
                    differenceInY /= differenceInDistance;
                    differenceInZ /= differenceInDistance;
                    double viewCoveredByBlocksPercent = (double)this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                    double d11 = (1.0D - distanceFromExplosion) * viewCoveredByBlocksPercent;
                    entity.attackEntityFrom(DamageSource.setExplosionSource(this), (float)((int)((d11 * d11 + d11) / 2.0D * 8.0D * (double)this.explosionSize + 1.0D)));
                    double d8 = EnchantmentProtection.func_92092_a(entity, d11);
                    entity.motionX += differenceInX * d8;
                    entity.motionY += differenceInY * d8;
                    entity.motionZ += differenceInZ * d8;

                    if (entity instanceof EntityPlayer)
                    {
                        this.field_77288_k.put((EntityPlayer)entity, Vec3.createVectorHelper(differenceInX * d11, differenceInY * d11, differenceInZ * d11));
                    }
                }
            }
        }

        this.explosionSize = explosionSize;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean p_77279_1_)
    {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
        {
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }
        else
        {
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        }

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;

        if (this.isSmoking)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);

                if (p_77279_1_)
                {
                    double d0 = (double)((float)i + this.worldObj.rand.nextFloat());
                    double d1 = (double)((float)j + this.worldObj.rand.nextFloat());
                    double d2 = (double)((float)k + this.worldObj.rand.nextFloat());
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = (double)MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / (double)this.explosionSize + 0.1D);
                    d7 *= (double)(this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }

                if (block.getMaterial() != Material.air)
                {
                    if (block.canDropFromExplosion(this))
                    {
                        block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), 1.0F / this.explosionSize, 0);
                    }

                    block.onBlockExploded(this.worldObj, i, j, k, this);
                }
            }
        }

        if (this.isFlaming)
        {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext())
            {
                chunkposition = (ChunkPosition)iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);
                Block block1 = this.worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial() == Material.air && block1.func_149730_j() && this.explosionRNG.nextInt(3) == 0)
                {
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
                }
            }
        }
    }

    public Map func_77277_b()
    {
        return this.field_77288_k;
    }

    /**
     * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
     */
    public EntityLivingBase getExplosivePlacedBy()
    {
        return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed)this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase)this.exploder : null));
    }
}