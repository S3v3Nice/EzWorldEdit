package ru.s3v3nice.ezworldedit.session;

import cn.nukkit.level.Position;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.data.UndoData;

public final class Session {
    private Position pos1;
    private Position pos2;
    private CuboidArea copiedArea;
    private UndoData undoData;

    public void setPos1(Position pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Position pos2) {
        this.pos2 = pos2;
    }

    public CuboidArea getSelectedArea() {
        if (pos1 != null && pos2 != null && pos1.level == pos2.level) {
            return new CuboidArea(pos1, pos2);
        } else {
            return null;
        }
    }

    public CuboidArea getCopiedArea() {
        return copiedArea;
    }

    public void setCopiedArea(CuboidArea copiedArea) {
        this.copiedArea = copiedArea;
    }

    public UndoData getUndoData() {
        return undoData;
    }

    public void setUndoData(UndoData undoData) {
        this.undoData = undoData;
    }
}
