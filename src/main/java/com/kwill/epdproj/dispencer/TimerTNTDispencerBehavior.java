package com.kwill.epdproj.dispencer;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Kyle on 10/26/2015.
 */
public class TimerTNTDispencerBehavior extends BehaviorDefaultDispenseItem
{
    private static final String __OBFID = "CL_00001403";
    /**
     * Dispense the specified stack, play the dispense sound and spawn particles.
     */
    protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_)
    {
        EnumFacing enumfacing = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
        World world = p_82487_1_.getWorld();
        int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
        int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
        int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();
        EntityTNTPrimed entitytntprimed = new EntityTNTPrimed(world, (double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), (EntityLivingBase)null);
        world.spawnEntityInWorld(entitytntprimed);
        return p_82487_2_;
    }
}
