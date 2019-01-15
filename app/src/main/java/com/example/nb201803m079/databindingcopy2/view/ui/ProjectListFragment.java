package com.example.nb201803m079.databindingcopy2.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.databinding.FragmentProjectListBinding;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.view.adapter.ProjectAdapter;
import com.example.nb201803m079.databindingcopy2.view.callback.ProjectClickCallback;
import com.example.nb201803m079.databindingcopy2.viewModel.ProjectListViewModel;

import java.util.List;


//前提として、このFragmentはMainActivityが呼ばれた時に呼ばれる。

public class ProjectListFragment extends Fragment {
    public static final String TAG = "ProjectListFragment";
    private ProjectAdapter projectAdapter;
    private FragmentProjectListBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstance) {

//        Fragmentに対しDatabindingを行うため、inflate
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);

        projectAdapter = new ProjectAdapter(projectClickCallback);
//        ProjectAdapterとは何か？
//        ここでアダプターをセットしている
        binding.projectList.setAdapter(projectAdapter);
//        isLoadingをビューで使えるようにする
        binding.setIsLoading(true);

        return binding.getRoot();
    }

    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ProjectListViewModel viewModel =
//              of=>activity/fragmentが生きている間、ViewModelを保持する
//              get=>viewmodelの取得
                ViewModelProviders.of(this).get(ProjectListViewModel.class);

        observeViewModel(viewModel);
    }


    private void observeViewModel(ProjectListViewModel viewModel) {


//        public LiveData<List<Project>> getProjectListObservable() {
//            return projectListObservable;
//        }
//
//        observe() メソッドを使用して、Observer オブジェクトを LiveData オブジェクトにアタッチします。
//        observe() メソッドは LifecycleOwner オブジェクトを取得します。
//        これにより、Observer オブジェクトが LiveData オブジェクトに登録され、変更が通知されるようになります。
//        通常は、UI コントローラ（アクティビティやフラグメントなど）の Observer オブジェクトをアタッチします。


//        変更の通知を行う
        viewModel.getProjectListObservable().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> projects) {
                if (projects != null) {
//                    ローディング中を消す
                    binding.setIsLoading(false);
//                    NULLでなければプロジェクト一覧をセット
                    projectAdapter.setProjectlist(projects);
                }
            }
        });

    }

    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
        @Override
        public void onClick(Project project) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(project);
            }
        }
    };


}
