package m0ttii.com.github.common;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import m0ttii.com.github.common.provider.MorphiaProvider;
import m0ttii.com.github.common.repository.PointRepository;
import m0ttii.com.github.common.repository.UserRepository;
import xyz.morphia.Datastore;
import xyz.morphia.Morphia;

public class AbuseSystemCommon extends AbstractModule {
    private final AbuseSystemConfig abuseSystemConfig;

    public AbuseSystemCommon(AbuseSystemConfig AbuseSystemConfig) {
        this.abuseSystemConfig = AbuseSystemConfig;
    }

    @Override
    protected void configure() {

        bind(Morphia.class).toProvider(MorphiaProvider.class).asEagerSingleton();
        bind(PointRepository.class);
        bind(UserRepository.class);
        //bind(PunishmentRepository.class).annotatedWith(Names.named(AbuseSystemConstants.AbuseSystem_DATASTORE));
    }

    @Provides
    public MongoClient provideMongoClient() {
        ServerAddress serverAddress = new ServerAddress(this.abuseSystemConfig.getDatabaseHost(), this.abuseSystemConfig.getDatabasePort());

        //MongoCredential credential = MongoCredential.createCredential(this.abuseSystemConfig.getDatabaseUser(), this.abuseSystemConfig.getDatabaseName(), this.abuseSystemConfig.getDatabasePassword().toCharArray());

        return new MongoClient(serverAddress);//, Collections.singletonList(credential));
    }

    @Provides
    @Named(AbuseSystemConstants.AbuseSystem_DATASTORE)
    public Datastore provideDatastore(MongoClient mongoClient, Morphia morphia) {
        Datastore datastore = morphia.createDatastore(mongoClient, this.abuseSystemConfig.getDatabaseName());
        datastore.ensureIndexes();

        return datastore;
    }
}
