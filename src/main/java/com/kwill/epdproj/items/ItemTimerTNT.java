package com.kwill.epdproj.items;

import com.kwill.epdproj.Resources;
import com.kwill.epdproj.blocks.BlockTimerTNT;
import com.kwill.epdproj.proxy.CommonProxy;
import com.kwill.epdproj.tileentity.TileEntityTimerTNT;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.lwjgl.Sys;

import java.util.List;

/**
 * Created by Kyle on 10/25/2015.
 */
public class ItemTimerTNT extends ItemBlock
{

    public ItemTimerTNT(Block block)
    {
        super(block);
        this.setCreativeTab(CreativeTabs.tabRedstone);
        this.setUnlocalizedName(Resources.item_timertnt_name);
        this.setTextureName(Resources.modid+":"+Resources.item_timertnt_name);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
        if (!world.isRemote)
        {
            if (player.isSneaking())
            {
                if (item.stackTagCompound != null)
                    item.stackTagCompound.setDouble("detTime", item.stackTagCompound.getDouble("detTime") + 1.0D);
                else {
                    item.stackTagCompound = new NBTTagCompound();
                    item.stackTagCompound.setDouble("detTime", 4.0D);
                }
                player.addChatMessage(new ChatComponentText("Detonation Time: "+item.stackTagCompound.getDouble("detTime")));
            }
        }
        return super.onItemRightClick(item, world, player);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer, World world, int xPos, int yPos, int zPos, int side, float xCoord, float yCoord, float zCoord) {
        boolean didPlace = super.onItemUse(itemStack, entityPlayer, world, xPos, yPos, zPos, side, xCoord, yCoord, zCoord);

        switch (side)
        {
            case 0:
                yPos--;
                break;
            case 1:
                yPos++;
                break;
            case 2:
                zPos--;
                break;
            case 3:
                zPos++;
                break;
            case 4:
                xPos--;
                break;
            case 5:
                xPos++;
                break;
        }

        if (didPlace)
        {
            TileEntityTimerTNT te = (TileEntityTimerTNT)world.getTileEntity(xPos, yPos, zPos);
            if (te != null)
            {
                if (itemStack.stackTagCompound != null) {
                   te.setFuse(itemStack.stackTagCompound.getDouble("detTime"));
                }
            } else
            {
                return false;
            }
        }
        return didPlace;
    }

    //
//    @Override
//    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int xPos, int yPos, int zPos, int side, float xClick, float yClick, float zClick) {
//        if (!world.isRemote)
//        {
//            switch (side)
//            {
//                case 0: //bottom
//                    yPos--;
//                    break;
//                case 1: //top
//                    yPos++;
//                    break;
//                case 2: //north
//                    zPos--;
//                    break;
//                case 3: //south
//                    zPos++;
//                    break;
//                case 4: //west
//                    xPos--;
//                    break;
//                case 5: //east
//                    xPos++;
//                    break;
//            }
//            if (player.canPlayerEdit(xPos, yPos, zPos,side, itemStack))
//            {
//                if (world.setBlock(xPos, yPos, zPos, CommonProxy.blockTimerTnt))
//                {
//                    TileEntityTimerTNT te = (TileEntityTimerTNT)world.getTileEntity(xPos, yPos, zPos);
//                    if (te != null && itemStack.stackTagCompound != null)
//                        te.setFuse(itemStack.stackTagCompound.getDouble("detTime"));
//                    else if (te != null)
//                    {
//                        te.setFuse(4.0D);
//                    }
//                    if (world.isBlockIndirectlyGettingPowered(xPos, yPos, zPos))
//                    {
//
//                        world.getBlock(xPos,yPos,zPos).onBlockDestroyedByPlayer(world, xPos, yPos, zPos, 1);
//                        world.setBlockToAir(xPos, yPos, zPos);
//                    }
//                    world.notifyBlockOfNeighborChange(xPos, yPos + 1, zPos, CommonProxy.blockTimerTnt);
//                }
//            } else {
//                return false;
//            }
//        }
//        return true;
//    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack item) {
        if (!entityLiving.worldObj.isRemote)
        {
            System.out.println(this.getUnlocalizedName());
            EntityPlayer player = null;
            if (entityLiving instanceof EntityPlayer)
                player = (EntityPlayer) entityLiving;
            else
                return super.onEntitySwing(entityLiving, item);
            if (player.isSneaking())
            {
                if (item.stackTagCompound != null)
                    item.stackTagCompound.setDouble("detTime", item.stackTagCompound.getDouble("detTime") - (item.stackTagCompound.getDouble("detTime") >= 1.0D ? 1.0D : 0.0D));
                else {
                    item.stackTagCompound = new NBTTagCompound();
                    item.stackTagCompound.setDouble("detTime", 4.0D);
                }
                player.addChatMessage(new ChatComponentText("Detonation Time: "+item.stackTagCompound.getDouble("detTime")));
            }
        }
        return super.onEntitySwing(entityLiving, item);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (itemStack.stackTagCompound != null) {
            double detTime = itemStack.stackTagCompound.getDouble("detTime");
            list.add("Time: " + detTime);
        }
    }
}
