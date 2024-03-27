package com.example.taskmanagement.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.databinding.RawTaskListBinding
import com.example.taskmanagement.model.TaskModel
import java.util.Locale


class TaskListAdapter(var context: Context, var clickListener: View.OnClickListener) : RecyclerView.Adapter<TaskListAdapter.Holder>(), Filterable {


    var objList: ArrayList<TaskModel> = ArrayList()
    var objMainList: ArrayList<TaskModel> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RawTaskListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return objList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = objList[position]
        holder.binding.apply {

            txtProjectName.text = item.projectName
            txtProjectDesc.text = item.projectDesc
            txtProjectDate.text = item.date

            btnDelete.tag = position
            btnDelete.setOnClickListener(clickListener)
        }
    }


    inner class Holder(val binding: RawTaskListBinding) : RecyclerView.ViewHolder(binding.root) {}


    fun addData(mObjList: ArrayList<TaskModel>) {
        objList = ArrayList()
        objList.addAll(mObjList)
        objMainList = ArrayList()
        objMainList.addAll(mObjList)
        this.notifyDataSetChanged()
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                objList = if (charSearch.isEmpty()) {
                    objMainList
                } else {
                    val resultList = ArrayList<TaskModel>()
                    for (row in objMainList) {
                        if (row.projectName.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) || row.projectDesc.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = objList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                objList = results?.values as ArrayList<TaskModel>
                notifyDataSetChanged()
            }

        }
    }

}