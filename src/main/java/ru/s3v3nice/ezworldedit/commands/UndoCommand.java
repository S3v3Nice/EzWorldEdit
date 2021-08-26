package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UndoCommand extends Command {
    public UndoCommand() {
        super("undo", "Отменить последнее действие (EzWorldEdit)");
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        UndoData undoData = session.getUndoData();

        if (undoData == null) {
            player.sendMessage(TextFormat.RED + "Нет действия, которое можно отменить. ");
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            undoData.undo();
            session.setUndoData(null);
            player.sendMessage(TextFormat.GRAY + "Последнее действие было отменено.");
        });
        executor.shutdown();

        return true;
    }
}
