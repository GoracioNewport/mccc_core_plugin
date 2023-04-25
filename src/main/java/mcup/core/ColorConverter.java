package mcup.core;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class ColorConverter {

  public static Color translateChatColorToColor(ChatColor chatColor) {
    switch (chatColor) {
      case AQUA:
        return Color.fromBGR(0x55FFFF);
      case DARK_AQUA:
        return Color.fromBGR(0x00AAAA);
      case BLACK:
        return Color.fromRGB(0x000000);
      case BLUE:
        return Color.fromRGB(0x5555FF);
      case DARK_BLUE:
        return Color.fromRGB(0x0000AA);
      case GRAY:
        return Color.fromRGB(0xAAAAAA);
      case DARK_GRAY:
        return Color.fromRGB(0x555555);
      case GREEN:
        return Color.fromRGB(0x55FF55);
      case DARK_GREEN:
        return Color.fromRGB(0x00AA00);
      case DARK_PURPLE:
        return Color.fromRGB(0xAA00AA);
      case LIGHT_PURPLE:
        return Color.fromRGB(0xFF55FF);
      case RED:
        return Color.fromRGB(0xFF5555);
      case DARK_RED:
        return Color.fromRGB(0xAA0000);
      case YELLOW:
        return Color.fromRGB(0xFFFF55);
      case GOLD:
        return Color.fromRGB(0xFFAA00);
      case WHITE:
        return Color.fromRGB(0xFFFFFF);
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
