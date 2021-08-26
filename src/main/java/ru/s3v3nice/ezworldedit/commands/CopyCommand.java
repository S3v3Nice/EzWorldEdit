package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.session.Session;

public class CopyCommand extends Command {
    public CopyCommand() {
        super("copy", "Скопировать выделенную область (EzWorldEdit)");
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        Position pos1 = session.getPos1();
        Position pos2 = session.getPos2();

        if (pos1 == null || pos2 == null) {
            player.sendMessage(TextFormat.RED + "Вы не выделили область!");
            return false;
        }

        session.setCopiedArea(new CuboidArea(pos1, pos2));
        player.sendMessage(TextFormat.ITALIC + "" + TextFormat.GOLD + "Область скопирована!");
        return true;
    }
}
