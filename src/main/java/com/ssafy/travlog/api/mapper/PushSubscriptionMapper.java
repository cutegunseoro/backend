package com.ssafy.travlog.api.mapper;

import java.util.List;

import com.ssafy.travlog.api.model.PushSubscriptionInsertModel;
import com.ssafy.travlog.api.model.PushSubscriptionModel;

public interface PushSubscriptionMapper {
	int insertPushSubscription(PushSubscriptionInsertModel pushSubscriptionInsertModel);

	PushSubscriptionModel selectPushSubscriptionByPushSubscriptionId(long pushSubscriptionId);

	List<PushSubscriptionModel> selectPushSubscriptionsByMemberId(long memberId);
}
