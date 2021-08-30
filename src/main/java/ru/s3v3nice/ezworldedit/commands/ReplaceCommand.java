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
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Utils;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReplaceCommand extends Command {
    public ReplaceCommand() {
        super("replace", "Заменить блоки в выделенной области (EzWorldEdit)");

        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("from-block", CommandEnum.ENUM_BLOCK),
                CommandParameter.newEnum("to-block", CommandEnum.ENUM_BLOCK)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) return false;
        if (!testPermission(player)) return false;
        if (strings.length < 2) {
            player.sendMessage("Использование: /replace <id блока> <id блока>");
            return false;
        }

        Session session = EzWorldEdit.getSession(player);
        Position pos1 = session.getPos1();
        Position pos2 = session.getPos2();

        if (pos1 == null || pos2 == null) {
            player.sendMessage(TextFormat.RED + "Вы не выделили область!");
            return false;
        }

        Block fromBlock = Item.fromString(strings[0]).getBlockUnsafe();
        Block toBlock = Item.fromString(strings[1]).getBlockUnsafe();

        if (fromBlock == null || toBlock == null) {
            player.sendMessage(TextFormat.RED + "Неверно введен id блока!");
            return false;
        }

        boolean ignoreMeta = !strings[0].replace("minecraft:", "").contains(":");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = Utils.replaceInArea(new CuboidArea(pos1, pos2), fromBlock, toBlock, ignoreMeta);
            session.setUndoData(undoData);

            player.sendMessage(TextFormat.ITALIC + "" + TextFormat.LIGHT_PURPLE + "Блоки в области успешно заменились!");
        });
        executor.shutdown();

        return true;
    }
}
