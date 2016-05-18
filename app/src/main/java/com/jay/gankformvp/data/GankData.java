package com.jay.gankformvp.data;

import com.google.gson.annotations.SerializedName;
import com.jay.gankformvp.data.entity.Gank;
import java.util.List;

/**
 * Created by jay on 16/5/18.
 */
public class GankData extends BaseData {

  public Result results;
  public List<String> category;

  public class Result {
    @SerializedName("Android") public List<Gank> androidList;
    @SerializedName("iOS") public List<Gank> iOSList;
    @SerializedName("前端") public List<Gank> webList;
    @SerializedName("瞎推荐") public List<Gank> recommendList;
    @SerializedName("拓展资源") public List<Gank> resourceList;
    @SerializedName("福利") public List<Gank> welfareList;
    @SerializedName("休息视频") public List<Gank> videoList;

  }

}
