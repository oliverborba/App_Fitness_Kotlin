package com.lucasborba.fitnesstracker

import com.lucasborba.fitnesstracker.model.Calc

interface OnListClickListener {
    fun onClick(id: Int, type: String)
    fun onLongClick(position: Int, calc: Calc)
}