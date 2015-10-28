package com.kwill.epdproj.proxy;

import com.kwill.epdproj.entity.EntityTimerTNTPrimed;
import com.kwill.epdproj.renderer.entity.RenderTimerTNTPrimed;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

/**
 * Created by Kyle on 10/25/2015.
 */
public class CombinedClientProxy extends CommonProxy
{
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
        RenderingRegistry.registerEntityRenderingHandler(EntityTimerTNTPrimed.class, new RenderTimerTNTPrimed());
    }
}
