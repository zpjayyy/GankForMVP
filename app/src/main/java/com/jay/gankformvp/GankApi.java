package com.jay.gankformvp;

import com.jay.gankformvp.data.GankData;
import com.jay.gankformvp.data.MeiziData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jay on 16/5/16.
 */
public interface GankApi {

  @GET("data/福利/" + GankFactory.meiziSize + "/{page}") Observable<MeiziData> getMeiziData(
      @Path("page") int page);

  @GET("day/{year}/{month}/{day}") Observable<GankData> getGankData(@Path("year") int year,
      @Path("month") int month, @Path("day") int day);
}
