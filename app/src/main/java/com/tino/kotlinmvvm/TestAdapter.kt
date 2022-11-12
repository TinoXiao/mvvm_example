package com.tino.kotlinmvvm

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(arrayData: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData: ArrayList<String> = arrayData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_test_layout, parent, false)
        return  TestViewHolder1(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TestViewHolder1) {
            holder.textView?.text = "这里的位置是$position"
            holder.btnTest?.text = "点我"
            holder.btnTest?.setOnClickListener {

                Log.e("AAA","fuck$position")
                holder.textView?.text = "fuck$position"
            }
            Thread{
                try {
                    Thread.sleep(1000)
                    listData[position] = "修改后的数据 $position"
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }.start()
        }
    }

    class TestViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView? = null
        var btnTest: Button? = null

        init {
            textView = itemView.findViewById(R.id.text_item_test)
            btnTest = itemView.findViewById(R.id.btn_item_test)
        }
    }
}