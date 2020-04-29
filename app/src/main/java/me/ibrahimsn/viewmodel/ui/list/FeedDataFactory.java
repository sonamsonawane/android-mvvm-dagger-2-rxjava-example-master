package me.ibrahimsn.viewmodel.ui.list;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import me.ibrahimsn.viewmodel.data.rest.RepoRepository;


public class FeedDataFactory extends DataSource.Factory {

    private MutableLiveData<FeedDataSource> mutableLiveData;
    private FeedDataSource feedDataSource;
    private Application application;
    private RepoRepository repoRepository;

    public FeedDataFactory(Application application, RepoRepository repoRepository) {
        this.application = application;
        this.repoRepository = repoRepository;
        this.mutableLiveData = new MutableLiveData<FeedDataSource>();
    }

    @Override
    public DataSource create() {
        feedDataSource = new FeedDataSource(application,repoRepository);
        mutableLiveData.postValue(feedDataSource);
        return feedDataSource;
    }


    public MutableLiveData<FeedDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
