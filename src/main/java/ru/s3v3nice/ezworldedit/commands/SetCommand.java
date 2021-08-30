package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
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
                CommandParameter.newEnum("blockId", CommandEnum.ENUM_BLOCK)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!testPermission(player)) return false;
        if (args.length < 1) {
            player.sendMessage("Использование: /set <id блока>");
            return false;
        }

        Session session = EzWorldEdit.getSession(player);
        Position pos1 = session.getPos1();
        Position pos2 = session.getPos2();

        if (pos1 == null || pos2 == null) {
            player.sendMessage(TextFormat.RED + "Вы не выделили область!");
            return false;
        }

        Block block = Item.fromString(args[0]).getBlockUnsafe();
        if (block == null) {
            player.sendMessage(TextFormat.RED + "Неверно введен id блока!");
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
