package lordfokas.stargatetech2.core.reference;

import java.util.ArrayList;

public class ConfigReference {
	
	//****************************************************************************************************
	// TREE NODES
	public static final String KEY_CFG_SERVER		= "cfg.server";
	public static final String KEY_CFG_SV_WGEN		= "cfg.server.worldgen";
	public static final String KEY_CFG_CLIENT		= "cfg.client";
	
	public static final String KEY_PLUGINS			= "plugins";
	public static final String KEY_PLUGINS_CC		= "plugins.computercraft";
	public static final String KEY_PLUGINS_IC2		= "plugins.industrialcraft2";
	public static final String KEY_PLUGINS_TE4		= "plugins.thermalexpansion4";
	public static final String KEY_PLUGINS_TICO		= "plugins.tinkersconstruct";
	public static final String KEY_PLUGINS_WAILA	= "plugins.waila";
	
	
	//****************************************************************************************************
	// SERVER
	
	
	//****************************************************************************************************
	// CLIENT
	
	
	//****************************************************************************************************
	// PLUGINS
	public static final ArrayList<String> PLUGIN_LIST = new ArrayList<String>();
	
	public static final String PLUGIN_ENABLE = "enable";
	
	static{
		PLUGIN_LIST.add(KEY_PLUGINS_CC);
		PLUGIN_LIST.add(KEY_PLUGINS_IC2);
		PLUGIN_LIST.add(KEY_PLUGINS_TE4);
		PLUGIN_LIST.add(KEY_PLUGINS_TICO);
	}
}