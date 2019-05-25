package com.zhxh.xbus.rxbus

import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap

import io.reactivex.disposables.Disposable


internal class CacheUtils private constructor() {

    private val stickyEventsMap = ConcurrentHashMap<Class<*>, List<TagMessage>>()

    private val disposablesMap = ConcurrentHashMap<Any, List<Disposable>>()

    fun addStickyEvent(stickyEvent: TagMessage) {
        val eventType = stickyEvent.eventType
        synchronized(stickyEventsMap) {
            var stickyEvents: MutableList<TagMessage>? = stickyEventsMap[eventType] as MutableList<TagMessage>?
            if (stickyEvents == null) {
                stickyEvents = ArrayList()
                stickyEvents.add(stickyEvent)
                stickyEventsMap.put(eventType!!, stickyEvents)
            } else {
                val indexOf = stickyEvents.indexOf(stickyEvent)
                if (indexOf == -1) {// 不存在直接插入
                    stickyEvents.add(stickyEvent)
                } else {// 存在则覆盖
                    stickyEvents.set(indexOf, stickyEvent)
                }
            }
        }
    }

    fun findStickyEvent(eventType: Class<*>, tag: String): TagMessage? {
        synchronized(stickyEventsMap) {
            val stickyEvents = stickyEventsMap[eventType]
            if (stickyEvents == null) return null
            val size = stickyEvents!!.size
            var res: TagMessage? = null
            for (i in size - 1 downTo 0) {
                val stickyEvent = stickyEvents[i]
                if (stickyEvent.isSameType(eventType, tag)) {
                    res = stickyEvents[i]
                    break
                }
            }
            return res
        }
    }

    fun addDisposable(subscriber: Any, disposable: Disposable) {
        synchronized(disposablesMap) {
            var list: MutableList<Disposable>? = disposablesMap[subscriber] as MutableList<Disposable>?
            if (list == null) {
                list = ArrayList()
                list.add(disposable)
                disposablesMap.put(subscriber, list)
            } else {
                list.add(disposable)
            }
        }
    }

    fun removeDisposables(subscriber: Any) {
        synchronized(disposablesMap) {
            val disposables = disposablesMap[subscriber]as MutableList<Disposable>?
            if (disposables == null) return
            for (disposable in disposables!!) {
                if (disposable != null && !disposable.isDisposed) {
                    disposable.dispose()
                }
            }
            disposables.clear()
            disposablesMap.remove(subscriber)
        }
    }

    private object Holder {
        internal val CACHE_UTILS = CacheUtils()
    }

    companion object {
        val instance: CacheUtils
            get() = Holder.CACHE_UTILS
    }
}
