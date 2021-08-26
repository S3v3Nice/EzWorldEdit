package ru.s3v3nice.ezworldedit;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import ru.s3v3nice.ezworldedit.commands.*;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;
import ru.s3v3nice.ezworldedit.session.SessionManager;

public final class EzWorldEdit extends PluginBase {
    private static boolean isBlockIdShown;

    @Override
    public void onLoad() {
        saveDefaultConfig();
        isBlockIdShown = getConfig().getBoolean("show-blockid-on-rightclick");
    }

    @Override
    public void onEnable() {
        registerPlayers();
        registerCommands();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    private void registerPlayers() {
        for (Player player : getServer().getOnlinePlayers().values()) {
            SessionManager.add(player);
        }
    }

    private void registerCommands() {
        Command[] commands = new Command[]{
                new WandCommand(),
                new SetCommand(),
                new CopyCommand(),
                new PasteCommand(),
                new UndoCommand()
        };

        for (Command command : commands) {
            getServer().getCommandMap().register("", command);
        }
    }

    public static boolean isBlockIdShown() {
        return isBlockIdShown;
    }

    public static Session getSession(Player player) {
        return SessionManager.get(player);
    }

    public static void setBlock(Level level, int x, int y, int z, Block block) {
        Utils.setBlock(level, x, y, z, block);
    }

    public static UndoData setArea(CuboidArea area, Block block) {
        return Utils.setArea(area, block);
    }

    public static UndoData pasteArea(CuboidArea area, Position position) {
        return Utils.pasteArea(area, position);
    }
}