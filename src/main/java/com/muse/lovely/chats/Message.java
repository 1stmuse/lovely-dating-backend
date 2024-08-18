package com.muse.lovely.chats;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {

    private Long senderId;
    private String message;
}
