package com.sanchit.healthzoid.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sanchit.healthzoid.database.MealItemDatabase.Companion.getInstance
import com.sanchit.healthzoid.repository.MealItemsRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext,params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = MealItemsRepository(database)
        return try {
            repository.refreshMealItems()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}