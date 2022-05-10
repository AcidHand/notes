package androis.example.notes.screens.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androis.example.notes.R
import androis.example.notes.databinding.FragmentNoteBinding
import androis.example.notes.models.AppNote
import androis.example.notes.utilits.APP_ACTIVITY

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = checkNotNull(_binding)
    private lateinit var mViewModel: NoteFragmentViewModel
    private lateinit var mCurrentNote: AppNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater, container, false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        setHasOptionsMenu(true)
        mBinding.noteText.text = mCurrentNote.text
        mBinding.noteName.text = mCurrentNote.name
        mViewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete -> deleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun deleteDialog() {
        AlertDialog.Builder(activity)
            .setTitle(getString(R.string.delete_dialog_title))
            .setPositiveButton(getString(R.string.dialog_positive)) { dialogInterface, _ ->
                APP_ACTIVITY.navController.navigate(R.id.action_notesFragment_to_mainFragment)
                mViewModel.delete(mCurrentNote) { }
                dialogInterface.cancel()
            }
            .setNegativeButton(getString(R.string.dialog_negative)) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }
}
