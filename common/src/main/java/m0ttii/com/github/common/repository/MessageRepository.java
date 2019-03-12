package m0ttii.com.github.common.repository;

import m0ttii.com.github.common.AbuseSystemConstants;
import m0ttii.com.github.common.entity.MessageEntity;
import xyz.morphia.Datastore;

import javax.inject.Named;

public class MessageRepository extends Repository<MessageEntity> {

    protected MessageRepository(@Named(AbuseSystemConstants.AbuseSystem_DATASTORE) Datastore datastore) {
        super(MessageEntity.class, datastore);
    }

    public String getByName(String name){
        return this.createQuery()
                .field("name")
                .equal(name).get().getMessage();
    }

    public MessageEntity createMessage(String name, String message){
        if(!(this.createQuery().field("name").equal(name).get() == null)){
            return null;
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setName(name);
        messageEntity.setMessage(message);
        getDatastore().save(messageEntity);
        return messageEntity;
    }
}
