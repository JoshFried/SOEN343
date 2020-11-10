package com.soen343.shs.dal.service.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserEntersRoomPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEvent(final long roomId, final long houseId) {
        publisher.publishEvent(new UserEntersRoomEvent(roomId, houseId));
    }

}
