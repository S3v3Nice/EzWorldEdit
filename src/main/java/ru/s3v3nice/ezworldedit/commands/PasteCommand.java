package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.MessageFormatter;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;
import ru.s3v3nice.ezworldedit.utils.WEUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasteCommand extends Command {
    public PasteCommand() {
        super("paste", EzWorldEdit.getInstance().getMessage("paste.description"));
        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("replaceOnlyAir", true, CommandEnum.ENUM_BOOLEAN)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getInstance().getSession(player);
        CuboidArea copiedArea = session.getCopiedArea();

        if (copiedArea == null) {
            player.sendMessage(EzWorldEdit.getInstance().getMessage("paste.area-not-copied"));
            return false;
        }

        boolean replaceOnlyAir = strings.length >= 1 && strings[0].toLowerCase().matches("(?i)|on|true|t|1");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = WEUtils.pasteArea(copiedArea, player, replaceOnlyAir);
            session.setUndoData(undoData);
            player.sendMessage(EzWorldEdit.getInstance().getMessage("paste.success"));
        });
        executor.shutdown();

        return true;
    }
}
