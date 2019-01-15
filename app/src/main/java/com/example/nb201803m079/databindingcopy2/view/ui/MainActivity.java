package com.example.nb201803m079.databindingcopy2.view.ui;

import android.arch.lifecycle.Lifecycle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.view.callback.ProjectClickCallback;

public class MainActivity extends AppCompatActivity {

//    ここで行われているのは、Fragmentの生成と画面遷移(？Fragmentの差替え)のみ
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activity_mainがビューです指定
        setContentView(R.layout.activity_main);

//        もしアクティビティが新しく作られていたら(savedInstanceStateがNULLということは、アクティビティが新しく作られているということ。
//　　　　 saved~は、Bundleなどと関係があり、状態の保存をしてくれている)
        if(savedInstanceState == null){
//            Fragmentを新しく生成する　インスタンスを生成
//            こっちはProjectListFragmentということに注意
            ProjectListFragment fragment = new ProjectListFragment();


            getSupportFragmentManager()
                    .beginTransaction()
//                    fragment_container=activity_mainで指定しているFrameLayoutのこと。
//                    ProjectListFragmentを、activity_mainのFrameLayout内に入れ込む
//            add (int containerViewId,
//            Fragment fragment,
//            String tag)
//            ProjectListFragmentのインスタンスを渡しているので、
//            ProjectListFragmentが表示される

                    .add(R.id.fragment_container, fragment, ProjectListFragment.TAG)

//                    変更を更新
                    .commit();
        }
    }

//    これはProjectListFragmentで使われている。
//    コールバックメソッドでshowを呼んでいる
//    private final ProjectClickCallback projectClickCallback = new ProjectClickCallback() {
//        @Override
//        public void onClick(Project project) {
//            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
//                ((MainActivity) getActivity()).show(project);
//            }
//        }
//    };



    public void show(Project project) {
//        こっちはProjectFragment　コールバックで呼び出されたらこっち。
//        =>クリックされたら詳細を出す

//        projectFragmentには何が入っているの？→後述
        ProjectFragment projectFragment = ProjectFragment.forProject(project.name);

        getSupportFragmentManager()
                .beginTransaction()
//                これはaddではなくreplaceであることに注意。ProjectListFragment→ProjectFragmentなので。
                .replace(R.id.fragment_container, projectFragment, null)
                .commit();
    }

//    ※forProjectはこう。
//    public static ProjectFragment forProject(String projectID) {
//    ProjectFragmentのインスタンスを作成
//        ProjectFragment fragment = new ProjectFragment();

//    Bundle型のargsにBundleのインスタンスを作成
//        Bundle args = new Bundle();
//
//        args.putString(KEY_PROJECT_ID, projectID);
//        fragment.setArguments(args);

//    インスタンスに対してputStringメソッドを実行
//    putStringメソッドとは？
//        忘れてた！！！！Intent系列のやつだ
//        argsはBundleのインスタンスなので、putStringメソッドによりデータを渡せる。
//        引数により渡ってきたprojectIDを、KEY_PROJECT_IDというタグで渡す

//        public void putChar (String key,
//        char value)

//
//        戻り値としてProjectFragmentのインスタンスを返している
//        return fragment;
//    }
//



}
