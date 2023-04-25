package mcup.core;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class ColorConverter {

  public static Color translateChatColorToColor(ChatColor chatColor) {
    switch (chatColor) {
      case AQUA:
        return Color.AQUA;
      case BLACK:
        return Color.BLACK;
      case BLUE:
        return Color.BLUE;
      case DARK_AQUA:
        return Color.BLUE;
      case DARK_BLUE:
        return Color.BLUE;
      case DARK_GRAY:
        return Color.GRAY;
      case DARK_GREEN:
        return Color.GREEN;
      case DARK_PURPLE:
        return Color.PURPLE;
      case DARK_RED:
        return Color.RED;
      case GOLD:
        return Color.YELLOW;
      case GRAY:
        return Color.GRAY;
      case GREEN:
        return Color.GREEN;
      case LIGHT_PURPLE:
        return Color.PURPLE;
      case RED:
        return Color.RED;
      case WHITE:
        return Color.WHITE;
      case YELLOW:
        return Color.YELLOW;
      default:
        break;
    }

    return null;
  }

  public static Color translateCharToColor(char c) {
    if (ChatColor.getByChar(c) == null)
      return Color.WHITE;

    return translateChatColorToColor(ChatColor.getByChar(c));
  }

}
