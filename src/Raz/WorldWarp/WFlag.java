package Raz.WorldWarp;

import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class WFlag {
	public WFlag(Player player, String[] args, FileConfiguration config,Server server) {	
		if(args.length < 1){
			player.sendMessage(ChatColor.RED + "[WorldWarp]: Use /wflag [name] -flags .");
		}else{
			if(config.contains("worlds." + args[0].toLowerCase())){

				if(isFlag("-nopvp",args)){
					server.getWorld(args[0]).setPVP(false);
					config.set("worlds." + args[0].toLowerCase() + ".pvp", false);
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to no-pvp.");
				}
				if(isFlag("-pvp",args)){
					server.getWorld(args[0]).setPVP(true);
					config.set("worlds." + args[0].toLowerCase() + ".pvp", false);
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to pvp.");
				}
				if(isFlag("-peaceful",args)){
					server.getWorld(args[0]).setDifficulty(Difficulty.PEACEFUL);
					config.set("worlds." + args[0].toLowerCase() + ".difficulty", "PEACEFUL");
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to peaceful.");
				}
				if(isFlag("-easy",args)){
					server.getWorld(args[0]).setDifficulty(Difficulty.EASY);
					config.set("worlds." + args[0].toLowerCase() + ".difficulty", "EASY");
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to easy.");
				}
				if(isFlag("-normal",args)){
					server.getWorld(args[0]).setDifficulty(Difficulty.NORMAL);
					config.set("worlds." + args[0].toLowerCase() + ".difficulty", "NORMAL");
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to normal.");
				}
				if(isFlag("-hard",args)){
					server.getWorld(args[0]).setDifficulty(Difficulty.HARD);
					config.set("worlds." + args[0].toLowerCase() + ".difficulty", "HARD");
					player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + args[0] + " is set to hard.");
				}     
			}else{
				player.sendMessage(ChatColor.RED + "[WorldWarp]: Sorry, can't find that world.");
			}
		}
	}
	public boolean isFlag(String flag, String[] args)
	{

		try {
			for (String s : args) {
				if (s.equalsIgnoreCase(flag)) {
					return true;
				}

			}
		} catch (IndexOutOfBoundsException e) {
		}
		return false;
	}

}
