package com.meowmasterextreme.clutteredvanillatextures.block.custom.multiblock;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class MultiblockPlacer extends Block {
    private static final MapCodec<MultiblockPlacer> CODEC = simpleCodec(MultiblockPlacer::new);
    private static final IntegerProperty MULTIBLOCK_PART = IntegerProperty.create("part", 1, 2);
    public static final DirectionProperty FACING;
    private final int[][][] MULTIBLOCK_SHAPE = new int[][][]{{{1}}};

    public int[][][] getMultiblockShape() {
        return this.MULTIBLOCK_SHAPE;
    }

    public IntegerProperty getMultiblockPart() {
        return MULTIBLOCK_PART;
    }

    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public MultiblockPlacer(BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(this.getMultiblockPart(), 1)).setValue(FACING, Direction.NORTH));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(new Property[]{FACING}).add(new Property[]{this.getMultiblockPart()});
    }

    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Level level = pContext.getLevel();
        BlockPos OGpos = pContext.getClickedPos();
        Direction direction = pContext.getHorizontalDirection();
        int[][][] multiblockShape = this.getMultiblockShape();
        int maxHeight = this.getMultiblockShape().length;
        int OGx = OGpos.getX();
        int OGy = OGpos.getY();
        int OGz = OGpos.getZ();
        int xOffset = 0;
        int zOffset = 0;
        if (OGpos.getY() + maxHeight >= level.getMaxBuildHeight()) {
            return null;
        } else {
            for(int y = 0; y < maxHeight; ++y) {
                for(int x = 0; x < multiblockShape[y].length; ++x) {
                    for(int z = 0; z < multiblockShape[y][x].length; ++z) {
                        if (multiblockShape[y][x][z] != 0) {
                            xOffset = this.getXOffset(direction, x, z);
                            zOffset = this.getZOffset(direction, x, z);
                            if (!level.getBlockState(new BlockPos(OGx + xOffset, OGy + y, OGz + zOffset)).canBeReplaced()) {
                                return null;
                            }
                        }
                    }
                }
            }

            return (BlockState)this.defaultBlockState().setValue(FACING, direction);
        }
    }

    protected int getXOffset(Direction facing, int x, int z) {
        int xOffset = 0;
        if (facing.equals(Direction.NORTH)) {
            xOffset = x;
        }

        if (facing.equals(Direction.SOUTH)) {
            xOffset = -x;
        }

        if (facing.equals(Direction.EAST)) {
            xOffset = z;
        }

        if (facing.equals(Direction.WEST)) {
            xOffset = -z;
        }

        return xOffset;
    }

    protected int getZOffset(Direction facing, int x, int z) {
        int zOffset = 0;
        if (facing.equals(Direction.NORTH)) {
            zOffset = -z;
        }

        if (facing.equals(Direction.SOUTH)) {
            zOffset = z;
        }

        if (facing.equals(Direction.EAST)) {
            zOffset = x;
        }

        if (facing.equals(Direction.WEST)) {
            zOffset = -x;
        }

        return zOffset;
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockPos state1Pos = null;
        if ((Integer)pState.getValue(this.getMultiblockPart()) != 1) {
            state1Pos = this.findBlockState1(pPos, pLevel);
        } else {
            state1Pos = pPos;
        }

        if (state1Pos != null) {
            if (!this.canSurvive(pLevel.getBlockState(state1Pos), pLevel, state1Pos)) {
                pLevel.destroyBlock(pPos, true);
            }
        } else {
            pLevel.destroyBlock(pPos, true);
        }

    }

    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        BlockPos state1Pos = null;
        if ((Integer)pState.getValue(this.getMultiblockPart()) != 1) {
            state1Pos = this.findBlockState1(pCurrentPos, pLevel);
        } else {
            state1Pos = pCurrentPos;
        }

        if (state1Pos != null) {
            if (!this.canSurvive(pLevel.getBlockState(state1Pos), pLevel, state1Pos)) {
                pLevel.scheduleTick(pCurrentPos, this, 0);
            }
        } else {
            pLevel.scheduleTick(pCurrentPos, this, 0);
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (!pLevel.getBlockState(pPos).is(this)) {
            return true;
        } else {
            Direction facing = (Direction)pState.getValue(FACING);
            if ((Integer)pState.getValue(this.getMultiblockPart()) != 1) {
                return true;
            } else {
                int OGx = pPos.getX();
                int OGy = pPos.getY();
                int OGz = pPos.getZ();
                int[][][] multiblockShape = this.getMultiblockShape();

                for(int y = 0; y < multiblockShape.length; ++y) {
                    for(int x = 0; x < multiblockShape[y].length; ++x) {
                        for(int z = 0; z < multiblockShape[y][x].length; ++z) {
                            int xOffset = this.getXOffset(facing, x, z);
                            int zOffset = this.getZOffset(facing, x, z);
                            int part = 0;
                            BlockState state = pLevel.getBlockState(new BlockPos(OGx + xOffset, OGy + y, OGz + zOffset));
                            if (state.hasProperty(this.getMultiblockPart())) {
                                part = (Integer)state.getValue(this.getMultiblockPart());
                            }

                            if (multiblockShape[y][x][z] != 0 && multiblockShape[y][x][z] != part) {
                                return false;
                            }
                        }
                    }
                }

                return true;
            }
        }
    }

    protected BlockPos findBlockState1(BlockPos currentPos, LevelAccessor level) {
        if (!level.getBlockState(currentPos).hasProperty(FACING)) {
            return null;
        } else {
            Direction facing = (Direction)level.getBlockState(currentPos).getValue(FACING);
            int[][][] multiblockShape = this.getMultiblockShape();
            int partNum = (Integer)level.getBlockState(currentPos).getValue(this.getMultiblockPart());

            for(int y = 0; y < multiblockShape.length; ++y) {
                for(int x = 0; x < multiblockShape[y].length; ++x) {
                    for(int z = 0; z < multiblockShape[y][x].length; ++z) {
                        if (multiblockShape[y][x][z] == partNum) {
                            int xOffset = -this.getXOffset(facing, x, z);
                            int zOffset = -this.getZOffset(facing, x, z);
                            BlockPos possibleState1 = new BlockPos(currentPos.getX() + xOffset, currentPos.getY() - y, currentPos.getZ() + zOffset);
                            if (level.getBlockState(possibleState1).is(this.asBlock()) && ((Direction)level.getBlockState(possibleState1).getValue(FACING)).equals(facing)) {
                                return possibleState1;
                            }
                        }
                    }
                }
            }

            return null;
        }
    }

    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pLevel.isClientSide) {
            Direction direction = (Direction)pState.getValue(FACING);
            int[][][] multiblockShape = this.getMultiblockShape();
            int OGx = pPos.getX();
            int OGy = pPos.getY();
            int OGz = pPos.getZ();
            if ((Integer)pState.getValue(this.getMultiblockPart()) == 1 && !pOldState.is(this.asBlock())) {
                for(int y = 0; y < multiblockShape.length; ++y) {
                    for(int x = 0; x < multiblockShape[y].length; ++x) {
                        for(int z = 0; z < multiblockShape[y][x].length; ++z) {
                            if (x + y + z != 0 && multiblockShape[y][x][z] != 0) {
                                int xOffset = this.getXOffset(direction, x, z);
                                int zOffset = this.getZOffset(direction, x, z);
                                pLevel.setBlock(new BlockPos(OGx + xOffset, OGy + y, OGz + zOffset), (BlockState)((BlockState)this.defaultBlockState().setValue(this.getMultiblockPart(), multiblockShape[y][x][z])).setValue(FACING, direction), 2);
                            }
                        }
                    }
                }
            }
        }

    }

    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    static {
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
