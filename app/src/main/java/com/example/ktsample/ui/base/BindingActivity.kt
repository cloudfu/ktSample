package com.example.ktsample.ui.base;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding;

abstract class BindingActivity<T : ViewDataBinding> constructor(
    @LayoutRes private val layoutId: Int,
) : AppCompatActivity() {

    protected var mBindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()
    protected val mBinding: T by lazy(LazyThreadSafetyMode.NONE) {
        DataBindingUtil.setContentView(this, layoutId, mBindingComponent)
    }

    init {
        // TODO:是否可以去除
        addOnContextAvailableListener {
            mBinding.notifyChange()
        }
    }

    /***
     * 对于Layout布局进行数据绑定操作
     */
    protected inline fun binding(block: T.() -> Unit): T {
        return mBinding.apply(block)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }
}
