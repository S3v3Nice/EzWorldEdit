package ru.s3v3nice.ezworldedit.data;

import cn.nukkit.level.Position;
import ru.s3v3nice.ezworldedit.utils.WEUtils;

public class UndoData {
    private final BlockData[] undoArray;

    public UndoData(BlockData[] undoArray) {
        this.undoArray = undoArray;
    }

    public void undo() {
        for (BlockData blockData : undoArray) {
            Position position = blockData.block;
            if (position.level != null) {
                WEUtils.setBlock(position.level, (int) position.x, (int) position.y, (int) position.z, blockData);
            }
        }
    }
}
