package com.example.expensesplitter.models

import com.google.type.Money

data class family(
    var req:ArrayList<money> = ArrayList(),
    var owd: ArrayList<money> = ArrayList()
){
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "req" to req,
            "owd" to owd
        )
    }
}
