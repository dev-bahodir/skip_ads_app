package dev.bahodir.skipadsapp.fragment

import android.graphics.Color
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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.bahodir.skipadsapp.R
import dev.bahodir.skipadsapp.adapter.VpAdapter
import dev.bahodir.skipadsapp.adapter.VpRvAdapterNews
import dev.bahodir.skipadsapp.animation.DepthPageTransformer
import dev.bahodir.skipadsapp.databinding.FragmentHomeBinding
import dev.bahodir.skipadsapp.databinding.SheetDialogItemBinding
import dev.bahodir.skipadsapp.databinding.TabItemBinding
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
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
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

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var vpRvAdapterNews: VpRvAdapterNews
    private lateinit var vpAdapter: VpAdapter
    private val TAG = "HomeFragment"
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        vpRvAdapterNews = VpRvAdapterNews(object : VpRvAdapterNews.OnItem {
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
        binding.viewP.adapter = vpRvAdapterNews
        binding.viewP.setPageTransformer(DepthPageTransformer())

        NewsClient.apiNewsService.getNews().enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()?.articles}")
                    vpRvAdapterNews.submitList(response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {

            }

        })

        val arr = arrayOf("Healthy", "Technology", "Finance", "Arts", "Sports")
        vpAdapter = VpAdapter(arr.size,requireActivity())
        binding.vView.adapter = vpAdapter

        TabLayoutMediator(binding.tabMode, binding.vView){ tab, position ->
            var bind = TabItemBinding.inflate(layoutInflater)

            tab.customView = bind.root
            bind.texts.text = arr[position]

            if (position == 0) {
                bind.texts.setBackgroundResource(R.drawable.tab_back_color)
                bind.texts.setTextColor(Color.WHITE)
            }
            else {
                bind.texts.setBackgroundResource(R.drawable.tab_back_white)
                bind.texts.setTextColor(Color.BLACK)
            }
        }.attach()

        binding.tabMode.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var bind = TabItemBinding.bind(tab?.customView!!)
                bind.texts.setBackgroundResource(R.drawable.tab_back_color)
                bind.texts.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var bind = TabItemBinding.bind(tab?.customView!!)
                bind.texts.setBackgroundResource(R.drawable.tab_back_white)
                bind.texts.setTextColor(Color.BLACK)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        binding.vView.isUserInputEnabled = false



        binding.lupa.setOnClickListener {
            binding.search.visibility = View.INVISIBLE
            binding.lupa.visibility = View.INVISIBLE
            binding.filter.visibility = View.INVISIBLE

            binding.searchGone.visibility = View.VISIBLE
            binding.eks.visibility = View.VISIBLE
        }

        binding.eks.setOnClickListener {
            binding.search.visibility = View.VISIBLE
            binding.lupa.visibility = View.VISIBLE
            binding.filter.visibility = View.VISIBLE

            binding.searchGone.visibility = View.GONE
            binding.eks.visibility = View.GONE
        }

        binding.filter.setOnClickListener {
            val bind: SheetDialogItemBinding = SheetDialogItemBinding.inflate(layoutInflater)
            val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)

            dialog.setContentView(bind.root)

            bind.save.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

        binding.tv2.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_seeAllFragment)

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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}