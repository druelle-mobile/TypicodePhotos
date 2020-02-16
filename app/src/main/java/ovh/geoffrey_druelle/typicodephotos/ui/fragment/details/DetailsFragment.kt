package ovh.geoffrey_druelle.typicodephotos.ui.fragment.details

import android.app.Dialog
import android.content.DialogInterface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.details_fragment.view.*

import ovh.geoffrey_druelle.typicodephotos.R
import ovh.geoffrey_druelle.typicodephotos.data.local.model.PhotoEntity
import ovh.geoffrey_druelle.typicodephotos.utils.ext.paint

class DetailsFragment : DialogFragment() {

    private lateinit var photo: PhotoEntity
    private lateinit var customView: View

    companion object {
        private var SELECTED_PHOTO = "SELECTED_PHOTO"
        private const val TAG = "DetailsFragment"

        private fun newInstance(photo: PhotoEntity): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val arguments = Bundle()
            arguments.putParcelable(SELECTED_PHOTO, photo)
            detailsFragment.arguments = arguments
            return detailsFragment
        }

        fun show(fragmentTransaction: FragmentTransaction, photo: PhotoEntity): DetailsFragment {
            val dialog = newInstance(photo)
            dialog.show(fragmentTransaction, TAG)
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photo = arguments?.getParcelable(SELECTED_PHOTO)!!
        isCancelable = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.details_fragment, null)
        customView = view

        view.detail_album_id.text = String.format("Album : %s", photo.albumId.toString())

        paint(photo.url, view.detail_photo_image)

        val builder = AlertDialog.Builder(context!!)
            .setTitle(photo.title)
            .setView(view)
            .setNegativeButton(getString(R.string.quit)) { _: DialogInterface, _: Int ->
                dialog?.cancel()
            }

        return builder.create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}
