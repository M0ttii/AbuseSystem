package m0ttii.com.github.common.entity.punishment;

import lombok.Getter;
import lombok.Setter;
import m0ttii.com.github.common.entity.AbstractEntity;
import m0ttii.com.github.common.entity.user.AbuseSystemUser;
import xyz.morphia.annotations.*;

import java.util.Date;


@Setter
@Getter
@Entity(value = "punishment", noClassnameStored = true)
public class AbuseSystemPunishment extends AbstractEntity {
    @Reference
    private AbuseSystemUser player_id;
    @Reference
    private AbuseSystemUser punisher_id;
    private AbuseSystemType type;
    private String reason;
    private String evidence;
    private Date expire_at;
}
