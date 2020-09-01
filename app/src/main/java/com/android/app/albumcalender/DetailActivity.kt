package com.android.app.albumcalender

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.android.app.albumcalender.databinding.DetailActivityBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: DetailActivityBinding
    private val viewModel: DetailViewModel by lazy {
        DetailViewModel(DetailModel(this))
    }

    private val itemTouchHelper = ItemTouchHelper(DetailImageItemTouchHelperCallback())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.detail_activity)
        binding.setVariable(BR.vm, viewModel)
        binding.run {
            //recycler view
            imageRecyclerView.apply {
                adapter = DetailAdapter()
                itemTouchHelper.attachToRecyclerView(this)
                // double 탭으로 zoom in,out 했을 때 spanCount를 변경한다.
                // https://stackoverflow.com/questions/6650398/android-imageview-zoom-in-and-zoom-out
                //(layoutManager as? GridLayoutManager)?.spanCount
            }

            //map
            map.setImageResource(android.R.drawable.ic_dialog_map)
            lifecycleOwner = this@DetailActivity

            map.setOnClickListener {
                Intent().apply {

                }
                startActivity(Intent(this@DetailActivity, DetailMapActivity::class.java))
                finish()
            }
        }

    }


}