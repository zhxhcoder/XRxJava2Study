package com.zhxh.rxjava2.samples.utils;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.zhxh.rxjava2.samples.model.ApiUser;
import com.zhxh.rxjava2.samples.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhxh on 2018/1/18
 */
public class Utils {

    private Utils() {
        // This class in not publicly instantiable.
    }

    public static List<User> getUserList() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.setFirstname("xh");
        userOne.setLastname("Lei");
        userList.add(userOne);

        User userTwo = new User();
        userTwo.setFirstname("ch");
        userTwo.setLastname("Zh");
        userList.add(userTwo);

        User userThree = new User();
        userThree.setFirstname("cy");
        userThree.setLastname("Zh");
        userList.add(userThree);

        return userList;
    }

    public static List<ApiUser> getApiUserList() {

        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.setFirstname("xh");
        apiUserOne.setLastname("Lei");
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.setFirstname("ch");
        apiUserTwo.setLastname("Zh");
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.setFirstname("cy");
        apiUserThree.setLastname("Zh");
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.setFirstname(apiUser.getFirstname());
            user.setLastname(apiUser.getLastname());
            userList.add(user);
        }

        return userList;
    }

    public static List<User> getUserListWhoLovesApple() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.setId(1);
        userOne.setFirstname("xh");
        userOne.setLastname("Lei");
        userList.add(userOne);

        User userTwo = new User();
        userTwo.setId(2);
        userTwo.setFirstname("ch");
        userTwo.setLastname("Zh");
        userList.add(userTwo);

        return userList;
    }


    public static List<User> getUserListWhoLovesBanana() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.setId(1);
        userOne.setFirstname("xh");
        userOne.setLastname("Lei");
        userList.add(userOne);

        User userTwo = new User();
        userTwo.setId(3);
        userTwo.setFirstname("cy");
        userTwo.setLastname("Zh");
        userList.add(userTwo);

        return userList;
    }


    public static List<User> filterUserWhoLovesBoth(List<User> appleFans, List<User> bananaFans) {
        List<User> userWhoLovesBoth = new ArrayList<User>();
        for (User appleFan : appleFans) {
            for (User bananaFan : bananaFans) {
                if (appleFan.getId() == bananaFan.getId()) {
                    userWhoLovesBoth.add(appleFan);
                }
            }
        }
        return userWhoLovesBoth;
    }

    public static void logError(String TAG, Throwable e) {
        if (e instanceof ANError) {
            ANError anError = (ANError) e;
            if (anError.getErrorCode() != 0) {
                // received ANError from server
                // error.getErrorCode() - the ANError code from server
                // error.getErrorBody() - the ANError body from server
                // error.getErrorDetail() - just a ANError detail
                Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            }
        } else {
            Log.d(TAG, "onError errorMessage : " + e.getMessage());
        }
    }

}
