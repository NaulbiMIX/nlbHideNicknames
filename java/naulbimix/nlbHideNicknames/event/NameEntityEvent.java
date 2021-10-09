package naulbimix.nlbHideNicknames.event;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.plugin.*;
import com.comphenix.protocol.*;
import naulbimix.nlbHideNicknames.*;
import net.minecraft.server.v1_8_R3.*;
import com.comphenix.protocol.events.*;
import naulbimix.nlbHideNicknames.manager.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;

public class NameEntityEvent extends PacketAdapter {

    public Main plugin;

    public NameEntityEvent(Plugin plugin, ListenerPriority listenerPriority, PacketType[] types) {
        super(plugin, listenerPriority, types);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.getPacketType() == PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            Player packetPlayer = e.getPlayer();
            if (!getPlayerManager().isHidePlayer(packetPlayer.getUniqueId())) return;
            Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers(); // haha bb optimization
            if (onlinePlayers.size() < 1) return;
            String playerName = packetPlayer.getName();
            onlinePlayers.stream().filter(other -> other.getName().equals(playerName)).forEach(other -> {
                EntityPlayer p = ((CraftPlayer) packetPlayer).getHandle();
                EntityPlayer t = ((CraftPlayer) other).getHandle();
                t.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, p));
                Bukkit.getScheduler().runTaskLater(Main.getPlugin(), () -> t.playerConnection.sendPacket(
                        new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, p)), 2);
            });
        }
    }

    public PlayerManager getPlayerManager() {
        return plugin.getPlayerManager();
    }
}
