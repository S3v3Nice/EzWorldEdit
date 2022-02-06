package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UndoCommand extends Command {
    public UndoCommand() {
        super("undo", EzWorldEdit.getInstance().getMessage("undo.description"));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getInstance().getSession(player);
        UndoData undoData = session.getUndoData();

        if (undoData == null) {
            player.sendMessage(EzWorldEdit.getInstance().getMessage("undo.no-action"));
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            undoData.undo();
            session.setUndoData(null);
            player.sendMessage(EzWorldEdit.getInstance().getMessage("undo.success"));
        });
        executor.shutdown();

        return true;
    }
}
