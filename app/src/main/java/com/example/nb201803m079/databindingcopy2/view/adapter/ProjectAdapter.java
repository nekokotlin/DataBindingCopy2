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
import android.support.annotation.RequiresApi;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;
import java.util.Objects;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {
//    Projectモデル型のリスト。NULLABLE
    List<? extends Project> projectlist;

    @Nullable
    private final ProjectClickCallback projectClickCallback;

//    これはインターフェース
//    public interface ProjectClickCallback {
//        void onClick(Project project);
//    }

//    コンストラクタ
//    ProjectAdapterが呼ばれる時は、必ずコールバックのインターフェースも呼ばれる。
    public ProjectAdapter(@Nullable ProjectClickCallback projectClickCallback) {
//        箱として、ProjectClickCalback型の｢projectClickCallback｣という変数を持っている。(Kotlinでいうval)
//        それに対して、引数で持ってきたコールバックを入れてあげる。
//        このProjectAdapterを使っているのはProjectListFragmentで、
//        そこで引数として渡すコールバックの実際の処理が書かれている
        this.projectClickCallback = projectClickCallback;
    }


//    projectlistはpublicな変数(多分)
    public void setProjectlist(final List<? extends Project> projectlist) {
//        もしprojectlistの中身がなかったら、引数で持ってきた値を入れてあげる。
//        ない時は最初に描画するときかな？
//        中身がすでにある時は、違いを算出して差分だけ出すようにしているっぽい
        if (this.projectlist == null) {
            this.projectlist = projectlist;
            notifyItemRangeInserted(0, projectlist.size());

//            void notifyItemRangeInserted (int positionStart,
//            int itemCount)
//            https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter.html#notifyitemrangeinserted/

//            要素の数を見て、どのアイテムが追加されたか検知する。それをあらゆるオブザーバーに知らせる。
//            projectlist.size()でlistの要素数を取得している。0(これは条件分岐でprojeclistがNULLのときなので、)からどれだけ増えたかを知らせている

//            これは、RecyclerViewのもつメソッド
//            public final void notifyItemInserted(int position) {
//                this.mObservable.notifyItemRangeInserted(position, 1);
//            }

//            mObservableって？
//            private final RecyclerView.AdapterDataObservable mObservable = new RecyclerView.AdapterDataObservable();


//            もし、既にprojectlistの中身があった場合の処理
        }else {
//            returnで帰ってきた値がresultの中に入っている？
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

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Project project = projectlist.get(newItemPosition);
                    Project old = projectlist.get(oldItemPosition);

//                    boolean型なので、この書き方ができる。本来はif文
                    return project.id == old.id && Objects.equals(project.git_url, old.git_url);
                }
            });

//            あとで中身を調べる用(実際に変更がないとこれは見えない)
            result.toString();

//　　　　　　引数でとってきたprojectlistが入るのは一緒
            this.projectlist = projectlist;

//            引数にはAdapterをとる
//            dispatchUpdatesToとは何をするメソッド？
//            →変更の通知
//            calculateDiff()から帰ってきたDiffResultのdispatchUpdatesTo(adapter)を呼び出すことでAdapterに変更が通知されます。
            result.dispatchUpdatesTo(this);
        }
    }


//    ここは実装必須
//    RecyclerView.ViewHolder生成
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    抽象メソッドを継承したonCreateViewHolderメソッドは、ViewHolderを返します。
//    ViewHolderはview項目のデータを扱うクラスです。
//    inflateで生成したviewオブジェクトをViewHolderのコンストラクタに設定します。



//    https://kamiya-kizuku.hatenablog.com/entry/2017/12/26/201623
//    ViewHolderというクラスを使って、初めの1回のみViewを一時格納してコスト削減する！という物です。



//    ここでViewHolderを作成する

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        ProjectListItemBinding binding = DataBindingUtil
//                inflateはビューを生成する。それに対してDataBindingを行う。
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.project_list_item, parent, false);

//        setCallbackは、ProjectListItemBindingにて定義されている抽象メソッド

//        これを書いてあげることで、CallbackをBindingクラスのインスタンスにセットし、
//        レイアウトファイルで使えるようになる
                binding.setCallback(projectClickCallback);
        return new ProjectViewHolder(binding);
    }

//    ここも実装必須


//    RecyclerView.ViewHolderのバインド
//    ここが何をしているのかわからない。
//    なぜViewHolderをバインドする必要があるのか
    @Override
    public void onBindViewHolder(ProjectViewHolder holder, int position){

//        下記bindingはProjectListItemBinding
//        もしbinding.setProject(~)だったら、project_list_item内で、Projectモデルの中身を使えるようにしている

//        ViewHolderに対してProjectモデルを使えるようにしている、ということ？
//        初めの一回のみ、表示するビューを格納するのがViewHolder
//        としたら、表示に関わるものだけBindしてあげて、
//        コールバックなどは不要？(処理だから)
//        setCallbackとsetProjectが分かれているのがわからない

//        setProjectはビューで表示するものだから、ViewHolder
//        コールバックは処理だから、ViewHolderでBindしなくてもいい？
        
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
