package com.zhxh.xbus.rxbus


internal class TagMessage(var mEvent: Any, var mTag: String) {

    val eventType: Class<*>?
        get() = Utils.getClassFromObject(mEvent)

    fun isSameType(eventType: Class<*>, tag: String): Boolean {
        return Utils.equals(eventType, eventType) && Utils.equals(this.mTag, tag)
    }

    override fun toString(): String {
        return "event: $mEvent, tag: $mTag"
    }
}
