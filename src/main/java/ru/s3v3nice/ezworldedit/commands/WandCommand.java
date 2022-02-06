package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import ru.s3v3nice.ezworldedit.EzWorldEdit;

public final class WandCommand extends Command {
    public WandCommand() {
        super("wand", EzWorldEdit.getInstance().getMessage("wand.description"));
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (!testPermission(player)) return false;

        Item item = Item.get(Item.GOLD_AXE)
                .setNamedTag(new CompoundTag().put("EzWorldEdit", new CompoundTag()))
                .setCustomName(TextFormat.RED + "EzWorldEdit");
        Item[] notAdded = player.getInventory().addItem(item);

        switch (notAdded.length) {
            case 0:
                commandSender.sendMessage(EzWorldEdit.getInstance().getMessage("wand.success"));
                break;
            case 1:
                commandSender.sendMessage(EzWorldEdit.getInstance().getMessage("wand.no-space"));
                break;
        }

        return true;
    }
}
