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

    public void spawnPrimedTNT()
    {
        World world = this.getWorldObj();
        if (!world.isRemote)
        {
            if ((blockMetadata & 1) == 1)
            {
                EntityTimerTNTPrimed entitytntprimed = new EntityTimerTNTPrimed(world, (double)((float)this.xCoord + 0.5F), (double)((float)this.yCoord + 0.5F), (double)((float)this.zCoord + 0.5F), null);
                entitytntprimed.fuse = this.fuse;
                world.spawnEntityInWorld(entitytntprimed);
                System.out.println("Entity Created at: "+entitytntprimed.posX + "," + entitytntprimed.posZ);
                world.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }
}

