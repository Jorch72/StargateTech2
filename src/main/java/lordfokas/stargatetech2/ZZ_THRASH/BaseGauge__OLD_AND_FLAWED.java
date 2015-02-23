package lordfokas.stargatetech2.ZZ_THRASH;

import lordfokas.stargatetech2.ZZ_THRASH.BaseGUI__OLD_AND_FLAWED.IGauge;
import lordfokas.stargatetech2.ZZ_THRASH.BaseGUI__OLD_AND_FLAWED.IHoverHandler;
import lordfokas.stargatetech2.util.Helper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import org.lwjgl.opengl.GL11;

import cofh.api.energy.EnergyStorage;

@Deprecated
public abstract class BaseGauge__OLD_AND_FLAWED implements IGauge{
	protected BaseGUI__OLD_AND_FLAWED master;
	protected GaugeHoverHandler hoverHandler;
	protected int cVal, mVal;
	protected int xPos, yPos;
	
	protected class GaugeHoverHandler implements IHoverHandler{
		public boolean isHover = false;
		public int hoverX, hoverY;
		
		@Override
		public void onHover(int x, int y) {
			isHover = true;
			hoverX = x;
			hoverY = y;
		}

		@Override
		public void onLeave() {
			isHover = false;
		}
		
	}
	
	protected void bindImage(ResourceLocation rl){
		Minecraft.getMinecraft().renderEngine.bindTexture(rl);;
	}
	
	public BaseGauge__OLD_AND_FLAWED(int x, int y, int maxValue){
		hoverHandler = new GaugeHoverHandler();
		mVal = maxValue;
		cVal = 0;
		xPos = x;
		yPos = y;
	}
	
	@Override
	public void register(BaseGUI__OLD_AND_FLAWED gui){
		master = gui;
		master.addHoverHandler(hoverHandler, xPos, yPos, 16, 64);
	}
	
	public static class TankGauge extends BaseGauge__OLD_AND_FLAWED{
		private IFluidTank tank;
		
		public TankGauge(int x, int y, IFluidTank tank) {
			super(x, y, tank.getCapacity());
			this.tank = tank;
		}

		@Override
		public void renderGauge() {
			float fill = ((float)cVal) / ((float)mVal);
			FluidStack fs = tank.getFluid();
			if(fs != null){
				IIcon icon = fs.getFluid().getIcon();
				bindImage(TextureMap.locationBlocksTexture);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if(fill > 0.75F) master.drawQuad(xPos, yPos,	icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), 16, 16);
				if(fill > 0.50F) master.drawQuad(xPos, yPos+16, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), 16, 16);
				if(fill > 0.25F) master.drawQuad(xPos, yPos+32, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), 16, 16);
				if(fill > 0.00F) master.drawQuad(xPos, yPos+48, icon.getMinU(), icon.getMaxU(), icon.getMinV(), icon.getMaxV(), 16, 16);
				
			}
			fill = 1F - fill;
			master.bindBGImage();
			master.drawLocalQuad(xPos, yPos, xPos, xPos+16, yPos, yPos + (64F * fill), 16, 64F*fill);
			master.bindBaseImage();
			master.drawLocalQuad(xPos, yPos, 80, 96, 0, 64, 16, 64);
		}
		
		@Override
		public void renderTooltip() {
			if(hoverHandler.isHover){
				int baseX = hoverHandler.hoverX + 2;
				int baseY = hoverHandler.hoverY - 2;
				String c = Helper.prettyNumber(cVal);
				String m = Helper.prettyNumber(mVal);
				FluidStack fluid = tank.getFluid();
				String name = fluid == null ? EnumChatFormatting.GRAY + "Empty" : fluid.getFluid().getLocalizedName();
				master.drawHover(baseX, baseY, name, String.format("%s / %s mB", c, m));
			}
		}

		@Override
		public void update() {
			cVal = tank.getFluidAmount();
		}
	}
	
	public static class PowerGauge extends BaseGauge__OLD_AND_FLAWED{
		private EnergyStorage capacitor;
		
		public PowerGauge(int x, int y, EnergyStorage capacitor) {
			super(x, y, capacitor.getMaxEnergyStored());
			this.capacitor = capacitor;
		}

		@Override
		public void renderGauge() {
			float power = ((float)cVal) / ((float)mVal);
			master.bindBaseImage();
			master.drawLocalQuad(xPos, yPos + (64F * (1F-power)), 64, 80, (64F - (64F * power)), 64, 16, 64F*power);
		}

		@Override
		public void renderTooltip() {
			if(hoverHandler.isHover){
				int baseX = hoverHandler.hoverX + 2;
				int baseY = hoverHandler.hoverY - 2;
				String c = Helper.prettyNumber(cVal);
				String m = Helper.prettyNumber(mVal);
				String rate = EnumChatFormatting.GRAY + "Max Rate: " + capacitor.getMaxReceive() + " RF/t";
				master.drawHover(baseX, baseY, EnumChatFormatting.YELLOW + "Energy (Redstone Flux)", rate, String.format("%s / %s RF", c, m));
			}
		}

		@Override
		public void update() {
			cVal = capacitor.getEnergyStored();
		}
	}
}