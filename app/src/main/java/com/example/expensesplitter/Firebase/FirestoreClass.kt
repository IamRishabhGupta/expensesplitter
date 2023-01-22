package com.example.expensesplitter.Firebase

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.expensesplitter.Constants.Constants
import com.example.expensesplitter.activity.*
import com.example.expensesplitter.fragments.AddFragment
import com.example.expensesplitter.fragments.splitFragmentFriend
import com.example.expensesplitter.models.Expense
import com.example.expensesplitter.models.friend
import com.example.expensesplitter.models.money
import com.example.expensesplitter.models.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.type.Money
import java.sql.Array

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
    fun getFriendsFrag(frag: splitFragmentFriend){
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
                when(frag){
                    is splitFragmentFriend ->{
                        frag.getFriendsName(friendName)
                    }
                }
            }.addOnFailureListener {

            }
    }

    fun addRequestMoney(moneyData: ArrayList<money>){
        var request : HashMap<String,Any> = HashMap()

        mFireStore.collection(Constants.SPLIT).document(getCurrentUserId())
            .get().addOnSuccessListener {doc ->

                if(doc.data != null){
                    for(num in doc.get(Constants.REQ) as ArrayList<HashMap<String,Any>>){

                        var mon= money(num.get("uuid").toString(),num.get("name").toString(),
                            num.get("title").toString(),num.get("amount").toString().toDouble()
                        )
                        moneyData.add(mon)
                    }

                    e("request",moneyData.toString())
                }
            }

        request[Constants.REQ] = moneyData
        mFireStore.collection(Constants.SPLIT).document(getCurrentUserId())
            .set(request, SetOptions.merge()).addOnSuccessListener {
                e("Added in firestore",request.toString())
            }.addOnFailureListener {

            }



        for(i in moneyData){
            var data : ArrayList<money> = ArrayList()
            mFireStore.collection(Constants.SPLIT).document(i.uuid.toString()).get()
                .addOnSuccessListener {doc ->
                    if(doc.data != null){
                        for(num in doc.get(Constants.OWD) as ArrayList<HashMap<String,Any>>){

                            var mon= money(num.get("uuid").toString(),num.get("name").toString(),
                                num.get("title").toString(),num.get("amount").toString().toDouble()
                            )
                            data.add(mon)
                        }

                        e("request",moneyData.toString())
                    }
                }
            data.add(money(i.uuid,i.name,i.title,i.amount))

            request[Constants.OWD] = moneyData

            mFireStore.collection(Constants.SPLIT).document(i.uuid.toString())
                .set(request, SetOptions.merge()).addOnSuccessListener {
                    e("Added in firestore",request.toString())
                }.addOnFailureListener {
                }
        }
    }

    fun getRequestMoneyDataReq(activity: FriendStatusActivity, id: String){
        var Reqdata : ArrayList<money> = ArrayList()
        mFireStore.collection(Constants.SPLIT).document(id)
            .get().addOnSuccessListener {doc ->
                if(doc.data != null){
                    for(num in doc.get(Constants.REQ) as ArrayList<HashMap<String,Any>>){

                        var mon= money(
                            num["uuid"].toString(), num["name"].toString(),
                            num["title"].toString(), num["amount"].toString().toDouble()
                        )
                        Reqdata.add(mon)
                    }

                    e("request",Reqdata.toString())
                    activity.gotTheListReq(Reqdata)
                }
            }
    }

    fun getRequestMoneyDataOwe(activity: FriendStatusActivity, id: String){
        var Owedata : ArrayList<money> = ArrayList()
        mFireStore.collection(Constants.SPLIT).document(id)
            .get().addOnSuccessListener {doc ->
                if(doc.data != null){
                    for(num in doc.get(Constants.OWD) as ArrayList<HashMap<String,Any>>){

                        var mon= money(
                            num["uuid"].toString(), num["name"].toString(),
                            num["title"].toString(), num["amount"].toString().toDouble()
                        )
                        Owedata.add(mon)
                    }

                    e("request",Owedata.toString())
                    activity.gotTheListOwe(Owedata)
                }
            }
    }

}

