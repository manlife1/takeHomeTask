package com.mr.retrofittest2

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.trimmedLength
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.mr.retrofittest2.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var retService:retrofitService
    private lateinit var lastUsedQuery:String
    private var pageNumber:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.recyclerView.layoutManager= GridLayoutManager(this,3)
        retService=RetrofitInstance
            .getRetrofitInstance()
            .create(retrofitService::class.java)

        binding.searchBtn.setOnClickListener {
            if(binding.searchText.text!=null && binding.searchText.text!!.trimmedLength()!=0){
                binding.pageNumText.text=getString(R.string.page1)
                searchForAlbum(binding.searchText.text.toString(),1)
                lastUsedQuery=binding.searchText.text.toString()
                pageNumber=1
                binding.searchText.text=null
            }
        }

        binding.prevBtn.setOnClickListener {
            if(pageNumber==2){
                binding.pageNumText.text=getString(R.string.page1)
                searchForAlbum(lastUsedQuery,1)
                pageNumber=1
            }
        }

        binding.nextBtn.setOnClickListener {
            if(pageNumber==1){
                binding.pageNumText.text=getString(R.string.page2)
                searchForAlbum(lastUsedQuery,2)
                pageNumber=2
            }
        }

        binding.closeBtn.setOnClickListener{
            binding.zoomedCard.visibility=View.GONE
        }

    }

    private fun searchForAlbum(queryValue:String,pageNumber:Int){
        val responseLiveData:LiveData<Response<Album>> = liveData {
            val response=retService.getAlbums(queryValue,pageNumber)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList=it.body()?.photos
            if(albumList!=null){
                binding.recyclerView.adapter=RecyclerViewAdapter(albumList,this::gridItemClicked)
            }
        })
    }

    private fun gridItemClicked(photosItem: PhotosItem){
        val imgUrl= photosItem.src?.large

        binding.zoomedCard.visibility= View.VISIBLE
        Glide.with(binding.zoomedImage.context).load(imgUrl).into(binding.zoomedImage)

    }
}
