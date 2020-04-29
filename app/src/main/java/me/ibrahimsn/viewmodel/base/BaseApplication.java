package me.ibrahimsn.viewmodel.base;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import me.ibrahimsn.viewmodel.di.component.ApplicationComponent;
import me.ibrahimsn.viewmodel.di.component.DaggerApplicationComponent;

public class BaseApplication extends DaggerApplication {

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
         component = DaggerApplicationComponent.builder().application(this).build();
        component.inject(this);

        return component;
    }

    public static ApplicationComponent getComponent() {
        return component;
    }
}
