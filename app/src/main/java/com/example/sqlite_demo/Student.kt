package com.example.sqlite_demo

import java.util.*

data class Student (
    var id :Int=getAutoID(),
    var name: String="",
    var email:String=""
){
    companion object{
        fun getAutoID():Int{
            val random= Random()
            return random.nextInt(100)
        }
    }
}