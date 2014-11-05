package lordfokas.stargatetech2.core.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import lordfokas.stargatetech2.core.base.BaseGUI;
import lordfokas.stargatetech2.enemy.gui.ContainerParticleIonizer;
import lordfokas.stargatetech2.enemy.gui.ContainerShieldController;
import lordfokas.stargatetech2.enemy.gui.GUIParticleIonizer;
import lordfokas.stargatetech2.enemy.gui.GUIShieldController;
import lordfokas.stargatetech2.enemy.tileentity.TileParticleIonizer;
import lordfokas.stargatetech2.enemy.tileentity.TileShieldController;
import lordfokas.stargatetech2.factory.gui.ContainerCrossover;
import lordfokas.stargatetech2.factory.gui.ContainerPrioritizer;
import lordfokas.stargatetech2.factory.gui.GUICrossover;
import lordfokas.stargatetech2.factory.gui.GUIPrioritizer;
import lordfokas.stargatetech2.factory.tileentity.TileCrossover;
import lordfokas.stargatetech2.factory.tileentity.TilePrioritizer;

public class GUIHandlerClient extends GUIHandler {
	
	@Override
	public BaseGUI getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity te = world.getTileEntity(x, y, z);
		BaseGUI gui = null;
		switch(Screen.values()[ID]){
			case SHIELD_CONTROLLER:
				if(te instanceof TileShieldController)
					gui = new GUIShieldController(new ContainerShieldController((TileShieldController)te));
				break;
			case PARTICLE_IONIZER:
				if(te instanceof TileParticleIonizer)
					gui = new GUIParticleIonizer(new ContainerParticleIonizer((TileParticleIonizer)te, player));
				break;
			case CROSSOVER:
				if(te instanceof TileCrossover)
					gui = new GUICrossover(new ContainerCrossover((TileCrossover)te, player));
			case PRIORITIZER:
				if(te instanceof TilePrioritizer)
					gui = new GUIPrioritizer(new ContainerPrioritizer((TilePrioritizer)te, player));
			default: break;
		}
		return gui;
	}
}