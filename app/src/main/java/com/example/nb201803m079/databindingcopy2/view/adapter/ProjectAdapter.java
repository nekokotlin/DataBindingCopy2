package com.example.nb201803m079.databindingcopy2.view.adapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.databinding.ProjectListItemBinding;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.view.callback.ProjectClickCallback;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.List;
import java.util.Objects;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
    List<? extends Project> projectlist;

    @Nullable
    private final ProjectClickCallback projectClickCallback;

    public ProjectAdapter(@Nullable ProjectClickCallback projectClickCallback) {
        this.projectClickCallback = projectClickCallback;
    }

    public void setProjectlist(final List<? extends Project> projectlist) {
        if (this.projectlist == null) {
            this.projectlist = projectlist;
            notifyItemRangeInserted(0, projectlist.size());
        }else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return ProjectAdapter.this.projectlist.size();
                }

                @Override
                public int getNewListSize() {
                    return projectlist.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return ProjectAdapter.this.projectlist.get(oldItemPosition).id == projectlist.get(newItemPosition).id;
                }

                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Project project = projectlist.get(newItemPosition);
                    Project old = projectlist.get(oldItemPosition);

                    return project.id == old.id && Objects.equals(project.git_url, old.git_url);
                }
            });
            this.projectlist = projectlist;

            result.dispatchUpdatesTo(this);
        }
    }


    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        ProjectListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.project_list_item, parent, false);

                binding.setCallback(projectClickCallback);
        return new ProjectViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position){

        holder.binding.setProject(projectlist.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return projectlist == null ? 0 : projectlist.size();
    }

    static class ProjectViewHolder extends RecyclerView.ViewHolder {
        final ProjectListItemBinding binding;

        public ProjectViewHolder(ProjectListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
