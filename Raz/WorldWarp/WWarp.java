package Raz.WorldWarp;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class WWarp
{
	public WWarp(Player p, String[] args, FileConfiguration config)
	{
		if (args.length < 1) {
			p.sendMessage(ChatColor.RED + "[WorldWarp]: Use /wwarp [name].");
			return;
		}
		if(config.contains("worlds." + args[0])){

			if (p.getServer().getWorld(args[0]) != null) {
				Location loc = p.getServer().getWorld(args[0]).getSpawnLocation();
				p.teleport(loc);
				p.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + "Welcome to " + args[0]);
				return;
			}
			p.sendMessage(ChatColor.RED + "[WorldWarp]: That world does not exist.");
			p.sendMessage(ChatColor.RED + "[WorldWarp]: Use /wlist to see worlds.");
		}
		else{
			p.sendMessage(ChatColor.RED + "[WorldWarp]: That world does not exist.");
			p.sendMessage(ChatColor.RED + "[WorldWarp]: Use /wlist to see worlds.");
		}
	}
}