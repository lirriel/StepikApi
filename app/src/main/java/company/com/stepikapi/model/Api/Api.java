package company.com.stepikapi.model.Api;

import company.com.stepikapi.model.Entity.Search;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("search-results")
    Call<Search> getSearch(@Query("query") String course);
}