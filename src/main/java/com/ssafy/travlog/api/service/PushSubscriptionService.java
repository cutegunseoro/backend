package com.ssafy.travlog.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.ssafy.travlog.api.dto.push_subscription.PushSubscriptionAddRequest;
import com.ssafy.travlog.api.mapper.PushSubscriptionMapper;
import com.ssafy.travlog.api.model.PushSubscriptionInsertModel;
import com.ssafy.travlog.api.model.PushSubscriptionModel;
import com.ssafy.travlog.api.util.MemberUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PushSubscriptionService {
	private final PushSubscriptionMapper pushSubscriptionMapper;

	private final MemberUtil memberUtil;

	@Value("${notification.max-push-subscription-per-member}")
	private int maxPushSubscriptionPerMember;

	public void addPushSubscription(
		Authentication authentication,
		PushSubscriptionAddRequest pushSubscriptionAddRequest
	) {
		String publicId = authentication.getName();
		long memberId = memberUtil.getMemberIdByPublicId(publicId);

		List<PushSubscriptionModel> subscriptions = pushSubscriptionMapper.selectPushSubscriptionsByMemberId(memberId);
		PushSubscriptionInsertModel model = PushSubscriptionInsertModel.builder()
			.memberId(memberId)
			.token(pushSubscriptionAddRequest.getToken())
			.build();

		int result;
		result = pushSubscriptionMapper.insertPushSubscription(model);
		if (result != 1) {
			throw new RuntimeException("push_subscription insert failed");
		}

		// delete after insertion
		// TODO: make transactional
		if (subscriptions.size() >= maxPushSubscriptionPerMember + 1) {
			result = pushSubscriptionMapper.deletePushSubscription(subscriptions.get(0).getPushSubscriptionId());
			if (result != 1) {
				throw new RuntimeException("push_subscription delete failed");
			}
		}
	}

}
