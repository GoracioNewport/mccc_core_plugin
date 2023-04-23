package mcup.core;

import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

  public static ItemStack addEnchantmentGlint(ItemStack item) {
    // enchantment is technically a NBT, right?
    item.addEnchantment((item.getType() == Material.BOW) ? Enchantment.PROTECTION_ENVIRONMENTAL : Enchantment.ARROW_INFINITE, 1);

    final ItemMeta meta = item.getItemMeta();
    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    item.setItemMeta(meta);

    return item;
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
