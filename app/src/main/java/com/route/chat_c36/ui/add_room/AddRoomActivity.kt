package com.route.chat_c36.ui.add_room

import android.app.ProgressDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.route.chat_c36.R
import com.route.chat_c36.databinding.ActivityAddRoomBinding
import com.route.chat_c36.model.Category
import com.route.chat_c36.ui.register_activity.BaseActivity

class AddRoomActivity : BaseActivity<AddRoomViewModel, ActivityAddRoomBinding>(), Navigator {
    var adapter: SpinnerAdapter = SpinnerAdapter(Category.categories)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewDataBinding.spinner.adapter = adapter
        viewModel.navigator = this
        progressDialog = ProgressDialog(this)
        subscribeToLiveData()
        viewDataBinding.spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, index: Int, id: Long) {
                viewModel.selectedCategoryId = Category.categories.get(index).id
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
    fun subscribeToLiveData(){
        viewModel.isLoadingLiveData.observe(this) {
            if(it){
                progressDialog = ProgressDialog.show(this, "Loading","")
            }else {
                progressDialog.dismiss()
            }
        }
        viewModel.messageLiveData.observe(this){
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Error")
                .setMessage(it)
                .setPositiveButton("Ok",object : DialogInterface.OnClickListener{
                    override fun onClick(p0: DialogInterface?, p1: Int) {
                        p0!!.dismiss()
                    }
                }).show()
        }
    }
    override fun getLayOutFile(): Int {
        return R.layout.activity_add_room
    }

    override fun initViewModel(): AddRoomViewModel {
        return ViewModelProvider(this).get(AddRoomViewModel::class.java)
    }

    override fun finishAddRoom() {
        finish()
    }

}