package me.ibrahimsn.viewmodel.data.rest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.ibrahimsn.viewmodel.data.model.NetworkRecords;
import me.ibrahimsn.viewmodel.data.model.Repo;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RepoService {

    @GET("orgs/Google/repos")
    Single<List<Repo>> getRepositories();

    @GET("repos/{owner}/{name}")
    Single<Repo> getRepo(@Path("owner") String owner, @Path("name") String name);

    @POST("datastore_search")
    Observable<NetworkRecords> getFilterList(
            @Query("resource_id") String resource_id,
            @Query("limit") String limit
    );

}
