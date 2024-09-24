package com.example.yourname.retrofitapi

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.yourname.retrofitapi.databinding.ActivityMainBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //https://meme-api.com/gimme

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getData()

        binding.buttonNewMeme.setOnClickListener {
            getData()
        }

    }

    private fun getData() {

        var ProgressDialog = ProgressDialog(this)
        ProgressDialog.setMessage("Please wait a moment...")
        ProgressDialog.setCancelable(false)
        ProgressDialog.show()

        RetrofitInstance.apiInterface.getData().enqueue(object : Callback<responseDataClass?> {
            override fun onResponse(
                call: Call<responseDataClass?>,
                response: Response<responseDataClass?>
            ) {

                binding.memeTitle.text = response.body()?.title
                binding.memeAuthor.text = response.body()?.author
                Glide.with(this@MainActivity).load(response.body()?.url).into(binding.memeImage)


                ProgressDialog.dismiss()



            }

            override fun onFailure(call: Call<responseDataClass?>, t: Throwable) {

                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                ProgressDialog.dismiss()

            }
        })
    }
}