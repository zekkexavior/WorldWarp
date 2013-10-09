package Raz.WorldWarp;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.mcstats.WWTrack;

public class WImport {

	public WImport(Player player, String[] args, FileConfiguration config,Server server) {
        if(args.length < 1){
        	player.sendMessage(ChatColor.RED + "[WorldWarp]: Use: /wimport [name]");
        }else{
		String worldname = args[0];
		if(config.contains("worlds." + worldname)){
			player.sendMessage(ChatColor.RED + "[WorldWarp]: That world is already active.");
		}else{
			if(new File(worldname + "/level.dat").exists()){
				  server.createWorld(new WorldCreator(worldname));
			      String n = server.getWorld(worldname).getName();
	              String Env = server.getWorld(worldname).getEnvironment().name();
	              Long seed = Long.valueOf(server.getWorld(worldname).getSeed());;	  
	              boolean pvp = server.getWorld(worldname).getPVP();
	              String diff = server.getWorld(worldname).getDifficulty().name();
	          	
					if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("NORMAL")){
						WWTrack.worldLoadsN.increment();
					}
					if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("NETHER")){
						WWTrack.worldLoadsNE.increment();
					}
					if(server.getWorld(worldname).getEnvironment().toString().equalsIgnoreCase("THE_END")){
						WWTrack.worldLoadsT.increment();
					}
				
	              config.set("worlds." + n + ".name", n);
	              config.set("worlds." + n + ".environmate", Env);
	              config.set("worlds." + n + ".seed", seed);
	              config.set("worlds." + n + ".pvp", pvp);
	              config.set("worlds." + n + ".difficulty", diff);
	              player.sendMessage(ChatColor.RED + "[WorldWarp]: " + ChatColor.GREEN + "Imported: " + worldname);
			}else{
				player.sendMessage(ChatColor.RED + "[WorldWarp]: Sorry, can't find that world.");
			}
		}
        }
	}

}
