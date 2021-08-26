package ru.s3v3nice.ezworldedit.data;

import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.level.Level;

public class BlockData {
    public final Block block;

    protected BlockData(Block block) {
        this.block = block;
    }

    public static BlockData get(Block block) {
        Level level = block.level;

        if (level != null) {
            BlockEntity blockEntity = level.getBlockEntity(block);

            if (blockEntity != null) {
                return new BlockEntityData(block, blockEntity);
            }
        }

        return new BlockData(block);
    }
}
