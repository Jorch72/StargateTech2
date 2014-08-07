package lordfokas.stargatetech2.world.worldgen;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import lordfokas.stargatetech2.world.worldgen.lists.BuildList.BuildMaterial;
import lordfokas.stargatetech2.world.worldgen.lists.LootPodBuildList;

public class WorldGenLootPod {
	private static final ArrayList<Integer> VALID_GROUND = new ArrayList<Integer>();
	private static final ArrayList<BuildMaterial[]> MATERIALS = new ArrayList<BuildMaterial[]>();
	private static final LootPodBuildList BUILD_LIST = new LootPodBuildList();
	
	static{
		addMaterials(Block.sand.blockID, BUILD_LIST.sands);
		addMaterials(Block.grass.blockID, BUILD_LIST.stone);
	}
	
	private static void addMaterials(int id, BuildMaterial[] list){
		VALID_GROUND.add(id);
		MATERIALS.add(list);
	}
	
	public void generate(Random r, int cX, int cZ, World w, IChunkProvider chunkGen, IChunkProvider provider){
		int x = cX * 16 + r.nextInt(16);
		int y = 60;
		int z = cZ * 16 + r.nextInt(16);
		BuildMaterial[] list = null;
		
		while(!w.canBlockSeeTheSky(x, y, z)){
			y++;
		}
		y--;
		
		int id = w.getBlockId(x, y, z);
		if(VALID_GROUND.contains(id) && w.getBiomeGenForCoords(x, z).biomeID != BiomeGenBase.desert.biomeID){
			int index = VALID_GROUND.indexOf(id);
			list = MATERIALS.get(index);
		}
		if(list != null){
			BUILD_LIST.build(w, x, y, z, list);
		}
	}
}