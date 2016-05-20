package com.jay.gankformvp;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jay on 16/5/16.
 */
public class GankRetrofit {

  final GankApi gankService;

  GankRetrofit() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addNetworkInterceptor(new HeaderInterceptor())
        .addNetworkInterceptor(new StethoInterceptor())
        .retryOnConnectionFailure(true)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://gank.io/api/")
        .client(client)
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    gankService = retrofit.create(GankApi.class);
  }

  public GankApi getGankService() {
    return gankService;
  }

  static class HeaderInterceptor implements Interceptor {
    @Override public Response intercept(Chain chain) throws IOException {
      Request originalRequest = chain.request();
      Request compressedRequest = originalRequest.newBuilder()
          .addHeader("1", "1")
          .build();
      return chain.proceed(compressedRequest);
    }
  }
}
