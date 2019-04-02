package Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DamagePreventionListener implements Listener {
		

	private Main plugin;

	public DamagePreventionListener(){
		this.plugin = plugin;
	}
	
		@EventHandler
		public void onFallDamage(EntityDamageEvent event){
			if (!(event.getEntity() instanceof Player)) return;
			if (!event.getCause().equals(DamageCause.FALL)) return;
			
			event.setCancelled(true);
		}
	}


