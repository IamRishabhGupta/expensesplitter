package com.example.expensesplitter.Firebase

import android.app.Activity
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.activity.MainActivity
import com.example.expensesplitter.activity.SignUp
import com.example.expensesplitter.activity.TransitionHistoryActivity
import com.example.expensesplitter.fragments.AddFragment
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFireStore = FirebaseFirestore.getInstance()

    //Authentication Logic
    fun registerUser(activity: SignUp, userinfo: user) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserId()).set(
            userinfo,
            SetOptions.merge()
        ).addOnSuccessListener {
            activity.userRegisteredSuccess()
        }.addOnFailureListener { e ->
            e(activity.javaClass.simpleName, e.toString())
        }
    }

    fun getCurrentUserId(): String {
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    //Database Logic
    fun addOrUpdateExpense(
        activity: MainActivity, addFragment: AddFragment, expenseHashMap: HashMap<String, Any>
    ) {

        mFireStore.collection(Constants.EXPENSE).document(getCurrentUserId())
            .set(expenseHashMap, SetOptions.merge()).addOnSuccessListener {
                e("firestore", "added to firestore")
                addFragment.expAddedSuccessfully()
                e("firestore", "complete")
            }.addOnFailureListener { it ->
                Log.i("efevcc", "band1")
                activity.hideProgressDialog()
                e(
                    activity.javaClass.simpleName,
                    "Error while adding Expense", it
                )
            }
    }

    fun getExpense(activity: Activity) {
        var expenseList: ArrayList<Expense> = ArrayList()
        mFireStore.collection(Constants.EXPENSE)
            .document(getCurrentUserId()).get().addOnSuccessListener { doc ->
                if (doc.data != null) {

                    for (num in doc.get(Constants.EXP_LIST) as ArrayList<HashMap<String, Any>>) {

                        var expense = Expense(
                            num.get("title").toString(),
                            num.get("amount").toString().toDouble(),
                            num.get("description").toString(),
                            num.get("tag").toString(),
                            num.get("date").toString(),
                            num.get("extra").toString().toBoolean()
                        )
                        expenseList.add(expense)
                    }


                    e("result", expenseList.toString())

                    when (activity) {
                        is MainActivity -> {
                            activity.getExpenseData(expenseList)
                        }
                        is TransitionHistoryActivity -> {
                            activity.getExpenseListData(expenseList)
                        }
                    }

                } else {
                    when (activity) {
                        is MainActivity -> {
                            activity.getExpenseData(expenseList)
                        }
                        is TransitionHistoryActivity -> {
                            activity.getExpenseListData(expenseList)
                        }
                    }
                    e("No result", "No result")
                }
            }
    }

    fun getAddFriend(email: String) {
        mFireStore.collection(Constants.USERS).whereEqualTo(Constants.EMAIL, email).get()
            .addOnSuccessListener { document ->
                var uid: String = ""
                e("friend", document.documents.toString())
                var friendList: ArrayList<user> = ArrayList()
                for (i in document) {
                    val friend = i.toObject(user::class.java)
                    uid = friend.id
                }
                val friendHashMap = HashMap<String, Any>()
                mFireStore.collection(Constants.USERS)
                    .document(getCurrentUserId()).get().addOnSuccessListener {
                        if (it.data?.isNotEmpty() == true) {
                            for (i in it.get(Constants.FRIENDS) as ArrayList<user>) {
                                e("val:", i.toString())
                                friendList.add(i)
                            }
                            mFireStore.collection(Constants.USERS).document(uid).get().addOnSuccessListener{doc->
                                var cur_user:user = user(doc.get("id") as String,
                                    doc.get("name") as String, doc.get("email") as String, doc.get("friends") as ArrayList<user>
                                )
                                friendList.add(cur_user)
                            }
                        }

                        friendHashMap[Constants.FRIENDS] = friendList
                        mFireStore.collection(Constants.USERS).document(getCurrentUserId())
                            .update(friendHashMap).addOnSuccessListener {

                        }.addOnFailureListener {
                            Log.e("friend error :", "error while friends")
                        }
                    }.addOnFailureListener {
                        Log.e("friend error :", "error while friends")
                    }

            }.addOnFailureListener {
            Log.e("friend error", "Error while adding a friend")
        }
    }


    fun getUserFromId(id:String){

        mFireStore.collection(Constants.USERS).document(id).get().addOnSuccessListener { doc->
            var cur_user:user = user(doc.get("id") as String,
                doc.get("name") as String, doc.get("email") as String, doc.get("friends") as ArrayList<user>
            )
            e("user",cur_user.toString())
        }.addOnFailureListener {
            Log.e("user error", "Error while getting a user")
        }
    }
}

