package ru.naulbimix.nlbHideNicknames.listeners;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import ru.naulbimix.nlbHideNicknames.utils.ServerUtils;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        ServerUtils.getWhitelistWorldPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        ServerUtils.setShowPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if(!ServerUtils.hidesPlayers.containsKey(p.getUniqueId())) {
            ServerUtils.getWhitelistWorldPlayer(p);
        }else{
            ServerUtils.setHidePlayer(p);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if (!ServerUtils.hidesPlayers.containsKey(p.getUniqueId())) {
            ServerUtils.getWhitelistWorldPlayer(p);
        }else{
            ServerUtils.setHidePlayer(p);
        }
    }
}
