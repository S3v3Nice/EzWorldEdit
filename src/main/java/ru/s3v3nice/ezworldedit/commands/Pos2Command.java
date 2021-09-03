package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Messages;
import ru.s3v3nice.ezworldedit.session.Session;

public class Pos2Command extends Command {
    public Pos2Command() {
        super("pos2", Messages.get("pos.description", 2));

        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newType("position", true, CommandParamType.POSITION)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getSession(player);
        Position position;

        if (strings.length < 3) {
            position = player.getPosition();
        } else {
            try {
                int x = (int) (strings[0].equals("~") ? player.x : Double.parseDouble(strings[0]));
                int y = (int) (strings[1].equals("~") ? player.y : Double.parseDouble(strings[1]));
                int z = (int) (strings[2].equals("~") ? player.z : Double.parseDouble(strings[2]));
                position = new Position(x, y, z, player.level);
            } catch (Exception e) {
                player.sendMessage(Messages.get("coordinates-invalid"));
                return false;
            }
        }

        session.setPos2(position);
        player.sendMessage(Messages.get("pos.set", 2, (int) position.x, (int) position.y, (int) position.z));
        return true;
    }
}
