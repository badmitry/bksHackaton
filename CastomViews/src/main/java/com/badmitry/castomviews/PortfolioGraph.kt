package com.badmitry.castomviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.badmitry.domain.entities.Bond

class PortfolioGraph : View {

    private var roundRadius = 0
    private var viewWidth = 0
    private var viewHeight = 0
    private var sizeOfLine = 10
    private val listOfBonds = mutableListOf<Bond>()
    private val listOfPaints = mutableListOf<Paint>()
    private val listOfRectangle = mutableListOf<Rect>()

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyle,
        defStyleRes
    ) {
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        sizeOfLine = w / 200
        roundRadius = w / 50
        viewWidth = w - paddingLeft - paddingRight
        viewHeight = h - paddingTop - paddingBottom
        var xPadding = 0
        for (i in 0 until listOfBonds.size) {
            listOfPaints.add(Paint().apply {
                color = context.resources.getColor(listOfBonds[i].colorRes)
                style = Paint.Style.FILL
            })
            val currentBondWidth =
                (viewWidth - sizeOfLine * (listOfBonds.size - 1)) * listOfBonds[i].perсentOfWeight / 100
            val rect = createSampleRect(currentBondWidth, xPadding)
            listOfRectangle.add(rect)
            xPadding += currentBondWidth + sizeOfLine
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until listOfBonds.size) {
            canvas?.drawRect(listOfRectangle[i], listOfPaints[i])
        }
    }

    fun setBonds(bonds: List<Bond>) {
        listOfPaints.clear()
        listOfRectangle.clear()
        listOfBonds.clear()
        listOfBonds.addAll(bonds)
    }

    private fun createSampleRect(currentBondWidth: Int, xPadding: Int): Rect {
        return Rect().apply {
            set(
                xPadding,
                viewHeight,
                xPadding + currentBondWidth,
                0
            )
        }
    }
}