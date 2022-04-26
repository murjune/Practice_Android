package org.techtown.myrecycleview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.techtown.myrecycleview.databinding.ItemSampleListBinding
import java.util.*

// RecyclerView.Adapter<>를 상속받는다.
// RecyclerViewAdapter에서 ItemTouchHelperAdapter를 구현한다.
// Adapter의 생성자로 넣어주는 람다 itemClick

class MyAdapter(private val itemClick: (UserData) -> (Unit)) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>(),
    ItemTouchHelperCallback.OnItemMoveListener {

    private lateinit var dragListener: OnStartDragListener
    val userList = mutableListOf<UserData>()

    init {
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

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemSampleListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        // context : Activity가 담고 있는 모든 정보
        // onCreateViewHolder()에서 ViewHolder를 생성할 때 람다 itemClick을 전달
        return MyViewHolder(binding, itemClick) // 뷰홀더 객체 생
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(userList[position])
        holder.binding.ivDraghandle.setOnTouchListener { view: View, event: MotionEvent ->
            // Action_DOWN : 화면을 손가락으로 누른 순간의 이벤트
            if (event.action == MotionEvent.ACTION_DOWN) {
                dragListener.onStartDrag(holder)
            }
            return@setOnTouchListener false
        }
    }

    class MyViewHolder(
        val binding: ItemSampleListBinding,
        // ViewHolder의 생성자로 넣어주는 람다 itemClick
        private val itemClick: (UserData) -> (Unit)
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: UserData) {
            binding.ivProfile.setImageResource(data.gender)
            binding.tvName.text = data.name
            binding.tvIntroduce.text = data.introduce
            binding.root.setOnClickListener {
                itemClick(data)
            }
        }
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }

    fun startDrag(listener: OnStartDragListener) {
        this.dragListener = listener
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        Collections.swap(userList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemSwiped(position: Int) {
        userList.removeAt(position)
        notifyItemRemoved(position)
    }
}
