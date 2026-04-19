package com.deepk.promtexa

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import java.util.HashMap
import java.util.logging.Logger

class MainActivity : AppCompatActivity() {


    private lateinit var adapter: ChatAdapter
    private val messages = mutableListOf<Message>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ChatAdapter(messages)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        // ✅ STEP 9: Welcome Message
        messages.add(Message("Hi Deep👋\nHow can I help you today?", false))
        adapter.notifyDataSetChanged()

        val etMessage = findViewById<EditText>(R.id.etMessage)
        val btnSend = findViewById<ImageView>(R.id.btnSend)

//        val text = etMessage.text.toString()

//        btnSend.setOnClickListener {
//            println("TRUE"+"Button clicked")
//
//            val text = etMessage.text.toString()
//
//
//
//            if (text.isNotEmpty()) {
//
//                // 1. Add user message
//                messages.add(Message(text, true))
//                adapter.notifyDataSetChanged()
//
//                // 2. Clear input
//                etMessage.text.clear()
//
//                // 3. Scroll down
//                recyclerView.scrollToPosition(messages.size - 1)
//
//                // 4. Show typing
//                messages.add(Message("...", false))
//                adapter.notifyDataSetChanged()
//
//                // 5. Call backend (next step)
//            }
//        }



//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.84.100.146:8080/") // acer nitro 5 laptop
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()

//        val client = OkHttpClient.Builder()
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(200, TimeUnit.SECONDS)
//            .writeTimeout(200, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.84.100.146:8080/")  // acer nitro 5 laptop
//            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val api = retrofit.create(ChatApi::class.java)
//
//        val request = HashMap<String, String>()
//        request["message"] = text
//
//        api.sendMessage(request).enqueue(object : Callback<ChatResponse> {

//            override fun onResponse(call: Call<String>, response: Response<String>) {
//
//                // Remove "..."
//                messages.removeAt(messages.size - 1)
//
//                val reply = response.body() ?: "No response"
//
//                // Add bot reply
//                messages.add(Message(reply, false))
//                adapter.notifyDataSetChanged()
//
//                recyclerView.scrollToPosition(messages.size - 1)
//            }

//            override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
//
//                // ✅ Safe removal of loading message
//                val lastMessage = messages.lastOrNull()
//                if (lastMessage != null && lastMessage.text == "Loading...") {
//                    messages.removeAt(messages.size - 1)
//                    adapter.notifyItemRemoved(messages.size)
//                }
//
//                // ✅ Get response safely
////                val reply = response.body() ?: "No response"
//                val reply = response.body()?.reply ?: "No response"
//
//                // ✅ Add bot reply
//                messages.add(Message(reply, false))
//                adapter.notifyItemInserted(messages.size - 1)
//
//                // ✅ Scroll
//                recyclerView.scrollToPosition(messages.size - 1)
//            }
//
//            override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
//
//                // Remove loading safely
//                val lastMessage = messages.lastOrNull()
//                if (lastMessage != null && lastMessage.text == "Loading...") {
//                    messages.removeAt(messages.size - 1)
//                    adapter.notifyItemRemoved(messages.size)
//                }
//
//                // Add error message
//                messages.add(Message("Error: ${t.message}", false))
//                adapter.notifyItemInserted(messages.size - 1)
//
//            }
//        })

        btnSend.setOnClickListener {

            val text = etMessage.text.toString()

            if (text.isNotEmpty()) {

                // Add user message
                messages.add(Message(text, true))
                adapter.notifyDataSetChanged()

                etMessage.text.clear()
                recyclerView.scrollToPosition(messages.size - 1)

                // Show loading
                messages.add(Message("Loading...", false))
                adapter.notifyDataSetChanged()

                // ✅ Create request HERE
                val request = HashMap<String, String>()
                request["message"] = text

                val client = OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(200, TimeUnit.SECONDS)
            .writeTimeout(200, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // acer nitro 5 laptop
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

                val api = retrofit.create(ChatApi::class.java)

                // ✅ Call API HERE
                api.sendMessage(request).enqueue(object : Callback<ChatResponse> {

                    override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {

                        messages.removeAt(messages.size - 1)

                        val reply = response.body()?.reply ?: "No response"

                        messages.add(Message(reply, false))
                        adapter.notifyDataSetChanged()

                        recyclerView.scrollToPosition(messages.size - 1)
                    }

                    override fun onFailure(call: Call<ChatResponse>, t: Throwable) {

                        messages.removeAt(messages.size - 1)
                        messages.add(Message("Error: ${t.message}", false))
                        adapter.notifyDataSetChanged()
                    }
                })
            }
        }
    }
}