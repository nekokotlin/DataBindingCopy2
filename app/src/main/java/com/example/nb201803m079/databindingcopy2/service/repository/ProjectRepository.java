package com.example.nb201803m079.databindingcopy2.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.Nullable;

import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectRepository {

    //GitHubService型である必要がある。インターフェースなので。
    //テスト
    private GitHubService gitHubService;

    private static ProjectRepository projectRepository;


    //ここはコンストラクタ。インスタンスを作成している
    private ProjectRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                //定数だから
                .baseUrl(GitHubService.HTTPS_API_GITHUB_URL)
                //GSONコンバータ
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        上記でインスタンスを作成し、作成したretrofitインスタンスからcreateを呼び出している
//        定義元ジャンプすれば、Retrofitが持つcreateメソッドに飛べる
        gitHubService = retrofit.create(GitHubService.class);
    }

    //シングルトンってstaticだけで書けないのか。Javaだから
    public synchronized static ProjectRepository getInstance() {
        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }
        return projectRepository;
    }


//    LiveData　つまり死活管理？
    public LiveData<List<Project>> getProjectList(String userId) {
        final MutableLiveData<List<Project>> data = new MutableLiveData<>();

//        gitHubService = retrofit.create(GitHubService.class);
//        これで作ったインスタンス。
//        enqueueはCall.java内にある。
//        Callbackは、retrofit2が持つインターフェース
//        getProjectListはGitHubServiceで指定したインターフェース

//        retrofitが持つenqueメソッドを使い非同期処理を行う。
//        その後、Callback.java内にあるインターフェースonResponse/onFailureの実装を書く。
//        onResponseでは、dataに値を入れている。
//        onFailureでは、エラーが発生した場合にnullを入れている。(あまりよくないらしい)

        gitHubService.getProjectList(userId).enqueue(new Callback<List<Project>>() {
            @Override
            public void onResponse(Call<List<Project>> call, @Nullable Response<List<Project>> response) {
//                これはMutableLiveData
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

//    リストと詳細を分けて取得している
//    なので型がList<Project>か、<Project>になる

    public LiveData<Project> getProjectDetails(String userID, String projectName) {
        final MutableLiveData<Project> data = new MutableLiveData<>();

//        GitHubServiceはインターフェース　その中にgetProjectListやgetProgectDetailsがある　実装をもたせたい
//        enqueueはインターフェースだから実装をもたせる
        gitHubService.getProjectDetails(userID, projectName).enqueue(new Callback<Project>() {
            @Override
            public void onResponse(Call<Project> call, Response<Project> response) {
                simulateDelay();
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Project> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }

    //引用：マルチスレッドの並列処理で無限ループを実行していた場合、CPUの負荷が大きくなりリソースを消費してメモリリークなどPCの動作が重くなる要因となってしまいます。
    //そのため、マルチスレッドの処理中にsleepメソッドを使用して、処理を一時停止すればCPUの負荷を抑えられることができます。
    private void simulateDelay() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
