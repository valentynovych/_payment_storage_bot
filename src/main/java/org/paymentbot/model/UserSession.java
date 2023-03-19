package org.paymentbot.model;

import lombok.Builder;
import lombok.Data;
import org.paymentbot.enums.ConversationState;

@Data
@Builder
public class UserSession {
    private Long chatId;
    private ConversationState state;

    }

