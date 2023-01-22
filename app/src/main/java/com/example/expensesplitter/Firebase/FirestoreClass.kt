package com.example.expensesplitter.Firebase

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.activity.MainActivity
import com.example.expensesplitter.activity.SignUp
import com.example.expensesplitter.activity.TransitionHistoryActivity
import com.example.expensesplitter.activity.splitActivity
import com.example.expensesplitter.fragments.AddFragment
import com.example.expensesplitter.fragments.splitFragmentFriend
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.friend
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

    fun getAddFriends(email: String, fragment: splitFragmentFriend) {
        mFireStore.collection(Constants.USERS).whereEqualTo(Constants.EMAIL, email).get()
            .addOnSuccessListener { document ->
                var uid: String = ""
                e("friend ye mila", document.documents.toString())
                if(document.documents.toString().isEmpty()){
                    e("ghg","sallalala")
                    return@addOnSuccessListener
                }
                var friendList: ArrayList<String> = ArrayList()
                for (i in document) {
                    val friend = i.toObject(user::class.java)
                    e("friend nahi mila", friend.toString())
                    uid = friend.id
                }
                val friendHashMap = HashMap<String, Any>()
                mFireStore.collection(Constants.USERS)
                    .document(getCurrentUserId()).get().addOnSuccessListener {
                        if (it.data?.isNotEmpty() == true) {
                            for (i in it.data!!.get("friends") as ArrayList<String>) {
                                    friendList.add(i)
                            }
                        }
                        friendList.add(uid)
                        e("3" , "ho gaya")
                        friendHashMap[Constants.FRIENDS] = friendList
                        mFireStore.collection(Constants.USERS).document(getCurrentUserId())
                            .update(friendHashMap).addOnSuccessListener {
                                e("4","ho gaya")
                                getFriendsFrag(fragment)
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

    @SuppressLint("RestrictedApi")
    fun getFriends(activity: Activity){
        var friend = ArrayList<String>()
        var friendName = ArrayList<friend>()
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get().addOnSuccessListener {
                if (it.data?.isNotEmpty() == true) {
                    for (i in it.data!!.get("friends") as ArrayList<String>) {
                        friend.add(i)
                    }
                    }

                mFireStore.collection(Constants.USERS).get().addOnSuccessListener {
                    for (i in it.documents){
//                        e("1 ---",i.data?.get("name").toString())
                        for(idf in friend){
//                            e("2 --- " , idf)
                            if(i.data?.get("id").toString() == idf){
                                e("ye gazab hai",i.data?.get("name").toString())
                                friendName.add(friend(i.data?.get("name").toString(),i.data?.get("id").toString()))
                            }
                        }
                    }
                }
                when(activity){
                    is TransitionHistoryActivity ->{
                        activity.getFriendsName(friendName)
                    }
                    is splitActivity ->{

                    }
                }
                }.addOnFailureListener {

            }
    }



    @SuppressLint("RestrictedApi")
    fun getFriendsFrag(fragment: splitFragmentFriend){
        var friend = ArrayList<String>()
        var friendName = ArrayList<String>()
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId()).get().addOnSuccessListener {
                if (it.data?.isNotEmpty() == true) {
                    for (i in it.data!!.get("friends") as ArrayList<String>) {
                        friend.add(i)
                    }
                }

                mFireStore.collection(Constants.USERS).get().addOnSuccessListener {
                    for (i in it.documents){
//                        e("1 ---",i.data?.get("name").toString())
                        for(idf in friend){
//                            e("2 --- " , idf)
                            if(i.data?.get("id").toString() == idf){
                                e("ye gazab hai",i.data?.get("name").toString())
                                friendName.add(i.data?.get("name").toString())
                            }
                        }
                    }
                    fragment.getFriendsName(friendName)
                    e("yaha toh aya hai", friendName.toString())
                }

            }.addOnFailureListener {

            }
    }


}

