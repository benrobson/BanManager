package me.confuser.banmanager.common.listeners;

import me.confuser.banmanager.common.BanManagerPlugin;
import me.confuser.banmanager.common.data.*;
import me.confuser.banmanager.common.util.DateUtils;
import me.confuser.banmanager.common.util.Message;

import java.util.List;

public class CommonDiscordListener {
  private BanManagerPlugin plugin;

  public CommonDiscordListener(BanManagerPlugin plugin) {
    this.plugin = plugin;
  }

  public Object[] notifyOnBan(PlayerBanData ban) {
    String channelName;
    Message message;

    if (ban.getExpires() == 0) {
      channelName = plugin.getDiscordConfig().getType("ban").getChannel();
      message = plugin.getDiscordConfig().getType("ban").getMessage();
    } else {
      channelName = plugin.getDiscordConfig().getType("tempban").getChannel();
      message = plugin.getDiscordConfig().getType("tempban").getMessage();
      message.set("expires", DateUtils.getDifferenceFormat(ban.getExpires()));
    }

    message.set("player", ban.getPlayer().getName())
        .set("playerId", ban.getPlayer().getUUID().toString())
        .set("actor", ban.getActor().getName())
        .set("reason", ban.getReason());

    return new Object[]{channelName, message};
  }

  public Object[] notifyOnBan(IpBanData ban) {
    List<PlayerData> players = plugin.getPlayerStorage().getDuplicates(ban.getIp());
    StringBuilder playerNames = new StringBuilder();

    for (PlayerData player : players) {
      playerNames.append(player.getName());
      playerNames.append(", ");
    }

    if (playerNames.length() >= 2) playerNames.setLength(playerNames.length() - 2);

    String channelName;
    Message message;

    if (ban.getExpires() == 0) {
      channelName = plugin.getDiscordConfig().getType("banip").getChannel();
      message = plugin.getDiscordConfig().getType("banip").getMessage();
    } else {
      channelName = plugin.getDiscordConfig().getType("tempbanip").getChannel();
      message = plugin.getDiscordConfig().getType("tempbanip").getMessage();
      message.set("expires", DateUtils.getDifferenceFormat(ban.getExpires()));
    }

    message.set("ip", ban.getIp().toString())
        .set("actor", ban.getActor().getName())
        .set("reason", ban.getReason())
        .set("players", playerNames.toString());

    return new Object[]{channelName, message};
  }

  public Object[] notifyOnKick(PlayerKickData kick) {
    String channelName = plugin.getDiscordConfig().getType("kick").getChannel();
    Message message = plugin.getDiscordConfig().getType("kick").getMessage();

    message.set("player", kick.getPlayer().getName())
        .set("playerId", kick.getPlayer().getUUID().toString())
        .set("actor", kick.getActor().getName())
        .set("reason", kick.getReason());

    return new Object[]{channelName, message};
  }

  public Object[] notifyOnMute(PlayerMuteData ban) {
    String channelName;
    Message message;

    if (ban.getExpires() == 0) {
      channelName = plugin.getDiscordConfig().getType("mute").getChannel();
      message = plugin.getDiscordConfig().getType("mute").getMessage();
    } else {
      channelName = plugin.getDiscordConfig().getType("tempmute").getChannel();
      message = plugin.getDiscordConfig().getType("tempmute").getMessage();
      message.set("expires", DateUtils.getDifferenceFormat(ban.getExpires()));
    }

    message.set("player", ban.getPlayer().getName())
        .set("playerId", ban.getPlayer().getUUID().toString())
        .set("actor", ban.getActor().getName())
        .set("reason", ban.getReason());

    return new Object[]{channelName, message};
  }

  public Object[] notifyOnWarn(PlayerWarnData ban) {
    String channelName = plugin.getDiscordConfig().getType("warn").getChannel();
    Message message = plugin.getDiscordConfig().getType("warn").getMessage();

    message.set("player", ban.getPlayer().getName())
        .set("playerId", ban.getPlayer().getUUID().toString())
        .set("actor", ban.getActor().getName())
        .set("reason", ban.getReason());

    return new Object[]{channelName, message};
  }
}
