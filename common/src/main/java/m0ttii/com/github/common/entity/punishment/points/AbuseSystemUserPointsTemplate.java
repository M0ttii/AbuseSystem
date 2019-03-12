package m0ttii.com.github.common.entity.punishment.points;

import lombok.Getter;
import lombok.Setter;
import m0ttii.com.github.common.entity.punishment.template.AbuseSystemTemplate;
import xyz.morphia.annotations.Embedded;

@Getter
@Setter
@Embedded
public class AbuseSystemUserPointsTemplate {
    private String template;
    private Integer points;
}
