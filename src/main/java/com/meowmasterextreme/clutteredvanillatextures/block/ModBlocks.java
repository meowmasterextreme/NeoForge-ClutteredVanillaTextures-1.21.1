package com.meowmasterextreme.clutteredvanillatextures.block;

import com.meowmasterextreme.clutteredvanillatextures.ClutteredVanillaTextures;
import com.meowmasterextreme.clutteredvanillatextures.block.custom.multiblock.DoubleSinkBlock;
import com.meowmasterextreme.clutteredvanillatextures.block.custom.multiblock.KitchenSinkBlock;
import com.meowmasterextreme.clutteredvanillatextures.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ClutteredVanillaTextures.MOD_ID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems("clutteredvanillatextures");

    public static final DeferredBlock<Block> OAK_TILED_SINK_RED;
    public static final DeferredBlock<Block> OAK_TILED_SINK_ORANGE;
    public static final DeferredBlock<Block> OAK_TILED_SINK_YELLOW;
    public static final DeferredBlock<Block> OAK_TILED_SINK_LIME;
    public static final DeferredBlock<Block> OAK_TILED_SINK_GREEN;
    public static final DeferredBlock<Block> OAK_TILED_SINK_CYAN;
    public static final DeferredBlock<Block> OAK_TILED_SINK_LIGHT_BLUE;
    public static final DeferredBlock<Block> OAK_TILED_SINK_BLUE;
    public static final DeferredBlock<Block> OAK_TILED_SINK_PURPLE;
    public static final DeferredBlock<Block> OAK_TILED_SINK_MAGENTA;
    public static final DeferredBlock<Block> OAK_TILED_SINK_PINK;
    public static final DeferredBlock<Block> OAK_TILED_SINK_WHITE;
    public static final DeferredBlock<Block> OAK_TILED_SINK_LIGHT_GREY;
    public static final DeferredBlock<Block> OAK_TILED_SINK_GREY;
    public static final DeferredBlock<Block> OAK_TILED_SINK_BLACK;
    public static final DeferredBlock<Block> OAK_TILED_SINK_BROWN;

    public ModBlocks() {
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> DeferredBlock<T> registerFuelBlock(String name, Supplier<T> block, int burnTime) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerFuelBlockItem(name, toReturn, burnTime);
        return toReturn;
    }

    private static <T extends Block> DeferredItem<BlockItem> registerBlockItem(String name, DeferredBlock<T> block) {
        return BLOCK_ITEMS.register(name, () -> new BlockItem((Block)block.get(), new Item.Properties()));
    }

    private static <T extends Block> DeferredItem<BlockItem> registerFuelBlockItem(String name, DeferredBlock<T> block, int burnTime) {
        return BLOCK_ITEMS.register(name, () -> new BlockItem((Block)block.get(), new Item.Properties()) {
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return burnTime;
            }
        });
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }

    static {
        OAK_TILED_SINK_RED = registerBlock("oak_tiled_sink_red", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_ORANGE = registerBlock("oak_tiled_sink_orange", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_YELLOW = registerBlock("oak_tiled_sink_yellow", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_LIME = registerBlock("oak_tiled_sink_lime", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_GREEN = registerBlock("oak_tiled_sink_green", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_CYAN = registerBlock("oak_tiled_sink_cyan", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_LIGHT_BLUE = registerBlock("oak_tiled_sink_light_blue", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_BLUE = registerBlock("oak_tiled_sink_blue", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_PURPLE = registerBlock("oak_tiled_sink_purple", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_MAGENTA = registerBlock("oak_tiled_sink_magenta", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_PINK = registerBlock("oak_tiled_sink_pink", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_WHITE = registerBlock("oak_tiled_sink_white", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_LIGHT_GREY = registerBlock("oak_tiled_sink_light_grey", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_GREY = registerBlock("oak_tiled_sink_grey", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_BLACK = registerBlock("oak_tiled_sink_black", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
        OAK_TILED_SINK_BROWN = registerBlock("oak_tiled_sink_brown", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));

    }
}