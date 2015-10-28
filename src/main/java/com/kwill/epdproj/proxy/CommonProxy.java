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
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;

/**
 * Created by Kyle on 10/25/2015.
 */
public class CommonProxy
{
    public static Block blockTimerTnt;

    public static Item itemTimerTnt;

    public void preInit(FMLPreInitializationEvent e)
    {
        blockTimerTnt = new BlockTimerTNT(Resources.block_timertnt_name);
        GameRegistry.registerBlock(blockTimerTnt, ItemTimerTNT.class, Resources.block_timertnt_name);

        GameRegistry.registerTileEntity(TileEntityTimerTNT.class, Resources.tileentity_timertnt_name);

        EntityRegistry.registerModEntity(EntityTimerTNTPrimed.class, Resources.block_timertnt_name, 1, TimerTNT.instance, 64, 10, true);
    }
    public void init(FMLInitializationEvent e)
    {

    }
    public void postInit(FMLPostInitializationEvent e)
    {
        BlockDispenser.dispenseBehaviorRegistry.putObject(itemTimerTnt, new TimerTNTDispencerBehavior());
    }
}
