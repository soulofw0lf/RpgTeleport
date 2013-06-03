package RC2K7.Plugins.RPGTeleport.RCConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import RC2K7.Plugins.RPGTeleport.RPGTeleport;

public class SaveLoad {
	
	RPGTeleport rpg;
	FileConfiguration ConfigFile;
	File data = null;
	String fileName;
	
	public SaveLoad(RPGTeleport rpg, String fileName) {
		this.rpg = rpg;
		this.fileName = fileName;
		File tmpFile = new File(rpg.getDataFolder() + fileName);
		if(tmpFile.exists()){
			this.data = tmpFile;
		}
		reloadConfig();
		saveConfig();
	}
	
	public FileConfiguration getConfig(){
		if(this.ConfigFile == null){
			reloadConfig();
		}
		return this.ConfigFile;
	}
	
	public void reloadConfig(){
		if(this.data == null){
			this.data = new File(this.rpg.getDataFolder(), this.fileName);
			this.ConfigFile = YamlConfiguration.loadConfiguration(this.data);
			InputStream defaultConfigFile = this.rpg.getResource(this.fileName);
			if(defaultConfigFile != null){
				YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(defaultConfigFile);
				this.ConfigFile.setDefaults(defaultConfig);
			}
			getConfig().options().copyDefaults(true);
		}else{
			this.ConfigFile = YamlConfiguration.loadConfiguration(this.data);
		}
	}
	
	public void saveConfig(){
		if(this.ConfigFile == null || this.data == null)
			return;
		try {
			getConfig().save(this.data);
		} catch (IOException e) {
			this.rpg.getLogger().log(Level.SEVERE, "Could Not Save Config To " + this.data, e);
		}
	}

}
