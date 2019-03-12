package m0ttii.com.github.abusesystem.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.json.client.JSONObject;
import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.common.entity.punishment.AbuseSystemPunishment;
import sun.rmi.rmic.iiop.ValueType;

public class RedisManager {

    public void sendObjectToRedis(ValueType valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(valueType);

        AbuseSystemPlugin.getRedisBungeeAPI().sendChannelMessage("punish-object", string);
    }

    public void sendPunishmentToRedis(AbuseSystemPunishment abuseSystemPunishment) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String string = objectMapper.writeValueAsString(abuseSystemPunishment);

        AbuseSystemPlugin.getRedisBungeeAPI().sendChannelMessage("punish-object", string);
    }
}
