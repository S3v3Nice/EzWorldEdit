package ru.s3v3nice.ezworldedit.data;

import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.nbt.tag.CompoundTag;

public class BlockEntityData extends BlockData {
    public final String blockEntityType;
    public final CompoundTag nbt;

    protected BlockEntityData(Block block, BlockEntity blockEntity) {
        super(block);
        this.blockEntityType = blockEntity.getClass().getSimpleName();
        blockEntity.saveNBT();
        this.nbt = blockEntity.namedTag.clone();
    }
}
