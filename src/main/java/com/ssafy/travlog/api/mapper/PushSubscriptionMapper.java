package com.ssafy.travlog.api.mapper;

import com.ssafy.travlog.api.model.PushSubscriptionInsertModel;
import com.ssafy.travlog.api.model.PushSubscriptionModel;

import java.util.List;

public interface PushSubscriptionMapper {
    int insertPushSubscription(PushSubscriptionInsertModel pushSubscriptionInsertModel);

    int deletePushSubscription(long pushSubscriptionId);

    PushSubscriptionModel selectPushSubscriptionByPushSubscriptionId(long pushSubscriptionId);

    List<PushSubscriptionModel> selectPushSubscriptionsByMemberId(long memberId);
}
