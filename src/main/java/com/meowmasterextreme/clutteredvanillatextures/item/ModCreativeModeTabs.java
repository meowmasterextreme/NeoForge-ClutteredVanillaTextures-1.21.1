package com.meowmasterextreme.clutteredvanillatextures.item;

import com.meowmasterextreme.clutteredvanillatextures.ClutteredVanillaTextures;
import com.meowmasterextreme.clutteredvanillatextures.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ClutteredVanillaTextures.MOD_ID);

    public static final Supplier<CreativeModeTab> CLUTTERED_VANILLA_TEXTURES_TAB= CREATIVE_MODE_TAB.register("cluttered_vanilla_textures_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.OAK_TILED_SINK_RED.get()))
                    .title(Component.translatable("Cluttered Vanilla Textures"))
                    .displayItems((parameters, output) ->{

                        output.accept(ModBlocks.OAK_TILED_SINK_RED);
                        output.accept(ModBlocks.OAK_TILED_SINK_ORANGE);
                        output.accept(ModBlocks.OAK_TILED_SINK_YELLOW);
                        output.accept(ModBlocks.OAK_TILED_SINK_LIME);
                        output.accept(ModBlocks.OAK_TILED_SINK_GREEN);
                        output.accept(ModBlocks.OAK_TILED_SINK_CYAN);
                        output.accept(ModBlocks.OAK_TILED_SINK_LIGHT_BLUE);
                        output.accept(ModBlocks.OAK_TILED_SINK_BLUE);
                        output.accept(ModBlocks.OAK_TILED_SINK_PURPLE);
                        output.accept(ModBlocks.OAK_TILED_SINK_MAGENTA);
                        output.accept(ModBlocks.OAK_TILED_SINK_PINK);
                        output.accept(ModBlocks.OAK_TILED_SINK_WHITE);
                        output.accept(ModBlocks.OAK_TILED_SINK_LIGHT_GREY);
                        output.accept(ModBlocks.OAK_TILED_SINK_GREY);
                        output.accept(ModBlocks.OAK_TILED_SINK_BLACK);
                        output.accept(ModBlocks.OAK_TILED_SINK_BROWN);

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }

}