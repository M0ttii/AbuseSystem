package m0ttii.com.github.common.repository;

import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import xyz.morphia.Datastore;
import xyz.morphia.query.Query;

import java.util.List;

public abstract class Repository<ValueType> {

    private final Class<? extends ValueType> entityClazz;

    private final Datastore datastore;

    protected Repository(Class<? extends ValueType> entityClazz, Datastore datastore) {
        this.entityClazz = entityClazz;
        this.datastore = datastore;

    }

    protected Datastore getDatastore() {
        return datastore;
    }

    public ValueType save(ValueType value) {
        this.datastore.save(value);
        return value;
    }

    public boolean deleteById(ObjectId id) {
        WriteResult writeResult = this.datastore.delete(this.entityClazz, id);
        return writeResult.getN() == 1;
    }

    public ValueType findById(ObjectId id) {
        return this.datastore.get(this.entityClazz, id);
    }

    public List<ValueType> findAll() {
        return this.createQuery().asList();
    }

    Query<ValueType> createQuery() {
        return (Query<ValueType>) this.datastore.createQuery(this.entityClazz);
    }

    Query<ValueType> find(){
        return (Query<ValueType>) this.datastore.find(this.entityClazz);
    }


}
