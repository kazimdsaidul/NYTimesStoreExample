package com.saidul.nytimesstoreexample;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nytimes.android.external.store.base.Fetcher;
import com.nytimes.android.external.store.base.Store;
import com.nytimes.android.external.store.base.impl.BarCode;
import com.nytimes.android.external.store.base.impl.ParsingStoreBuilder;
import com.saidul.nytimesstoreexample.model.GithubUser;

import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TEST_USER_LOGIN = "kazimdsaidul";
    private GithubUser githubUser;
    private GithubApi githubApi;
    private Store store;
    private BarCode barCode;

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUser();
        initRetrofit();
        callAPI();


    }

    private void callAPI() {
        // get store
        store = provideGithubUserStore();

        // get bar code for unique
        final BarCode githubUserBarCode = StoreUtils.generateBarCodeForGithubUser(githubUser.getName());

        store.get(githubUserBarCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GithubUser>() {
                    @Override
                    public void onCompleted() {

                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(GithubUser githubUser) {
                        handleGithubUser(githubUser);
                    }
                });

    }

    private void handleGithubUser(GithubUser githubUser) {
        Toast.makeText(this, "UserName: " + githubUser.getName(), Toast.LENGTH_LONG).show();
    }


    private Store provideGithubUserStore() {
        return ParsingStoreBuilder.<BufferedSource, GithubUser>builder()
                .fetcher(new Fetcher<BufferedSource>() {
                    @NonNull
                    @Override
                    public Observable<BufferedSource> fetch(BarCode barCode) {
                        return StoreUtils.buildGithubUserFetcher(apiBuildSourceObservable());
                    }
                })
                .persister(StoreUtils.newPersister(this))

                .parser(StoreUtils.provideGsonParserFactoryFor(GithubUser.class))

                .open();
    }

    private Observable<GithubUser> apiBuildSourceObservable() {
        return githubApi.getUser(TEST_USER_LOGIN);
    }

    private void initUser() {
        this.githubUser = new GithubUser();
        this.githubUser.setName("kazimdsaidul");
        this.githubUser.setLogin(TEST_USER_LOGIN);
    }

    private void initRetrofit() {
        this.githubApi = provideGithubAPI();
    }

    private GithubApi provideGithubAPI() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(GithubApi.class);
    }
}
