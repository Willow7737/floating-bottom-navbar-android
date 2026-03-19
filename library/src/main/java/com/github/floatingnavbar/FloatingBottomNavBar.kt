package com.github.floatingnavbar

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.DrawableCompat

class FloatingBottomNavBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val container = LinearLayout(context)
    private var selectedIndex = -1
    private val items = mutableListOf<NavBarItem>()
    private val iconViews = mutableListOf<ImageView>()
    private val iconContainers = mutableListOf<LinearLayout>()
    
    @ColorInt private var navBarBackgroundColor = Color.parseColor("#1C1B1F")
    private var navBarCornerRadius = 80f
    @ColorInt private var selectedIconColor = Color.WHITE
    @ColorInt private var unselectedIconColor = Color.parseColor("#E6E1E5")
    @ColorInt private var selectedItemBackground = Color.parseColor("#3894F0")
    
    var onItemSelectedListener: ((Int) -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingBottomNavBar)
        navBarBackgroundColor = typedArray.getColor(R.styleable.FloatingBottomNavBar_navBarBackgroundColor, navBarBackgroundColor)
        navBarCornerRadius = typedArray.getDimension(R.styleable.FloatingBottomNavBar_navBarCornerRadius, navBarCornerRadius)
        selectedIconColor = typedArray.getColor(R.styleable.FloatingBottomNavBar_selectedIconColor, selectedIconColor)
        unselectedIconColor = typedArray.getColor(R.styleable.FloatingBottomNavBar_unselectedIconColor, unselectedIconColor)
        selectedItemBackground = typedArray.getColor(R.styleable.FloatingBottomNavBar_selectedIconBackground, selectedItemBackground)
        typedArray.recycle()

        setupContainer()
    }

    private fun setupContainer() {
        radius = navBarCornerRadius
        setCardBackgroundColor(navBarBackgroundColor)
        elevation = 12f
        
        container.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        container.orientation = LinearLayout.HORIZONTAL
        container.gravity = Gravity.CENTER
        container.setPadding(24, 24, 24, 24)
        
        addView(container)
    }

    fun setItems(newItems: List<NavBarItem>) {
        items.clear()
        items.addAll(newItems)
        renderItems()
    }

    private fun renderItems() {
        container.removeAllViews()
        iconViews.clear()
        iconContainers.clear()

        for (i in items.indices) {
            val item = items[i]
            val itemView = createItemView(item, i)
            container.addView(itemView)
        }
        
        if (items.isNotEmpty()) {
            selectItem(0, animate = false)
        }
    }

    private fun createItemView(item: NavBarItem, index: Int): View {
        val iconContainer = LinearLayout(context)
        val params = LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
        iconContainer.layoutParams = params
        iconContainer.gravity = Gravity.CENTER
        
        val iconView = ImageView(context)
        val iconParams = LinearLayout.LayoutParams(110, 110)
        iconView.layoutParams = iconParams
        iconView.setImageResource(item.iconRes)
        iconView.setPadding(25, 25, 25, 25)
        
        iconContainer.addView(iconView)
        iconViews.add(iconView)
        iconContainers.add(iconContainer)

        iconContainer.setOnClickListener {
            if (selectedIndex != index) {
                selectItem(index)
                onItemSelectedListener?.invoke(index)
            }
        }

        return iconContainer
    }

    fun selectItem(index: Int, animate: Boolean = true) {
        if (index < 0 || index >= items.size) return
        
        val previousIndex = selectedIndex
        selectedIndex = index

        if (previousIndex != -1) {
            deselectItemView(previousIndex, animate)
        }
        
        selectItemView(selectedIndex, animate)
    }

    private fun selectItemView(index: Int, animate: Boolean) {
        val iconView = iconViews[index]
        
        val shape = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 35f
            setColor(selectedItemBackground)
        }
        
        if (animate) {
            iconView.alpha = 0f
            iconView.background = shape
            iconView.animate()
                .alpha(1f)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(200)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction {
                    iconView.animate().scaleX(1f).scaleY(1f).setDuration(100).start()
                }
                .start()
            
            animateIconTint(iconView, unselectedIconColor, selectedIconColor)
        } else {
            iconView.background = shape
            DrawableCompat.setTint(iconView.drawable, selectedIconColor)
        }
    }

    private fun deselectItemView(index: Int, animate: Boolean) {
        val iconView = iconViews[index]
        
        if (animate) {
            iconView.background = null
            animateIconTint(iconView, selectedIconColor, unselectedIconColor)
        } else {
            iconView.background = null
            DrawableCompat.setTint(iconView.drawable, unselectedIconColor)
        }
    }

    private fun animateIconTint(imageView: ImageView, fromColor: Int, toColor: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), fromColor, toColor)
        colorAnimation.duration = 250
        colorAnimation.addUpdateListener { animator ->
            val color = animator.animatedValue as Int
            DrawableCompat.setTint(imageView.drawable, color)
        }
        colorAnimation.start()
    }

    data class NavBarItem(
        @DrawableRes val iconRes: Int,
        val title: String = ""
    )
}
