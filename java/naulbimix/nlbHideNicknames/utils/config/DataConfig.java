package naulbimix.nlbHideNicknames.utils.config;

import java.util.*;
import org.bukkit.configuration.file.*;

public class DataConfig {

    private FileConfiguration config;
    private List<String> whitelistWorlds;

    public DataConfig(FileConfiguration config) {
        this.config = config;
        this.whitelistWorlds = config.getStringList("settings.whitelistWorlds"); // no use many gets string list in file :)
    }

    public List<String> getWhitelistWorlds() {
        return whitelistWorlds;
    }
}
