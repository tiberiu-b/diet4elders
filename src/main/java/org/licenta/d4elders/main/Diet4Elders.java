package org.licenta.d4elders.main;

import org.licenta.d4elders.model.user_profile.NutritionalRecommandationHelper;
import org.licenta.d4elders.model.user_profile.UserProfileStub;

public class Diet4Elders {
	public static UserProfileStub userProfile;

	public Diet4Elders() {
		userProfile = new UserProfileStub();
		new NutritionalRecommandationHelper(userProfile);
	}

	public void run() {
		AlgorithmVer1 a1 = new AlgorithmVer1();
		a1.start();
	}

}
