package com.codewithsouma.bookhub.fragment

import android.app.AlertDialog
import android.app.VoiceInteractor
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.codewithsouma.bookhub.R
import com.codewithsouma.bookhub.adapter.DashboardRecyclerAdapter
import com.codewithsouma.bookhub.model.Book
import com.codewithsouma.bookhub.util.ConnectionManager

class DashboardFragment : Fragment() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var checkInternetButton: Button
    private val bookInfoList = arrayListOf<Book>()

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard = view.findViewById(R.id.recyclerDashboard)
        checkInternetButton = view.findViewById(R.id.btnCheckInternet)
        checkInternetButton.setOnClickListener {
            if (ConnectionManager().checkConnectivity(activity as Context)) {
                //Internet is available
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection Found")
                dialog.setPositiveButton("Ok") { text, listener ->
                    // do nothing
                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                    // do nothing
                }
                dialog.create()
                dialog.show()

            } else {
                //Internet is not available
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("Success")
                dialog.setMessage("Internet Connection is not Found")
                dialog.setPositiveButton("Ok") { text, listener ->
                    // do nothing
                }
                dialog.setNegativeButton("Cancel") { text, listener ->
                    // do nothing
                }
                dialog.create()
                dialog.show()
            }
        }
        layoutManager = LinearLayoutManager(activity)

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books"
        val jsonObjectRequest =
            object : JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                val success = it.getBoolean("success")
                if (success) {
                    val data = it.getJSONArray("data")
                    for (index in 0 until data.length()) {
                        var bookJsonObject = data.getJSONObject(index)
                        val bookObject = Book(
                            bookJsonObject.getString("book_id"),
                            bookJsonObject.getString("name"),
                            bookJsonObject.getString("author"),
                            bookJsonObject.getString("rating"),
                            bookJsonObject.getString("price"),
                            bookJsonObject.getString("image")
                        )
                        bookInfoList.add(bookObject)
                        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookInfoList)
                        recyclerDashboard.adapter = recyclerAdapter
                        recyclerDashboard.layoutManager = layoutManager
                        recyclerDashboard.addItemDecoration(
                            DividerItemDecoration(
                                recyclerDashboard.context,
                                (layoutManager as LinearLayoutManager).orientation
                            )
                        )

                    }
                }else
                    Toast.makeText(activity as Context, " Some Error Occurred!!", Toast.LENGTH_SHORT).show()

            }, Response.ErrorListener {
                // here we handel the errors
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "bf4bdd304c6713"
                    return headers
                }
            }

        queue.add(jsonObjectRequest)

        return view
    }

}