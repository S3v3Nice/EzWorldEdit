package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandEnum;
import cn.nukkit.command.data.CommandParameter;
import ru.s3v3nice.ezworldedit.CuboidArea;
import ru.s3v3nice.ezworldedit.EzWorldEdit;
import ru.s3v3nice.ezworldedit.Messages;
import ru.s3v3nice.ezworldedit.data.UndoData;
import ru.s3v3nice.ezworldedit.session.Session;
import ru.s3v3nice.ezworldedit.utils.BlockUtils;
import ru.s3v3nice.ezworldedit.utils.WEUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReplaceCommand extends Command {
    public ReplaceCommand() {
        super("replace", Messages.get("replace.description"));

        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("from-id", CommandEnum.ENUM_BLOCK),
                CommandParameter.newEnum("to-id", CommandEnum.ENUM_BLOCK)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;
        if (strings.length < 2) {
            player.sendMessage(Messages.get("replace.usage"));
            return false;
        }

        Session session = EzWorldEdit.getSession(player);
        CuboidArea area = session.getSelectedArea();

        if (area == null) {
            player.sendMessage(Messages.get("area-not-selected"));
            return false;
        }

        Block fromBlock = BlockUtils.getBlockFromString(strings[0]);
        Block toBlock = BlockUtils.getBlockFromString(strings[1]);

        if (fromBlock == null || toBlock == null) {
            player.sendMessage(Messages.get("blockid-invalid"));
            return false;
        }

        boolean ignoreMeta = !strings[0].replace("minecraft:", "").contains(":");

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = WEUtils.replaceInArea(area, fromBlock, toBlock, ignoreMeta);
            session.setUndoData(undoData);

            player.sendMessage(Messages.get("replace.success"));
        });
        executor.shutdown();

        return true;
    }
}
