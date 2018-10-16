package pro.ahoora.zhin.om.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pro.ahoora.zhin.om.R

class ColorAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private lateinit var iClick: OnClickListener

    interface OnClickListener {
        /**
         * 0 -> search background
         * 1 -> search text
         * 2 -> toolbar background
         * 3-> toolbar text
         * 4 -> background
         * 5-> navigation background
         * 6 -> text color
         * 7 -> separator line color
         * 8 -> bottom bar color
         */
        fun onClick(action: Int, color: Int)
    }

    fun setOnClickListener(i: OnClickListener) {
        iClick = i
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.color_item, parent, false)
        return ColorHolder(view)
    }

    override fun getItemCount(): Int {
        return 76
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ColorHolder

        holder.card.setCardBackgroundColor(getBGColor(position))
        holder.tvName.text = getColorName(position)
    }

    private fun getBGColor(position: Int): Int {
        val color: Int
        return when (position) {
            0 -> ContextCompat.getColor(context, R.color.silver)
            1 -> ContextCompat.getColor(context, R.color.graySms)
            2 -> ContextCompat.getColor(context, R.color.gray1)
            3 -> ContextCompat.getColor(context, R.color.gray2)
            4 -> ContextCompat.getColor(context, R.color.gray3)
            5 -> ContextCompat.getColor(context, R.color.gray4)
            6 -> ContextCompat.getColor(context, R.color.gray5)
            7 -> ContextCompat.getColor(context, R.color.gray6)
            8 -> ContextCompat.getColor(context, R.color.gray7)
            9 -> ContextCompat.getColor(context, R.color.gray8)
            10 -> ContextCompat.getColor(context, R.color.gray9)
            11 -> ContextCompat.getColor(context, R.color.gray10)
            12 -> ContextCompat.getColor(context, R.color.gray11)
            13 -> ContextCompat.getColor(context, R.color.gray12)
            14 -> ContextCompat.getColor(context, R.color.gray13)
            15 -> ContextCompat.getColor(context, R.color.gray14)
            16 -> ContextCompat.getColor(context, R.color.gray15)
            17 -> ContextCompat.getColor(context, R.color.gray16)
            18 -> ContextCompat.getColor(context, R.color.gray17)
            19 -> ContextCompat.getColor(context, R.color.gray18)
            20 -> ContextCompat.getColor(context, R.color.gray19)
            21 -> ContextCompat.getColor(context, R.color.gray20)
            22 -> ContextCompat.getColor(context, R.color.gray21)
            23 -> ContextCompat.getColor(context, R.color.gray22)
            24 -> ContextCompat.getColor(context, R.color.gray23)
            25 -> ContextCompat.getColor(context, R.color.gray24)
            26 -> ContextCompat.getColor(context, R.color.gray25)
            27 -> ContextCompat.getColor(context, R.color.gray26)
            28 -> ContextCompat.getColor(context, R.color.gray27)
            29 -> ContextCompat.getColor(context, R.color.gray28)
            30 -> ContextCompat.getColor(context, R.color.gray29)
            31 -> ContextCompat.getColor(context, R.color.gray30)
            32 -> ContextCompat.getColor(context, R.color.gray31)
            33 -> ContextCompat.getColor(context, R.color.gray32)
            34 -> ContextCompat.getColor(context, R.color.gray33)
            35 -> ContextCompat.getColor(context, R.color.gray34)
            36 -> ContextCompat.getColor(context, R.color.gray35)
            37 -> ContextCompat.getColor(context, R.color.gray36)
            38 -> ContextCompat.getColor(context, R.color.gray37)
            39 -> ContextCompat.getColor(context, R.color.gray38)
            40 -> ContextCompat.getColor(context, R.color.gray39)
            41 -> ContextCompat.getColor(context, R.color.gray40)
            42 -> ContextCompat.getColor(context, R.color.gray41)
            43 -> ContextCompat.getColor(context, R.color.gray42)
            44 -> ContextCompat.getColor(context, R.color.gray43)
            45 -> ContextCompat.getColor(context, R.color.gray44)
            46 -> ContextCompat.getColor(context, R.color.gray45)
            47 -> ContextCompat.getColor(context, R.color.gray46)
            48 -> ContextCompat.getColor(context, R.color.gray47)
            49 -> ContextCompat.getColor(context, R.color.gray48)
            50 -> ContextCompat.getColor(context, R.color.gray49)
            51 -> ContextCompat.getColor(context, R.color.gray50)
            52 -> ContextCompat.getColor(context, R.color.gray51)
            53 -> ContextCompat.getColor(context, R.color.gray52)
            54 -> ContextCompat.getColor(context, R.color.gray53)
            55 -> ContextCompat.getColor(context, R.color.gray54)
            56 -> ContextCompat.getColor(context, R.color.gray55)
            57 -> ContextCompat.getColor(context, R.color.gray56)
            58 -> ContextCompat.getColor(context, R.color.gray57)
            59 -> ContextCompat.getColor(context, R.color.gray58)
            60 -> ContextCompat.getColor(context, R.color.gray59)
            61 -> ContextCompat.getColor(context, R.color.gray60)
            62 -> ContextCompat.getColor(context, R.color.gray61)
            63 -> ContextCompat.getColor(context, R.color.gray62)
            64 -> ContextCompat.getColor(context, R.color.gray63)
            65 -> ContextCompat.getColor(context, R.color.gray64)
            66 -> ContextCompat.getColor(context, R.color.gray65)
            67 -> ContextCompat.getColor(context, R.color.gray66)
            68 -> ContextCompat.getColor(context, R.color.gray67)
            69 -> ContextCompat.getColor(context, R.color.bg)
            70 -> ContextCompat.getColor(context, R.color.darkBG)
            71 -> ContextCompat.getColor(context, R.color.whiteBG)
            72 -> ContextCompat.getColor(context, R.color.iosToolbar)
            73 -> ContextCompat.getColor(context, R.color.gray68)
            74 -> ContextCompat.getColor(context, R.color.gray69)
            75 -> ContextCompat.getColor(context, R.color.gray70)
            else -> 0
        }
    }

    private fun getColorName(position: Int): String {
        var p = position
        if (position > 1) {
            p++
        }
        return when (p) {
            0 -> "silver"
            1 -> "gray sms"
            else -> x(p)
        }
    }

    private fun x(position: Int) = "gray $position"

    inner class ColorHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        override fun onClick(v: View?) {
            val action = when (v?.id) {
                R.id.btn_search -> 0
                R.id.btn_searchText -> 1
                R.id.btn_toolbar -> 2
                R.id.btn_toolbarText -> 3
                R.id.btn_back -> 4
                R.id.btn_navigation -> 5
                R.id.btn_text -> 6
                R.id.btn_separator -> 7
                R.id.btn_bottomBar -> 8
                else -> 0
            }

            /**
             * 0 -> search background
             * 1 -> search text
             * 2 -> toolbar background
             * 3-> toolbar text
             * 4 -> background
             * 5-> navigation background
             * 6 -> text color
             * 7 -> separator line color
             * 8 -> bottom bar color
             */

            iClick.onClick(action, getBGColor(adapterPosition))
        }

        val card: CardView = itemView.findViewById(R.id.cv_colorHolder)
        val tvName: AppCompatTextView = itemView.findViewById(R.id.tv_colorName)


        val textColor: AppCompatButton = itemView.findViewById(R.id.btn_text)
        val searchBg: AppCompatButton = itemView.findViewById(R.id.btn_search)
        val searchTextColor: AppCompatButton = itemView.findViewById(R.id.btn_searchText)
        val toolbarBg: AppCompatButton = itemView.findViewById(R.id.btn_toolbar)
        val toolbarTextColor: AppCompatButton = itemView.findViewById(R.id.btn_toolbarText)
        val background: AppCompatButton = itemView.findViewById(R.id.btn_back)
        val navigation: AppCompatButton = itemView.findViewById(R.id.btn_navigation)
        val bottomBar: AppCompatButton = itemView.findViewById(R.id.btn_bottomBar)
        val separator: AppCompatButton = itemView.findViewById(R.id.btn_separator)


        init {
            card.setOnClickListener(this)

            textColor.setOnClickListener(this)
            searchBg.setOnClickListener(this)
            searchTextColor.setOnClickListener(this)
            toolbarBg.setOnClickListener(this)
            toolbarTextColor.setOnClickListener(this)
            background.setOnClickListener(this)
            navigation.setOnClickListener(this)
            bottomBar.setOnClickListener(this)
            separator.setOnClickListener(this)
        }

    }

}