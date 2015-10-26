package com.kwill.epdproj.proxy;

import com.kwill.epdproj.Resources;
import com.kwill.epdproj.TimerTNT;
import com.kwill.epdproj.blocks.BlockTimerTNT;
import com.kwill.epdproj.dispencer.TimerTNTDispencerBehavior;
import com.kwill.epdproj.entity.EntityTimerTNTPrimed;
import com.kwill.epdproj.items.ItemTimerTNT;
import com.kwill.epdproj.tileentity.TileEntityTimerTNT;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * Created by Kyle on 10/25/2015.
 */
public class CommonProxy
{
    public static Block blockTimerTnt = new BlockTimerTNT(Resources.block_timertnt_name);

    public static Item itemTimerTnt = new ItemTimerTNT(Resources.item_timertnt_name);

    public void preInit(FMLPreInitializationEvent e)
    {
        registerRenders();
        GameRegistry.registerBlock(blockTimerTnt, Resources.block_timertnt_name);
        GameRegistry.registerItem(itemTimerTnt, Resources.item_timertnt_name);

        GameRegistry.registerTileEntity(TileEntityTimerTNT.class, Resources.tileentity_timertnt_name);


    }
    public void init(FMLInitializationEvent e)
    {
        int cTntID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityTimerTNTPrimed.class, Resources.block_timertnt_name, cTntID);
        EntityRegistry.registerModEntity(EntityTimerTNTPrimed.class, Resources.block_timertnt_name, cTntID, TimerTNT.instance, 64, 10, true);

    }
    public void postInit(FMLPostInitializationEvent e)
    {
        BlockDispenser.dispenseBehaviorRegistry.putObject(itemTimerTnt, new TimerTNTDispencerBehavior());
    }

    public void registerRenders()
    {

    }
}
