package com.kwill.epdproj.tileentity;

import com.kwill.epdproj.entity.EntityTimerTNTPrimed;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Kyle on 10/25/2015.
 */
public class TileEntityTimerTNT extends TileEntity
{
    public int fuse;

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("fuse", this.fuse);
        super.writeToNBT(tagCompound);
    }

    public void readFromNBT(NBTTagCompound tagCompound)
    {
        this.fuse = tagCompound.getInteger("fuse");
        super.readFromNBT(tagCompound);
    }

    public void setFuse(double detTime)
    {
        this.fuse = (int)(detTime * 20);
    }


}

