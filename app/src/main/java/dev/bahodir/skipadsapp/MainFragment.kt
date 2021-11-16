package dev.bahodir.skipadsapp

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import dev.bahodir.skipadsapp.adapter.VpMainAdapter
import dev.bahodir.skipadsapp.databinding.FragmentMainBinding
import eightbitlab.com.blurview.RenderScriptBlur

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
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
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        binding.viewTransition.adapter = VpMainAdapter(requireActivity())

        binding.viewTransition.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.navView.selectedItemId = when(position) {
                    0 -> R.id.home
                    1 -> R.id.favourite
                    else -> R.id.profile
                }
            }
        })

        binding.navView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    binding.viewTransition.currentItem = 0

                    binding.v1.visibility = View.VISIBLE
                    binding.v2.visibility = View.INVISIBLE
                    binding.v3.visibility = View.INVISIBLE
                }
                R.id.favourite -> {
                    binding.viewTransition.currentItem = 1

                    binding.v1.visibility = View.INVISIBLE
                    binding.v2.visibility = View.VISIBLE
                    binding.v3.visibility = View.INVISIBLE
                }
                else -> {
                    binding.viewTransition.currentItem = 2

                    binding.v1.visibility = View.INVISIBLE
                    binding.v2.visibility = View.INVISIBLE
                    binding.v3.visibility = View.VISIBLE
                }
            }
            true
        }

        val radius = 21f;
        val decorView: View = requireActivity().window.decorView
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        binding.blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true).setOverlayColor(Color.parseColor("#16FFFFFF"))

        binding.viewTransition.isUserInputEnabled = false

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
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}