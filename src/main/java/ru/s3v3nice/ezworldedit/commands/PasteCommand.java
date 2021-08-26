package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Utils;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasteCommand extends Command {
    public PasteCommand() {
        super("paste", "Вставить скопированную область (EzWorldEdit)");
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        CuboidArea copiedArea = session.getCopiedArea();

        if (copiedArea == null) {
            player.sendMessage(TextFormat.RED + "Вы не скопировали область!");
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = Utils.pasteArea(copiedArea, player);
            session.setUndoData(undoData);
            player.sendMessage(TextFormat.ITALIC + "" + TextFormat.AQUA + "Вставка области прошла успешно!");
        });
        executor.shutdown();

        return true;
    }
}
