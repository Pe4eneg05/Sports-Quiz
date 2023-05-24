package com.pechenegmobilecompanyltd.sportsquiz.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.pechenegmobilecompanyltd.sportsquiz.R
import com.pechenegmobilecompanyltd.sportsquiz.databinding.FragmentShopBinding
import com.pechenegmobilecompanyltd.sportsquiz.entity.PhotoItem
import com.pechenegmobilecompanyltd.sportsquiz.recyclerview.AdapterRecyclerView
import com.pechenegmobilecompanyltd.sportsquiz.data.DataClass

class ShopFragment : Fragment() {

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = DataClass()
        var balance = getPointCount()
        val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(activity)

        fun onClickItemRecyclerView(item: PhotoItem, price: Int) {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Warning!")
            builder.setMessage("Are you sure you want to change the wallpaper?")
            builder.setPositiveButton("Yes") { _, _ ->
                if (balance >= price) {
                    balance -= price
                    binding.balance.text = "Balance: $balance"
                    wallpaperManager.setResource(item.resource)
                    Toast.makeText(activity, "Wallpaper successfully changed!", Toast.LENGTH_SHORT)
                        .show()
                    savePointsPrefData(balance)
                } else Toast.makeText(activity, "Not Enough Coins!", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.show()
        }

        binding.recycler.layoutManager =
            GridLayoutManager(context, 2)
        val adapter = AdapterRecyclerView { onClickItemRecyclerView(it, it.price) }
        adapter.setData(data.listPhotos)
        binding.recycler.adapter = adapter

        binding.balance.text = "Balance: $balance"

        binding.buttonBack.setOnClickListener {
            findNavController().navigate(R.id.action_ShopFragment_to_StartFragment)
            savePointsPrefData(balance)
        }


    }

    private fun savePointsPrefData(points: Int) {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        val editor = sharedPreferences!!.edit()
        editor.putInt("points", points)
        editor.apply()
    }

    private fun getPointCount(): Int {
        sharedPreferences = context?.getSharedPreferences("point", Context.MODE_PRIVATE)
        return sharedPreferences!!.getInt("points", 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}