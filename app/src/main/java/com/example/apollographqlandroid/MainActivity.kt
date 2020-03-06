package com.example.apollographqlandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.*
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        val apolloClient = ApolloClient.builder().serverUrl("http://192.168.1.8:8080/graphql").build()

        btn_login.setOnClickListener {
            apolloClient.query(
                LoginUserQuery.builder().email(tx_email.text.toString()).password(
                    tx_password.text.toString()
                ).build()
            ).enqueue(object: ApolloCall.Callback<LoginUserQuery.Data>() {
                override fun onFailure(e: ApolloException) {
                    Logger.d(e.localizedMessage ?: "Error")
                }

                override fun onResponse(response: Response<LoginUserQuery.Data>) {
                    Logger.d(response.data().toString())
                }
            })
        }
    }
}
