package m0ttii.com.github.abusesystem.redis;

import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.common.entity.punishment.AbuseSystemPunishment;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.repository.MessageRepository;
import m0ttii.com.github.common.repository.UserRepository;
import net.md_5.bungee.api.ProxyServer;

public class Notify {

    private AbuseSystemPunishment abuseSystemPunishment;
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public Notify(AbuseSystemPunishment abuseSystemPunishment){
        this.abuseSystemPunishment = abuseSystemPunishment;
        this.messageRepository = AbuseSystemPlugin.getMessageRepository();
        this.userRepository = AbuseSystemPlugin.getUserRepository();
    }

    public void notifyPunishment(AbuseSystemType abuseSystemType){
        if(abuseSystemType.equals(AbuseSystemType.BAN)){
            String message = messageRepository.getByName("staff-notify-ban")
                    .replace("%player", abuseSystemPunishment.getPlayer_id().getLatestName())
                    .replace("%reason", abuseSystemPunishment.getReason())
                    .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName())
                    .replace("%date", (CharSequence) abuseSystemPunishment.getExpire_at());
            ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> {
                if(userRepository.findByUniqueId(proxiedPlayer.getUniqueId()).isNotify()){
                    if(proxiedPlayer.hasPermission("punish.notify")){
                        proxiedPlayer.sendMessage(message);
                    }
                }
            });
        }
        if(abuseSystemType.equals(AbuseSystemType.KICK)){
            String message = messageRepository.getByName("staff-notify-kick")
                    .replace("%player", abuseSystemPunishment.getPlayer_id().getLatestName())
                    .replace("%reason", abuseSystemPunishment.getReason())
                    .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName());
            ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> {
                if(userRepository.findByUniqueId(proxiedPlayer.getUniqueId()).isNotify()){
                    if(proxiedPlayer.hasPermission("punish.notify")){
                        proxiedPlayer.sendMessage(message);
                    }
                }
            });
        }
        if(abuseSystemType.equals(AbuseSystemType.MUTE)){
            String message = messageRepository.getByName("staff-notify-mute")
                    .replace("%player", abuseSystemPunishment.getPlayer_id().getLatestName())
                    .replace("%reason", abuseSystemPunishment.getReason())
                    .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName())
                    .replace("%date", (CharSequence) abuseSystemPunishment.getExpire_at());
            ProxyServer.getInstance().getPlayers().forEach(proxiedPlayer -> {
                if(userRepository.findByUniqueId(proxiedPlayer.getUniqueId()).isNotify()){
                    if(proxiedPlayer.hasPermission("punish.notify")){
                        proxiedPlayer.sendMessage(message);
                    }
                }
            });
        }
    }
}
