package com.estyle.rxretrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface HttpService {

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20")
    public Call<FoodBean> getCall(@Query("page") int page);

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20")
    public Observable<FoodBean> getObservable(@Query("page") int page);

}
