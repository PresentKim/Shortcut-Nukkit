package kim.present.shortcut.listener;

import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.server.RemoteServerCommandEvent;
import cn.nukkit.event.server.ServerCommandEvent;
import kim.present.shortcut.Shortcut;

public class CommandEventListener implements Listener {
    private Shortcut plugin;

    public CommandEventListener(Shortcut plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        if (message.startsWith("/")) {
            event.setMessage(onCommandEnter(event.getPlayer(), message.substring(1)));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(RemoteServerCommandEvent event) {
        event.setCommand(onCommandEnter(event.getSender(), event.getCommand()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(ServerCommandEvent event) {
        event.setCommand(onCommandEnter(event.getSender(), event.getCommand()));
    }

    public String onCommandEnter(CommandSender sender, String command) {
        if (sender.hasPermission("shortcut.use")) {
            //TODO: Replace command when command was shortcut
        }
        return command;
    }
}
