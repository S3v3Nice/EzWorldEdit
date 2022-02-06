package ru.s3v3nice.ezworldedit;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;

public class EventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        EzWorldEdit.getInstance().addSession(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        EzWorldEdit.getInstance().removeSession(event.getPlayer());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("ezworldedit.*")) return;

        Item heldItem = player.getInventory().getItemInHand();
        if (heldItem.getNamedTagEntry("EzWorldEdit") == null) return;

        event.setCancelled();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getAction() != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK &&
                event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("ezworldedit.*")) return;

        Item heldItem = player.getInventory().getItemInHand();
        if (heldItem.getNamedTagEntry("EzWorldEdit") == null) return;

        event.setCancelled();

        Block block = event.getBlock();
        int posNum = event.getAction() == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK ? 1 : 2;

        switch (posNum) {
            case 1:
                EzWorldEdit.getInstance().getSession(player).setPos1(block);
                break;
            case 2:
                EzWorldEdit.getInstance().getSession(player).setPos2(block);
                break;
        }

        String message = EzWorldEdit.getInstance().getMessage("pos.set", posNum, (int) block.x, (int) block.y, (int) block.z);
        player.sendMessage(message);
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent event) {
        if (!EzWorldEdit.getInstance().isBlockIdShown()) return;
        if (event.getAction() != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        if (!player.hasPermission("ezworldedit.*")) return;

        Block block = event.getBlock();
        player.sendTip(TextFormat.YELLOW + "ID: " + TextFormat.AQUA + block.getId() +
                TextFormat.YELLOW + ", Meta: " + TextFormat.AQUA + block.getDamage());
    }
}
