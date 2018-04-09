package com.lbb.common.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lbb.common.R
import com.lbb.common.adapter.DialogMutiAdapter.DialogViewHolder
import com.lbb.common.adapter.DialogSingleAdapter.DialogSingleViewHolder
import com.lbb.common.model.bean.DialogItem

/**
 * Created by 胡涛.
 */
class DialogSingleAdapter(val mContext: Context, var list: List<DialogItem>?) : Adapter<DialogSingleViewHolder>() {

    fun setData(list: List<DialogItem>?) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DialogSingleViewHolder {
        val contentView = LayoutInflater.from(mContext).inflate(R.layout.recycler_dialog_item, parent, false)
        return DialogSingleViewHolder(contentView)
    }

    private var prePosition: Int? = null

    override fun onBindViewHolder(holder: DialogSingleViewHolder?, @SuppressLint("RecyclerView") position: Int) {
        list?.let {
            val list = it
            holder?.let {
                it.item.text = list[position].item
                if (list[position].isCheck) {
                    it.isCheck.setImageResource(R.mipmap.star_select)
                } else {
                    it.isCheck.setImageResource(R.mipmap.star)
                }
                it.container.setOnClickListener {
                    list[position].isCheck = !list[position].isCheck
                    if (prePosition != null) {
                        list[prePosition!!].isCheck = !list[position].isCheck
                        notifyItemChanged(prePosition!!)
                    }
                    this.prePosition = position
                    notifyItemChanged(position)
                }
            }
        }
    }

    fun getPosition(): Int? {
        return prePosition
    }

    inner class DialogSingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: TextView = itemView.findViewById<TextView>(R.id.tv_dialog_item)
        val isCheck: ImageView = itemView.findViewById<ImageView>(R.id.iv_dialog_ischeck)
        val container: LinearLayout = itemView.findViewById<LinearLayout>(R.id.ll_dialog_container)
    }
}