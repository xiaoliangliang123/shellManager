package com.shell.manager.ui.listener;

public abstract class UIUpdateListener  {


    private UIUpdateActioner uiUpdateActioner;

    public UIUpdateActioner getUiUpdateActioner() {
        return uiUpdateActioner;
    }

    public void setUiUpdateActioner(UIUpdateActioner uiUpdateActioner) {
        this.uiUpdateActioner = uiUpdateActioner;
    }

    public void onUpdate(String content){
        uiUpdateActioner.doUpdate(content);
    }

    public interface UIUpdateActioner{
        void doUpdate(String content);
    }
}


