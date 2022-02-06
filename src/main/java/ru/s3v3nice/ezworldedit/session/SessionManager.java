package ru.s3v3nice.ezworldedit.session;

import cn.nukkit.Player;

import java.util.HashMap;
import java.util.Map;

public final class SessionManager {
    private final Map<String, Session> sessions = new HashMap<>();

    public void add(Player player) {
        sessions.put(player.getName(), new Session());
    }

    public void remove(Player player) {
        sessions.remove(player.getName());
    }

    public Session get(Player player) {
        return sessions.get(player.getName());
    }
}
