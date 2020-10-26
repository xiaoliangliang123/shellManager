package com.shell.manager.ui.listener.event;


import org.springframework.context.ApplicationEvent;

import java.util.EventObject;

public class RefreshServerTreeEvent  extends ApplicationEvent {

    public RefreshServerTreeEvent(Object source) {
        super(source);
    }
}
