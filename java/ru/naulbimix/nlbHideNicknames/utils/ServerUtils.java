package ru.naulbimix.nlbHideNicknames.utils;

import java.util.*;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.naulbimix.nlbHideNicknames.*;

public class ServerUtils {

    /*
    Список пользователей скрытых. Формат сохранения - UUID.
     */
    public static Scoreboard scoreboard;
    public static String teamName = "hideNicknames";
    private static Main plugin = Main.getPlugin();
    public static HashMap<UUID, Long> hidesPlayers = new HashMap<>();

    public static String s(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void Logger(String message) {
        Bukkit.getConsoleSender().sendMessage(s(message));
    }

    public static void createTeam(Scoreboard scoreboard) {
        Team team = scoreboard.registerNewTeam(teamName);
        team.setNameTagVisibility(NameTagVisibility.NEVER);
    }

    public static void setHidePlayer(Player p) {
        UUID UUID = p.getUniqueId();
        if(!hidesPlayers.containsKey(UUID)) {
            hidesPlayers.put(UUID, System.currentTimeMillis());
            if (p.getScoreboard() != null) {
                if (p.getScoreboard().getTeam(teamName) != null) {
                    p.getScoreboard().getTeam(teamName).addPlayer((OfflinePlayer)p);
                } else {
                    createTeam(p.getScoreboard());
                    p.getScoreboard().getTeam(teamName).addPlayer((OfflinePlayer)p);
                }
            } else {
                p.setScoreboard(scoreboard);
                p.getScoreboard().getTeam(teamName).addPlayer((OfflinePlayer)p);
            }
        }
    }
/*
    public static void sendPacketTAB(Player receiver) {
        EntityPlayer craftPlayer = ((CraftPlayer)receiver).getHandle();
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, craftPlayer);
        for(Player p : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        }
   //     ProtocolLibrary.getProtocolManager().createPacketConstructor(PacketType.Play.Server.PLAYER_INFO, "", false, (int) 0);
        Collection<? extends Player> playersBukkit = Bukkit.getOnlinePlayers();
        EntityPlayer[] playersNMS = new EntityPlayer[playersBukkit.size()];
        int current = 0;
        for (Player player : playersBukkit) {
            if(hidesPlayers.containsKey(player.getUniqueId())) {
                playersNMS[current] = ((CraftPlayer) player).getHandle();
                current++;
            }
        }
        PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playersNMS);
        ((CraftPlayer) receiver).getHandle().playerConnection.sendPacket(packet);
    } */

    public static void setShowPlayer(Player p) {
        if(p.getScoreboard() != null && p.getScoreboard().getTeam(teamName) != null) {
            p.getScoreboard().getTeam(teamName).removePlayer((OfflinePlayer)p);
            hidesPlayers.remove(p.getUniqueId());
        }
    }

    public static void getWhitelistWorldPlayer(Player p) {
        if (plugin.getConfig().getStringList("settings.worlds").contains(p.getWorld().getName())) {
            setHidePlayer(p);
        }else {
            setShowPlayer(p);
        }
    }



}
