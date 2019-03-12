package m0ttii.com.github.abusesystem.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imaginarycode.minecraft.redisbungee.events.PubSubMessageEvent;
import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.abusesystem.redis.Notify;
import m0ttii.com.github.common.entity.punishment.AbuseSystemPunishment;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.repository.MessageRepository;
import m0ttii.com.github.common.repository.UserRepository;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.UUID;

public class PubSubListener implements Listener {

    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private Notify notify;

    public PubSubListener(){
        this.messageRepository = AbuseSystemPlugin.getMessageRepository();
        this.userRepository = AbuseSystemPlugin.getUserRepository();
    }

    @EventHandler
    public void onPubSub(PubSubMessageEvent event) throws IOException {
        if(event.getChannel().equals("punish-punishment-object")){
            ObjectMapper mapper = new ObjectMapper();

            String json = event.getMessage();
            AbuseSystemPunishment abuseSystemPunishment = mapper.readValue(json, AbuseSystemPunishment.class);
            this.notify = new Notify(abuseSystemPunishment);

            UUID player_uuid = abuseSystemPunishment.getPlayer_id().getUuid();
            if(ProxyServer.getInstance().getPlayer(player_uuid).isConnected()){
                if(abuseSystemPunishment.getType().equals(AbuseSystemType.KICK)){
                    String message = messageRepository.getByName("player-kick-kick")
                            .replace("%player", ProxyServer.getInstance().getPlayer(player_uuid).getName())
                            .replace("%reason", abuseSystemPunishment.getReason())
                            .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName());
                    ProxyServer.getInstance().getPlayer(player_uuid).disconnect(message);
                    notify.notifyPunishment(AbuseSystemType.KICK);
                }
                if(abuseSystemPunishment.getType().equals(AbuseSystemType.BAN)){
                    String message = messageRepository.getByName("player-ban-kick")
                            .replace("%player", ProxyServer.getInstance().getPlayer(player_uuid).getName())
                            .replace("%reason", abuseSystemPunishment.getReason())
                            .replace("%date", (CharSequence) abuseSystemPunishment.getExpire_at())
                            .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName());
                    ProxyServer.getInstance().getPlayer(player_uuid).disconnect(message);
                    notify.notifyPunishment(AbuseSystemType.BAN);
                }
                if(abuseSystemPunishment.getType().equals(AbuseSystemType.MUTE)){
                    String message = messageRepository.getByName("player-mute-mute")
                            .replace("%player", ProxyServer.getInstance().getPlayer(player_uuid).getName())
                            .replace("%reason", abuseSystemPunishment.getReason())
                            .replace("%date", (CharSequence) abuseSystemPunishment.getExpire_at())
                            .replace("%punisher", abuseSystemPunishment.getPunisher_id().getLatestName());
                    ProxyServer.getInstance().getPlayer(player_uuid).sendMessage(message);
                    notify.notifyPunishment(AbuseSystemType.MUTE);
                }

            }
        }
    }
}
