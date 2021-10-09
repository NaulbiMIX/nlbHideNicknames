package naulbimix.nlbHideNicknames.manager;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.*;
import naulbimix.nlbHideNicknames.utils.config.*;

public class PlayerManager {

    public DataConfig dataConfig;
    public Scoreboard scoreboard;
    public String teamName = "hideNicknames"; // maximum size - 16, i no use substring for fix this moment, i just shortened the name of the string :)
    /*
    Список скрытых пользователей. Формат сохранения - UUID.
     */
    public HashMap<UUID, Long> hidesPlayers = new HashMap<>();

    public PlayerManager(DataConfig dataConfig) {
        this.dataConfig = dataConfig;
        scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        createTeam(scoreboard);
    }

    public void createTeam(Scoreboard scoreboard) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setNameTagVisibility(NameTagVisibility.NEVER);
     /*  try {
            team.setNameTagVisibility(NameTagVisibility.NEVER);
        }catch (IllegalArgumentException ignored) {} */
    }

    public void setHidePlayer(Player p) { // TODO: Replace get param scoreboardPlayer or delete this param and more edits this method
        UUID UUID = p.getUniqueId();
        if(hidesPlayers.containsKey(UUID)) return;
        hidesPlayers.put(UUID, System.currentTimeMillis());
        Scoreboard scoreboardPlayer = p.getScoreboard();
        if(scoreboardPlayer == null) {
            p.setScoreboard(scoreboard);
            p.getScoreboard() // if set new scoreboard, param scoreboardPlayer no updated..., useless new scoreboard in player
                    .getTeam(teamName).addPlayer((OfflinePlayer) p);
            return;
        }
        if(scoreboardPlayer.getTeam(teamName) == null) {
            createTeam(scoreboard);
            scoreboardPlayer.getTeam(teamName).addPlayer((OfflinePlayer) p);
            p.setScoreboard(scoreboardPlayer);
            return;
        }
        scoreboardPlayer.getTeam(teamName).addPlayer((OfflinePlayer) p);
        p.setScoreboard(scoreboardPlayer);
    }

    public void setShowPlayer(Player p) {
        Scoreboard scoreboardPlayer = p.getScoreboard();
        if(scoreboardPlayer == null) return;
        if(scoreboardPlayer.getTeam(teamName) == null) return;
        scoreboardPlayer.getTeam(teamName).removePlayer((OfflinePlayer) p);
        removeHidePlayers(p.getUniqueId());
    }

    public void setPlayerStatus(Player p) {
        if (dataConfig.getWhitelistWorlds().contains(p.getWorld().getName())) {
            setHidePlayer(p);
            return;
        }
        setShowPlayer(p);
    }

    public boolean isHidePlayer(UUID UUID) {
        return hidesPlayers.containsKey(UUID);
    }

    public void removeHidePlayers(UUID UUID) {
        hidesPlayers.remove(UUID);
    }

    public void clearHidePlayers() {
        hidesPlayers.clear();
    }

}
