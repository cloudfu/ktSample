package com.example.ktsample.data.repository

class ResultPackage<T>(
    val state: DataPackageState,
    val source: String = "remote",
    val data: T? = null
) {

    companion object {
        fun <T> loading(): ResultPackage<T> {
            return ResultPackage(
                state = DataPackageState.LOADING,
                source = "",
                data = null)
        }
    }

    fun isSucceed(): Boolean{
        return state == DataPackageState.SUCCEED
    }

    fun isLoading(): Boolean{
        return state == DataPackageState.LOADING
    }

    override fun toString(): String {
        return state.getStateMessage()
    }
}

enum class DataPackageState (val errCode: Int, private var errMsg: String = ""){

    LOADING(-1, "请求中"),
    SUCCEED(0, "获取成功"),
    SERVER_FAILED(1000, "服务器处理问题"),
    NO_NETWORK(100, "无网络"),
    TIMEOUT(200, "请求超时"),
    UNKNOWN_ERROR(2000, "未知错误");

    fun getStateMessage(): String{
        return when(this){
            LOADING -> "请求中..."
            SUCCEED -> "获取成功"
            SERVER_FAILED -> "服务器内部问题:$errMsg"
            TIMEOUT -> "加载超时"
            NO_NETWORK -> "无网络"
            UNKNOWN_ERROR -> "未知错误"
        }
    }
}

