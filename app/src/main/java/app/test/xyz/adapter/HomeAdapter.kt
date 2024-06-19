package app.test.xyz.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import app.test.xyz.ItemDetailsActivity
import app.test.xyz.R
import app.test.xyz.modal.HomeData
import app.test.xyz.modal.selectedHomeData

class HomeAdapter(private val context: Context, private val dataList: List<HomeData>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appName: TextView = itemView.findViewById(R.id.tv_appname)
        val uploaderName: TextView = itemView.findViewById(R.id.uploaderName)
        val totallimit: TextView = itemView.findViewById(R.id.tv_totallimit)
        val notcomplete: TextView = itemView.findViewById(R.id.tv_notcomplete)
        val join: Button = itemView.findViewById(R.id.joinButton)
        // Define other views here
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.appName.text = data.app_name
        holder.uploaderName.text = data.uploader_name
        holder.totallimit.text = data.totallimit
        holder.notcomplete.text = data.test_complete

        val appname=data.app_name
        val instruction=data.instruction
        val link=data.app_link
        val upname=data.uploader_name
        val com=data.test_complete
        val total=data.totallimit
        // Bind other fields here
        holder.join.setOnClickListener {
            val intent = Intent(context, ItemDetailsActivity::class.java).apply {
             selectedHomeData=data
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = dataList.size
}
