package br.com.zaptest.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.zaptest.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_immobile_images.*

class ImmobileImagesFragment(private val imageUrl: String) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_immobile_images, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Picasso.get().load(imageUrl).into(image)
    }
}