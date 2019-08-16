package com.example.androidbase

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        var x = 0
        Observable.just(1,2,3,4)
            .subscribeOn(Schedulers.trampoline())
            .subscribe({ println(it)})

        Observable.just( 5,6, 7,8, 9)
            .subscribeOn(Schedulers.trampoline())
            .subscribe( { println(it)} )

        println(x)
    }
}
