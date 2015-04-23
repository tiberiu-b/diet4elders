package org.licenta.d4elders.model.user_profile;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by cristiprg on 22.04.2015.
 * Business logic for User profile
 */
public class UserProfileHelper {
    private static UserProfileStub userProfileStub = new UserProfileStub();


    public static Set<String> getLikeList(){
        return userProfileStub.getLikeSet();
    }

    public static Set<String> getDisLikeList(){
        return userProfileStub.getDislikeSet();
    }

    public static Set<String> getPreferenceSet(){
        return unionSet(userProfileStub.getLikeSet(), userProfileStub.getDislikeSet());
    }
    private static Set<String> unionSet(Set<String> A, Set<String> B){
        Set<String> newSet = new TreeSet<String>();
        for (String s : A){
            newSet.add(s);
        }

        for (String s : B){
            newSet.add(s);
        }
        return newSet;
    }


}
