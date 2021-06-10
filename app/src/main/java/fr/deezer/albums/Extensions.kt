package fr.deezer.albums

import android.content.Context


fun Context.calculateNoOfColumns(
    columnWidthDp: Float
): Int {
    val displayMetrics = this.resources.displayMetrics
    val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
    return (screenWidthDp / columnWidthDp + 0.5).toInt()
}