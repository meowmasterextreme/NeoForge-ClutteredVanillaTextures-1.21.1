package com.meowmasterextreme.clutteredvanillatextures.item;

import com.meowmasterextreme.clutteredvanillatextures.ClutteredVanillaTextures;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ClutteredVanillaTextures.MOD_ID);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
