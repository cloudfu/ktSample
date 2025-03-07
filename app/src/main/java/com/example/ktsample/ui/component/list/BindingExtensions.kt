package com.example.ktsample.ui.component.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty

fun <T : ViewDataBinding> ViewGroup.binding(
    @LayoutRes layoutRes: Int,
    attachToParent: Boolean = false,
): T {
    return DataBindingUtil.inflate(
        LayoutInflater.from(context),
        layoutRes,
        this,
        attachToParent,
    )
}

/**
 * @author skydoves (Jaewoong Eum)
 *
 * A binding extension for inflating a [layoutRes] and returns a DataBinding type [T] with a receiver.
 *
 * @param layoutRes The layout resource ID of the layout to inflate.
 * @param attachToParent Whether the inflated hierarchy should be attached to the parent parameter.
 * @param block A DataBinding receiver lambda.
 *
 * @return T A DataBinding class that inflated using the [layoutRes].
 */
inline fun <T : ViewDataBinding> ViewGroup.binding(
    @LayoutRes layoutRes: Int,
    attachToParent: Boolean = false,
    block: T.() -> Unit,
): T {
    return binding<T>(layoutRes, attachToParent).apply(block)
}


@OptIn(ExperimentalContracts::class)
@JvmSynthetic
public inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit,
): Any? {
    contract {
        callsInPlace(whatIf, InvocationKind.AT_MOST_ONCE)
    }
    return whatIfNotNullAs(
        whatIf = whatIf,
        whatIfNot = { },
    )
}

@OptIn(ExperimentalContracts::class)
@JvmSynthetic
public inline fun <reified R> Any?.whatIfNotNullAs(
    whatIf: (R) -> Unit,
    whatIfNot: () -> Unit,
): Any? {
    contract {
        callsInPlace(whatIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(whatIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this is R) {
        whatIf(this as R)
        return this
    }
    whatIfNot()
    return this
}