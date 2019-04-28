package com.example.customdialog.widget.manager;


import com.example.customdialog.widget.CustomDialog;

/**
 * 管理多个dialog 按照dialog的优先级依次弹出
 */

public class DialogWrapper {

    private CustomDialog.Builder dialog;//统一管理dialog的弹出顺序

    public DialogWrapper(CustomDialog.Builder dialog) {
        this.dialog = dialog;
    }

    public CustomDialog.Builder getDialog() {
        return dialog;
    }

    public void setDialog(CustomDialog.Builder dialog) {
        this.dialog = dialog;
    }

}
