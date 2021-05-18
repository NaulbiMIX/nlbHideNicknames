package ru.naulbimix.nlbHideNicknames.listeners;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import ru.naulbimix.nlbHideNicknames.Main;
import ru.naulbimix.nlbHideNicknames.utils.ServerUtils;

public class TabListener extends PacketAdapter {

    public TabListener(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
        super(plugin, listenerPriority, types);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.getPacketType() == PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            Player packetPlayer = e.getPlayer();
            if(ServerUtils.hidesPlayers.containsKey(packetPlayer.getUniqueId()) && Bukkit.getOnlinePlayers().size() > 1) {
                for (Player other : Bukkit.getOnlinePlayers()) {
                    if (other.getName().equals(packetPlayer.getName())) {
                        continue;
                    }
                    EntityPlayer p = ((CraftPlayer) packetPlayer).getHandle();
                    EntityPlayer t = ((CraftPlayer) other).getHandle();
                    t.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, p));
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> t.playerConnection.sendPacket(
                            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, p)), 2);
                }
            }
        }
         /*   Player target = e.getPlayer();
            if(!target.isOp()) {
                EntityPlayer target_ = ((CraftPlayer)e.getPlayer()).getHandle();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (target.getName().equals(p.getName())) {
                        continue;
                    }
                    EntityPlayer p_ = ((CraftPlayer) p).getHandle();
                    target_.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, p_));
                    Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> target_.playerConnection.sendPacket(
                            new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, p_)), 2);
                }
            }
        } */
    }
}
