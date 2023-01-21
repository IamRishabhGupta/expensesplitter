package com.example.expensesplitter.Firebase

import android.app.Activity
import android.util.Log
import android.util.Log.e
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.activity.SignUp
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.user
import com.example.pocketmanager.activity.MainActivity
import com.example.pocketmanager.fragments.AddFragment
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

    //Database Logic
    fun addOrUpdateExpense(activity : MainActivity, addFragment: AddFragment
                           , expenseHashMap : HashMap<String , Any>){

        mFireStore.collection(Constants.EXPENSE).document(getCurrentUserId())
            .set(expenseHashMap, SetOptions.merge()).addOnSuccessListener {
                e("firestore", "added to firestore")
                addFragment.expAddedSuccessfully()
                e("firestore", "complete")
            }.addOnFailureListener {it ->
                Log.i("efevcc","band1")
                activity.hideProgressDialog()
                e(
                    activity.javaClass.simpleName,
                    "Error while adding Expense",it
                )
            }
    }

    fun getExpense(activity: Activity){
        var expenseList:ArrayList<Expense> = ArrayList()
        mFireStore.collection(Constants.EXPENSE)
            .document(getCurrentUserId()).
            get().addOnSuccessListener {
                    doc->
                if (doc.data != null){

                    for(num in doc.get(Constants.EXP_LIST) as ArrayList<HashMap<String,Any>>){

                        var expense= Expense(num.get("title").toString(),num.get("amount").toString().toDouble(),
                            num.get("description").toString(),num.get("tag").toString(),num.get("date").toString(),
                            num.get("extra").toString().toBoolean()
                        )
                        expenseList.add(expense)
                    }


                    e("result",expenseList.toString())

                    when(activity){
                        is MainActivity -> {
                            activity.getExpenseData(expenseList)
                        }
//                        is TransitionHistoryActivity -> {
//                            activity.getExpenseListData(expenseList)
//                        }
                    }

                }else{
                    when(activity){
                        is MainActivity -> {
                            activity.getExpenseData(expenseList)
                        }
//                        is TransitionHistoryActivity -> {
//                            activity.getExpenseListData(expenseList)
//                        }
                    }
                    e("No result" , "No result")
                }
            }
    }

}

