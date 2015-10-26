package com.kwill.epdproj.proxy;

import com.kwill.epdproj.entity.EntityTimerTNTPrimed;
import com.kwill.epdproj.renderer.entity.RenderTimerTNTPrimed;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * Created by Kyle on 10/25/2015.
 */
public class CombinedClientProxy extends CommonProxy
{
    public void registerRenders()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityTimerTNTPrimed.class, new RenderTimerTNTPrimed());
    }
}
