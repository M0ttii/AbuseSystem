package m0ttii.com.github.common.entity;

import lombok.*;
import xyz.morphia.annotations.Entity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@Entity(value = "messages", noClassnameStored = true)
public class MessageEntity extends AbstractEntity{
    private String name;
    private String message;
}
