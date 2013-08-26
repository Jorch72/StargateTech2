package stargatetech2.common.machine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import stargatetech2.StargateTech2;
import stargatetech2.api.ITabletAccess;
import stargatetech2.common.base.BaseBlockContainer;
import stargatetech2.common.base.BaseTileEntity;
import stargatetech2.common.packet.PacketOpenGUI;
import stargatetech2.common.reference.TextureReference;
import stargatetech2.common.util.Helper;
import stargatetech2.common.util.IconRegistry;
import stargatetech2.common.util.GUIHandler.Screen;

public abstract class BlockMachine extends BaseBlockContainer implements ITabletAccess{
	private Screen screen;
	
	public BlockMachine(String uName, Screen scr){
		super(uName);
		screen = scr;
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase living, ItemStack stack){
		ForgeDirection dir = Helper.yaw2dir(living.rotationYaw);
		w.setBlockMetadataWithNotify(x, y, z, dir.ordinal(), 2);
	}
	
	@Override
	public Icon getBaseIcon(int side, int meta){
		switch(side){
			case 0: return IconRegistry.icons.get(TextureReference.MACHINE_BOTTOM);
			case 1: return IconRegistry.icons.get(TextureReference.MACHINE_TOP);
			case 3: return blockIcon;
			default: return IconRegistry.icons.get(TextureReference.MACHINE_SIDE);
		}
	}

	@Override
	public boolean onTabletAccess(EntityPlayer player, World world, int x, int y, int z) {
		if(screen != null){
			PacketOpenGUI packet = new PacketOpenGUI();
			packet.guiID = screen.ordinal();
			packet.x = x;
			packet.y = y;
			packet.z = z;
			packet.sendToServer();
			TileEntity te = world.getBlockTileEntity(x, y, z);
			if(te instanceof BaseTileEntity){
				((BaseTileEntity)te).updateClients();
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int getRenderType(){
		return RenderBlockMachine.instance().renderID;
	}
	
	public abstract String getFace(IBlockAccess world, int x, int y, int z);
	public abstract String getGlow(IBlockAccess world, int x, int y, int z);
}