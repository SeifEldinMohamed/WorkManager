package com.seif.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class SimpleWorker(context: Context, params:WorkerParameters): Worker(context, params) {
    override fun doWork(): Result { // called by workManager to implement the task in the background
        val message = inputData.getString("WORK_MESSAGE")
        Thread.sleep(10000)
        WorkStatusSingleton.workComplete = true
        if (message!= null){
            WorkStatusSingleton.workMessage = message
        }
        return Result.success()
    }
}