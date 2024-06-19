package app.test.xyz.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.test.xyz.ItemDetailsActivity
import app.test.xyz.MyCampaignDetailsActivity
import app.test.xyz.R
import app.test.xyz.modal.AcceptData
import app.test.xyz.modal.SeletedMycampaignData
import app.test.xyz.modal.myCampaignData
import app.test.xyz.modal.selectedHomeData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.test.xyz.Companion

class CampaignAdapter(
    private val context: Context,
    private val dataList1: List<myCampaignData>
) : RecyclerView.Adapter<CampaignAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Name: TextView = itemView.findViewById(R.id.tv_appname)
        val uploder: TextView = itemView.findViewById(R.id.uploaderName)
        val completeT: TextView = itemView.findViewById(R.id.tv_notcomplete)
        val joinBtn: Button = itemView.findViewById(R.id.joinButton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_home_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList1[position]
        holder.Name.text = data.app_name
        holder.uploder.text = data.uploader_name
        holder.completeT.text = data.test_complete

        holder.joinBtn.setOnClickListener {
            val intent = Intent(context, MyCampaignDetailsActivity::class.java).apply {
                SeletedMycampaignData=data
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataList1.size

//    private fun Changedata(i: Int, id: Int) {
//        val url = Companion.siteUrl + "change_status.php?status=$i&id=$id"
//
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//
//
//            },
//            { error ->
//
//            }
//        )
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest)
//    }

}