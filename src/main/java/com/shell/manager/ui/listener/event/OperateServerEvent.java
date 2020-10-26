package com.shell.manager.ui.listener.event;

import org.springframework.context.ApplicationEvent;


public class OperateServerEvent  extends ApplicationEvent {
    public OperateServerEvent(Object source) {
        super(source);
    }
}
