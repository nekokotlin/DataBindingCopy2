package com.example.nb201803m079.databindingcopy2.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.service.repository.ProjectRepository;

import java.util.List;

import retrofit2.http.Path;

public class ProjectListViewModel extends AndroidViewModel {

    private final LiveData<List<Project>> projectListObservable;
    public ProjectListViewModel(Application application){
        super(application);

//        ProjectRepositoryクラスのインスタンスを作成し、
// 　　　　そのインスタンスが持つインスタンスメソッド｢getProjectList｣を呼び出している。
//        getProjectListの引数はString型のuserIdなので、渡してあげる。
//        それが更にGitHubServiceのCall<List<Project>> getProjectList(@Path("user") String user);
//        ここに繋がり、APIを通じてデータを取得できる
//        依存関係が一方向！今の所ViewModel→Model→APIの順に動いている
        projectListObservable = ProjectRepository.getInstance().getProjectList(getApplication().getString(R.string.github_user_name));
    }

//    取得した値を返す
//    観察可能
    public LiveData<List<Project>> getProjectListObservable() {
        return projectListObservable;
    }

}
