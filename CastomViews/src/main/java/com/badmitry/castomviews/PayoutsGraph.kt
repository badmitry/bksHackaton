package com.badmitry.castomviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.badmitry.domain.entities.Finance

class PayoutsGraph : View {

    private var viewWidth = 0
    private var viewHeight = 0
    private var sizeOfPaddingY = 10
    private var maxHeight = 0
    private var sizeOfPaddingX = 10
    private var widthOfBar = 0
    private var graphPaddingTop = 1.3
    private var graphPaddingBottom = 32
    private val sizeText = 32F
    private var textPaint: Paint? = null
    private var textAmount = ""
    private var xAmount = 0F
    private var yAmount = 0F
    private val listOfData = mutableMapOf<Int, MutableList<Finance>>()
    private val listOfPaints = mutableListOf<Paint>()
    private val mapOfRectangle = mutableMapOf<Rect, Int>()
    private val listOfXCord = mutableListOf<Int>()

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
        sizeOfPaddingY = h / 200
        viewWidth = w - paddingLeft - paddingRight
        viewHeight = h - paddingTop - paddingBottom
        sizeOfPaddingX = w / 20
        widthOfBar = if (listOfData.size != 0) {
            (viewWidth - sizeOfPaddingX * (listOfData.size - 1)) / listOfData.size
        } else
            0
        textPaint = Paint().apply {
            textSize = sizeText
            setShadowLayer(sizeText, 0F, 0F, Color.WHITE)
        }
        var xPadding = 0
        listOfData.forEach { map ->
            var yCord = viewHeight - graphPaddingBottom
            listOfXCord.add(xPadding)
            map.value.forEach {
                listOfPaints.add(Paint().apply {
                    color = context.resources.getColor(it.colorRes)
                    style = Paint.Style.FILL
                })
                val currentHeight = (viewHeight / graphPaddingTop * it.value / maxHeight).toInt()
                mapOfRectangle.put(Rect().apply {
                    set(
                        xPadding,
                        yCord - currentHeight,
                        xPadding + widthOfBar,
                        yCord
                    )
                }, it.value)
                yCord -= currentHeight + sizeOfPaddingY
            }
            xPadding += widthOfBar + sizeOfPaddingX
        }
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until mapOfRectangle.size) {
            canvas?.drawRect(mapOfRectangle.keys.toList()[i], listOfPaints[i])
        }
        for (i in 0 until listOfData.size) {
            textPaint?.let {
                Log.e("!!!", "onDraw: ${listOfData.keys.toList()[i]}")
                canvas?.drawText(
                    listOfData.keys.toList()[i].toString(),
                    listOfXCord[i].toFloat(),
                    viewHeight.toFloat(),
                    it
                )
            }
        }
        textPaint?.let {
            canvas?.drawText(
                maxHeight.toString(),
                (viewWidth - graphPaddingBottom * 4).toFloat(),
                (viewHeight - viewHeight / graphPaddingTop).toFloat(),
                it
            )
            canvas?.drawText(
                textAmount,
                xAmount,
                yAmount,
                it
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    val touched_x = it.x
                    val touched_y = it.y
                    mapOfRectangle.forEach {
                        if (touched_x < it.key.right && touched_x > it.key.left && touched_y < it.key.bottom && touched_y > it.key.top) {
                            Log.e("!!!", "onTouchEvent: ${it.value}")
                            xAmount = touched_x
                            yAmount = touched_y
                            textAmount = it.value.toString()
                            invalidate()
                            return true
                        }
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setFinances(finances: List<Finance>) {
        listOfData.clear()
        listOfPaints.clear()
        mapOfRectangle.clear()
        listOfXCord.clear()
        maxHeight = 0
        finances.forEach { finance ->
            listOfData.get(finance.year)?.let {
                it.add(finance)
            } ?: listOfData.put(finance.year, mutableListOf(finance))
        }
        var heightOfLine = 0
        listOfData.forEach {
            it.value.forEach {
                heightOfLine += it.value
            }
            if (heightOfLine > maxHeight) {
                maxHeight = heightOfLine
            }
            heightOfLine = 0
        }
        Log.e("!!!", "listOfData ${listOfData} maxHeight $maxHeight")
    }
}