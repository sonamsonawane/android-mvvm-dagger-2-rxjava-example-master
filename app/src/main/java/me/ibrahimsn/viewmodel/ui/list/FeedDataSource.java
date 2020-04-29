package me.ibrahimsn.viewmodel.ui.list;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import me.ibrahimsn.viewmodel.data.model.NetworkRecords;
import me.ibrahimsn.viewmodel.data.model.Record;
import me.ibrahimsn.viewmodel.data.rest.RepoRepository;
import me.ibrahimsn.viewmodel.dto.ApiResponse;
import me.ibrahimsn.viewmodel.dto.Resource;
import me.ibrahimsn.viewmodel.room.repository.AboutCanadaRepository;
import me.ibrahimsn.viewmodel.util.NetworkBoundResource;
import me.ibrahimsn.viewmodel.util.NetworkState;

public class FeedDataSource extends PageKeyedDataSource<Integer, Record> implements BaseConstants {

    private static final String TAG = FeedDataSource.class.getSimpleName();
    private final AboutCanadaRepository aboutCanadaRepository;

    private Application application;
    private RepoRepository repoRepository;

    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public FeedDataSource(Application application, RepoRepository repoRepository) {
        this.application = application;
        this.repoRepository = repoRepository;
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        aboutCanadaRepository = new AboutCanadaRepository(application);
    }


    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Record> callback) {
        ArrayList<Record> list = new ArrayList<Record>();
        Record record = new Record();
        list.add(record);
        aboutCanadaRepository.insertAll(list);
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        LiveData<Resource<List<Record>>> data = new NetworkBoundResource<List<Record>, List<Record>>() {
            @Override
            protected void saveCallResult(@NonNull List<Record> items) {
                aboutCanadaRepository.insertAll(items);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Record> data) {
                return true;//let's always refresh to be up to date. data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<Record>> loadFromDb() {
                return aboutCanadaRepository.getAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Record>>> createCall() {
                LiveData<ApiResponse<List<Record>>> response = new MutableLiveData<>();
//                LiveData<ApiResponse<List<Record>>> response = Api.getApi().getAboutCanadas();
//                return response;
                repoRepository.getSKUResponseSingle(API_KEY, params.requestedLoadSize, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<NetworkRecords>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(NetworkRecords result) {
                                callback.onResult(result.getResult().getRecords(), 0, params.requestedLoadSize);
                                initialLoading.postValue(NetworkState.LOADED);
                                networkState.postValue(NetworkState.LOADED);
                            }

                            @Override
                            public void onError(Throwable e) {
                                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
                                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
                            }
                        });
                return response;
            }
        }.getAsLiveData();
        /*repoRepository.getSKUResponseSingle(API_KEY, params.requestedLoadSize, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkRecords>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkRecords result) {
                        callback.onResult(result.getResult().getRecords(), 0, params.requestedLoadSize);
                        initialLoading.postValue(NetworkState.LOADED);
                        networkState.postValue(NetworkState.LOADED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
                    }
                });*/

    }


    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, Record> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, Record> callback) {
        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);
        networkState.postValue(NetworkState.LOADING);
        repoRepository.getSKUResponseSingle(API_KEY, params.requestedLoadSize, params.key)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<NetworkRecords>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(NetworkRecords result) {
                        int nextKey = (params.key == result.getResult().getTotal()) ? null : params.key + 1;
                        callback.onResult(result.getResult().getRecords(), nextKey);
                        networkState.postValue(NetworkState.LOADED);
                    }

                    @Override
                    public void onError(Throwable e) {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
                    }
                });
    }
}