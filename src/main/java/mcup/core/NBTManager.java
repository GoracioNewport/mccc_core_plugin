package mcup.core;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;

public class NBTManager {
  
  public static ItemStack setCanDestroyTag(ItemStack item, ArrayList<Material> materialList) {

    NBTItem nbtItem = new NBTItem(item);
    NBTList<String> canDestroyList = nbtItem.getStringList("CanDestroy");

    for (Material material : materialList)
      canDestroyList.add("minecraft:" + material.name().toLowerCase());

    return nbtItem.getItem();
  }


  public static ItemStack setCanPlaceOnTag(ItemStack item, ArrayList<Material> materialList) {

    NBTItem nbtItem = new NBTItem(item);
    NBTList<String> canDestroyList = nbtItem.getStringList("CanPlaceOn");

    for (Material material : materialList)
      canDestroyList.add("minecraft:" + material.name().toLowerCase());

    return nbtItem.getItem();
  }

  public static ItemStack setTag(ItemStack item, String tag, String tagValue) {

    NBTItem nbtItem = new NBTItem(item);
    nbtItem.setString(tag, tagValue);

    return nbtItem.getItem();
  }

  public static boolean checkTag(ItemStack item, String tag, String tagValue) {

    NBTItem nbtItem = new NBTItem(item);


    if (tagValue == null)
      return nbtItem.hasTag(tag);

    else
      return Objects.equals(nbtItem.getString(tag), tagValue);

  }

  public static boolean hasTag(ItemStack item, String tag) {
    return checkTag(item, tag, null);
  }

  
}
