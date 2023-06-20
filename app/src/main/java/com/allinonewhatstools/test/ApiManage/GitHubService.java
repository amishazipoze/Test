package com.allinonewhatstools.test.ApiManage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GitHubService {
    @GET("retest.php")
    Call<ResponseBody> listRepos();
}
