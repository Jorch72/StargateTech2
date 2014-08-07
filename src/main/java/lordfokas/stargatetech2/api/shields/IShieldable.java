package lordfokas.stargatetech2.api.shields;

import net.minecraft.world.World;

/**
 * Used by shield emitters to make shieldable blocks in their way raise and lower their shields.
 * 
 * @author LordFokas
 */
public interface IShieldable {
	/**
	 * Called by shield emitters to make blocks raise their shields.
	 * The block on {px, py, pz} contains an IShieldController from
	 * which you can get the current ShieldPermissions object.
	 * 
	 * @param world The world this IShieldable is on.
	 * @param x This IShieldable's X Coordinate.
	 * @param y This IShieldable's Y Coordinate.
	 * @param z This IShieldable's Z Coordinate.
	 * @param px The X Coordinate of the shield controller in charge of the shield on this block.
	 * @param py The Y Coordinate of the shield controller in charge of the shield on this block.
	 * @param pz The Z Coordinate of the shield controller in charge of the shield on this block.
	 */
	public void onShield(World world, int x, int y, int z, int px, int py, int pz);
	
	/**
	 * Called by shield emitters to make blocks lower their shields.
	 * 
	 * @param world The world this IShieldable is on.
	 * @param x This IShieldable's X Coordinate.
	 * @param y This IShieldable's Y Coordinate.
	 * @param z This IShieldable's Z Coordinate.
	 */
	public void onUnshield(World world, int x, int y, int z);
}