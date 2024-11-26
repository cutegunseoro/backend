package com.ssafy.travlog.api.util;

import com.github.f4b6a3.uuid.UuidCreator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidUtil {
    public UUID getUUIDv7() {
        return UuidCreator.getTimeOrderedEpoch();
    }
}
