package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.Utils;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SetCommand extends Command {
    public SetCommand() {
        super("set", "Заполнить выделенную область (EzWorldEdit)");

        setPermission("ezworldedit.*");

        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("blockName", CommandEnum.ENUM_BLOCK),
                CommandParameter.newType("data", true, CommandParamType.INT)
        });
        addCommandParameters("byStringId", new CommandParameter[]{
                CommandParameter.newType("id", CommandParamType.STRING),
                CommandParameter.newType("data", true, CommandParamType.INT)
        });
        addCommandParameters("byId", new CommandParameter[]{
                CommandParameter.newType("id", CommandParamType.INT),
                CommandParameter.newType("data", true, CommandParamType.INT)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!testPermission(player)) return false;
        if (args.length < 1) {
            player.sendMessage("Использование: /set <id блока> <data блока>");
            return false;
        }

        Block block;
        try {
            Item item = Item.fromString(args[0]);
            int data = args.length > 1 ? Integer.parseInt(args[1]) : 0;
            block = Block.get(item.getId(), data);
        } catch (Exception e) {
            player.sendMessage(TextFormat.RED + "Неверный формат ввода!");
            return false;
        }

        Session session = EzWorldEdit.getSession(player);
        Position pos1 = session.getPos1();
        Position pos2 = session.getPos2();

        if (pos1 == null || pos2 == null) {
            player.sendMessage(TextFormat.RED + "Вы не выделили область!");
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = Utils.setArea(new CuboidArea(pos1, pos2), block);
            session.setUndoData(undoData);
            player.sendMessage(TextFormat.ITALIC + "" + TextFormat.LIGHT_PURPLE + "Область успешно заполнилась!");
        });
        executor.shutdown();

        return true;
    }
}
