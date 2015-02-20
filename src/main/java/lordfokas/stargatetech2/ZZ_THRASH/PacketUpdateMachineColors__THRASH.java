package lordfokas.stargatetech2.ZZ_THRASH;

import lordfokas.stargatetech2.lib.packet.BasePacket.ServerToClient;
import lordfokas.stargatetech2.lib.packet.PacketCoordinates;
import lordfokas.stargatetech2.lib.tileentity.faces.FaceColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.relauncher.Side;

@ServerToClient
public class PacketUpdateMachineColors__THRASH extends PacketCoordinates {
	private FaceColor[] colors;
	
	public PacketUpdateMachineColors__THRASH(){
		colors = new FaceColor[6];
	}
	
	public PacketUpdateMachineColors__THRASH(FaceColor[] colors){
		this.colors = colors;
	}
	
	@Override
	protected void writeData() throws Exception{
		for(FaceColor color : colors){
			output.writeByte(color.ordinal());
		}
	}

	@Override
	protected IMessage readData(EntityPlayer player, Side side) throws Exception {
		for(int s = 0; s < 6; s++){
			colors[s] = FaceColor.values()[input.readByte()];
		}
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if(te instanceof TileMachine__THRASH){
			((TileMachine__THRASH)te).updateColors(colors);
		}
		return null;
	}
}