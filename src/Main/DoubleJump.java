package Main;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;


public class DoubleJump implements Listener{
	

	private Main plugin;

	public DoubleJump(Main plugin){
		this.plugin = plugin;
	}
	
	
	private static final double UP_VELOCITY = 0.8, FORWARD_VELOCITY = 1.8;

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(player.getGameMode() == GameMode.CREATIVE)
			return;
		event.setCancelled(true);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setVelocity(player.getLocation().getDirection().multiply(FORWARD_VELOCITY).setY(UP_VELOCITY));
	}
	
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if((player.getGameMode() != GameMode.CREATIVE) 
				&& (player.getLocation().subtract(0,1,0).getBlock().getType() != Material.AIR) 
				&& (!player.isFlying())) {
			
			player.setAllowFlight(true);
			
		}
	}

}
