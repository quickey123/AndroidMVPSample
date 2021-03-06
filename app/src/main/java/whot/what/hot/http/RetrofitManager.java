package whot.what.hot.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import whot.what.hot.api.ApiServices;

/**
 * 請求 retrofit 方法
 * Created by sapido on 22/09/2017.
 */

public class RetrofitManager {
    private static ApiServices SERVICE;
    //請求逾時
    private static final int DEFAULT_TIMEOUT = 4;

    public static ApiServices getDefault(String URL) {
        if (SERVICE == null) {
            //手動創建一個OkHttpClient並設置超時時間
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            //對所有請求加上頭
            httpClientBuilder.addInterceptor(chain -> {
                    Request request = chain.request();
                    okhttp3.Response originalResponse = chain.proceed(request);
                    return originalResponse.newBuilder().header("key1", "value1").addHeader("key2", "value2").build();
            });
            SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(URL)
                    .build().create(ApiServices.class);
        }
        return SERVICE;
    }

    /**
     * 當執行過後清除service，
     * 確保每次新增的service url可以一致或不一致
     * */
    public static void removeService(){
        SERVICE = null;
    }
}
