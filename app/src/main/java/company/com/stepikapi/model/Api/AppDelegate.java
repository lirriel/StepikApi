package company.com.stepikapi.model.Api;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import company.com.stepikapi.BuildConfig;
import company.com.stepikapi.model.Entity.Search;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class AppDelegate extends Application {

    private Api apiService;

    public static AppDelegate from(Context context) {
        return (AppDelegate) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public Api getApiService() {
        return apiService;
    }

    private void initRetrofit() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // set gson deserializer
        gsonBuilder.registerTypeAdapter(Search.class, new SearchDeserializer());
        Gson gson = gsonBuilder.create();

        apiService = new Retrofit.Builder()
                .baseUrl("https://stepik.org/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

}

