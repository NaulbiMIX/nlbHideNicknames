package naulbimix.nlbHideNicknames;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.plugin.java.*;
import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import naulbimix.nlbHideNicknames.utils.*;
import naulbimix.nlbHideNicknames.event.*;
import naulbimix.nlbHideNicknames.manager.*;
import naulbimix.nlbHideNicknames.listeners.*;
import naulbimix.nlbHideNicknames.utils.config.*;

public class Main extends JavaPlugin {

    private static Main plugin;
    public DataConfig dataConfig;
    public ServerUtils serverUtils;
    public PlayerManager playerManager;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            serverUtils = new ServerUtils(this);
            serverUtils.Logger("info", "&aЗапуск &7плагина &enlbHideNicknames &7версии &e1.1 &7от &6NaulbiMIX&7!");
            /* anti turov pasting momentiym system, haha
            PluginDescriptionFile description = getDescription();
            if(!description.getVersion().equals("1.0") ||
                    !description.getAuthors().contains("NaulbiMIX") ||
                    !description.getWebsite().equals("https://naulbimix.ru/") ||
                    !description.getName().equals("nlbExploitPatcher") ||
                    !description.getMain().equals(Main.class.getName()) ||
                    !description.getDescription().equals("The plugin is custom-made for 150 rubles. The recompiled version. If the price were higher, there would be optimization.")
            ) {
                ServerUtils.Logger(
                        "error",
                        "&7При запуске плагина &enlbHideNicknames &7версии &e1.1 &7от &eNaulbiMIX &7произошла &cошибка&7! Причина: &cБыла замечена &6подмена авторства плагина&c! Плагин будет отключён."
                );
                Bukkit.getServer().getPluginManager().disablePlugin(this);
                return; // Бывает что даже при выключении дальше парсит класс, хотя это не всегда работает, но else нету, поэтому пусть будет.
            }
             */
            if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
                serverUtils.Logger("error","&7При запуске плагина &enlbHideNicknames &7версии &e1.1 &7от &eNaulbiMIX &7произошла &cошибка&7! Причина: &cОтсутвует плагин-зависимость &eProtocolLib&c! Плагин будет отключён.");
                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
            saveDefaultConfig();
            playerManager = new PlayerManager(dataConfig = new DataConfig(getConfig()));
            Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
            ProtocolLibrary.getProtocolManager().addPacketListener(new NameEntityEvent(this, ListenerPriority.HIGH, new PacketType[]{PacketType.Play.Server.NAMED_ENTITY_SPAWN}));
        }catch(NoClassDefFoundError ex) { // Error compiled plugin by IntelliJ IDEA (bugs) and delete class by users
            getLogger().severe("When loading the plugin nlbHideNicknames version 1.1 by NaulbiMIX a critical error has occurred - Classes plugin not founded!");
            getLogger().severe("Learn more about the error: " + ex.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            playerManager.clearHidePlayers();
            serverUtils.Logger("info", "&cВыключение &7плагина &enlbHideNicknames &7версии &e1.1 &7от &6NaulbiMIX&7!");
        }catch(Exception ignored) {} // fucking momentiym getting class or set params value
        HandlerList.unregisterAll(this);
    }

    public static Main getPlugin() {
        return plugin;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
