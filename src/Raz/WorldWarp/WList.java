package Raz.WorldWarp;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WList
{
  public WList(Player p, FileConfiguration config)
  {
    List<World> worlds = p.getServer().getWorlds();
    p.sendMessage(ChatColor.RED + "[WorldWarp] Worlds");
    for (int i = 0; i < worlds.size(); i++)
    {
      if(config.contains("worlds." + worlds.get(i).getName())){
    	  
      
      if (((World)worlds.get(i)).getEnvironment() == World.Environment.NORMAL)
        p.sendMessage("- " + ChatColor.GREEN + ((World)worlds.get(i)).getName());
      else if (((World)worlds.get(i)).getEnvironment() == World.Environment.NETHER)
        p.sendMessage("- " + ChatColor.RED + ((World)worlds.get(i)).getName());
      else if (((World)worlds.get(i)).getEnvironment() == World.Environment.THE_END)
        p.sendMessage("- " + ChatColor.DARK_AQUA + ((World)worlds.get(i)).getName());
      }
      }
      
  }
}