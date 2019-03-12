package m0ttii.com.github.common.repository;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import m0ttii.com.github.common.AbuseSystemConstants;
import m0ttii.com.github.common.entity.punishment.AbuseSystemPunishment;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.entity.punishment.template.AbuseSystemTemplate;
import m0ttii.com.github.common.entity.punishment.template.AbuseSystemTemplatePunishment;
import m0ttii.com.github.common.entity.user.AbuseSystemUser;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PunishmentRepository extends Repository<AbuseSystemPunishment> {

    @Inject private UserRepository userRepository;
    @Inject private TemplateRepository templateRepository;
    @Inject private PointRepository pointRepository;

    @Inject
    protected PunishmentRepository(@Named(AbuseSystemConstants.AbuseSystem_DATASTORE)Datastore datastore) {
        super(AbuseSystemPunishment.class, datastore);
    }

    private Date getDate(String timeToAdd){
        Date currentDate = new Date();

        // convert date to calendar
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        System.out.println(currentDate);

        // manipulate date
        String substring = timeToAdd.substring(timeToAdd.length() - 1);
        String string = timeToAdd.substring(0, timeToAdd.length() - 1);
        switch (substring){
            case "s":
                c.add(Calendar.SECOND, Integer.parseInt(string));
            case "m":
                c.add(Calendar.MINUTE, Integer.parseInt(string));
            case "h":
                c.add(Calendar.HOUR, Integer.parseInt(string));
            case "d":
                c.add(Calendar.DATE, Integer.parseInt(string));
                System.out.println(Integer.parseInt(string));
        }
        Date currentDatePlusOne = c.getTime();
        System.out.println(currentDatePlusOne);
        return currentDatePlusOne;
    }


    public AbuseSystemPunishment createPunishment(UUID player_uuid, UUID punisher_uuid, String reason){
        AbuseSystemPunishment punishment = new AbuseSystemPunishment();
        AbuseSystemUser player = userRepository.findByUniqueId(player_uuid);
        AbuseSystemTemplatePunishment usedTemplate = templateRepository.getTemplateByPlayerPoints(reason, pointRepository.getPoints(player_uuid, "reason"));
        punishment.setPlayer_id(player);
        punishment.setPunisher_id(userRepository.findByUniqueId(punisher_uuid));
        punishment.setType(usedTemplate.getType());
        punishment.setReason(usedTemplate.getReason());
        punishment.setExpire_at(getDate(usedTemplate.getExpire_after()));
        pointRepository.addPoints(player_uuid, reason, usedTemplate.getPointsToAdd());
        getDatastore().save(punishment);
        System.out.println("Finish");
        return punishment;
    }

    public AbuseSystemPunishment createPunishment(UUID player_uuid, UUID punisher_uuid, AbuseSystemType type, String reason, Date expire_at){
        AbuseSystemPunishment punishment = new AbuseSystemPunishment();
        punishment.setPlayer_id(userRepository.findByUniqueId(player_uuid));
        punishment.setPunisher_id(userRepository.findByUniqueId(punisher_uuid));
        punishment.setType(type);
        punishment.setReason(reason);
        punishment.setExpire_at(expire_at);
        getDatastore().save(punishment);
        return punishment;
    }

    public AbuseSystemPunishment createPunishment(UUID player_uuid, UUID punisher_uuid, AbuseSystemType type, String reason, String evidence, Date expire_at){
        AbuseSystemPunishment punishment = new AbuseSystemPunishment();
        punishment.setPlayer_id(userRepository.findByUniqueId(player_uuid));
        punishment.setPunisher_id(userRepository.findByUniqueId(punisher_uuid));
        punishment.setType(type);
        punishment.setReason(reason);
        punishment.setEvidence(evidence);
        punishment.setExpire_at(expire_at);
        getDatastore().save(punishment);
        return punishment;
    }

    public List<AbuseSystemPunishment> getPunishmentHistory(UUID player_uuid){
        AbuseSystemUser player = userRepository.findByUniqueId(player_uuid);
        return (List<AbuseSystemPunishment>) this.createQuery()
                .field("player_id").equal(player)
                .get();
    }

    public AbuseSystemPunishment getPunishmentByPlayerUUID(UUID player_uuid){
        AbuseSystemUser player = userRepository.findByUniqueId(player_uuid);
        AbuseSystemPunishment punishment = this.createQuery()
                .field("player_id").equal(player)
                .get();
        System.out.println(punishment);
        if(punishment.getExpire_at().after(new Date())){
            return punishment;
        }
        return null;
    }

    public boolean isBanned(UUID player_uuid){
        AbuseSystemUser player = userRepository.findByUniqueId(player_uuid);
        AbuseSystemPunishment punishment = this.createQuery()
                .field("player_id").equal(player)
                .get();
        if(punishment.getType().equals(AbuseSystemType.BAN)){
            return true;
        }
        return false;
    }




}
