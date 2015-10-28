package com.kwill.epdproj.entity;

import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Created by Kyle on 10/25/2015.
 */
public class EntityTimerTNTPrimed extends Entity
{

    private int fuse;
    private EntityLivingBase placedBy;

    public EntityTimerTNTPrimed(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
        this.yOffset = this.height / 2.0F;
    }

    public EntityTimerTNTPrimed(World world, double xPos, double yPos, double zPos, EntityLivingBase explosivePlacedBy) {
        this(world);
        this.setLocationAndAngles(xPos, yPos, zPos, 0, 0);
        this.motionY = 0.20000000298023224D;
        this.fuse = 80;
        this.prevPosX = xPos;
        this.prevPosY = yPos;
        this.prevPosZ = zPos;
        this.placedBy = explosivePlacedBy;
    }

    @Override
    protected void entityInit() {
        DataWatcher dw = this.getDataWatcher();
        dw.addObject(2, (int)fuse);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    @Override
    public void onUpdate() {
        DataWatcher dw = this.getDataWatcher();
        this.fuse = dw.getWatchableObjectInt(2);
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.025D;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);

        if (this.onGround)
        {
            this.motionY *= -0.5D;
        }
        setFuse(fuse - 1);

        if (this.fuse <= 0)
        {
            this.setDead();

            if (!this.worldObj.isRemote)
            {
                this.explode();
            }
        }
        else
        {
            this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode()
    {
        float f = 4.0F;
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        if (tag.getInteger("fuse") >= 0)
            this.setFuse(tag.getInteger("fuse"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) { tag.setInteger("fuse", this.fuse);
    }

    public void setFuse(int fuse)
    {
        this.fuse = fuse;
        DataWatcher dw = this.getDataWatcher();
        dw.updateObject(2, this.fuse);
    }

    public int getFuse()
    {
        return this.fuse;
    }
}
