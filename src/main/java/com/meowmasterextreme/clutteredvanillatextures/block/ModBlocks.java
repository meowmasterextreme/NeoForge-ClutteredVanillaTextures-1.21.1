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

    public static final DeferredBlock<Block> SWEETHEART_SINK;

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
        SWEETHEART_SINK = registerBlock("sweetheart_sink", () -> new DoubleSinkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(DyeColor.BROWN).noOcclusion()));
    }
}