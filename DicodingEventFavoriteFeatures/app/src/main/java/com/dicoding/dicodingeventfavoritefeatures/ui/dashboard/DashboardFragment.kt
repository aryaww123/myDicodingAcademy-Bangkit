package com.dicoding.dicodingeventfavoritefeatures.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.dicodingeventfavoritefeatures.data.remote.response.Response
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiConfig
import com.dicoding.dicodingeventfavoritefeatures.data.remote.retrofit.ApiService
import com.dicoding.dicodingeventfavoritefeatures.databinding.FragmentDashboardBinding
import com.dicoding.dicodingeventfavoritefeatures.ui.EventAdapter
import com.dicoding.dicodingeventfavoritefeatures.ui.EventItem
import retrofit2.Call
import retrofit2.Callback

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var apiService: ApiService

    companion object{
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        eventAdapter = EventAdapter(requireContext())
        apiService = ApiConfig.getApiService()
        setupRecyclerView()
        getEvent()
        return binding.root
    }
    private fun getEvent(){
        showLoading(true)
        val call = ApiConfig.getApiService().getEvents(0)
        call.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>){
                showLoading(false)
                Log.d("HomeFragment", "Response: $response")
                if (response.isSuccessful){
                    val events = response.body()
                    events?.let {
                        val eventItems = it.listEvents.map { event ->
                            EventItem(
                                name = event.name ?: "No Name",
                                mediaCover = event.mediaCover ?:"",
                                id = event.id
                            )
                        }
                        eventAdapter.submitList(eventItems)
                        Log.d("HomeFragment", "Events: $events")
                    }
                    Log.d("HomeFragment", "Events: $events")
                } else {
                    showError("Error: ${response.code()}")
                    Toast.makeText(context, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Response>, t:Throwable){
                showLoading(false)
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                showError("Error: ${t.message}")
            }
        })
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = eventAdapter
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String?){
        val errorHandlingLayout = binding.errorHandling
        errorHandlingLayout.root.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        errorHandlingLayout.textError.text = message

        errorHandlingLayout.buttonRetry.setOnClickListener{
            errorHandlingLayout.root.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            getEvent()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}