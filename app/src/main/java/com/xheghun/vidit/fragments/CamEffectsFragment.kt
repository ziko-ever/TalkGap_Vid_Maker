package com.xheghun.vidit.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.media.effect.Effect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.talkgap.R
import com.xheghun.vidit._interface.EffectsItemClickListener
import com.xheghun.vidit.adapter.CamEffectsAdapter
import com.xheghun.vidit.models.EffectsItem
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */

class CamEffectsFragment(val count: Int) : Fragment(), EffectsItemClickListener {

    private lateinit var mView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var effects: ArrayList<EffectsItem>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_cam_effects, container, false)
        val layoutManager = GridLayoutManager(context, 5)

        effects = ArrayList()
        var isLocked = true

        var x = 0

        while (x < count) {
            val effect = EffectsItem()
            effect.isLocked = isLocked
            isLocked = !isLocked
            effects.add(effect)
            x++
        }


        recyclerView = mView.findViewById(R.id.cam_effects_recylerview)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = CamEffectsAdapter(context, effects, this@CamEffectsFragment)
        return mView
    }

    override fun onClick(effectsItem: EffectsItem?) {
        if (effectsItem!!.isLocked) {
            showDialog()
            Toast.makeText(context, "is locked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showDialog() {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Unlock effect")
        val items = arrayOf("unlock this effect \t 5 KozCoin", "unlock all effects  50 KozCoin")
        var checkedItem = 1
        alertDialog.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            when (which) {
                0 -> {
                    checkedItem = 0
                }
                1 -> {
                    checkedItem = 1
                }
            }
        }
        alertDialog.setPositiveButton("OK") { dialog, _ ->
            Toast.makeText(context,"selected ${items[checkedItem]}",Toast.LENGTH_SHORT).show()
            dialog!!.dismiss()
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog!!.dismiss()
        }

        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()

    }


}
