package com.meowmasterextreme.clutteredvanillatextures.block.custom.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DoubleSinkBlock extends MultiblockPlacer implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED;
    private static final VoxelShape SHAPE;
    public static final IntegerProperty MULTIBLOCK_PART;
    private static final int[][][] MULTIBLOCK_SHAPE;

    public DoubleSinkBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(Items.BUCKET) && !stack.is(Items.GLASS_BOTTLE)) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        } else {
            if (!level.isClientSide) {
                ItemStack filledItem;
                SoundEvent sound;
                if (stack.is(Items.GLASS_BOTTLE)) {
                    ItemStack i = new ItemStack(Items.POTION);
                    i.set(DataComponents.POTION_CONTENTS, new PotionContents(Potions.WATER));
                    filledItem = i;
                    sound = SoundEvents.BOTTLE_FILL;
                } else {
                    filledItem = new ItemStack(Items.WATER_BUCKET);
                    sound = SoundEvents.BUCKET_FILL;
                }

                if (stack.getCount() == 1) {
                    player.setItemInHand(hand, filledItem);
                } else if (player.addItem(filledItem)) {
                    stack.shrink(1);
                } else {
                    player.drop(filledItem, true);
                    stack.shrink(1);
                }

                level.playSound((Player)null, pos, sound, SoundSource.BLOCKS);
            }

            return ItemInteractionResult.SUCCESS;
        }
    }

    public FluidState getFluidState(BlockState pState) {
        return (Boolean)pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if ((Boolean)pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    public int[][][] getMultiblockShape() {
        return MULTIBLOCK_SHAPE;
    }

    public IntegerProperty getMultiblockPart() {
        return MULTIBLOCK_PART;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(new Property[]{WATERLOGGED});
    }

    static {
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        SHAPE = Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F);
        MULTIBLOCK_PART = IntegerProperty.create("part", 1, 2);
        MULTIBLOCK_SHAPE = new int[][][]{{{1}, {2}}};
    }
}
