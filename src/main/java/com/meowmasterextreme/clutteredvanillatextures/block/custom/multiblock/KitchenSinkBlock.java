package com.meowmasterextreme.clutteredvanillatextures.block.custom.multiblock;

import com.meowmasterextreme.clutteredvanillatextures.block.custom.SmallFurnitureBlock;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class KitchenSinkBlock extends SmallFurnitureBlock {
    private static final VoxelShape SHAPE_NORTH;
    private static final VoxelShape SHAPE_SOUTH;
    private static final VoxelShape SHAPE_EAST;
    private static final VoxelShape SHAPE_WEST;

    public KitchenSinkBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
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

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction facing = (Direction)pState.getValue(FACING);
        VoxelShape var10000;
        switch (facing) {
            case SOUTH -> var10000 = SHAPE_SOUTH;
            case EAST -> var10000 = SHAPE_EAST;
            case WEST -> var10000 = SHAPE_WEST;
            default -> var10000 = SHAPE_NORTH;
        }

        return var10000;
    }

    static {
        SHAPE_NORTH = Shapes.join(Block.box((double)0.0F, (double)0.0F, (double)1.0F, (double)16.0F, (double)14.0F, (double)16.0F), Block.box((double)0.0F, (double)14.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), BooleanOp.OR);
        SHAPE_SOUTH = Shapes.join(Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)14.0F, (double)15.0F), Block.box((double)0.0F, (double)14.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), BooleanOp.OR);
        SHAPE_EAST = Shapes.join(Block.box((double)0.0F, (double)0.0F, (double)0.0F, (double)15.0F, (double)14.0F, (double)16.0F), Block.box((double)0.0F, (double)14.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), BooleanOp.OR);
        SHAPE_WEST = Shapes.join(Block.box((double)1.0F, (double)0.0F, (double)0.0F, (double)16.0F, (double)14.0F, (double)16.0F), Block.box((double)0.0F, (double)14.0F, (double)0.0F, (double)16.0F, (double)16.0F, (double)16.0F), BooleanOp.OR);
    }
}
