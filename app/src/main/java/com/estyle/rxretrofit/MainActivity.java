package com.estyle.rxretrofit;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.estyle.rxretrofit.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String JSON_URL = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";

    private ActivityMainBinding binding;
    private MyRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        adapter = new MyRVAdapter(this);
        binding.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.qubaobei.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofit.create(HttpService.class)
                .getObservable(1)
                .subscribeOn(Schedulers.io())
//              .map(bean -> bean.getData())
                .map(new Func1<FoodBean, List<FoodBean.DataBean>>() {
                    @Override
                    public List<FoodBean.DataBean> call(FoodBean foodBean) {
                        return foodBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(datas -> adapter.addDatas(datas))
                .subscribe(new Action1<List<FoodBean.DataBean>>() {
                    @Override
                    public void call(List<FoodBean.DataBean> datas) {
                        adapter.addDatas(datas);
                    }
                });


    }

}
