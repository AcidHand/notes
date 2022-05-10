package androis.example.notes.screens.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androis.example.notes.R
import androis.example.notes.databinding.FragmentMainBinding
import androis.example.notes.models.AppNote
import androis.example.notes.utilits.APP_ACTIVITY
import androis.example.notes.utilits.AppPreference

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = checkNotNull(_binding)
    private lateinit var mViewModel: MainFragmentViewModel

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mObserverList: Observer<List<AppNote>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        setHasOptionsMenu(true)
        mainAdapter = MainAdapter()
        mRecyclerView = mBinding.recyclerView
        mRecyclerView.adapter = mainAdapter
        mObserverList = Observer {
            val list = it.asReversed()
            mainAdapter.setList(list)
        }
        mViewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        mViewModel.allNotes.observe(this, mObserverList)

        mBinding.btnAddNote.setOnClickListener {
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_addNewNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        mViewModel.allNotes.removeObserver(mObserverList)
        mRecyclerView.adapter = null
    }

    companion object {
        fun click(note: AppNote) {
            val bundle = Bundle()
            bundle.putSerializable("note", note)
            APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_notesFragment, bundle)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.exit_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_exit -> exitDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun exitDialog() {
        AlertDialog.Builder(activity)
            .setTitle(getString(R.string.exit_dialog_title))
            .setPositiveButton(getString(R.string.dialog_positive)) { dialogInterface, _ ->
                mViewModel.signOut()
                AppPreference.setInitUser(false)
                APP_ACTIVITY.navController.navigate(R.id.action_mainFragment_to_startFragment)
                dialogInterface.cancel()
            }
            .setNegativeButton(getString(R.string.dialog_negative)) { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .show()
    }
}
