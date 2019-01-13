package com.example.nb201803m079.databindingcopy2.view.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.service.model.Project;

public class MainActivity extends AppCompatActivity {

//    ここで行われているのは、Fragmentの生成と画面遷移(？Fragmentの差替え)のみ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            ProjectListFragment fragment = new ProjectListFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)
                    .commit();
        }
    }

    public void show(Project project) {
        ProjectFragment projectFragment = ProjectFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, projectFragment, null)
                .commit();
    }
}
