package com.example.expensesplitter.Firebase

import android.util.Log
import android.util.Log.e
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.activity.SignUp
import com.example.expensesplitter.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore= FirebaseFirestore.getInstance()

    //Authentication Logic
    fun registerUser(activity: SignUp, userinfo: user){
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(userinfo,
            SetOptions.merge()).addOnSuccessListener {
            activity.userRegisteredSuccess()
        }.addOnFailureListener{
                e->
            e(activity.javaClass.simpleName,e.toString())
        }
    }

    fun getCurrentUserId():String{
        var currentUser= FirebaseAuth.getInstance().currentUser
        var currentUserId=""
        if(currentUser!=null){
            currentUserId=currentUser.uid
        }
        return currentUserId
    }

}

