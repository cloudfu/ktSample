package com.example.ktsample.data.remote

sealed class ApiResponse<T>(
    val code: Int = 0,
    val msg: String = "",
    val data: T? = null
) {

    class Success<T>(data: T): ApiResponse<T>(ResponseResultCode.SUCCEED.code, ResponseResultCode.SUCCEED.msg, data)
    class Failure<T>(code: Int, msg: String): ApiResponse<T>(code, msg, null)
    class Loading<T>(): ApiResponse<T>(ResponseResultCode.LOADING.code, ResponseResultCode.LOADING.msg,null)
    class Empty<T>(): ApiResponse<T>(ResponseResultCode.SERVER_FAILED.code, ResponseResultCode.SERVER_FAILED.msg,null)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success:code=$code, msg=$msg"
            is Failure -> "Success:code=$code, msg=$msg"
            is Loading<T> -> "Loading"
            is Empty -> TODO()
        }
    }
}

enum class ResponseResultCode (val code: Int = 0, var msg: String = ""){
    LOADING(-1, "请求中"),
    SUCCEED(0, "获取成功"),
    SERVER_FAILED(1000, "服务器处理问题"),
    NO_NETWORK(100, "无网络"),
    TIMEOUT(200, "请求超时"),
    UNKNOWN_ERROR(2000, "未知错误");
}
