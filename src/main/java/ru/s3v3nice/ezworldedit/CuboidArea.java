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
        this.minX = (int) Math.min(pos1.x, pos2.x);
        this.minY = (int) Math.min(pos1.y, pos2.y);
        this.minZ = (int) Math.min(pos1.z, pos2.z);
        this.maxX = (int) Math.max(pos1.x, pos2.x);
        this.maxY = (int) Math.max(pos1.y, pos2.y);
        this.maxZ = (int) Math.max(pos1.z, pos2.z);
    }
}
