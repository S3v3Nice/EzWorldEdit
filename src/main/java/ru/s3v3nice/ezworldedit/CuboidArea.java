package ru.s3v3nice.ezworldedit;

import cn.nukkit.level.Level;
import cn.nukkit.level.Position;

public class CuboidArea {
    public final Level level;
    public final int minX;
    public final int minY;
    public final int minZ;
    public final int maxX;
    public final int maxY;
    public final int maxZ;

    public CuboidArea(Position pos1, Position pos2) {
        this.level = pos1.level;
        this.minX = Math.min(pos1.getFloorX(), pos2.getFloorX());
        this.minY = Math.min(pos1.getFloorY(), pos2.getFloorY());
        this.minZ = Math.min(pos1.getFloorZ(), pos2.getFloorZ());
        this.maxX = Math.max(pos1.getFloorX(), pos2.getFloorX());
        this.maxY = Math.max(pos1.getFloorY(), pos2.getFloorY());
        this.maxZ = Math.max(pos1.getFloorZ(), pos2.getFloorZ());
    }
}
