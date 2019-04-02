package Main;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class JumpPad implements Listener{


	private Main plugin;

	public JumpPad(Main plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if(e.getTo().getBlock().getRelative(BlockFace.SELF).getType() == Material.STONE_PLATE) {
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(3));
			e.getPlayer().setVelocity(new Vector(e.getPlayer().getVelocity().getX(), 1.0D, e.getPlayer().getVelocity().getZ()));
		}
	}

}
