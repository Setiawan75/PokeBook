package com.world.pokebook.screen.shared.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.world.pokebook.R
import com.world.pokebook.databinding.LayoutModifyToolbarBinding

class ModifyToolbarView: ConstraintLayout {
    /**
     * Variables
     */
    //State
    private lateinit var binding: LayoutModifyToolbarBinding

    private var listener: ToolbarListener? = null

    /**
     * Constructors
     */
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Initialization
     */
    private fun init(context: Context, attributeSet: AttributeSet?) {
        //Inflate view
        binding = LayoutModifyToolbarBinding.bind(LayoutInflater.from(context).inflate(R.layout.layout_modify_toolbar, this, true))
    }

    private fun TextView.setMarginLR(margin: Int) {
        val params : LayoutParams = this.layoutParams as LayoutParams
        params.setMargins(margin, params.topMargin, margin, params.bottomMargin)
        this.layoutParams = params
    }


    fun toolbar(): Toolbar {
        return binding.toolbar
    }

    fun setTitle(title: String) {
        binding.lblTitle.text = title
    }

    fun setListener(listener: ToolbarListener) {
        this.listener = listener
    }

    fun hideNavigationIcon() {
        binding.toolbar.navigationIcon = null
    }

    interface ToolbarListener {
        fun onConnectionChanged(isConnected: Boolean)
    }

    fun setColorBackground(color: Int){
        binding.toolbar.setBackgroundColor(color)
    }

}