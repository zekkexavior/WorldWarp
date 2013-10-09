package Raz.WorldWarp;
import java.io.File;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
public class WDelete {
	public WDelete(Player player, String[] args, FileConfiguration config,Server server) {	
		if(args.length < 1){
			player.sendMessage(ChatColor.RED + "[WorldWarp]: Use /wdelete [name].");
		}else{
			if(config.contains("worlds." + args[0])){
				boolean hard = false;
				if(args.length > 1){
					if(args[1].equalsIgnoreCase("-h")){
						hard = true;
					}
				}	
				World world = server.getWorld(args[0]);
				World newworld = server.getWorlds().get(0);
				List<Player> players = world.getPlayers();    
				for (Player pl : players) {
					pl.teleport(newworld.getSpawnLocation());
				}
				if(hard){
					File folder = new File(server.getWorld(args[0]).getWorldFolder().getPath());	
					server.unloadWorld(args[0], true);
					config.getConfigurationSection("worlds").set(args[0], null);
					deleteDirectory(folder);
					player.sendMessage(ChatColor.RED + "[WorldWarp]: World have been HARD deleted");

				}else{
					server.unloadWorld(args[0], true);
					config.getConfigurationSection("worlds").set(args[0], null);	
					player.sendMessage(ChatColor.RED + "[WorldWarp]: World have been SOFT deleted, use /wimport " + args[0]  + " to import it again.");
				}
			}else{
				player.sendMessage(ChatColor.RED + "[WorldWarp]: That world does not exist.");
			}
		}
	}

	public static boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return( path.delete() );
	}
}
