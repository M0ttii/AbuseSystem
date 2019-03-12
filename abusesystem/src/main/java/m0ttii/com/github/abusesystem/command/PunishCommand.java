package m0ttii.com.github.abusesystem.command;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import m0ttii.com.github.abusesystem.AbuseSystemConstants;
import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.repository.PointRepository;
import m0ttii.com.github.common.repository.PunishmentRepository;
import m0ttii.com.github.common.repository.TemplateRepository;
import m0ttii.com.github.common.repository.UserRepository;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;


@CommandAlias("punish")
public class PunishCommand extends BaseCommand {

    private UserRepository userRepository;
    private PunishmentRepository punishmentRepository;
    private TemplateRepository templateRepository;
    private PointRepository pointRepository;

    public PunishCommand(){
       userRepository = AbuseSystemPlugin.getUserRepository();
       punishmentRepository = AbuseSystemPlugin.getPunishmentRepository();
       templateRepository = AbuseSystemPlugin.getTemplateRepository();
       pointRepository = AbuseSystemPlugin.getPointRepository();

    }

    @Default
    public void punish(CommandSender player) {
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/punish [player] [reason]");
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/unpunish [player]");
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/pinfo [player]");
    }

    @Subcommand("optional optional")
    @CommandCompletion("@players @reason")
    public void punishPlayer(CommandSender commandSender, @Optional String player, @Optional String reason) {
        ProxiedPlayer proxiedPlayer = ProxyServer.getInstance().getPlayer(player);
        ProxiedPlayer punisher = (ProxiedPlayer) commandSender;

        if(userRepository.findByUniqueId(proxiedPlayer.getUniqueId()) == null){
            commandSender.sendMessage("Dieser Spieler existiert nicht.");
            return;
        }

        if(proxiedPlayer.hasPermission("punish.punish")){
            commandSender.sendMessage("Du darfst diesen Spieler nicht bestrafen.");
            return;
        }

        if(!(templateRepository.getAviableReasons().contains(reason))){
            commandSender.sendMessage("Dieser Grund existiert nicht.");
            return;
        }

        if(punishmentRepository.getPunishmentByPlayerUUID(proxiedPlayer.getUniqueId())
                .getType()
                .equals(AbuseSystemType.BAN)){
            commandSender.sendMessage("Dieser Spieler ist bereits gebannt.");
            return;
        }

        if(punishmentRepository.getPunishmentByPlayerUUID(proxiedPlayer.getUniqueId())
                .getType()
                .equals(AbuseSystemType.MUTE)){
            if(templateRepository.getTemplateByPlayerPoints(reason, pointRepository.getPoints(proxiedPlayer.getUniqueId(), reason))
                    .getType()
                    .equals(AbuseSystemType.MUTE)){
                commandSender.sendMessage("Dieser Spieler ist bereits gemuted.");
                return;
            }
        }
        punishmentRepository.createPunishment(proxiedPlayer.getUniqueId(), punisher.getUniqueId(), reason);

    }
}
