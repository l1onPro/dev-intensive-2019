package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attr, defStyleAttr) {
    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density.toInt())
    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = 2.dp

    init {
        if (attr != null) {
            val a = context.obtainStyledAttributes(attr, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimensionPixelSize(R.styleable.CircleImageView_cv_borderWidth, borderWidth)
            a.recycle()
        }
    }

    @Dimension
    fun getBorderWidth(): Int = borderWidth.dp

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dp.dp
        this.invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        this.invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ContextCompat.getColor(App.applicationContext(), colorId)
        this.invalidate()
    }

}