package com.example.nb201803m079.databindingcopy2.service.repository;

import android.support.annotation.Nullable;

import com.example.nb201803m079.databindingcopy2.service.model.Project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GitHubService {

    //これは定数？
    String HTTPS_API_GITHUB_URL = "https://api.github.com/";


    //    この中身見たらGET以下の指定についてわかる
//    https://api.github.com/
    //ここの、getProjectListをどこで使うのか確認。
    @GET("users/{user}/repos")
    Call<List<Project>> getProjectList(@Path("user") String user);

    @GET("/repos/{user}/{reponame}")
    Call<Project> getProjectDetails(@Path("user") String user, @Path("reponame") String projectName);

}
