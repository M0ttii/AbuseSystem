package m0ttii.com.github.abusesystem.listener;

import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.common.entity.punishment.AbuseSystemPunishment;
import m0ttii.com.github.common.entity.user.AbuseSystemUser;
import m0ttii.com.github.common.repository.MessageRepository;
import m0ttii.com.github.common.repository.PunishmentRepository;
import m0ttii.com.github.common.repository.UserRepository;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener implements Listener {

    private MessageRepository messageRepository;
    private PunishmentRepository punishmentRepository;
    private UserRepository userRepository;

    public PostLoginListener(){
        messageRepository = AbuseSystemPlugin.getMessageRepository();
        punishmentRepository = AbuseSystemPlugin.getPunishmentRepository();
        userRepository = AbuseSystemPlugin.getUserRepository();
    }

    @EventHandler
    public void onLogin(ServerConnectedEvent event){
        ProxiedPlayer player = event.getPlayer();
        AbuseSystemUser abuseSystemUser = userRepository.findByUniqueId(player.getUniqueId());

        if(abuseSystemUser == null){
            userRepository.createUser(player.getUniqueId(), player.getName());
        }

        if(abuseSystemUser.getLatestName() != player.getName()){
            abuseSystemUser.setLatestName(player.getName());
            userRepository.save(abuseSystemUser);
        }

        AbuseSystemPunishment punishment = punishmentRepository.getPunishmentByPlayerUUID(player.getUniqueId());
        if(AbuseSystemPlugin.getPunishmentRepository().isBanned(player.getUniqueId())){
            String message = messageRepository.getByName("player-ban-connect-cancel")
                    .replace("%player", player.getName())
                    .replace("%reason", punishment.getReason())
                    .replace("%date", (CharSequence) punishment.getExpire_at())
                    .replace("%punisher", punishment.getPunisher_id().getLatestName());
            player.disconnect(message);
        }
    }
}
