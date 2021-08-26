package ru.s3v3nice.ezworldedit.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;

public final class WandCommand extends Command {
    public WandCommand() {
        super("wand", "Получить топорик для выделения (EzWorldEdit)");
        setPermission("ezworldedit.*");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!testPermission(player)) return false;

        Item item = Item.get(Item.GOLD_AXE)
                .setNamedTag(new CompoundTag().put("EzWorldEdit", new CompoundTag()))
                .setCustomName(TextFormat.RED + "EzWorldEdit");

        Item[] notAdded = ((Player) sender).getInventory().addItem(item);
        switch (notAdded.length) {
            case 0 -> sender.sendMessage(TextFormat.YELLOW + "" + TextFormat.ITALIC + "Приятного строительства.");
            case 1 -> sender.sendMessage(TextFormat.GOLD + "" + TextFormat.ITALIC + "В инвентаре не хватает места для нового предмета!");
        }

        return true;
    }
}
