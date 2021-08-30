package ru.s3v3nice.ezworldedit.utils;

import cn.nukkit.block.Block;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.nbt.tag.CompoundTag;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.data.BlockData;
import ru.s3v3nice.ezworldedit.data.BlockEntityData;
import ru.s3v3nice.ezworldedit.data.UndoData;

import java.util.LinkedList;
import java.util.List;

public class WEUtils {
    public static void setBlock(Level level, int x, int y, int z, Block block) {
        setBlock(level, x, y, z, BlockData.get(block));
    }

    public static void setBlock(Level level, int x, int y, int z, BlockData blockData) {
        level.setBlock(x, y, z, blockData.block, false, false);

        if (blockData instanceof BlockEntityData blockEntityData) {
            CompoundTag nbt = blockEntityData.nbt.putInt("x", x).putInt("y", y).putInt("z", z);
            BlockEntity.createBlockEntity(blockEntityData.blockEntityType, level.getChunk(x >> 4, z >> 4), nbt);
        }
    }

    public static UndoData setArea(CuboidArea area, Block block) {
        Level level = area.level;
        List<BlockData> undoList = new LinkedList<>();

        for (int x = area.minX; x <= area.maxX; x++) {
            for (int y = area.minY; y <= area.maxY; y++) {
                for (int z = area.minZ; z <= area.maxZ; z++) {
                    undoList.add(BlockData.get(level.getBlock(x, y, z)));
                    setBlock(level, x, y, z, block);
                }
            }
        }

        return new UndoData(undoList.toArray(new BlockData[0]));
    }

    public static UndoData pasteArea(CuboidArea area, Position position) {
        Level level = position.level;
        int initialX = (int) position.x;
        int initialY = (int) position.y;
        int initialZ = (int) position.z;
        List<BlockData> undoList = new LinkedList<>();

        for (int x = area.minX, pasteX = initialX; x <= area.maxX; x++, pasteX++) {
            for (int y = area.minY, pasteY = initialY; y <= area.maxY; y++, pasteY++) {
                for (int z = area.minZ, pasteZ = initialZ; z <= area.maxZ; z++, pasteZ++) {
                    undoList.add(BlockData.get(level.getBlock(pasteX, pasteY, pasteZ)));
                    setBlock(level, pasteX, pasteY, pasteZ, level.getBlock(x, y, z));
                }
            }
        }

        return new UndoData(undoList.toArray(new BlockData[0]));
    }

    public static UndoData replaceInArea(CuboidArea area, Block fromBlock, Block toBlock, boolean ignoreMeta) {
        Level level = area.level;
        List<BlockData> undoList = new LinkedList<>();

        for (int x = area.minX; x <= area.maxX; x++) {
            for (int y = area.minY; y <= area.maxY; y++) {
                for (int z = area.minZ; z <= area.maxZ; z++) {
                    Block block = level.getBlock(x, y, z);
                    if (block.getId() == fromBlock.getId() && (ignoreMeta || block.getDamage() == fromBlock.getDamage())) {
                        undoList.add(BlockData.get(level.getBlock(x, y, z)));
                        setBlock(level, x, y, z, toBlock);
                    }
                }
            }
        }

        return new UndoData(undoList.toArray(new BlockData[0]));
    }
}
