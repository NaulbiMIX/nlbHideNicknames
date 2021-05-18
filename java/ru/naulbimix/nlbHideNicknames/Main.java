package ru.naulbimix.nlbHideNicknames;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import org.bukkit.*;
import org.bukkit.scoreboard.*;
import org.bukkit.plugin.java.*;
import ru.naulbimix.nlbHideNicknames.utils.*;
import ru.naulbimix.nlbHideNicknames.listeners.*;

public class Main extends JavaPlugin {

    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ServerUtils.Logger("&7Запуск плагина &enlbHideNicknames &7версии &e" + getDescription().getVersion() + " &7от &6vk.com/evildonat");
        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
            ServerUtils.Logger("&7Отсутвует плагин-зависимость &eProtocolLib");
            Bukkit.getPluginManager().disablePlugin(this);
        }else{
            saveDefaultConfig();
            ServerUtils.hidesPlayers.clear();
            ServerUtils.scoreboard = getServer().getScoreboardManager().getNewScoreboard();
            ServerUtils.createTeam(ServerUtils.scoreboard);
            Bukkit.getPluginManager().registerEvents(new EventListener(), this);
            ProtocolLibrary.getProtocolManager().addPacketListener(new TabListener(this, ListenerPriority.HIGH, new PacketType[]{PacketType.Play.Server.NAMED_ENTITY_SPAWN}));
        }
    }

    @Override
    public void onDisable() {
        ServerUtils.hidesPlayers.clear();
    }

    public static Main getPlugin() {
        return plugin;
    }
}
