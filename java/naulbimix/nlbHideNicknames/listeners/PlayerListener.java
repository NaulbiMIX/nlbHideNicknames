package naulbimix.nlbHideNicknames.listeners;

import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.event.player.*;
import naulbimix.nlbHideNicknames.*;
import naulbimix.nlbHideNicknames.manager.*;

public class PlayerListener implements Listener {

    public Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent e) {
        plugin.getPlayerManager().setPlayerStatus(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPlayerManager().setShowPlayer(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if(getPlayerManager().isHidePlayer(p.getUniqueId())) {
            getPlayerManager().setHidePlayer(p);
            return;
        }
        getPlayerManager().setPlayerStatus(p);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if(getPlayerManager().isHidePlayer(p.getUniqueId())) {
            getPlayerManager().setHidePlayer(p);
            return;
        }
        getPlayerManager().setShowPlayer(p);
    }

    public PlayerManager getPlayerManager() {
        return plugin.getPlayerManager();
    }
}
