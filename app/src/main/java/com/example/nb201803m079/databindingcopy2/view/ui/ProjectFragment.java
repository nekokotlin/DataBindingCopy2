package com.example.nb201803m079.databindingcopy2.view.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nb201803m079.databindingcopy2.R;
import com.example.nb201803m079.databindingcopy2.databinding.FragmentProjectDetailsBinding;
import com.example.nb201803m079.databindingcopy2.service.model.Project;
import com.example.nb201803m079.databindingcopy2.viewModel.ProjectViewModel;


//前提として、ここは、ProjectListFragmentからのコールバックで呼ばれるもの！！！！
public class ProjectFragment extends Fragment {
    private static final String KEY_PROJECT_ID = "project_id";
    private FragmentProjectDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

//        onCreateViewとは？
//        Fragmentのライフサイクル
//        bindingには何が入っている？

//        containerはViewGroup型の何かが入る
//        ここでinflateを使っている目的はなんだ？


//        DataBindingをFragmentに対し使いたいので、inflateする必要がある。
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details,container, false);

//        inflateは何？
//        inflateはよそのビューを使えるようにする。
//        レイアウトXMLファイルから View を生成するとき

//        そこで、実行時に追加すべきレイアウトを(静的に)xmlファイルに定義しておき、実行時にLayoutInflaterを用いて、
//        そのxmlで定義したレイアウトを、任意の(別の)レイアウトに追加してやるのである。
//        この場合、「動的に追加すべきレイアウト」と「追加する位置」との関係性が課題となる。

//        →本PJに当てはめて考えると、
//        動的に追加すべきレイアウト　=>　R.layout.fragment_project_details
//        追加する位置　=>　container
//        となる。

//        inflateを使う理由としては、｢動的に追加すべきレイアウト｣だから？→たぶんちがう

//        DataBinding×setContentView
//        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        Activity 以外で使うもの ↓コレ
//        T DataBindingUtil.inflate(LayoutInflater inflater, int layoutId, @Nullable ViewGroup parent, boolean attachToParent)
//
// 　　　　FragmentやDialogPreference、ListView のアイテムに適用するレイアウトなどに対して
// 　　　　Data Binding を利用する場合に使えそうです。


//        public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater inflater,
//        int layoutId, @Nullable ViewGroup parent, boolean attachToParent) {
//            return inflate(inflater, layoutId, parent, attachToParent, sDefaultComponent);
//        }

//
//
//        binding.setPresenter(new Presenter()); //name="presenter"
//        binding.setUser(new UserData()); //name="user"
//
//        なるほど！！！！！！
//        XMLのnameで指定しているものが、そのままセッターになる。



        return binding.getRoot();

//        このgetRootは何をしている？
//        バインディングに関連付けられているレイアウトファイル内の最も外側のビューを返します。
//        このバインディングがマージレイアウトファイルに対するものである場合、これはマージタグの最初のルートを返します。


//        @NonNull
//        public View getRoot() {
//            return mRoot;
//        }

//        /**
//         * The root View that this Binding is associated with.
//         */
//        private final View mRoot;
//

    }



//    --------------------------------　いったんProjectListFragmentへ。　2019/01/13　15:21

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
