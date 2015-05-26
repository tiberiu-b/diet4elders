package d4elders.model.user_profile;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by cristiprg on 22.04.2015. Business logic for User profile
 */
public class UserProfileHelper {
	private static UserProfileStub userProfileStub = new UserProfileStub();

	public static ArrayList<String> getLikeList() {
		return userProfileStub.getLikeList();
	}

	public static ArrayList<String> getDisLikeList() {
		return userProfileStub.getDislikeList();
	}

	public static ArrayList<String> getPreferenceList() {
		return unionList(userProfileStub.getLikeList(), userProfileStub.getDislikeList());
	}

	private static ArrayList<String> unionList(ArrayList<String> A, ArrayList<String> B) {
		ArrayList<String> result = A;
		for (String b : B) {
			if (!result.contains(b))
				result.add(b);
		}
		return result;
	}

	private static Set<String> unionSet(Set<String> A, Set<String> B) {
		Set<String> newSet = new TreeSet<String>();
		for (String s : A) {
			newSet.add(s);
		}

		for (String s : B) {
			newSet.add(s);
		}
		return newSet;
	}

}
