package lordfokas.stargatetech2.automation;

import java.util.ArrayList;
import java.util.List;

import lordfokas.stargatetech2.ModuleAutomation;
import lordfokas.stargatetech2.api.bus.IBusDevice;
import lordfokas.stargatetech2.api.bus.IBusInterface;
import lordfokas.stargatetech2.core.Vec3Int;
import lordfokas.stargatetech2.core.Vec4Int;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class RecursiveBusRemapper {
	public static void scan(World w, Vec3Int start){
		if(w.isRemote) return;
		scan(w, start, true);
	}
	
	private static void scan(World w, Vec3Int start, boolean allowMachine){
		if(w.getBlock(start.x, start.y, start.z) instanceof BlockBusCable){
			scanCable(w, start);
		}else if(w.getTileEntity(start.x, start.y, start.z) instanceof IBusDevice && allowMachine){
			for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS){
				scan(w, start.offset(direction), false);
			}
		}
	}
	
	private static void scanCable(World w, Vec3Int start){
		ArrayList<Vec3Int> memory = new ArrayList();
		ArrayList<Vec4Int> interfaces = new ArrayList();
		propagateScan(w, start, memory, interfaces);
		ArrayList ifmemory = new ArrayList();
		for(Vec4Int device : interfaces){
			ArrayList<Vec4Int> addressingTable = new ArrayList();
			for(Vec4Int address : interfaces){
				if(address != device){
					addressingTable.add(address);
				}
			}
			int s = device.w;
			int x = device.x;
			int y = device.y;
			int z = device.z;
			TileEntity te = w.getTileEntity(x, y, z);
			if(te instanceof IBusDevice){
				for(IBusInterface b : ((IBusDevice)te).getInterfaces(s)){
					if(b instanceof BusInterface && !ifmemory.contains(b)){
						((BusInterface)b).setAddressingTable(s, addressingTable);
						ifmemory.add(b);
					}
				}
			}
		}
	}
	
	private static void propagateScan(World w, Vec3Int location, List<Vec3Int> memory, List<Vec4Int> interfaces){
		if(!memory.contains(location)){
			memory.add(location);
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
				Connection connection = ModuleAutomation.busCable.getBusConnection(w, location.x, location.y, location.z, dir);
				if(connection.isConnected()){
					Vec3Int next = location.offset(dir);
					if(connection.hasPlug()){
						interfaces.add(new Vec4Int(dir.getOpposite().ordinal(), next.x, next.y, next.z));
					}else{
						propagateScan(w, next, memory, interfaces);
					}
				}
			}
		}
	}
}