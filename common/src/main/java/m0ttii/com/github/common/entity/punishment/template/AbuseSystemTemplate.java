package m0ttii.com.github.common.entity.punishment.template;

import lombok.Getter;
import lombok.Setter;
import m0ttii.com.github.common.entity.AbstractEntity;
import xyz.morphia.annotations.Embedded;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Reference;

import java.util.List;

@Getter
@Setter
@Entity(value = "templates", noClassnameStored = true)
public class AbuseSystemTemplate extends AbstractEntity {
    /*"_id":"",
   "number": 31,
   "name":"BOTS",
   "permission": "iwmedia.abc",
   "punishments":[],
   "created_at":"",
   "updated_at":""

   student.grades = Arrays.asList(grade1, grade2);*/

    private int number;
    private String name;
    private String permission;
    private List<AbuseSystemTemplatePunishment> differentPunishmentTemplates;




}
