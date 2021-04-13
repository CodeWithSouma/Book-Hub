package com.codewithsouma.bookhub.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.codewithsouma.bookhub.R
import com.codewithsouma.bookhub.util.ConnectionManager
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class DescriptionActivity : AppCompatActivity() {
    lateinit var txtBookName: TextView
    lateinit var txtBookPrice: TextView
    lateinit var txtBookAuthor: TextView
    lateinit var txtBookRating: TextView
    lateinit var imgBookImage: ImageView
    lateinit var txtBookDescription: TextView
    lateinit var btnAddToFav: Button
    lateinit var progressBar: ProgressBar
    lateinit var progressLayout: RelativeLayout
    lateinit var toolbar: Toolbar

    var bookId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Book Details"


        txtBookName = findViewById(R.id.txtBookName)
        txtBookPrice = findViewById(R.id.txtBookPrice)
        txtBookAuthor = findViewById(R.id.txtBookAuthor)
        txtBookRating = findViewById(R.id.txtBookRating)
        imgBookImage = findViewById(R.id.imgBookImage)
        txtBookDescription = findViewById(R.id.txtBookDesc)
        btnAddToFav = findViewById(R.id.btnAddToFavourite)
        progressBar = findViewById(R.id.progressBar)
        progressLayout = findViewById(R.id.progressLayout)

        if (intent != null) {
            bookId = intent.getStringExtra("book_id")
        } else {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        if (bookId == "100") {
            finish()
            Toast.makeText(
                this@DescriptionActivity,
                "Some unexpected error occurred!",
                Toast.LENGTH_SHORT
            ).show()
        }

        progressLayout.visibility = View.VISIBLE


        if (ConnectionManager().checkConnectivity(this@DescriptionActivity)){
            val queue = Volley.newRequestQueue(this@DescriptionActivity)
            val url = "http://13.235.250.119/v1/book/get_book/"
            val jsonParams = JSONObject()
            jsonParams.put("book_id", bookId)

            val jsonObjectRequest =
                object : JsonObjectRequest(Request.Method.POST, url, jsonParams, Response.Listener {
                    progressLayout.visibility = View.GONE

                    try {
                        val success = it.getBoolean("success")
                        if (success) {
                            val data = it.getJSONObject("book_data")
                            txtBookName.text = data.getString("name")
                            txtBookAuthor.text = data.getString("author")
                            txtBookPrice.text = data.getString("price")
                            txtBookRating.text = data.getString("rating")
                            txtBookDescription.text = data.getString("description")
                            val bookImageURL = data.getString("image")
                            Picasso.get().load(bookImageURL).error(R.drawable.default_book_cover)
                                .into(imgBookImage)
                        }else{
                            Toast.makeText(
                                this@DescriptionActivity,
                                " Some Error Occurred!!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } catch (jsonException: JSONException) {
                        Toast.makeText(
                            this@DescriptionActivity,
                            "Some unexpected error occurred!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                }, Response.ErrorListener {

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "bf4bdd304c6713"
                        return headers
                    }
                }

            queue.add(jsonObjectRequest)

        }else{
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Success")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open settings") { text, listener ->
                val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                finish()
            }
            dialog.setNegativeButton("Exit") { text, listener ->
                ActivityCompat.finishAffinity(this)
            }
            dialog.create()
            dialog.show()
        }




    }
}