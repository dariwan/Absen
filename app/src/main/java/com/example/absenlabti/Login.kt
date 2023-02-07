package com.example.absenlabti

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.absenlabti.admin.HomeAdmin
import com.example.absenlabti.user.HomeUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    //inisialisasi database firebase
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private lateinit var context: Context
    private lateinit var pref:Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        context = this
        pref = Preferences(context)

        btnLogin.setOnClickListener {
            val username: String = input_npm.text.toString()
            val password: String = input_password.text.toString()
            //cek jika user tidak memasukkan data
            if (username.isEmpty()) {
                input_npm.error = "NPM Tidak Boleh Kosong"
                input_npm.requestFocus()
            } else if (password.isEmpty()) {
                input_password.error = "Password Tidak Boleh Kosong"
                input_password.requestFocus()
            }else {
                //jika data terisi dan benar
                val query: Query = database.child("absen").orderByChild("username").equalTo(username)
                query.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) =
                        //cek apakah user ada atau tidak
                        if (snapshot.exists()){
                            for (item in snapshot.children){
                                val user = item.getValue<UserData>()
                                if (user != null){
                                    if (user.password.equals(password)){
                                        pref.prefStatus = true
                                        pref.prefLevel = user.level
                                        var intent: Intent? = null
                                        if (user.level.equals("user")){
                                            intent = Intent(context, HomeUser::class.java)
                                        }else{
                                            intent = Intent(context, HomeAdmin::class.java)
                                        }
                                        startActivity(intent)
                                        finish()
                                    }else{
                                        Toast.makeText(context, "Password Salah", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(context, "Username Tidak Ditemukan", Toast.LENGTH_LONG).show()
                        }

                    override fun onCancelled(error: DatabaseError) {
                        //pop up kecil toast
                        Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        //cek jika status true
        if (pref.prefStatus){
            var intent: Intent? = null
            if (pref.prefLevel.equals("user")){
                intent = Intent(context, HomeUser::class.java)
            }else{
                intent = Intent(context, HomeAdmin::class.java)
            }
            startActivity(intent)
            finish()
        }
    }


}