package com.tino.base.livedata

import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * 防止数据倒灌的MutableLiveData
 * @param T 泛型
 * @property isAllowNullValue Boolean
 * @property observers HashMap<Int, Boolean?>
 */
open class BaseLiveData<T> : MutableLiveData<T> {
    private var isAllowNullValue = false
    private val observers: HashMap<Int, Boolean?> = HashMap()

    constructor(value: T) :super(value)

    constructor():super()

    fun observeInActivity(@NonNull activity: AppCompatActivity, @NonNull observer: Observer<in T>) {
        val owner: LifecycleOwner = activity
        val storeId = System.identityHashCode(observer)
        //源码这里是activity.getViewModelStore()，是为了保证同一个ViewModel环境下"唯一可信源"
        observe(storeId, owner, observer)
    }

    override fun observe(
        @NonNull owner: LifecycleOwner,
        @NonNull observer: Observer<in T>
    ) {
        val storeId = System.identityHashCode(observer)
        observe(storeId, owner, observer)
    }

    /**
     * 添加observe时不接收数据，只有当setValue或postValue才接收数据
     * @param storeId Int
     * @param owner LifecycleOwner
     * @param observer Observer<in T>
     */
    private fun observe(
        @NonNull storeId: Int,
        @NonNull owner: LifecycleOwner,
        @NonNull observer: Observer<in T>
    ) {
        if (observers[storeId] == null) {
            observers[storeId] = true
        }
        super.observe(owner) { t: T? ->
            if (!observers[storeId]!!) {
                observers[storeId] = true
                if (t != null || isAllowNullValue) {
                    observer.onChanged(t)
                }
            }
        }
    }

    /**
     * setValue时才打开observe
     * @param value T
     */
    override fun setValue(value: T?) {
        if (value != null || isAllowNullValue) {
            for (entry in observers.entries) {
                entry.setValue(false)
            }
            super.setValue(value)
        }
    }

    /**
     * postValue时才打开observe
     * @param value T
     */
    override fun postValue(value: T) {
        if (value != null || isAllowNullValue) {
            for (entry in observers.entries) {
                entry.setValue(false)
            }
            super.postValue(value)
        }
    }

    protected fun clear() {
        super.setValue(null)
    }
}