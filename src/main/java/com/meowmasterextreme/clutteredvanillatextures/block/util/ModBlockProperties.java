package com.meowmasterextreme.clutteredvanillatextures.block.util;

import com.meowmasterextreme.clutteredvanillatextures.ClutteredVanillaTextures;
import com.meowmasterextreme.clutteredvanillatextures.block.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockProperties {
    public static final EnumProperty<SinkPart> SINK_PART = EnumProperty.create("part", SinkPart.class);

}
