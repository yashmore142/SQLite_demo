package com.example.sqlite_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edtName:EditText
    private lateinit var edtEmail:EditText
    private lateinit var btnAdd:Button
    private lateinit var btnView:Button
    private lateinit var btnUpdate:Button

    private lateinit var recyclerView: RecyclerView
    private var adapter:StudentAdapter?=null

    private var std:Student? =null
    private lateinit var sqlDataHelper: SQLDataHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initrecycler()
        sqlDataHelper= SQLDataHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudent() }
        btnUpdate.setOnClickListener { updateStudent() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edtName.setText(it.name)
            edtEmail.setText(it.email)
            std=it
        }
        adapter?.setOnClickDeleteItem { deleteStudent(it.id) }
    }



    private fun initrecycler() {
        recyclerView.layoutManager=LinearLayoutManager(this)
        adapter=StudentAdapter()
        recyclerView.adapter=adapter
    }

    private fun getStudent() {
        val stdList=sqlDataHelper.getAllStudent()
        Log.e("student","${stdList.size}")
        adapter?.addItems(stdList)
    }

    private fun addStudent() {
        val name=edtName.text.toString()
        val email=edtEmail.text.toString()

        if (name.isEmpty()|| email.isEmpty()){
            Toast.makeText(this, "Please Enter a Name and Email", Toast.LENGTH_SHORT).show()
        }
        else{
            val std=Student(name=name, email = email)
            val status= sqlDataHelper.insertStudent(std)

            if (status >-1){
                Toast.makeText(this, "Student Added...", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()
            }
            else{
                Toast.makeText(this, "Record not Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent() {
        val name=edtName.text.toString()
        val email=edtEmail.text.toString()


        if (name==std?.name && email==std?.email){
            Toast.makeText(this, "Record Not Changed...", Toast.LENGTH_SHORT).show()
        return
        }
        if (std == null) return

        val std=Student(id=std!!.id,name=name, email = email)
        val status= sqlDataHelper.updateStudent(std)

        if (status >-1){
            Toast.makeText(this, "Record Updated...", Toast.LENGTH_SHORT).show()
            clearEditText()
            getStudent()
        }
        else{
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show()
        }



    }

    private fun deleteStudent(id:Int) {
    if(id==null) return
        val builder=AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog,_->
            sqlDataHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog,_->

            dialog.dismiss()

        }
        val alert=builder.create()
        alert.show()
    }
    private fun clearEditText() {
        edtName.setText("")
        edtEmail.setText("")
    }

    private fun initView() {
        edtName=findViewById(R.id.edtName)
        edtEmail=findViewById(R.id.edtEmail)
        btnView=findViewById(R.id.btn_View)
        btnAdd=findViewById(R.id.btn_add)
        recyclerView=findViewById(R.id.recyclerView)
        btnUpdate=findViewById(R.id.btn_Update)

    }
}