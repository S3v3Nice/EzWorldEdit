package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.session.Session;

public class CopyCommand extends Command {
    public CopyCommand() {
        super("copy", EzWorldEdit.getInstance().getMessage("copy.description"));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getInstance().getSession(player);
        CuboidArea area = session.getSelectedArea();

        if (area == null) {
            player.sendMessage(EzWorldEdit.getInstance().getMessage("area-not-selected"));
            return false;
        }

        session.setCopiedArea(area);
        player.sendMessage(EzWorldEdit.getInstance().getMessage("copy.success"));
        return true;
    }
}
