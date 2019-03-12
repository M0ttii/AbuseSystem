package m0ttii.com.github.common;

import com.google.inject.Guice;
import com.google.inject.Injector;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.repository.PointRepository;
import m0ttii.com.github.common.repository.PunishmentRepository;
import m0ttii.com.github.common.repository.TemplateRepository;
import m0ttii.com.github.common.repository.UserRepository;

import java.util.Date;
import java.util.UUID;


public class Test {

    /*public static void main( String[] args ){
        AbuseSystemConfig abuseSystemConfig = new AbuseSystemConfig("localhost", 27017, "" , "", "test");
        Injector injector = Guice.createInjector(new AbuseSystemCommon(abuseSystemConfig));

        UserRepository userRepository = injector.getInstance(UserRepository.class);
        PunishmentRepository punishmentRepository = injector.getInstance(PunishmentRepository.class);
        TemplateRepository templateRepository = injector.getInstance(TemplateRepository.class);
        PointRepository pointRepository = injector.getInstance(PointRepository.class);


        //templateRepository.createTemplate("Hacking", 1, "perm.hacking");
        //System.out.println(punishmentRepository.getPunishmentByPlayerUUID(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6")));
        //System.out.println(punishmentRepository.getActivePunishmentByPlayerUUID(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6")));
        //punishmentRepository.
        //punishmentRepository.createPunishment(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6"), UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6"), "Hacking");
        //userRepository.createUser(String.valueOf(UUID.randomUUID()),"Motti");
        //System.out.println(userRepository.findByUniqueId(UUID.fromString("331a85f7-b036-45c0-8c64-1dc961e9b645")));
        //punishmentRepository.createPunishment(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6"), UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6"), AbuseSystemType.BAN, "Test", new Date());
        //pointRepository.addPoints(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6"), "test", 2);
        //System.out.println(templateRepository.getDifferentPTemplates("Hacking", 0).getReason());
        //System.out.println(templateRepository.getTemplateByPlayerPoints("Hacking", 5).getReason());
        //System.out.println(pointRepository.findByUniqueId(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6")).getTemplates().get(1).getPoints());
        //System.out.println(pointRepository.findByUniqueId(UUID.fromString("14e0fb5b-5fea-4cc2-85be-b9d9e7d080b6")));
        //System.out.println(userRepository.findByLatestName("Motti").getUuid());


        //punishmentRepository.createPunishment(UUID.fromString("e34b3560-0c4f-4d27-cf22-7f1ab2947fbe"), UUID.fromString("c24d636b-0e1a-fb15-eff6-c96c334294a6"), AbuseSystemType.BAN, "Hacking", new Date());


    }*/
}
