package com.zhxh.rxjava2.samples.utils

import android.util.Log

import com.androidnetworking.error.ANError
import com.zhxh.rxjava2.samples.model.ApiUser
import com.zhxh.rxjava2.samples.model.User

import java.util.ArrayList

/**
 * Created by zhxh on 2018/1/18
 */
object Utils {

    val userList: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.firstname = "xh"
            userOne.lastname = "Lei"
            userList.add(userOne)

            val userTwo = User()
            userTwo.firstname = "ch"
            userTwo.lastname = "Zh"
            userList.add(userTwo)

            val userThree = User()
            userThree.firstname = "cy"
            userThree.lastname = "Zh"
            userList.add(userThree)

            return userList
        }

    val apiUserList: List<ApiUser>
        get() {

            val apiUserList = ArrayList<ApiUser>()

            val apiUserOne = ApiUser()
            apiUserOne.firstname = "xh"
            apiUserOne.lastname = "Lei"
            apiUserList.add(apiUserOne)

            val apiUserTwo = ApiUser()
            apiUserTwo.firstname = "ch"
            apiUserTwo.lastname = "Zh"
            apiUserList.add(apiUserTwo)

            val apiUserThree = ApiUser()
            apiUserThree.firstname = "cy"
            apiUserThree.lastname = "Zh"
            apiUserList.add(apiUserThree)

            return apiUserList
        }

    val userListWhoLovesApple: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.id = 1
            userOne.firstname = "xh"
            userOne.lastname = "Lei"
            userList.add(userOne)

            val userTwo = User()
            userTwo.id = 2
            userTwo.firstname = "ch"
            userTwo.lastname = "Zh"
            userList.add(userTwo)

            return userList
        }


    val userListWhoLovesBanana: List<User>
        get() {

            val userList = ArrayList<User>()

            val userOne = User()
            userOne.id = 1
            userOne.firstname = "xh"
            userOne.lastname = "Lei"
            userList.add(userOne)

            val userTwo = User()
            userTwo.id = 3
            userTwo.firstname = "cy"
            userTwo.lastname = "Zh"
            userList.add(userTwo)

            return userList
        }

    fun convertApiUserListToUserList(apiUserList: List<ApiUser>): List<User> {

        val userList = ArrayList<User>()

        for (apiUser in apiUserList) {
            val user = User()
            user.firstname = apiUser.firstname
            user.lastname = apiUser.lastname
            userList.add(user)
        }

        return userList
    }


    fun filterUserWhoLovesBoth(appleFans: List<User>, bananaFans: List<User>): List<User> {
        val userWhoLovesBoth = ArrayList<User>()
        for (appleFan in appleFans) {
            for (bananaFan in bananaFans) {
                if (appleFan.id == bananaFan.id) {
                    userWhoLovesBoth.add(appleFan)
                }
            }
        }
        return userWhoLovesBoth
    }

    fun logError(TAG: String, e: Throwable) {
        if (e is ANError) {
            if (e.errorCode != 0) {
                // received ANError from server
                // error.getErrorCode() - the ANError code from server
                // error.getErrorBody() - the ANError body from server
                // error.getErrorDetail() - just a ANError detail
                Log.d(TAG, "onError errorCode : " + e.errorCode)
                Log.d(TAG, "onError errorBody : " + e.errorBody)
                Log.d(TAG, "onError errorDetail : " + e.errorDetail)
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.d(TAG, "onError errorDetail : " + e.errorDetail)
            }
        } else {
            Log.d(TAG, "onError errorMessage : " + e.message)
        }
    }

}// This class in not publicly instantiable.
