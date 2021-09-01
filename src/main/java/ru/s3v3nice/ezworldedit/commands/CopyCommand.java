package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Messages;
import ru.s3v3nice.ezworldedit.session.Session;

public class CopyCommand extends Command {
    public CopyCommand() {
        super("copy", Messages.get("copy.description"));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        CuboidArea area = session.getSelectedArea();

        if (area == null) {
            player.sendMessage(Messages.get("area-not-selected"));
            return false;
        }

        session.setCopiedArea(area);
        player.sendMessage(Messages.get("copy.success"));
        return true;
    }
}
