package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Messages;
import ru.s3v3nice.ezworldedit.session.Session;

public class Pos2Command extends Command {
    public Pos2Command() {
        super("pos2", Messages.get("pos.description", 2));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        session.setPos2(player.getPosition());

        player.sendMessage(Messages.get("pos.set", 2, (int) player.x, (int) player.y, (int) player.z));
        return true;
    }
}
