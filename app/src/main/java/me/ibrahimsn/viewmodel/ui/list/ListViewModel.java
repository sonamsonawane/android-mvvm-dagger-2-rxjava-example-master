package me.ibrahimsn.viewmodel.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.ibrahimsn.viewmodel.data.model.NetworkRecords;
import me.ibrahimsn.viewmodel.data.model.Record;
import me.ibrahimsn.viewmodel.data.model.Repo;
import me.ibrahimsn.viewmodel.data.rest.RepoRepository;

public class ListViewModel extends ViewModel {

    private final RepoRepository repoRepository;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<Repo>> repos = new MutableLiveData<>();
    private final MutableLiveData<Boolean> repoLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<List<Record>> records = new MutableLiveData<>();
    private final MutableLiveData<NetworkRecords> networkRecords = new MutableLiveData<>();

    @Inject
    public ListViewModel(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
        disposable = new CompositeDisposable();
        fetchRepos();
    }

    LiveData<List<Repo>> getRepos() {
        return repos;
    }
    LiveData<Boolean> getError() {
        return repoLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }
    LiveData<NetworkRecords> getNetworkRecords(){ return networkRecords; }
    LiveData<List<Record>> getRecords(){
        return records;
    }


    private void fetchRepos() {
        loading.setValue(true);
        repoRepository.getSKUResponseSingle("a807b7ab-6cad-4aa6-87d0-e283a7353a0f",String.valueOf(10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NetworkRecords>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NetworkRecords result) {
                        repoLoadError.setValue(false);
                        networkRecords.setValue(result);
                        records.setValue(result.getResult().getRecords());
                        loading.setValue(false);
                        Log.e("!!!!!!!!!!!!!!!!!",result.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        repoLoadError.setValue(true);
                        loading.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }


    public void pullProducts(int limit) {
        fetchRepos();
    }
}
