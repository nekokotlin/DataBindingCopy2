package com.example.nb201803m079.databindingcopy2.service.repository;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {

    //GitHubService型である必要がある。インターフェースなので。
    //テスト
    private GitHubService gitHubService;

    private static ProjectRepository projectRepository;


    private ProjectRepository(){

        Retrofit retrofit = new Retrofit.Builder()
                //これは、インターフェースが一つしか無いからクラス名.呼び出し　ができている？　そもそも定数だから？
                .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                //GSONコンバータ
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        上記でインスタンスを作成し、作成したretrofitインスタンスからcreateを呼び出している
                gitHubService = retrofit.create(GitHubService.class);
    }

    //シングルトンってstaticだけで書けないのか。Javaだから
    public synchronized static ProjectRepository getInstance(){
        if(projectRepository == null){
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }

}
