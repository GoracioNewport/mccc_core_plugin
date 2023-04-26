package mcup.core.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import mcup.core.Core;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class PacketListener extends PacketAdapter {

  @Override
  public void onPacketSending(PacketEvent event) {
    if (event.isCancelled() || event.getPacketType() != PacketType.Play.Server.ENTITY_METADATA)
      return;

    PacketContainer packet = event.getPacket();
    Entity targetEntity = packet.getEntityModifier(event).read(0);

    if (targetEntity == null || targetEntity.getType() != EntityType.PLAYER)
      return;

    Player targetPlayer = (Player)targetEntity;

    if (!core.offlinePlayerScheduler.checkGlow(event.getPlayer().getName(), targetPlayer.getName()))
      return;


    // oh my god what am I doing

    WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(targetEntity).deepClone();

    List<WrappedDataValue> values = packet.getDataValueCollectionModifier().getValues().get(0);
    WrappedWatchableObject entry = dataWatcher.getWatchableObject(0);

    int mask = (byte)entry.getValue() | (byte)0x40;

    values.set(0, new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), (byte)mask));
    packet.getDataValueCollectionModifier().write(0, values);

    event.setPacket(packet);

  }

  protected Core core;

  public PacketListener(Plugin plugin, PacketType type) {
    super(plugin, type);
    core = (Core)plugin;
  }
}
