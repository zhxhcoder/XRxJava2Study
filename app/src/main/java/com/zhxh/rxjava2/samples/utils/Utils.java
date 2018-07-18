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
        userOne.firstname = "xh";
        userOne.lastname = "Lei";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.firstname = "ch";
        userTwo.lastname = "Zh";
        userList.add(userTwo);

        User userThree = new User();
        userThree.firstname = "cy";
        userThree.lastname = "Zh";
        userList.add(userThree);

        return userList;
    }

    public static List<ApiUser> getApiUserList() {

        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstname = "xh";
        apiUserOne.lastname = "Lei";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstname = "ch";
        apiUserTwo.lastname = "Zh";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstname = "cy";
        apiUserThree.lastname = "Zh";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstname = apiUser.firstname;
            user.lastname = apiUser.lastname;
            userList.add(user);
        }

        return userList;
    }

    public static List<User> getUserListWhoLovesApple() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "xh";
        userOne.lastname = "Lei";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 2;
        userTwo.firstname = "ch";
        userTwo.lastname = "Zh";
        userList.add(userTwo);

        return userList;
    }


    public static List<User> getUserListWhoLovesBanana() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "xh";
        userOne.lastname = "Lei";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 3;
        userTwo.firstname = "cy";
        userTwo.lastname = "Zh";
        userList.add(userTwo);

        return userList;
    }


    public static List<User> filterUserWhoLovesBoth(List<User> appleFans, List<User> bananaFans) {
        List<User> userWhoLovesBoth = new ArrayList<User>();
        for (User appleFan : appleFans) {
            for (User bananaFan : bananaFans) {
                if (appleFan.id == bananaFan.id) {
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
