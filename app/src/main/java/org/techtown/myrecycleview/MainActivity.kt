package org.techtown.myrecycleview

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import org.techtown.myrecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var userList : ArrayList<UserData>
    private lateinit var userAdapter : MyListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDataSet()
        initAdapter()
    }
    fun setDataSet(){
        userList = ArrayList<UserData>()
        userList.addAll(
            listOf(
                UserData(R.drawable.man, "이준원", "안드로이드 YB"),
                UserData(R.drawable.woman, "김수빈", "안드로이드 OB"),
                UserData(R.drawable.man, "권용민", "안드로이드 OB"),
                UserData(R.drawable.woman, "최유리", "안드로이드 YB"),
                UserData(R.drawable.woman, "최윤정", "안드로이드 YB"),
            )
        )
    }
    fun initAdapter() {
        userAdapter = MyListAdapter{
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("gender", it.gender)
                putExtra("introduce",it.introduce)
                putExtra("name",it.name)
            }
            startActivity(intent)
        }
        binding.rvMyfollower.apply{
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
        userAdapter.submitList(userList)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
