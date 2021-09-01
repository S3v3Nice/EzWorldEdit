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

public final class SetCommand extends Command {
    public SetCommand() {
        super("set", Messages.get("set.description"));

        setPermission("ezworldedit.*");
        addCommandParameters("default", new CommandParameter[]{
                CommandParameter.newEnum("id", CommandEnum.ENUM_BLOCK)
        });
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!testPermission(player)) return false;
        if (args.length < 1) {
            player.sendMessage(Messages.get("set.usage"));
            return false;
        }

        Session session = EzWorldEdit.getSession(player);
        CuboidArea area = session.getSelectedArea();

        if (area == null) {
            player.sendMessage(Messages.get("area-not-selected"));
            return false;
        }

        Block block = BlockUtils.getBlockFromString(args[0]);
        if (block == null) {
            player.sendMessage(Messages.get("blockid-invalid"));
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            UndoData undoData = WEUtils.setArea(area, block);
            session.setUndoData(undoData);
            player.sendMessage(Messages.get("set.success"));
        });
        executor.shutdown();

        return true;
    }
}
