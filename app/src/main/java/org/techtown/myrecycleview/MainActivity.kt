package org.techtown.myrecycleview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import org.techtown.myrecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAdapter()
    }

    fun initAdapter() {
        val myAdapter = MyAdapter {
            // apply : 초기화할 때 넣어주는 것이 좋다. 인스턴스를 리턴해주기 때애문
            val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                putExtra("name", it.name) // it = UserData
                putExtra("introduce", it.introduce)
                putExtra("gender", it.gender)
            }
            startActivity(intent)
        }
        with(binding) {
            with(rvMyfollower) {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = myAdapter
                addItemDecoration(MyDecoration(this@MainActivity))
            }
            // myAdapter객체는 클래스 OnItemMoveListener 구현했으므로, OnItemMoveListener의 자손이다.
            val itemTouchHelper =
                ItemTouchHelper(ItemTouchHelperCallback(myAdapter))
            itemTouchHelper.attachToRecyclerView(rvMyfollower)
            // ItemTouchHelper(ItemTouchHelperCallback(myAdapter)).attachToRecyclerView((rvMyfollower))
            // 생성자 ItemTouchHelper(CallBack callback)
            // val callBack = ItemTouchHelperCallback(myAdapter) 커스텀 CallBack
            // val itemTouchHelper = ItemTouchHelper(callBack)
            myAdapter.startDrag(object : MyAdapter.OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
