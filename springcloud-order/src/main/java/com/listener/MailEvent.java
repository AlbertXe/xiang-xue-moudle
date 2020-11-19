package com.listener;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @author: AlbertXe
 * @create: 2020-11-19 17:44
 */
@Getter
@Setter
public class MailEvent extends ApplicationEvent {
    private String mail;
    private String content;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public MailEvent(Object source) {
        super(source);
    }

    public MailEvent(Object source, String mail, String content) {
        super(source);
        this.mail = mail;
        this.content = content;
    }
}
