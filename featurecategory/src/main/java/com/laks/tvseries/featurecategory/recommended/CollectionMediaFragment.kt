package com.laks.tvseries.featurecategory.recommended

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import com.google.firebase.database.*
import com.laks.tvseries.core.base.fragment.CategoryBaseFragment
import com.laks.tvseries.core.cache.MemoryCache
import com.laks.tvseries.core.data.model.CollectionArray
import com.laks.tvseries.core.data.model.CollectionSliderModel
import com.laks.tvseries.core.data.model.MediaType
import com.laks.tvseries.core.global.GlobalConstants
import com.laks.tvseries.featurecategory.R
import com.laks.tvseries.featurecategory.category.CategoryViewModel
import com.laks.tvseries.featurecategory.databinding.FragmentCollectionMediaBinding
import com.laks.tvseries.featurecategory.di.categoryDIModule
import org.koin.core.module.Module

class CollectionMediaFragment: CategoryBaseFragment<CategoryViewModel>(CategoryViewModel::class), BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    override val modules: List<Module>
        get() = arrayListOf(categoryDIModule)

    private lateinit var binding: FragmentCollectionMediaBinding

    private var collectionList: ArrayList<CollectionSliderModel> = arrayListOf()

    private lateinit var fbDatabase : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_media, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = baseViewModel

        if(MemoryCache.cache.findMemoryCacheValue(GlobalConstants.FIREBASE_COLLECTION_SERIES_TABLE) == null) {
            getFirebaseDatabaseValueList(GlobalConstants.FIREBASE_COLLECTION_SERIES_TABLE)
        } else {
            collectionList = (MemoryCache.cache.findMemoryCacheValue(GlobalConstants.FIREBASE_COLLECTION_SERIES_TABLE) as CollectionArray).collectionList
            setCollectionList()
        }

        return binding.root
    }

    private fun setCollectionList() {
        binding.sliderLayout.removeAllSliders()
          for(item in collectionList) {
            val collectionSeries = TextSliderView(requireContext())
            collectionSeries
                .image("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${item.image}")
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this)
            collectionSeries.bundle(Bundle())
            binding.sliderLayout.addSlider(collectionSeries)
        }

        binding.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
        binding.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        binding.sliderLayout.setCustomAnimation(DescriptionAnimation())
        binding.sliderLayout.setDuration(5000)
        binding.sliderLayout.addOnPageChangeListener(this)
    }

    private fun getFirebaseDatabaseValueList(path: String) {
        fbDatabase = FirebaseDatabase.getInstance().getReference(path)
        //collectionList.clear()
        fbDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    when (path) {
                        GlobalConstants.FIREBASE_COLLECTION_SERIES_TABLE -> {
                            ds.getValue(CollectionSliderModel::class.java)?.let { collectionList.add(it) }
                            val collectionArray = CollectionArray(collectionList)
                            MemoryCache.cache.setMemoryCacheValue(GlobalConstants.FIREBASE_COLLECTION_SERIES_TABLE, collectionArray)
                            setCollectionList()
                            fbDatabase.onDisconnect()
                        }
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        val item = collectionList.first { x -> ("${GlobalConstants.SERVER_BACK_DROP_IMAGE_URL}${x.image}") == slider?.url }

        MemoryCache.cache.setMemoryCacheValue(GlobalConstants.COLLECTION_MODEL, item)
        val intent = Intent(requireActivity(), CollectionDetailActivity::class.java)
        startActivity(intent)

    }
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        if(position<=collectionList.size) {
            binding.labelCollectionTitle.text = collectionList[position].title
        }
    }
    override fun onPageScrollStateChanged(state: Int) {}
}