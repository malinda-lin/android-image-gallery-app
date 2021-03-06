package com.onramp.android.takehome.explore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.onramp.android.takehome.*
import com.onramp.android.takehome.adapters.ImageAdapter
import com.onramp.android.takehome.imageData.Image
import com.onramp.android.takehome.imageData.source.local.FavoriteImage
import com.onramp.android.takehome.services.ImageDownloadService
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class ExploreActivity : AppCompatActivity(), ExploreContract.View {

    private var adapter: ImageAdapter? = null
    private lateinit var presenter: ExploreContract.Presenter

    var downloadMap = hashMapOf<String, Map<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

        setPresenter(ExplorePresenter(this, DependencyInjectorImpl()))
        // onViewCreated is blank
        presenter.onViewCreated()

        // async function to retrieve data
        CoroutineScope(IO).launch { presenter.getRandomImageData(this@ExploreActivity) }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: ExploreContract.Presenter) {
        this.presenter = presenter
    }

    override suspend fun setBlank() {
        withContext(Main) {
            Toast.makeText(applicationContext, "Problem with connection", Toast.LENGTH_SHORT).show()
        }
    }

    override suspend fun setImagesOnMainThread(activityContext: Context, imageList: ArrayList<Image>) {
        withContext(Main) {
            setImagesToGridView(activityContext, imageList)
        }
    }

    // bind adapter to gridView
    private fun setImagesToGridView(activityContext: Context, imageList: ArrayList<Image>) {
        adapter = ImageAdapter(activityContext)
        adapter?.setData(imageList)
        val gridView = findViewById<GridView>(R.id.imageGrid)
        gridView.adapter = adapter
    }

    private var focusedView: View? = null

    // TODO: move this function to adapter
    // OnClick function for CardViews
    fun showPhotoDetail(view: View) = try {
        val imageView = view.findViewById<ImageView>(R.id.cardImageView)
        val textView = view.findViewById<TextView>(R.id.cardTextView)
        val imageButton = view.findViewById<ImageButton>(R.id.favoriteImageButton)

        when (focusedView) {
            view -> {
                textView.visibility = View.INVISIBLE
                imageButton.visibility = View.INVISIBLE
                imageView.alpha = 1f
                focusedView = null
            }
            null -> {
                textView.visibility = View.VISIBLE
                imageButton.visibility = View.VISIBLE
                imageView.alpha = 0.5f
                focusedView = view
            }
            else -> {
                // deselect previous card
                val prevImageView = focusedView!!.findViewById<ImageView>(R.id.cardImageView)
                val prevTextView = focusedView!!.findViewById<TextView>(R.id.cardTextView)
                val prevImageButton = focusedView!!.findViewById<ImageButton>(R.id.favoriteImageButton)
                prevTextView.visibility = View.INVISIBLE
                prevImageButton.visibility = View.INVISIBLE
                prevImageView.alpha = 1f

                // select the current card
                textView.visibility = View.VISIBLE
                imageButton.visibility = View.VISIBLE
                imageView.alpha = 0.3f
                focusedView = view
            }
        }
    } catch (e: Exception) {
        throw Exception("MaterialCardView OnClick: $e")
    }

    fun saveToFavorites(view: View) {
        val url = focusedView?.findViewById<ImageView>(R.id.cardImageView)?.tag
        val imageData = FavoriteImage(0, "", "",
                url.toString(), "")
        CoroutineScope(IO).launch { presenter.saveFavoriteImage(this@ExploreActivity, imageData) }
    }

    fun startDownload(view: View) {
        // view refers to download button
        setStartDownloadVisibility(false)

        adapter?.setSwitchVisibility(false)

        // Call to service
        val intent = Intent(this, ImageDownloadService::class.java)
        // Resource used: https://stackoverflow.com/questions/4992097/android-how-to-pass-hashmapstring-string-between-activities/4992465#4992465
        intent.apply { putExtra("downloadMap", downloadMap) }
        startService(intent)
    }

    fun saveForDownload(view: View) {
        // view refers to switchMaterial
        val switchMaterial = view.findViewById<SwitchMaterial>(R.id.downloadSwitchMaterial)
        val tag = switchMaterial.tag as List<*>
        val key = tag[0].toString()
        val url = tag[1].toString()
        val name = tag[2].toString()
        val description = tag[3].toString()

        val imageObject = mapOf<String, String>(
                "url" to url,
                "name" to name,
                "description" to description
        )

        if (switchMaterial.isChecked) downloadMap[key] = imageObject
        else downloadMap.remove(key)

    }

    internal fun setStartDownloadVisibility(visible: Boolean) {
        val downloadButton = findViewById<MaterialButton>(R.id.downloadButton)
        if (visible) downloadButton.visibility = View.VISIBLE
        else downloadButton.visibility = View.GONE
    }

    internal fun setSwitchVisibility(visible: Boolean) {
        adapter?.setSwitchVisibility(visible)
    }
}