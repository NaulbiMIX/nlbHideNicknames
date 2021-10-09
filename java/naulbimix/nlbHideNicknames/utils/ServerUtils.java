package naulbimix.nlbHideNicknames.utils;

import org.bukkit.*;
import java.util.logging.*;
import naulbimix.nlbHideNicknames.*;

public class ServerUtils {

    public Main plugin;

    public ServerUtils(Main plugin) {
        this.plugin = plugin;
    }

    public String s(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public void Logger(String type, String message) {
        type = type.toUpperCase();
        type = type.equals("ERROR") ? "SEVERE" : (type.equals("WARN") ? "WARNING" : type);
        plugin.getLogger().log(Level.parse(type), s(message));
    }

}
