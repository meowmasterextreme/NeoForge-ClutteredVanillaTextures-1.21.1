package com.meowmasterextreme.clutteredvanillatextures.block.util;

import net.minecraft.util.StringRepresentable;

public enum SinkPart implements StringRepresentable {
    RIGHT("right"),
    LEFT("left");

    private final String name;

    private SinkPart(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
