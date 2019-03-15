package m0ttii.com.github.common.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import m0ttii.com.github.common.AbuseSystemConstants;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import m0ttii.com.github.common.entity.punishment.template.AbuseSystemTemplate;
import m0ttii.com.github.common.entity.punishment.template.AbuseSystemTemplatePunishment;
import xyz.morphia.Datastore;
import xyz.morphia.query.UpdateOperations;

import java.util.*;

public class TemplateRepository extends Repository<AbuseSystemTemplate> {

    @Inject
    protected TemplateRepository(@Named(AbuseSystemConstants.AbuseSystem_DATASTORE)Datastore datastore) {
        super(AbuseSystemTemplate.class, datastore);
    }

    public AbuseSystemTemplate createTemplate(String name, Integer id, String neededPermission){
        AbuseSystemTemplate template = new AbuseSystemTemplate();
        template.setName(name);
        template.setNumber(id);
        template.setPermission(neededPermission);
        AbuseSystemTemplatePunishment templatePunishment = new AbuseSystemTemplatePunishment();
        templatePunishment.setType(AbuseSystemType.KICK);
        templatePunishment.setReason("Default");
        templatePunishment.setEvidence_needed(true);
        templatePunishment.setExpire_after("1d");
        templatePunishment.setPointsNeeded(2);
        template.setDifferentPunishmentTemplates(Arrays.asList(templatePunishment));
        getDatastore().save(template);
        return template;
    }

    public AbuseSystemTemplate getTemplateById(Integer id){
        return this.createQuery()
                .field("number").equal(id)
                .get();
    }

    public AbuseSystemTemplate getTemplateByName(String name){
        return this.createQuery()
                .field("name").equal(name)
                .get();
    }

    public List<String> getAviableReasons(){
        List reasons = new ArrayList();
        getTemplates().forEach(abuseSystemTemplate -> {
            reasons.add(abuseSystemTemplate.getName());
        });
        return reasons;
    }

    public List<AbuseSystemTemplate> getTemplates(){
        return this.findAll();
    }

    public void editTemplateById(Integer id, String field, String value){
        AbuseSystemTemplate query = getTemplateById(id);
        UpdateOperations operation = getDatastore().createUpdateOperations(AbuseSystemTemplate.class).set(field, value);
        getDatastore().update(query, operation);
    }

    public AbuseSystemTemplatePunishment getDifferentPTemplates(String templatename, Integer number){
        AbuseSystemTemplate query = this.getTemplateByName(templatename);
        return query.getDifferentPunishmentTemplates().get(number);
    }

    public AbuseSystemTemplatePunishment getTemplateByPlayerPoints(String templatename, Integer playerpoints){
        AbuseSystemTemplate query = this.getTemplateByName(templatename);
        NavigableSet<Integer> set = new TreeSet<Integer>();
        query.getDifferentPunishmentTemplates().forEach(abuseSystemTemplatePunishment -> {
            set.add(abuseSystemTemplatePunishment.getPointsNeeded());
        });
        Integer nextpoints;
        if(playerpoints < set.pollFirst()){
            nextpoints = set.pollFirst();
        }else{
            nextpoints = set.floor(playerpoints);
        }
        return query.getDifferentPunishmentTemplates().stream()
                .filter(abuseSystemTemplatePunishment -> abuseSystemTemplatePunishment.getPointsNeeded() == nextpoints)
                .findFirst()
                .orElse(null);
    }


}
