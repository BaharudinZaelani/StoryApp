package com.bahardev.storyapp.cv

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.bahardev.storyapp.R

class InputFieldPassword: AppCompatEditText, View.OnTouchListener {
    private fun init() {
        setOnTouchListener(this)
    }
    constructor(context: Context): super(context) { init() }
    constructor(context: Context, attr: AttributeSet): super(context, attr){ init() }
    constructor(context: Context, attr: AttributeSet, defStyleAttr: Int): super(context,attr,defStyleAttr){ init() }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }
    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if ( text.toString().length < 8 ) {
            setError(context.getString(R.string.error_field_password), null)
        }else {
            error = null
        }
    }
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        return false
    }
}