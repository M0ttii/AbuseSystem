package m0ttii.com.github.common.entity;

import lombok.*;
import org.bson.types.ObjectId;
import xyz.morphia.annotations.Id;
import xyz.morphia.annotations.PreSave;

import java.util.Date;


@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AbstractEntity {
    @Id
    @Getter
    private ObjectId id;

    private Date createdAt;
    private Date updatedAt;


    @PreSave
    public void preSave() {
        this.createdAt = createdAt != null ? createdAt : new Date();
        this.updatedAt = new Date();
    }
}

