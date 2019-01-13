package com.example.nb201803m079.databindingcopy2.view.callback;

import com.example.nb201803m079.databindingcopy2.service.model.Project;

/**
 * クリック操作を伝えるinterface
 * @link onClick(Project project) 詳細画面に移動
 */

public interface ProjectClickCallback {
    void onClick(Project project);
}
