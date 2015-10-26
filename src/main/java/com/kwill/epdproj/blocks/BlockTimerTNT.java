package com.kwill.epdproj.blocks;

import com.kwill.epdproj.Resources;
import com.kwill.epdproj.entity.EntityTimerTNTPrimed;
import com.kwill.epdproj.tileentity.TileEntityTimerTNT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * Created by Kyle on 10/25/2015.
 */
public class BlockTimerTNT extends Block
{

    IIcon[] blockTextures = new IIcon[3];

    public BlockTimerTNT(String name) {
        super(Material.tnt);
        this.setBlockName(name);
        this.setStepSound(soundTypeGrass);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int someInt)
    {
        return side == 0 || side == 1 ? blockTextures[side] : blockTextures[2];
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockTextures[0] = iconRegister.registerIcon(Resources.modid+":"+Resources.block_timertnt_texture_bot);
        blockTextures[1] = iconRegister.registerIcon(Resources.modid+":"+Resources.block_timertnt_texture_top);
        blockTextures[2] = iconRegister.registerIcon(Resources.modid+":"+Resources.block_timertnt_texture_side);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityTimerTNT();
    }

    @Override
    public void onBlockAdded(World world, int xPos, int yPos, int zPos)
    {
        super.onBlockAdded(world, xPos, yPos, zPos);
        System.out.println(this.getUnlocalizedName());
        if (world.isBlockIndirectlyGettingPowered(xPos, yPos, zPos))
        {
            this.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, 1);
            world.setBlockToAir(xPos, yPos, zPos);
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int xPos, int yPos, int zPos, Block block)
    {
        if (world.isBlockIndirectlyGettingPowered(xPos, yPos, zPos))
        {
            this.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, 1);
            world.setBlockToAir(xPos, yPos, zPos);
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int xPos, int yPos, int zPos, Explosion explosion)
    {
        if (!world.isRemote)
        {
            EntityTimerTNTPrimed entitytntprimed = new EntityTimerTNTPrimed(world, (double)((float)xPos + 0.5F), (double)((float)yPos + 0.5F), (double)((float)zPos + 0.5F), explosion.getExplosivePlacedBy());
            entitytntprimed.fuse = world.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            world.spawnEntityInWorld(entitytntprimed);
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int xPos, int yPos, int zPos, int meta)
    {
        this.spawnPrimedTNT(world, xPos, yPos, zPos, meta, (EntityLivingBase) null);
    }

    public void spawnPrimedTNT(World world, int xPos, int yPos, int zPos, int metadata, EntityLivingBase entity)
    {
        if (!world.isRemote)
        {
            if ((metadata & 1) == 1)
            {
                EntityTimerTNTPrimed entitytntprimed = new EntityTimerTNTPrimed(world, (double)((float)xPos + 0.5F), (double)((float)yPos + 0.5F), (double)((float)zPos + 0.5F), entity);
                entitytntprimed.fuse = ((TileEntityTimerTNT)world.getTileEntity(xPos,yPos,zPos)).fuse;
                world.spawnEntityInWorld(entitytntprimed);
                System.out.println("Entity Created at: "+entitytntprimed.posX + "," + entitytntprimed.posZ);
                world.playSoundAtEntity(entitytntprimed, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }

    @Override
    public boolean canDropFromExplosion(Explosion p_149659_1_)
    {
        return false;
    }
}
