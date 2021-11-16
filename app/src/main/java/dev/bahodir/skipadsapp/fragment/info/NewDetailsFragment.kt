package dev.bahodir.skipadsapp.fragment.info

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import dev.bahodir.skipadsapp.R
import dev.bahodir.skipadsapp.databinding.FragmentNewDetailsBinding
import dev.bahodir.skipadsapp.news.Article
import dev.bahodir.skipadsapp.room.User
import dev.bahodir.skipadsapp.room.UserDatabase
import eightbitlab.com.blurview.RenderScriptBlur

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "article"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var article: Article? = null
    //private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getSerializable(ARG_PARAM1) as Article?
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    private var _binding: FragmentNewDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewDetailsBinding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        Picasso.get().load(article?.urlToImage).into(binding.actionImage)
        binding.date.text = article?.publishedAt.toString().split("T")[0]
        binding.description.text = article?.description
        binding.name.text = article?.source?.name
        binding.title.text = article?.title

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

        binding.blur.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true).setOverlayColor(Color.parseColor("#16FFFFFF"))

        binding.fabLike.setOnClickListener {
            binding.fabLike.setImageResource(R.drawable.like)

            UserDatabase
                .getInstance(requireContext())
                .userDao()
                .addUser(
                    User(
                        author = article?.author,
                        title = article?.title,
                        description = article?.description,
                        url = article?.urlToImage,
                        published = article?.publishedAt?.split("T")?.get(0)
                    )
                )
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

/*    companion object {
        */
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param article Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewDetailsFragment.
     *//*
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(article: String, param2: String) =
            NewDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, article)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}