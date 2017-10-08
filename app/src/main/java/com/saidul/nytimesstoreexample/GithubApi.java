package com.saidul.nytimesstoreexample;

import com.saidul.nytimesstoreexample.model.GithubUser;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Kazi Md. Saidul Email: Kazimdsaidul@gmail.com  Mobile: +8801675349882 on 10/8/17.
 */

interface GithubApi {

    @GET("users/{username}")
    Observable<GithubUser> getUser(@Path("username") String username);

}
