package com.zhxh.rxjava2.samples.ui.networking

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.Pair
import android.view.View

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.zhxh.rxjava2.samples.R
import com.zhxh.rxjava2.samples.model.ApiUser
import com.zhxh.rxjava2.samples.model.User
import com.zhxh.rxjava2.samples.model.UserDetail
import com.zhxh.rxjava2.samples.utils.Utils

import java.util.ArrayList

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers

/**
 * Created by zhxh on 2018/1/18
 */

class NetworkingActivity : AppCompatActivity() {


    /**
     * zip Operator Example
     */

    /**
     * This observable return the list of User who loves apple
     */
    private val appleFansObservable: Observable<List<User>>
        get() = Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllAppleFans")
            .build()
            .getObjectListObservable(User::class.java)

    /*
    * This observable return the list of User who loves Banana
    */
    private val bananaFansObservable: Observable<List<User>>
        get() = Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllBananaFans")
            .build()
            .getObjectListObservable(User::class.java)


    /**
     * flatMap and filter Operators Example
     */

    private val allMyFriendsObservable: Observable<List<User>>
        get() = Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFriends/{userId}")
            .addPathParameter("userId", "1")
            .build()
            .getObjectListObservable(User::class.java)

    /**
     * flatMapWithZip Operator Example
     */

    private val userListObservable: Observable<List<User>>
        get() = Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
            .addPathParameter("pageNumber", "0")
            .addQueryParameter("limit", "10")
            .build()
            .getObjectListObservable(User::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)
    }

    /**
     * Map Operator Example
     */
    fun map(view: View) {
        Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUser/{userId}")
            .addPathParameter("userId", "1")
            .build()
            .getObjectObservable(ApiUser::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { apiUser ->
                // here we get ApiUser from server
                // then by converting, we are returning user
                User(apiUser)
            }
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(user: User) {
                    Log.d(TAG, "user : $user")
                }

                override fun onError(e: Throwable) {
                    Utils.logError(TAG, e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    /*
    * This do the complete magic, make both network call
    * and then returns the list of user who loves both
    * Using zip operator to get both response at a time
    */
    private fun findUsersWhoLovesBoth() {
        // here we are using zip operator to combine both request
        Observable.zip(appleFansObservable, bananaFansObservable,
            BiFunction<List<User>, List<User>, List<User>> { appleFans, bananaFans -> filterUserWhoLovesBoth(appleFans, bananaFans) })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<User>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(users: List<User>) {
                    // do anything with user who loves both
                    Log.d(TAG, "userList size : " + users.size)
                    for (user in users) {
                        Log.d(TAG, "user : $user")
                    }
                }

                override fun onError(e: Throwable) {
                    Utils.logError(TAG, e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    private fun filterUserWhoLovesBoth(appleFans: List<User>, bananaFans: List<User>): List<User> {
        val userWhoLovesBoth = ArrayList<User>()

        for (bananaFan in bananaFans) {
            if (appleFans.contains(bananaFan)) {
                userWhoLovesBoth.add(bananaFan)
            }
        }

        return userWhoLovesBoth
    }


    fun zip(view: View) {
        findUsersWhoLovesBoth()
    }

    fun flatMapAndFilter(view: View) {
        allMyFriendsObservable
            .flatMap { usersList ->
                // flatMap - to return users one by one
                Observable.fromIterable(usersList) // returning user one by one from usersList.
            }
            .filter { user ->
                // filtering user who follows me.
                user.isFollowing
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(user: User) {
                    // only the user who is following me comes here one by one
                    Log.d(TAG, "user : $user")
                }

                override fun onError(e: Throwable) {
                    Utils.logError(TAG, e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }


    /**
     * take Operator Example
     */

    fun take(view: View) {
        userListObservable
            .flatMap { usersList ->
                // flatMap - to return users one by one
                Observable.fromIterable(usersList) // returning user one by one from usersList.
            }
            .take(4) // it will only emit first 4 users out of all
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<User> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(user: User) {
                    // // only four user comes here one by one
                    Log.d(TAG, "user : $user")
                }

                override fun onError(e: Throwable) {
                    Utils.logError(TAG, e)
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }


    /**
     * flatMap Operator Example
     */

    fun flatMap(view: View) {
        userListObservable
            .flatMap { usersList ->
                // flatMap - to return users one by one
                Observable.fromIterable(usersList) // returning user one by one from usersList.
            }
            .flatMap { user ->
                // here we get the user one by one
                // and returns corresponding getUserDetailObservable
                // for that userId
                getUserDetailObservable(user.id)
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UserDetail> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Utils.logError(TAG, e)
                }

                override fun onNext(userDetail: UserDetail) {
                    // do anything with userDetail
                    Log.d(TAG, "userDetail : $userDetail")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    private fun getUserDetailObservable(id: Long): Observable<UserDetail> {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
            .addPathParameter("userId", id.toString())
            .build()
            .getObjectObservable(UserDetail::class.java)
    }

    fun flatMapWithZip(view: View) {
        userListObservable
            .flatMap { usersList ->
                // flatMap - to return users one by one
                Observable.fromIterable(usersList) // returning user one by one from usersList.
            }
            .flatMap { user ->
                // here we get the user one by one and then we are zipping
                // two observable - one getUserDetailObservable (network call to get userDetail)
                // and another Observable.just(user) - just to emit user
                Observable.zip(getUserDetailObservable(user.id),
                    Observable.just(user),
                    BiFunction<UserDetail, User, Pair<UserDetail, User>> { userDetail, user ->
                        // runs when network call completes
                        // we get here userDetail for the corresponding user
                        Pair(userDetail, user) // returning the pair(userDetail, user)
                    })
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Pair<UserDetail, User>> {
                override fun onComplete() {
                    // do something onCompleted
                    Log.d(TAG, "onComplete")
                }

                override fun onError(e: Throwable) {
                    // handle error
                    Utils.logError(TAG, e)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(pair: Pair<UserDetail, User>) {
                    // here we are getting the userDetail for the corresponding user one by one
                    val userDetail = pair.first
                    val user = pair.second
                    Log.d(TAG, "user : $user")
                    Log.d(TAG, "userDetail : $userDetail")
                }
            })
    }

    companion object {

        val TAG = NetworkingActivity::class.java.simpleName
    }
}
