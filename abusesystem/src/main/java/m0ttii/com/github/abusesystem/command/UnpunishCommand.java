package m0ttii.com.github.abusesystem.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import m0ttii.com.github.abusesystem.AbuseSystemConstants;
import m0ttii.com.github.abusesystem.AbuseSystemPlugin;
import m0ttii.com.github.common.repository.PointRepository;
import m0ttii.com.github.common.repository.PunishmentRepository;
import m0ttii.com.github.common.repository.TemplateRepository;
import m0ttii.com.github.common.repository.UserRepository;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class UnpunishCommand extends BaseCommand {

    private UserRepository userRepository;
    private PunishmentRepository punishmentRepository;
    private TemplateRepository templateRepository;
    private PointRepository pointRepository;

    public UnpunishCommand(){
        userRepository = AbuseSystemPlugin.getUserRepository();
        punishmentRepository = AbuseSystemPlugin.getPunishmentRepository();
        templateRepository = AbuseSystemPlugin.getTemplateRepository();
        pointRepository = AbuseSystemPlugin.getPointRepository();

    }

    @Default
    public void defaultMessage(CommandSender player) {
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/punish [player] [reason]");
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/unpunish [player]");
        player.sendMessage(AbuseSystemConstants.AbuseSystem_PREFIX + "/pinfo [player]");
    }

    @Subcommand("optional")
    public void unpunishPlayer(CommandSender commandSender, @Optional ProxiedPlayer proxiedPlayer){
        if(userRepository.findByUniqueId(proxiedPlayer.getUniqueId()) == null){
            commandSender.sendMessage("§cDieser Spieler existiert nicht.");
        }
    }



}
