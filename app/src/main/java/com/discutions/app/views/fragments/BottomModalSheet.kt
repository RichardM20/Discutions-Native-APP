package com.discutions.app.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.discutions.app.R
import com.discutions.app.controllers.HomeFragmentController
import com.discutions.app.interfaces.OnDataCommentsListener
import com.discutions.app.models.CommentsData
import com.discutions.app.utils.LoadingDialog
import com.discutions.app.views.adapters.BottomModalSheetAdapter
import com.discutions.app.views.adapters.HomeFragmentAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText

class FullScreenBottomSheetFragment : BottomSheetDialogFragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BottomModalSheetAdapter
    private lateinit var _fragmentController: HomeFragmentController;
    private lateinit var commentList: List<CommentsData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
        //obtenemos los datos
        commentList = arguments?.getSerializable("comments_data_list") as ArrayList<CommentsData>;

        recyclerView= view.findViewById(R.id.commentContentRecycleView);
        recyclerView.layoutManager= LinearLayoutManager(context);
        //pasamos los datos al adapter
        adapter = BottomModalSheetAdapter(commentList);
        _fragmentController= HomeFragmentController();

        recyclerView.adapter=adapter;

        commentButtonEvent(view);

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun commentButtonEvent(view: View){
        val commentField = view.findViewById<TextInputEditText>(R.id.commentField);
        view.findViewById<Button>(R.id.commentButton).setOnClickListener {
               _fragmentController.comment(commentField.text.toString());
        }
    }

}