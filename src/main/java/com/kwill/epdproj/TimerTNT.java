package com.kwill.epdproj;

import com.kwill.epdproj.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by Kyle on 10/24/2015.
 */
@Mod(modid = Resources.modid, version = Resources.version)
public class TimerTNT
{
    @Mod.Instance(Resources.modid)
    public static TimerTNT instance;

    @SidedProxy(clientSide = "com.kwill.epdproj.proxy.CombinedClientProxy", serverSide = "com.kwill.epdproj.proxy.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
}
