package m0ttii.com.github.common.provider;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import m0ttii.com.github.common.AbuseSystemConstants;
import xyz.morphia.Morphia;
import xyz.morphia.ext.guice.GuiceExtension;

public class MorphiaProvider implements Provider<Morphia> {

    private Injector injector;

    @Inject
    public MorphiaProvider(Injector injector) {
        this.injector = injector;
    }

    @Override
    public Morphia get() {
        Morphia morphia = new Morphia();

        new GuiceExtension(morphia, this.injector);

        morphia.mapPackage(AbuseSystemConstants.AbuseSystem_COMMON_MODEL_PACKAGE);

        return morphia;


    }
}
