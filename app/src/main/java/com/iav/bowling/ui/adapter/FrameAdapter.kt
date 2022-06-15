package com.iav.bowling.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iav.bowling.model.Frame
import com.iav.bowling.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.scorecard_list_item.view.*

class FrameAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val frames = mutableListOf<Frame>()

    fun clear() {
        frames.clear()
        notifyDataSetChanged()
    }

    fun addAll(items: List<Frame>) {
        frames.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.scorecard_list_item, parent, false)
        return FrameViewHolder(view)
    }

    override fun getItemCount(): Int = frames.count()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val frame = frames[position]
        bindFrameViewHolder(holder as FrameViewHolder, frame)
    }

    private fun bindFrameViewHolder(holder: FrameViewHolder, frame: Frame) {
        holder.ballOne.text = frame.knockedList[0].toString()
        when {
            frame.type == Frame.Type.STRIKE -> holder.ballTwo.setBackgroundResource(R.drawable.background_strike)
            frame.type == Frame.Type.SPARE -> holder.ballTwo.setBackgroundResource(R.drawable.background_spare)
            else -> {
                holder.ballTwo.text = frame.knockedList[1].toString()
            }
        }
        if (frame.knockedList.size == 3) {
            holder.ballThree.visibility = View.VISIBLE
            holder.ballThree.text = frame.knockedList[2].toString()
        }
        holder.frameScore.text = frame.score.toString()

    }

    class FrameViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        internal val ballOne = containerView.ballOne
        internal val ballTwo = containerView.BallTwo
        internal val ballThree = containerView.ballExtra
        internal val frameScore = containerView.viewScorecardListItemFrameScore
    }
}
