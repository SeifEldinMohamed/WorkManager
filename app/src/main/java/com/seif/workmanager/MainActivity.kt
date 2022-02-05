package com.seif.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.work.*
import com.seif.workmanager.databinding.ActivityMainBinding
import java.sql.Time
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var workManager = WorkManager.getInstance(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartWork.setOnClickListener {
           // val workRequest = OneTimeWorkRequest.Builder(SimpleWorker::class.java).build()
           // val data = Data.Builder() .putString("WORK_MESSAGE", "Work Completed!")

            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            // from kotlin extension package
            val data = workDataOf("WORK_MESSAGE" to "Work Completed!")
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()
            val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWorker>(
                5, TimeUnit.MINUTES,
                1, TimeUnit.MINUTES
            )
            workManager.enqueue(workRequest) // Enqueues one item for background processing.
        }
        binding.btnWorkStatus.setOnClickListener {
            Toast.makeText(this, "The work status is ${WorkStatusSingleton.workMessage}", Toast.LENGTH_SHORT).show()
        }
        binding.btnResetStatus.setOnClickListener {
            WorkStatusSingleton.workComplete = false
        }
        binding.btnWorkUIThread.setOnClickListener {
            Thread.sleep(10000)
            WorkStatusSingleton.workComplete = true
        }
    }
}