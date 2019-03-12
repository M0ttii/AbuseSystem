package m0ttii.com.github.common.entity.user;

import lombok.*;
import m0ttii.com.github.common.entity.AbstractEntity;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Indexed;
import xyz.morphia.annotations.Property;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@Entity(value = "users", noClassnameStored = true)
public class AbuseSystemUser extends AbstractEntity {
    @Indexed(unique = true)
    private UUID uuid;

    private String latestName;
    private boolean notify;

    @Property("latest_offer")
    private Date latestOffer;
}
