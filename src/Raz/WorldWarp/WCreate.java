package Raz.WorldWarp;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.Server;

import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mcstats.WWTrack;

import Raz.WorldGenerators.Flatlands;


public class WCreate {

	public WCreate(Player player, String[] args, FileConfiguration config, Server server) {
		if(args.length < 2){
			player.sendMessage(ChatColor.RED + "[WorldWarp]: Use: /Wcreate [name] [environment] <seed>");
		}else{
			String worldname = args[0].toLowerCase();
			if(config.contains("worlds." + worldname)){
				player.sendMessage(ChatColor.RED + "[WorldWarp]: That world already exists, use /wlist to see active worlds.");
			}else{
				if(new File(worldname + "/level.dat").exists()){
					player.sendMessage(ChatColor.RED + "[WorldWarp]: A world by that name seems to exist. Try /wimport "+worldname);
				}else{
					int isFlat = 0;
					if ((args[1].equalsIgnoreCase("NETHER")) || (args[1].equalsIgnoreCase("THE_END")) || (args[1].equalsIgnoreCase("NORMAL") || (args[1].equalsIgnoreCase("FLATLANDS")))){
						WorldCreator World = WorldCreator.name(worldname);
						Long Seed = null;
						if (args.length > 2) {
							if(!args[2].startsWith("-")){
								if (isInt(args[2]))
									Seed = Long.valueOf(args[2]);
								else {
									Seed = Long.valueOf(args[2].hashCode());
								}
							}
						}
						if(Seed != null){
							World.seed(Seed);
						}
						if(args[1].equalsIgnoreCase("FLATLANDS")){
							isFlat = 1;
							World.generator(new Flatlands("16"));
							World.environment(Environment.valueOf("NORMAL"));
							
						}else{
							World.environment(Environment.valueOf(args[1].toUpperCase()));
						}
						player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.DARK_GREEN + "Generating world: " + worldname);
						World.createWorld();
						server.getWorld(worldname).setPVP(false);	
						server.getWorld(worldname).setDifficulty(Difficulty.PEACEFUL);
						if(isFlag("-nopvp",args)){
							server.getWorld(worldname).setPVP(false);
						}
						if(isFlag("-pvp",args)){
							server.getWorld(worldname).setPVP(true);
						}
						if(isFlag("-peaceful",args)){
							server.getWorld(worldname).setDifficulty(Difficulty.PEACEFUL);
						}
						if(isFlag("-easy",args)){
							server.getWorld(worldname).setDifficulty(Difficulty.EASY);
						}
						if(isFlag("-normal",args)){
							server.getWorld(worldname).setDifficulty(Difficulty.NORMAL);
						}
						if(isFlag("-hard",args)){
							server.getWorld(worldname).setDifficulty(Difficulty.HARD);
						}
						
							if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("NORMAL")){
								WWTrack.worldLoadsN.increment();
							}
							if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("NETHER")){
								WWTrack.worldLoadsNE.increment();
							}
							if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("THE_END")){
								WWTrack.worldLoadsT.increment();
							}
						String n = server.getWorld(worldname).getName();
						String Env = server.getWorld(worldname).getEnvironment().name();
						Long seed = Long.valueOf(server.getWorld(worldname).getSeed());
						boolean pvp = server.getWorld(worldname).getPVP();
						String diff = server.getWorld(worldname).getDifficulty().name();
						if(isFlat == 1){
							config.set("worlds." + n + ".flatlands",true);
						}
						config.set("worlds." + n + ".name", n);
						config.set("worlds." + n + ".environmate", Env);
						config.set("worlds." + n + ".seed", seed);
						config.set("worlds." + n + ".pvp", pvp);
						config.set("worlds." + n + ".difficulty", diff);

						player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.YELLOW + "Done generating " + worldname);


					}else{
						player.sendMessage(ChatColor.RED + "[WorldWarp]: You can only create: NORMAL/NETHER/THE_END/FLATLANDS");
					}
				}
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

	public boolean isInt(String num)
	{
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
