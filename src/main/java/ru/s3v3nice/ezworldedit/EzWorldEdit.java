package ru.s3v3nice.ezworldedit;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.plugin.PluginBase;
import ru.s3v3nice.ezworldedit.commands.*;
import ru.s3v3nice.ezworldedit.session.Session;
import ru.s3v3nice.ezworldedit.session.SessionManager;

public final class EzWorldEdit extends PluginBase {
    private static EzWorldEdit instance;
    private boolean isBlockIdShown;
    private SessionManager sessionManager;
    private MessageFormatter messageFormatter;

    public static EzWorldEdit getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        isBlockIdShown = getConfig().getBoolean("show-blockid-on-rightclick");
        sessionManager = new SessionManager();
        messageFormatter = new MessageFormatter();
    }

    @Override
    public void onEnable() {
        registerPlayers();
        registerCommands();
        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    private void registerPlayers() {
        for (Player player : getServer().getOnlinePlayers().values()) {
            addSession(player);
        }
    }

    private void registerCommands() {
        Command[] commands = new Command[]{
                new Pos1Command(),
                new Pos2Command(),
                new WandCommand(),
                new SetCommand(),
                new ReplaceCommand(),
                new CopyCommand(),
                new PasteCommand(),
                new UndoCommand()
        };

        for (Command command : commands) {
            getServer().getCommandMap().register("", command);
        }
    }

    public boolean isBlockIdShown() {
        return isBlockIdShown;
    }

    public String getMessage(String key, Object... vars) {
        return messageFormatter.formatMessage(key, vars);
    }

    public Session getSession(Player player) {
        return sessionManager.get(player);
    }

    public void addSession(Player player) {
        sessionManager.add(player);
    }

    public void removeSession(Player player) {
        sessionManager.remove(player);
    }
}
