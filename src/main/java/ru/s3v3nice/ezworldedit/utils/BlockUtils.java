package ru.s3v3nice.ezworldedit.utils;

import cn.nukkit.block.Block;

public class BlockUtils {
    public static Block getBlockFromString(String string) {
        String[] b = string.replace("minecraft:", "").split(":");
        int id;
        try {
            id = Integer.parseInt(b[0]);
            if (id > 255 || id < 0) return null;
        } catch (Exception e) {
            try {
                id = Block.class.getField(b[0].toUpperCase()).getInt(null);
            } catch (Exception e2) {
                return null;
            }
        }

        int meta = 0;
        if (b.length > 1) {
            try {
                meta = Math.min(Math.max(Integer.parseInt(b[1]), 0), 15);
            } catch (Exception ignored) {
            }
        }

        return Block.get(id, meta);
    }
}
