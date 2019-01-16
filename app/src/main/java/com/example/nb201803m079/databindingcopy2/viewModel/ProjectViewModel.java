package com.example.nb201803m079.databindingcopy2.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.service.repository.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {

    private final LiveData<Project> projectObservable;
    private final String mProjectID;

    public ObservableField<Project> project = new ObservableField<>();




    private ProjectViewModel(@NonNull Application application, final String projectID) {
        super(application);
        this.mProjectID = projectID;
        projectObservable = ProjectRepository.getInstance().getProjectDetails(getApplication().getString(R.string.github_user_name), mProjectID);
    }

    public LiveData<Project> getObservableProject() {
        return projectObservable;
    }

    public void setProject(Project project){
        this.project.set(project);
    }





    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final String projectID;

        public Factory(@NonNull Application application, String projectID) {
            this.application = application;
            this.projectID = projectID;
        }

        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ProjectViewModel(application, projectID);
        }
    }
}


//オブザーバーについて
//オブザーバーが次に非アクティブな状態からアクティブな状態に変わったときには、
// 前回アクティブになったときから値が変更された場合にのみ最新データを受け取ります。


