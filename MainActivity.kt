package com.kolonka.control

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val bg = Color.rgb(15, 19, 29)
    private val panel = Color.rgb(31, 41, 58)
    private val blue = Color.rgb(55, 126, 235)
    private lateinit var body: LinearLayout
    private var volume = 65
    private var lightOn = true
    private var screenOn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setBackgroundColor(bg); setPadding(22, 28, 22, 18) }
        val title = TextView(this).apply { text = "KOLONKA CONTROL\nDEMO · ESP32-S3"; textSize = 23f; setTextColor(Color.WHITE); gravity = Gravity.CENTER_VERTICAL }
        root.addView(title)
        val tabs = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        root.addView(tabs)
        body = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(0, 18, 0, 0) }
        root.addView(body, LinearLayout.LayoutParams(-1, 0, 1f))
        listOf("Главная", "Эквалайзер", "Подсветка", "Экран").forEach { name ->
            val b = Button(this).apply { text = name; textSize = 10f; isAllCaps = false; setOnClickListener { show(name) } }
            tabs.addView(b, LinearLayout.LayoutParams(0, -2, 1f))
        }
        setContentView(root)
        show("Главная")
    }

    private fun text(s: String, size: Float = 17f): TextView = TextView(this).apply {
        text = s; textSize = size; setTextColor(Color.WHITE); setPadding(8, 12, 8, 12)
    }
    private fun card(): LinearLayout = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL; setPadding(16, 12, 16, 12); setBackgroundColor(panel)
        val p = LinearLayout.LayoutParams(-1, -2); p.bottomMargin = 14; body.addView(this, p)
    }
    private fun show(tab: String) {
        body.removeAllViews()
        when(tab) {
            "Главная" -> {
                card().apply {
                    addView(text("МОЯ КОЛОНКА", 20f))
                    addView(text("◉                         ◉\n\n              21:30\n       HAVE A NICE EVENING", 22f))
                    addView(text("Громкость: $volume%"))
                    val seek = SeekBar(this@MainActivity).apply { max = 100; progress = volume; setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(s: SeekBar?, p: Int, u: Boolean) { volume = p }
                        override fun onStartTrackingTouch(s: SeekBar?) {}
                        override fun onStopTrackingTouch(s: SeekBar?) {}
                    }) }
                    addView(seek)
                    addView(text("⏮             ▶ / ❚❚             ⏭", 24f))
                    addView(text("Демонстрационный режим — колонка не подключена", 12f))
                }
                card().addView(text("Профиль звука: Рок · Поп · Бас · Джаз · Классика"))
            }
            "Эквалайзер" -> {
                card().apply {
                    addView(text("ПЯТИПОЛОСНЫЙ ЭКВАЛАЙЗЕР", 19f))
                    listOf("Бас", "Низкая середина", "Средние", "Высокая середина", "Высокие").forEach { n ->
                        addView(text(n))
                        addView(SeekBar(this@MainActivity).apply { max = 24; progress = 12 })
                    }
                    addView(text("Настройки пока демонстрационные", 12f))
                }
                card().apply { addView(text("ПРЕСЕТЫ")); addView(text("Рок     Поп     Бас     Классика     Джаз")) }
            }
            "Подсветка" -> card().apply {
                addView(text("УПРАВЛЕНИЕ ПОДСВЕТКОЙ", 19f))
                val sw = Switch(this@MainActivity).apply { text = "Подсветка"; isChecked = lightOn; setTextColor(Color.WHITE); setOnCheckedChangeListener { _, v -> lightOn = v } }
                addView(sw)
                addView(text("Цвет свечения"))
                addView(text("🔵       🔴       🟢       🟣", 28f))
                addView(text("Эффекты: Постоянное · Дыхание · Мигание · Реакция на музыку"))
            }
            else -> card().apply {
                addView(text("ЭКРАН КОЛОНКИ", 19f))
                val sw = Switch(this@MainActivity).apply { text = "Дисплей"; isChecked = screenOn; setTextColor(Color.WHITE); setOnCheckedChangeListener { _, v -> screenOn = v } }
                addView(sw)
                addView(text("◉       ◉\n\n       21:30\n\n ♪  ▂ ▅ ▇ ▃", 26f))
                addView(text("Что отображать: Часы · Глазки · Эквалайзер · Музыкальная анимация · Громкость"))
            }
        }
    }
}
