package com.example.nb201803m079.databindingcopy2.view.ui;

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
import com.example.nb201803m079.databindingcopy2.databinding.FragmentProjectDetailsBinding;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.viewModel.ProjectViewModel;

public class ProjectFragment extends Fragment {
    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details,container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        ここでfactoryが出てる！
        ProjectViewModel.Factory factory = new ProjectViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_PROJECT_ID)
        );

        final ProjectViewModel viewModel = ViewModelProviders.of(this, factory).get(ProjectViewModel.class);

        binding.setProjectViewModel(viewModel);

        binding.setIsLoading(true);

        observeViewModel(viewModel);

    }

    public void observeViewModel(final ProjectViewModel viewModel){
        viewModel.getObservableProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project project) {
                if(project != null){
                    binding.setIsLoading(false);
                    viewModel.setProject(project);
                }
            }
        });
    }

    public static ProjectFragment forProject(String projectID) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();

        args.putString(KEY_PROJECT_ID, projectID);
        fragment.setArguments(args);

        return fragment;
    }

}
