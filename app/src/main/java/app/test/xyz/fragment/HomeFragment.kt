package app.test.xyz.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.test.xyz.adapter.HomeAdapter
import app.test.xyz.databinding.FragmentHomeBinding
import app.test.xyz.modal.HomeData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.test.xyz.Companion.siteUrl
import com.test.xyz.TinyDB
import org.json.JSONArray

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val dataList = mutableListOf<HomeData>()
    private lateinit var queue: RequestQueue
    lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        email = TinyDB.getString(requireContext(), "email_id","").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView
        val recyclerView = binding.rvHome
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize HomeAdapter with empty data list
        homeAdapter =  HomeAdapter(requireContext(), dataList)

        recyclerView.adapter = homeAdapter

        // Initialize Volley RequestQueue
        queue = Volley.newRequestQueue(requireContext())

        // Fetch home data
        fetchHomeData()

    }

    private fun fetchHomeData() {
        val url = siteUrl + "home_data.php?email=$email"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    // Parse JSON response
                    val jsonArray = JSONArray(response)
                    if (jsonArray.length() == 0) {
                        // No data found, show a message
                        binding.nodata.visibility = View.VISIBLE
                        binding.rvHome.visibility = View.GONE
                    } else {
                        dataList.clear()
                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            val homeData = HomeData(
                                app_name = jsonObject.getString("app_name"),
                                c_id = jsonObject.getString("c_id"),
                                test_complete = jsonObject.getString("test_complete"),
                                test_uncomplete = jsonObject.getString("test_uncomplete"),
                                totallimit = jsonObject.getString("total_limit"),
                                uploader_name = jsonObject.getString("uploader_name"),
                                app_link = jsonObject.getString("app_link"),
                                instruction = jsonObject.getString("instruction")
                            )
                            dataList.add(homeData)
                        }
                        // Notify adapter
                        homeAdapter.notifyDataSetChanged()
                    }
                } catch (e: Exception) {
                    // Handle JSON parsing exception
                    Toast.makeText(context, "Failed to parse data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                // Handle Volley error
                binding.nodata.visibility = View.VISIBLE
                binding.rvHome.visibility = View.GONE
            }
        )

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }


}
