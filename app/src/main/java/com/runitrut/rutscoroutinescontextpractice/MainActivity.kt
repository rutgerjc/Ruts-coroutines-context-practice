package com.runitrut.rutscoroutinescontextpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.runitrut.rutscoroutinescontextpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /* This is a Normal Coroutine CALLING the SUSPEND FUNCTION*/
        GlobalScope.launch {
            val networkcall = doNetWorkCall()
            Log.d(TAG, networkcall)
        }

        /* This a coroutine WITH A PARAMETER of (Dispatcher) */
        /* Dispatchers.Main with start a coroutine in the MAIN thread. only UI changes can happen in the main thread
        *  Dispatchers.IO is used for data operation, networking, or reading/writing to files
        *  Dispatchers.Default is for long run task and complex operations that might block the main thread
        *  Dispatchers.Unconfined not confined to a specific thread
        * */
        GlobalScope.launch(Dispatchers.Main) {

        }
        /*
        * cast the Dispatcher.IO for data request, BUT;
        * THEN recast withContext(Dispatcher.Main to update something, or anything in the main thread
        * LIKE and IMAGE VIEW*/
        GlobalScope.launch (Dispatchers.IO){
            Log.d(TAG, "starting coroutine in thread ${Thread.currentThread().name}")
            val answer = doNetWorkCall()
            withContext(Dispatchers.Main){
                Log.d(TAG, "Setting text in thread ${Thread.currentThread().name}")
                binding.tvDummy.text = answer
            }
        }
        GlobalScope.launch(newSingleThreadContext("My thread")) {

        }
    }

    suspend fun doNetWorkCall(): String {
        delay(3000L)
        return "returns the NetWorkCall"
    }
}