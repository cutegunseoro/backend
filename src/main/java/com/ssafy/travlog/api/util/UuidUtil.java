package com.ssafy.travlog.api.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.f4b6a3.uuid.UuidCreator;

@Component
public class UuidUtil {
	public UUID getUUIDv7() {
		return UuidCreator.getTimeOrderedEpoch();
	}
}
