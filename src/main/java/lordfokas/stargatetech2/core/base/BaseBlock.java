package lordfokas.stargatetech2.core.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import lordfokas.stargatetech2.StargateTech2;
import lordfokas.stargatetech2.api.bus.BusEvent;
import lordfokas.stargatetech2.core.reference.ModReference;
import lordfokas.stargatetech2.core.util.MaterialNaquadah;
import lordfokas.stargatetech2.core.util.StargateTab;
import cpw.mods.fml.common.registry.GameRegistry;

public class BaseBlock extends Block{
	protected IIcon[] iconOverride;
	protected boolean isOverride = false;
	private boolean isAbstractBus = false;
	private String unlocalized;
	
	public BaseBlock(String uName){
		this(uName, false, true);
	}
	
	public BaseBlock(String uName, boolean breakable, boolean requiresTool) {
		this(uName, breakable, requiresTool ? MaterialNaquadah.unbreakable : MaterialNaquadah.breakable);
	}
	
	public BaseBlock(String uName, boolean breakable, Material material){
		super(material);
		// StargateTech2.config.getBlockID(uName)
		unlocalized = uName;
		this.setBlockTextureName(ModReference.MOD_ID + ":" + uName);
		if(!breakable){
			this.setBlockUnbreakable();
			this.setResistance(20000000F);
		}
		this.setCreativeTab(StargateTab.instance);
	}
	
	@Override
	public String getUnlocalizedName(){
		return ModReference.MOD_ID + ":block." + unlocalized;
	}
	
	@Override
	public int getMobilityFlag(){
		return 2;
	}
	
	protected void setIsAbstractBusBlock(){
		isAbstractBus = true;
	}
	
	public void registerBlock(){
		GameRegistry.registerBlock(this, getUnlocalizedName());
	}
	
	public void setOverride(IIcon[] icons){
		iconOverride = icons;
		isOverride = true;
	}
	
	public void restoreTextures(){
		isOverride = false;
	}
	
	@Override
	public void onBlockAdded(World w, int x, int y, int z){
		super.onBlockAdded(w, x, y, z);
		if(isAbstractBus) MinecraftForge.EVENT_BUS.post(new BusEvent.AddToNetwork(w, x, y, z));
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int m){
		super.breakBlock(w, x, y, z, b, m);
		if(isAbstractBus) MinecraftForge.EVENT_BUS.post(new BusEvent.RemoveFromNetwork(w, x, y, z));
	}
	
	@Override
	public final IIcon getIcon(int side, int meta){
		if(isOverride){
			return iconOverride[side];
		}else{
			return getBaseIcon(side, meta);
		}
	}
	
	public IIcon getBaseIcon(int side, int meta){
		return blockIcon;
	}
	
	public void dropSelf(World w, int x, int y, int z){
		w.setBlockToAir(x, y, z);
		dropItemStack(w, x, y, z, new ItemStack(this));
	}
	
	public void dropItemStack(World w, int x, int y, int z, ItemStack stack){
		dropStackAt(w, ((double)x)+0.5D, ((double)y)+0.5D, ((double)z)+0.5D, stack);
	}
	
	public void dropItemStack(World w, EntityPlayer p, ItemStack stack){
		dropStackAt(w, p.posX, p.posY, p.posZ, stack);
	}
	
	private void dropStackAt(World w, double x, double y, double z, ItemStack stack){
		if(w.isRemote) return;
		w.spawnEntityInWorld(new EntityItem(w, x, y, z, stack));
	}
}