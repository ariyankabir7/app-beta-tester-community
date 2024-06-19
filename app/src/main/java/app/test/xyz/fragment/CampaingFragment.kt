package app.test.xyz.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.test.xyz.R
import app.test.xyz.adapter.CampaignAdapter
import app.test.xyz.adapter.HomeAdapter
import app.test.xyz.databinding.FragmentCampaingBinding
import app.test.xyz.databinding.FragmentHomeBinding
import app.test.xyz.modal.AcceptData
import app.test.xyz.modal.myCampaignData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.test.xyz.Companion
import com.test.xyz.TinyDB
import org.json.JSONArray

class CampaingFragment : Fragment() {
    private lateinit var binding: FragmentCampaingBinding
    private val dataList1 = mutableListOf<myCampaignData>()
    private lateinit var queue: RequestQueue
    lateinit var email: String


    private lateinit var campaign: CampaignAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCampaingBinding.inflate(inflater, container, false)

        email = TinyDB.getString(requireContext(), "email_id","").toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val recyclerView = binding.rvCampaign
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize HomeAdapter with empty data list
        campaign =  CampaignAdapter(requireContext(), dataList1)

        recyclerView.adapter = campaign

        // Initialize Volley RequestQueue
        queue = Volley.newRequestQueue(requireContext())

        // Fetch home data
        fetchHomeData()

    }
//            val builder = AlertDialog.Builder(requireContext(), R.style.updateDialogTheme)
//            builder.setView(customLayout)
//            builder.setCancelable(true)
//
//            val popupDialog = builder.create()
//            popupDialog.show()
//
//            // Initialize RecyclerView
//            val recyclerView = customLayout.findViewById<RecyclerView>(R.id.rv_joinRequest)
//            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//
//            // Initialize HomeAdapter with empty data list
//            mycampaign = MyCampaignAdapter(requireContext(), dataList)
//            Handler(Looper.getMainLooper()).postDelayed({
//                recyclerView.adapter = mycampaign
//
//            }, 200)
//
//
//        }

    private fun fetchHomeData() {
        val url = Companion.siteUrl + "my_campaign_data.php?email=$email"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Parse JSON response
                    val jsonArray = JSONArray(response)
                    if (jsonArray.length() == 0) {
                        // No data found, show a message

                    } else {
                        dataList1.clear()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val myCampaignData = myCampaignData(
                                app_name = jsonObject.getString("app_name"),
                                uploader_name = jsonObject.getString("uploader_name"),
                                app_link = jsonObject.getString("app_link"),
                                c_id = jsonObject.getString("c_id").toString(),
                                test_complete = jsonObject.getInt("test_complete").toString()
                            )
                            dataList1.add(myCampaignData)

                        }
                        campaign.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    // Handle JSON parsing exception
                    Toast.makeText(
                        context,
                        "Failed to parse data: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            { error ->

            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


}