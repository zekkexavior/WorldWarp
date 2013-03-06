package Raz.WorldWarp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class WBack
{
	public WBack(Player p, String[] args, FileConfiguration config)
	{
		if(!WorldWarp.LastLocation.containsKey(p)){
			p.sendMessage(ChatColor.RED + "[WorldWarp]: We don't have any data of previous teleports.");
			return;
		}
		Location target = WorldWarp.LastLocation.get(p);
		String name = target.getWorld().getName();
			if (p.getServer().getWorld(name) != null) {
				Location last = p.getLocation();
				p.teleport(target);
				WorldWarp.LastLocation.remove(p);
				WorldWarp.LastLocation.put(p, last);
				p.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + "Welcome to " + name);
				return;
			}
			p.sendMessage(ChatColor.RED + "[WorldWarp]: That world does not exist anymore o_O");
		}
	}
