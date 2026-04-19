package com.deepk.promtexa

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: MutableList<Message>) :
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMessage: TextView = view.findViewById(R.id.tvMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = messages.size

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val message = messages[position]
//
//        holder.tvMessage.text = message.text
//
//        if (message.isUser) {
//            holder.tvMessage.setBackgroundColor(Color.TRANSPARENT)
//            holder.tvMessage.setTextColor(Color.BLACK)
//            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
//        } else {
//            holder.tvMessage.setBackgroundColor(Color.parseColor("#F5F5F5"))
//            holder.tvMessage.setTextColor(Color.BLACK)
//            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
//        }
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val message = messages[position]
        holder.tvMessage.text = message.text

        if (message.isUser) {
            // USER → right aligned, no background
            holder.tvMessage.setBackgroundColor(Color.TRANSPARENT)
            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        } else {
            // BOT → card style
            holder.tvMessage.setBackgroundResource(R.drawable.bg_bot_dark)
            holder.tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        }

        // ✅ STEP 8: ADD ANIMATION HERE (LAST LINE)
        holder.itemView.translationY = 30f
        holder.itemView.alpha = 0f
        holder.itemView.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(300)
            .start()
    }
}