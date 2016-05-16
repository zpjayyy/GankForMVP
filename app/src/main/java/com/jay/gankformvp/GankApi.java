package com.jay.gankformvp;

import com.jay.gankformvp.data.MeiziData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jay on 16/5/16.
 */
public interface GankApi {

  @GET("data/福利/" + GankFactory.meiziSize + "/{page}")
  Observable<MeiziData> getMeiziData(@Path("page") int page);

}
