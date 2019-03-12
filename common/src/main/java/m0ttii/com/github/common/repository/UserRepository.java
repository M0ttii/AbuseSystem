package m0ttii.com.github.common.repository;


import com.google.inject.Inject;
import com.google.inject.name.Named;
import m0ttii.com.github.common.AbuseSystemConstants;
import m0ttii.com.github.common.entity.user.AbuseSystemUser;
import xyz.morphia.Datastore;
import xyz.morphia.converters.UUIDConverter;


import java.util.UUID;

public class UserRepository extends Repository<AbuseSystemUser> {

    @Inject private PunishmentRepository punishmentRepository;
    @Inject private PointRepository pointRepository;

    @Inject
    protected UserRepository(@Named(AbuseSystemConstants.AbuseSystem_DATASTORE) Datastore datastore) {
        super(AbuseSystemUser.class, datastore);
    }

    public AbuseSystemUser findByUniqueId(UUID uuid) {
        return this.createQuery()
                .field("uuid").equal(uuid)
                .get();
    }


    public AbuseSystemUser findByLatestName(String name) {
        return this.createQuery()
                .field("latestName").equal(name)
                .get();
    }


    /*public List<AbuseSystemPunishment> getPunishments() {
        return punishmentRepository.createQuery().
    }*/

    //DEBUG
    public AbuseSystemUser createUser(String latestName) {
        AbuseSystemUser user = new AbuseSystemUser();
        user.setUuid(UUID.fromString(latestName));
        user.setLatestName(latestName);
        pointRepository.createRepo(UUID.fromString(latestName));
        getDatastore().save(user);
        return user;
    }

    public AbuseSystemUser createUser(UUID uuid, String latestName) {
        AbuseSystemUser user = new AbuseSystemUser();
        user.setUuid(uuid);
        user.setLatestName(latestName);
        pointRepository.createRepo(uuid);
        getDatastore().save(user);
        return user;
    }








}
