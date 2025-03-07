package com.example.ktsample.ui.base

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

open class BaseViewModel : ViewModel() {

    fun <T> Flow<T>.bindProperty(
       coroutineScope: CoroutineScope,
       defaultValue: T
    ): FlowBindingProperty<T> {
        val property = FlowBindingProperty<T>(defaultValue, coroutineScope)
        property.collect(this)
        return property
    }

    class FlowBindingProperty<T>(
        private var value: T,
        private val coroutineScope: CoroutineScope
    ){
        fun collect(flow: Flow<T>){
            coroutineScope.launch {
                // distinctUntilChanged() 它的作用是过滤掉相邻的重复值。
                // 也就是说，只有当当前发射的值与前一个发射的值不同时，才会将该值继续传递下去。
                flow.distinctUntilChanged().collect{
                    value = it
                }
            }
        }

        operator fun getValue(thisRef: Any, property: KProperty<*>): T = value
//        operator fun setValue(thisRef: Any, property: KProperty<*>, value: T){
//            this.value = value
//        }
    }
}