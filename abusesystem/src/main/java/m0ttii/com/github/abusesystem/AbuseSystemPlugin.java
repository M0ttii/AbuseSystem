package m0ttii.com.github.abusesystem;

import co.aikar.commands.BungeeCommandManager;
import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.imaginarycode.minecraft.redisbungee.RedisBungeeAPI;
import lombok.Getter;
import lombok.Setter;
import m0ttii.com.github.abusesystem.command.PunishCommand;
import m0ttii.com.github.common.AbuseSystemCommon;
import m0ttii.com.github.common.AbuseSystemConfig;
import m0ttii.com.github.common.repository.*;
import net.md_5.bungee.api.plugin.Plugin;

import java.awt.*;

@Getter
@Setter
public class AbuseSystemPlugin extends Plugin {
    @Getter
    public static AbuseSystemPlugin abuseSystemPlugin;
    @Getter
    public static RedisBungeeAPI redisBungeeAPI;
    @Getter
    public static UserRepository userRepository;
    @Getter
    public static PunishmentRepository punishmentRepository;
    @Getter
    public static TemplateRepository templateRepository;
    @Getter
    public static PointRepository pointRepository;
    @Getter
    public static MessageRepository messageRepository;
    @Getter
    public static BungeeCommandManager commandManager;

    @ Override
    public void onEnable(){
        System.out.println("abusesystem enabled");
        abuseSystemPlugin = this;
        commandManager = new BungeeCommandManager(this);
        AbuseSystemConfig abuseSystemConfig = new AbuseSystemConfig("localhost", 27017, "" , "", "test");
        Injector injector = Guice.createInjector(new AbuseSystemCommon(abuseSystemConfig));

        this.userRepository = injector.getInstance(UserRepository.class);
        this.punishmentRepository = injector.getInstance(PunishmentRepository.class);
        this.templateRepository = injector.getInstance(TemplateRepository.class);
        this.pointRepository = injector.getInstance(PointRepository.class);
        this.messageRepository = injector.getInstance(MessageRepository.class);
        getCommandManager().registerCommand(new PunishCommand());

        registerTabCompletions();
        //new PunishCommand();
    }

    private void registerTabCompletions(){
        getCommandManager().getCommandCompletions().registerCompletion("reasons", c -> {
            return getTemplateRepository().getAviableReasons();
        });
    }

    public void onDisable(){

    }
}
