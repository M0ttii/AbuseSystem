package m0ttii.com.github.common.entity.punishment.template;

import lombok.Getter;
import lombok.Setter;
import m0ttii.com.github.common.entity.AbstractEntity;
import m0ttii.com.github.common.entity.punishment.AbuseSystemType;
import xyz.morphia.annotations.Embedded;

@Getter
@Setter
@Embedded
public class AbuseSystemTemplatePunishment extends AbstractEntity {
    /*"above":2,
         "type":"mute",
         "type_time":"1h",
         "points_expire":"1d",
         "evidence_needed": false,
         "reason": "Wiederholtes Spamming"
         */
    private int pointsNeeded;
    private int pointsToAdd;
    private AbuseSystemType type;
    private String expire_after; //Formats: s -> second, m -> minute, h -> hour, y -> year
    private String points_expire; //Same ^^
    private Boolean evidence_needed;
    private String reason;

}
