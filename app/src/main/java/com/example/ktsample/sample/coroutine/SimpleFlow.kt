package com.example.ktsample.sample.coroutine

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

// https://www.cnblogs.com/joy99/p/15805955.html
fun testSimpleEvent() {
    testFlow1()
}

// region mock Flow Event
// 定义事件类
data class Event(val id: Int, val message: String)

// 定义 EventStream 类
class EventStream {
    private val events = mutableListOf<Event>()

    // 用于发射事件的函数
    suspend fun emit(event: Event) {
        events.add(event)
    }

    // 用于收集事件的函数
    suspend fun collect(action: suspend (Event) -> Unit) {
        for (event in events) {
            action(event)
        }
    }
}

fun mockSimpleEvent() = runBlocking {
    // 使用自定义构建器函数创建 EventStream 对象
    val eventStream = eventStream {
        // 模拟事件发射，每次发射间隔 500 毫秒
        for (i in 1..3) {
            delay(500)
            emit(Event(i, "Event message $i"))
        }
    }

    // 收集并处理 EventStream 中的事件
    eventStream.collect { event ->
        println("Received event: ${event.message}")
    }
}
// 自定义的构建器函数，类似于 flow { ... }
fun eventStream(block: suspend EventStream.() -> Unit): EventStream {
    val stream = EventStream()
    runBlocking {
        stream.block()
    }
    return stream
}

// endregion

// region emit
fun simpleEmitFlow1(): Flow<Int> = flow {
    delay(1000)
    emit(1)
    delay(1000)
    emit(2)
    delay(1000)
    emit(3)
}

fun simpleEmitFlow2(): Flow<Int> = flowOf(1,2,3).onEach {
    delay(1000)
}

/***
 * 可以用于UnitTest制造批量测试数据场景 listOf(1,2 3)
 */
fun simpleEmitFlow3(): Flow<Int> = listOf(1,2,3).asFlow().onEach {
    delay(1000)
}

fun getSimpleFlow()= runBlocking {
    simpleEmitFlow2().collect{
        println(it.toString())
    }
}

// endregion

// region cancel fow
fun testCancelFlow() = runBlocking {

    val flow = flow{
        for(i in 1..1000){
            emit(i)
            delay(100)
        }
    }

    val job = launch(Dispatchers.Default + CoroutineName("myCoroutine")) {
        withTimeoutOrNull(1000){
            flow.collect {
                val coroutineName = coroutineContext[CoroutineName]?.name
                println("当前协程名称: $coroutineName")
                println(it.toString())
            }
        }
    }
    // 模拟一段时间后取消协程
    println("ready cancel")
    delay(3000)
    job.cancel()
    println("canceled")
}


// endregion

// region operation tag

    fun testFlowOperation() = runBlocking {

        val sum = flow(){
            for(i in 1..10){
                emit(i)
            }
        }.fold(100){ a, b -> a + b }
        println(sum)
    }
// endregion

// region launchIn 可以手动指派启动的协程
fun flowLaunchIn() = runBlocking {
    val mDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    val scope = CoroutineScope(mDispatcher)
    (1..5).asFlow().onEach {
        println("当前协程所在线程: ${Thread.currentThread().name}")
        println(it)
    }
        .onCompletion { mDispatcher.close() }
        .launchIn(scope)}

// endregion

/***
 * 两个协程按照顺序支持，第一个结束之后执行第二个
 * 结果并不是并行执行的
 * 1
 * 2
 * 3
 * 4
 * 5
 * one
 * two
 * three
 * four
 * five
 * cosTime: 1645
 */
fun testFlow1() = runBlocking{
    val cosTime = measureTimeMillis {
        (1..5).asFlow()
            .onEach { delay(100) }
            .flowOn(Dispatchers.IO)
            .collect { println(it) }

        flowOf("one", "two", "three", "four", "five")
            .onEach { delay(200) }
            .flowOn(Dispatchers.IO)
            .collect { println(it) }
    }
    println("cosTime: $cosTime")
}

// 并行执行
fun testFlow2() = runBlocking<Unit>{
    launch {
        (1..5).asFlow()
            .onEach { delay(100) }
            .flowOn(Dispatchers.IO)
            .collect { println(it) }
    }
    launch {
        flowOf("one", "two", "three", "four", "five")
            .onEach { delay(200) }
            .flowOn(Dispatchers.IO)
            .collect { println(it) }
    }
}


/***
 * Filter 1
 * Filter 2
 * Map 2
 * Collect string 2
 * Filter 3
 * Filter 4
 * Map 4
 * Collect string 4
 * Filter 5
 */
fun testFlowOperation1() = runBlocking {
    (1..5).asFlow()
        .filter {
            println("Filter $it")
            it % 2 == 0
        }
        .map {
            println("Map $it")
            "string $it"
        }.collect {
            println("Collect $it")
        }

    flow<Int>{
        emit(1)
    }.map {
        it.toString() + "name"
    }
}

/***
 * 如果上游遇到了异常，并使用了 retry 操作符，则 retry 会让 Flow 最多重试 retries 指定的次数
 * retry 最终调用的是 retryWhen 操作符。下面的代码与上面的代码逻辑一致。
 */
fun testFlowRetry() = runBlocking {
//    val _uiStateFlow = MutableStateFlow("初始状态")
//    val liveData = _uiStateFlow.asLiveData()
    (1..5).asFlow().onEach {
        if (it == 4) {
            throw Exception("test exception")
        }
        delay(100)
        println("produce data: $it")
    }.retry(2) {
        it.message == "test exception"
    }.retryWhen { cause, attempt ->
        //retry 最终调用的是 retryWhen 操作符。下面的代码与上面的代码逻辑一致。
        cause.message == "test exception" && attempt < 2
    }.catch { ex ->
        println("catch exception: ${ex.message}")
    }.collect {
        println("collect: $it")
    }
}





