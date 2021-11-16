package dev.bahodir.skipadsapp.fragment.vp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dev.bahodir.skipadsapp.R
import dev.bahodir.skipadsapp.adapter.VpRvAdapterGnews
import dev.bahodir.skipadsapp.databinding.FragmentVpBinding
import dev.bahodir.skipadsapp.news.Article
import dev.bahodir.skipadsapp.news.News
import dev.bahodir.skipadsapp.retrofit.news.NewsClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentVpBinding? = null
    private val binding get() = _binding!!
    private lateinit var vpRvAdapterGnews: VpRvAdapterGnews
    private val TAG = "VpFragment"
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVpBinding.inflate(inflater, container, false)

        vpRvAdapterGnews = VpRvAdapterGnews(object : VpRvAdapterGnews.OnItem {
            override fun onTouchListener(article: Article, position: Int, view: View) {
                val bundle = Bundle()
                bundle.putSerializable("article", article)
                findNavController().navigate(R.id.action_mainFragment_to_newDetailsFragment, bundle)

                val adRequest = AdRequest.Builder().build()

                InterstitialAd.load(
                    requireContext(),
                    "ca-app-pub-3940256099942544/1033173712",
                    adRequest,
                    object : InterstitialAdLoadCallback() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Log.d(TAG, adError.message)
                            mInterstitialAd = null
                        }

                        override fun onAdLoaded(interstitialAd: InterstitialAd) {
                            Log.d(TAG, "Ad was loaded.")
                            mInterstitialAd = interstitialAd
                        }
                    })
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(requireActivity())
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }
            }

        })
        binding.rec.adapter = vpRvAdapterGnews

        NewsClient.apiNewsService.getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()?.articles}")
                    vpRvAdapterGnews.submitList(response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

            }

        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}