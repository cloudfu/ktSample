package com.example.ktsample.sample.coroutine

import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class Demo {
    fun mainException() {
        //协程名称
        var name = CoroutineName("MyNamedCoroutine")

        // 结合调度器和协程异常处理器
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            println("Caught exception: ${throwable.message}")
        }
        var customJob = Job()
        val context = name + Dispatchers.Default + exceptionHandler + Job()
        val coroutineScope = CoroutineScope(context)

        customJob = coroutineScope.launch(Dispatchers.Default + CoroutineName("test")) {
            throw RuntimeException("Something went wrong")
        } as CompletableJob

        // 等待一段时间以确保协程执行完毕
        Thread.sleep(1000)

        CoroutineScope(Dispatchers.IO)
    }
}

class Test {
    private val _state = MutableStateFlow<String>("unKnown")
    val state: StateFlow<String> get() = _state

    fun getApi(scope: CoroutineScope) {
        scope.launch {
            val res = getApi()
            _state.value = res
        }
    }

    private suspend fun getApi() = withContext(Dispatchers.IO) {
        delay(2000) // 模拟耗时请求
        "hello, stateFlow"
    }
}

fun main() = runBlocking {
    val test: Test = Test()

    test.getApi(this) // 开始获取结果

    launch(Dispatchers.IO) {
        test.state.collect {
        }
    }
    launch(Dispatchers.IO) {
        test.state.collect {
        }
    }
}

