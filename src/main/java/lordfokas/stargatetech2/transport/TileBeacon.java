package lordfokas.stargatetech2.transport;

import lordfokas.stargatetech2.api.bus.IBusDevice;
import lordfokas.stargatetech2.core.base.BaseTileEntity;
import net.minecraft.world.World;

public abstract class TileBeacon extends BaseTileEntity implements IBusDevice{
	
	@Override
	public final boolean canUpdate(){
		return false;
	}
	
	@Override
	public World getWorld() {
		return worldObj;
	}
	
	@Override
	public int getXCoord() {
		return xCoord;
	}
	
	@Override
	public int getYCoord() {
		return yCoord;
	}
	
	@Override
	public int getZCoord() {
		return zCoord;
	}
}