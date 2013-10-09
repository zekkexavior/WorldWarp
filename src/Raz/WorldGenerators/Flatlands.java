package Raz.WorldGenerators;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class Flatlands extends ChunkGenerator
{	
	
	private int Height;
	
	public Flatlands(String id)
	{
		if(id == null)
		{
			Height = 16;
		}
		else
		{
			try
			{
				Height = Integer.parseInt(id);
			}
			catch(NumberFormatException e)
			{
				Height = 16;
			}
		}
	}
	
	void setBlock(byte[][] result, int x, int y, int z, byte blkid)
	{
        if (result[y >> 4] == null) {
            result[y >> 4] = new byte[4096];
        }
        result[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = blkid; 
    }
	
	public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid)
	{
		byte[][] result = new byte[16][];
		int x,y,z;
		for(x = 0; x < 16; x++)
		{
			for(z = 0; z < 16; z++)
			{
				setBlock(result, x, 0, z, (byte)Material.BEDROCK.getId());
			}
		}
		for(x = 0; x < 16; x++)
		{
			for(y = 1; y < Height - 1; y++)
			{
				for (z = 0; z < 16; z++)
				{
					setBlock(result, x, y, z, (byte)Material.DIRT.getId());
				}
			}
		}
		for(x = 0; x < 16; x++)
		{
			for(z = 0; z < 16; z++)
			{
				setBlock(result, x, Height - 1, z, (byte)Material.GRASS.getId());
			}
		}
		
		return result;
	}
}