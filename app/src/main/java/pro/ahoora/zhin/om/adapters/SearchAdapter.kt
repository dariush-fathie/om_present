package pro.ahoora.zhin.om.adapters

import android.content.Context
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.model.Patient
import pro.ahoora.zhin.om.ui.detail.PatientDetailActivity
import java.util.*

class SearchAdapter(private val dataSet: List<Patient>, private val context: Context) : RecyclerView.Adapter<SearchAdapter.ItemHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val p = dataSet[position]
        holder.tvName.text = p.name
        holder.tvSickness.text = p.x
        (holder.ivThumbnail.background as GradientDrawable).setColor(getRandomColor())
    }

    private fun getRandomColor(): Int {
        val random = Random()
        val i = random.nextInt(7)

        when (i) {
            0 -> return ContextCompat.getColor(context, R.color.red)
            1 -> return ContextCompat.getColor(context, R.color.orange)
            2 -> return ContextCompat.getColor(context, R.color.green)
            3 -> return ContextCompat.getColor(context, R.color.tealBlue)
            4 -> return ContextCompat.getColor(context, R.color.blue)
            5 -> return ContextCompat.getColor(context, R.color.purple)
            6 -> return ContextCompat.getColor(context, R.color.pink)
            7 -> return ContextCompat.getColor(context, R.color.yellow)
        }
        return 0
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            Toast.makeText(context, "$adapterPosition", Toast.LENGTH_LONG).show()
            context.startActivity(Intent(context, PatientDetailActivity::class.java).apply { putExtra("index", adapterPosition) })
        }

        val tvName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
        val tvSickness: AppCompatTextView = itemView.findViewById(R.id.tv_sickness)
        val ivThumbnail: AppCompatImageView = itemView.findViewById(R.id.iv_thumbnail)

        init {
            itemView.setOnClickListener(this)
        }

    }

}