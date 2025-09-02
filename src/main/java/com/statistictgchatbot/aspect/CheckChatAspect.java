package com.statistictgchatbot.aspect;

import com.statistictgchatbot.service.ChatService;
import com.statistictgchatbot.service.MessageSender;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import static com.statistictgchatbot.constant.MessageToUser.CHAT_DOES_NOT_FOUND;

@Component
@Aspect
public class CheckChatAspect {

    private final ChatService chatService;
    private final MessageSender messageSender;

    public CheckChatAspect(ChatService chatService, MessageSender messageSender) {
        this.chatService = chatService;
        this.messageSender = messageSender;
    }

    @Around(
            value = "@annotation(com.statistictgchatbot.annotation.CheckChatExists) && args(chatId, chatName, ..)",
            argNames = "joinPoint,chatId,chatName"
    )
    public Object checkChatExists(ProceedingJoinPoint joinPoint, Long chatId, String chatName) throws Throwable {
        if (chatService.chatIsExistByChatName(chatName)) {
            return joinPoint.proceed();
        } else {
            messageSender.sendMessage(chatId, CHAT_DOES_NOT_FOUND);
            return null;
        }
    }
}
