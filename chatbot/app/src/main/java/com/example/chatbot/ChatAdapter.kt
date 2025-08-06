// ChatAdapter.kt
package com.example.chatbot

import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val textView = TextView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setPadding(32, 16, 32, 16)
            textSize = 16f
        }
        return MessageViewHolder(textView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = messages[position]
        holder.textView.text = msg.text

        val screenWidth = holder.textView.context.resources.displayMetrics.widthPixels
        val layoutParams = ViewGroup.MarginLayoutParams(
            (screenWidth * 0.8).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            topMargin = 12
            bottomMargin = 12
            marginStart = if (msg.isUser) 80 else 16
            marginEnd = if (msg.isUser) 16 else 80
        }

        holder.textView.layoutParams = layoutParams
        holder.textView.gravity = if (msg.isUser) Gravity.END else Gravity.START
        holder.textView.setBackgroundResource(
            if (msg.isUser) R.drawable.bubble_user else R.drawable.bubble_ai
        )
    }

    override fun getItemCount() = messages.size
}
// NOTE: Create bubble_user.xml and bubble_ai.xml in res/drawable with appropriate
// corner radii and background colors for user and AI speech bubbles.