package Raz.WorldWarp;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.List;
import java.util.logging.Logger;


import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.WWTrack;
import Raz.WorldGenerators.Flatlands;


public class WorldWarp extends JavaPlugin
{ 


	private static final Logger log = Logger.getLogger("Minecraft");
	public void onEnable() {

		try {
			checkFiles();
		} catch (FileNotFoundException e) {
			log.warning("[WorldWarp] Can't create config.yml.");
		} catch (IOException e) {
			log.warning("[WorldWarp] IOException occured. Try to remove WorldWarp Folder.");
		}
		
		log.info("[WorldWarp] Enabled! Running 3.0");	
		ConsoleCommandSender console = getServer().getConsoleSender();
		console.sendMessage(ChatColor.GREEN + " _  _  _             _     _ _  _  _                   ");
		console.sendMessage(ChatColor.GREEN + "| || || |           | |   | | || || |                  ");
		console.sendMessage(ChatColor.GREEN + "| || || | ___   ____| | _ | | || || | ____  ____ ____  ");
		console.sendMessage(ChatColor.GREEN + "| ||_|| |/ _ \\ / ___) |/ || | ||_|| |/ _  |/ ___)  _ \\ ");
		console.sendMessage(ChatColor.GREEN + "| |___| | |_| | |   | ( (_| | |___| ( ( | | |   | | | |");
		console.sendMessage(ChatColor.GREEN + " \\______|\\___/|_|   |_|\\____|\\______|\\_||_|_|   | ||_/ ");
		console.sendMessage(ChatColor.GREEN + "                                                |_|    ");
        WWTrack.init(this);
		loadWorlds();

	}

	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
	{
		return new Flatlands("16");
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{   
		if(!(sender instanceof Player)){
			log.warning("[WorldWarp] WorldWarp currently only accepts In-Game commands.");
			return true;
		}
		Player player = (Player) sender;
		if(hasPerm(player,label)){
			FileConfiguration config = getConfig();

			if(label.equalsIgnoreCase("wlist")){
				new WList(player,config);
			}
			if(label.equalsIgnoreCase("wwarp")){
				new WWarp(player,args,config);
			}
			if(label.equalsIgnoreCase("wcreate")){
				new WCreate(player,args,config,getServer());
				this.saveConfig();
			}
			if(label.equalsIgnoreCase("wimport")){
				new WImport(player,args,config,getServer());
				this.saveConfig();
			}
			if(label.equalsIgnoreCase("wdelete")){
				new WDelete(player,args,config,getServer());
				this.saveConfig();
			}
			if(label.equalsIgnoreCase("wflag")){
				new WFlag(player,args,config,getServer());
				this.saveConfig();
			}
		}
		return true;		
	}
	private boolean hasPerm(Player player, String node)
	{
		if ((getConfig().get("Settings.permissions.use").toString().equalsIgnoreCase("true")) || (getConfig().get("Settings.permissions.use").toString().equalsIgnoreCase("'true'"))) {
			if (player.hasPermission("WorldWarp." + node)) {
				return true;
			}
			player.sendMessage(ChatColor.DARK_RED + "[WorldWarp]: " + ChatColor.RED + "You do not have permissions to do that.");
			return false;
		}

		if ((node.equalsIgnoreCase("wcreate")) || (node.equalsIgnoreCase("wdelete")) || (node.equalsIgnoreCase("wflag")) || (node.equalsIgnoreCase("wimport")))
		{
			if (player.isOp()) {
				return true;
			}
			player.sendMessage(ChatColor.DARK_RED + "[WorldWarp]: " + ChatColor.RED + "You do not have permissions to do that.");
			return false;
		}

		return true;
	}
	public void onDisable(){
		this.saveConfig();
		log.info("[WorldWarp] Saving data, Goodbye!");
	}	
	private void loadWorlds(){
		log.info("[WorldWarp] Loading worlds.");
		Object[] worldNames = getConfig().getConfigurationSection("worlds").getKeys(false).toArray();
		int counter = 0;

		for(Object lWorld : worldNames){
			counter++;
			String label = lWorld.toString();	
			getServer().createWorld(new WorldCreator(label));
			
				if(getServer().getWorld(label).getEnvironment().toString().equalsIgnoreCase("NORMAL")){
					WWTrack.worldLoadsN.increment();
				}
				if(getServer().getWorld(label).getEnvironment().toString().equalsIgnoreCase("NETHER")){
					WWTrack.worldLoadsNE.increment();
				}
				if(getServer().getWorld(label).getEnvironment().toString().equalsIgnoreCase("THE_END")){
					WWTrack.worldLoadsT.increment();
				}
			
			getServer().getWorld(label).setPVP(getConfig().getBoolean("worlds." + label + ".pvp"));	
			if(getConfig().contains("worlds." + label + ".difficulty")){
				getServer().getWorld(label).setDifficulty(Difficulty.valueOf(getConfig().getString("worlds." + label + ".difficulty").toUpperCase()));
			}else{
				getConfig().set("worlds." + label  + ".difficulty", "EASY");
				getServer().getWorld(label).setDifficulty(Difficulty.EASY);
				this.saveConfig();
				
			}
			log.info("[WorldWarp] Loading: "+label+" : "+counter+"/"+worldNames.length );
		}
	}

	
	public void checkFiles() throws FileNotFoundException, IOException{

		getConfig().options().copyDefaults();
		this.saveConfig();
		if(!getConfig().contains("Settings")){
			getConfig().set("Settings.permissions.use", true);
			this.saveConfig();
		}
		if(!getConfig().contains("worlds")){
			List<World> worlds = getServer().getWorlds();
			for (int i = 0; i < worlds.size(); i++)
			{

				String n = ((World)worlds.get(i)).getName();
				log.info("[WorldWarp] Adding world "+n);
				String Env = ((World)worlds.get(i)).getEnvironment().name();
				Long seed = Long.valueOf(((World)worlds.get(i)).getSeed());   
				boolean pvp = ((World)worlds.get(i)).getPVP();
				String diff = ((World)worlds.get(i)).getDifficulty().name();
				getConfig().set("worlds." + n + ".name", n);
				getConfig().set("worlds." + n + ".environmate", Env);
				getConfig().set("worlds." + n + ".seed", seed);
				getConfig().set("worlds." + n + ".pvp", pvp);
				getConfig().set("worlds." + n + ".difficulty", diff);

				this.saveConfig();
			}

			this.saveConfig();
		}	


	}

}