package com.sanchit.healthzoid.facts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sanchit.healthzoid.R
import com.sanchit.healthzoid.databinding.FragmentFactsBinding
import org.imaginativeworld.whynotimagecarousel.CarouselItem

class FactsFragment : Fragment() {
    private lateinit var binding: FragmentFactsBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_facts, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = " Mystery Facts"
        val carouselList = mutableListOf<CarouselItem>()
        carouselList.add(CarouselItem(R.drawable.first))
        carouselList.add(CarouselItem(R.drawable.second))
        carouselList.add(CarouselItem(R.drawable.third))
        carouselList.add(CarouselItem(R.drawable.fourth))
        carouselList.add(CarouselItem(R.drawable.fifth))
        carouselList.add(CarouselItem(R.drawable.sixth))
        carouselList.add(CarouselItem(R.drawable.seventh))
        carouselList.add(CarouselItem(R.drawable.eighth))
        carouselList.add(CarouselItem(R.drawable.ninth))
        carouselList.add(CarouselItem(R.drawable.tenth))
        carouselList.add(CarouselItem(R.drawable.eleventh))
        carouselList.add(CarouselItem(R.drawable.twelfth))
        carouselList.add(CarouselItem(R.drawable.thirteenth))
        carouselList.add(CarouselItem(R.drawable.fourteenth))
        carouselList.add(CarouselItem(R.drawable.fifteenth))
        carouselList.add(CarouselItem(R.drawable.sixteenth))
        carouselList.add(CarouselItem(R.drawable.seventeenth))
        carouselList.add(CarouselItem(R.drawable.eighteenth))
        carouselList.add(CarouselItem(R.drawable.ninteenth))
        carouselList.add(CarouselItem(R.drawable.twenty))
        carouselList.add(CarouselItem(R.drawable.twentyone))
        carouselList.add(CarouselItem(R.drawable.twentytwo))
        carouselList.add(CarouselItem(R.drawable.twentythree))
        carouselList.add(CarouselItem(R.drawable.twentyfour))
        carouselList.add(CarouselItem(R.drawable.twentyfive))
        carouselList.add(CarouselItem(R.drawable.twentysix))
        carouselList.add(CarouselItem(R.drawable.twentyseven))
        carouselList.add(CarouselItem(R.drawable.twentyeight))
        carouselList.add(CarouselItem(R.drawable.twentynine))
        carouselList.add(CarouselItem(R.drawable.thirty))
        carouselList.add(CarouselItem(R.drawable.thirtyone))
        carouselList.add(CarouselItem(R.drawable.thirtytwo))
        carouselList.add(CarouselItem(R.drawable.thirtythree))
        carouselList.add(CarouselItem(R.drawable.thirtyfour))
        carouselList.add(CarouselItem(R.drawable.thirtyfive))
        carouselList.add(CarouselItem(R.drawable.thirtysix))
        carouselList.add(CarouselItem(R.drawable.thirtyseven))
        carouselList.add(CarouselItem(R.drawable.thirtyeight))
        carouselList.add(CarouselItem(R.drawable.thirtynine))
        carouselList.add(CarouselItem(R.drawable.fourty))
        carouselList.add(CarouselItem(R.drawable.fourtyone))
        carouselList.add(CarouselItem(R.drawable.fourtytwo))
        carouselList.add(CarouselItem(R.drawable.fourtythree))
        carouselList.add(CarouselItem(R.drawable.fourtyfour))
        carouselList.add(CarouselItem(R.drawable.fourtyfive))
        carouselList.add(CarouselItem(R.drawable.fourtysix))
        carouselList.add(CarouselItem(R.drawable.fourtyseven))
        carouselList.add(CarouselItem(R.drawable.fourtyeight))
        carouselList.add(CarouselItem(R.drawable.fourtynine))
        carouselList.add(CarouselItem(R.drawable.fifty))
        carouselList.shuffle()

        binding.carousel.addData(carouselList)

        return binding.root
    }
}