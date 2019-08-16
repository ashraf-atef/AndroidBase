package com.example.androidbase.common.presentationLayer.schedulers

import io.reactivex.Scheduler

interface SchedulersService {
    fun io(): Scheduler

    fun mainThread(): Scheduler
}
