package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.session.Session;

public class Pos1Command extends Command {
    public Pos1Command() {
        super("pos1", "Установить 1 позицию (EzWorldEdit)");
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        session.setPos1(player.getPosition());

        player.sendMessage(TextFormat.MINECOIN_GOLD + "Позиция " + TextFormat.AQUA + 1 +
                TextFormat.MINECOIN_GOLD + " установлена (" + TextFormat.DARK_AQUA + (int) player.x + " " +
                (int) player.y + " " + (int) player.z + TextFormat.MINECOIN_GOLD + ")");

        return true;
    }
}
