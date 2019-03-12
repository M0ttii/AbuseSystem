package m0ttii.com.github.common.repository;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import m0ttii.com.github.common.AbuseSystemConstants;
import m0ttii.com.github.common.entity.punishment.points.AbuseSystemUserPoints;
import m0ttii.com.github.common.entity.punishment.points.AbuseSystemUserPointsTemplate;
import m0ttii.com.github.common.entity.user.AbuseSystemUser;
import xyz.morphia.Datastore;

import java.util.Arrays;
import java.util.Iterator;
import java.util.UUID;

public class PointRepository extends Repository<AbuseSystemUserPoints> {

    @Inject private UserRepository userRepository;

    @Inject
    protected PointRepository(@Named(AbuseSystemConstants.AbuseSystem_DATASTORE)Datastore datastore) {
        super(AbuseSystemUserPoints.class, datastore);
    }

    public AbuseSystemUserPoints findByUniqueId(UUID uuid){
        return this.createQuery()
                .field("player").equal(uuid)
                .get();
    }

    public Integer getPoints(UUID uuid, String template){
        AbuseSystemUserPoints repo = this.findByUniqueId(uuid);
        for (AbuseSystemUserPointsTemplate abuseSystemUserPointsTemplate : repo.getTemplates()) {
            if (abuseSystemUserPointsTemplate.getTemplate().equals(template)){
                return abuseSystemUserPointsTemplate.getPoints();
            }
        }
        return 0;
    }

    public Integer addPoints(UUID uuid, String template, Integer points){
        AbuseSystemUserPoints repo = this.findByUniqueId(uuid);
        for (AbuseSystemUserPointsTemplate abuseSystemUserPointsTemplate : repo.getTemplates()) {
            if (abuseSystemUserPointsTemplate.getTemplate().equals(template)){
                abuseSystemUserPointsTemplate.setPoints(abuseSystemUserPointsTemplate.getPoints() + points);
                getDatastore().save(repo);
                return abuseSystemUserPointsTemplate.getPoints();
            }
        }
        AbuseSystemUserPointsTemplate template1 = new AbuseSystemUserPointsTemplate();
        template1.setTemplate(template);
        template1.setPoints(points);
        repo.getTemplates().addAll(Arrays.asList(template1));
        System.out.println("LOLOL");
        getDatastore().save(repo);
        return null;

    }

    public Integer removePoints(UUID uuid, String template, Integer points){
        AbuseSystemUserPoints repo = this.findByUniqueId(uuid);
        for (AbuseSystemUserPointsTemplate abuseSystemUserPointsTemplate : repo.getTemplates()) {
            if (abuseSystemUserPointsTemplate.getTemplate().equals(template)){
                if(abuseSystemUserPointsTemplate.getPoints() != 0){
                    if(!(points < abuseSystemUserPointsTemplate.getPoints())){
                        abuseSystemUserPointsTemplate.setPoints(abuseSystemUserPointsTemplate.getPoints() + points);
                        getDatastore().save(repo);
                        return abuseSystemUserPointsTemplate.getPoints();
                    }
                }
                return null;
            }
        }
        return null;
    }

    public void createRepo(UUID player_uuid){
        System.out.println("DEBUG1");
        AbuseSystemUserPoints repo = new AbuseSystemUserPoints();
        repo.setPlayer(player_uuid);
        AbuseSystemUserPointsTemplate template = new AbuseSystemUserPointsTemplate();
        template.setTemplate("test");
        template.setPoints(1);
        repo.setTemplates(Arrays.asList(template));
        getDatastore().save(repo);
    }

}
