package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Messages;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;
import ru.s3v3nice.ezworldedit.utils.WEUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasteCommand extends Command {
    public PasteCommand() {
        super("paste", Messages.get("paste.description"));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        CuboidArea copiedArea = session.getCopiedArea();

        if (copiedArea == null) {
            player.sendMessage(Messages.get("paste.area-not-copied"));
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = WEUtils.pasteArea(copiedArea, player);
            session.setUndoData(undoData);
            player.sendMessage(Messages.get("paste.success"));
        });
        executor.shutdown();

        return true;
    }
}
