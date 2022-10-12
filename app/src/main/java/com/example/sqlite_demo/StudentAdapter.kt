package com.example.sqlite_demo

import android.view.LayoutInflater
import android.view.ScrollCaptureCallback
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter :RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){


    private var stdList: ArrayList<Student> = ArrayList()
    private var onClickItem:((Student)->Unit)?=null
    private var onClickDeleteItem:((Student)->Unit)?=null

    fun addItems(items: ArrayList<Student>){
        this.stdList=items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback:(Student)->Unit){
        this.onClickItem=callback
    }

    fun setOnClickDeleteItem(callback:(Student)->Unit){
        this.onClickDeleteItem=callback
    }
    class StudentViewHolder(var view:View):RecyclerView.ViewHolder(view){
        private var id=view.findViewById<TextView>(R.id.txtID)
        private var name=view.findViewById<TextView>(R.id.txtName)
        private var email=view.findViewById<TextView>(R.id.txtEmail)
        var btnDelete=view.findViewById<Button>(R.id.btnDelete)

        fun bindView(std:Student){
            id.text=std.id.toString()
            name.text=std.name
            email.text=std.email
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= StudentViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.stdudent_items,parent,false)
    )

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std=stdList[position]
        holder.bindView(std)

        holder.itemView.setOnClickListener{ onClickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(std) }
    }

    override fun getItemCount(): Int {
   return stdList.size
    }
}