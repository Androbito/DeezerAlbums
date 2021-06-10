package fr.deezer.albums.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.airbnb.mvrx.BaseMvRxFragment
import com.airbnb.mvrx.MvRx
import fr.deezer.albums.R
import kotlinx.android.synthetic.main.activity_albums_list.*

abstract class BaseFragment: BaseMvRxFragment() {

    protected lateinit var toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar = view.findViewById(R.id.toolbar)
        toolbar.setupWithNavController(findNavController())
    }

    protected fun navigateTo(@IdRes actionId: Int, arg: Parcelable? = null) {
        val bundle = arg?.let { Bundle().apply { putParcelable(MvRx.KEY_ARG, it) } }
        findNavController().navigate(actionId, bundle)
    }
}