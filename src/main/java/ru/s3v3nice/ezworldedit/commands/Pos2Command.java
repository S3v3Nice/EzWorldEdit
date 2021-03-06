package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.MessageFormatter;
import ru.s3v3nice.ezworldedit.session.Session;

public class Pos2Command extends Command {
    public Pos2Command() {
        super("pos2", EzWorldEdit.getInstance().getMessage("pos.description", 2));

        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newType("position", true, CommandParamType.POSITION)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;

        Session session = EzWorldEdit.getInstance().getSession(player);
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
                player.sendMessage(EzWorldEdit.getInstance().getMessage("coordinates-invalid"));
                return false;
            }
        }

        session.setPos2(position);
        player.sendMessage(EzWorldEdit.getInstance().getMessage("pos.set", 2, position.getFloorX(), position.getFloorY(), position.getFloorZ()));
        return true;
    }
}
