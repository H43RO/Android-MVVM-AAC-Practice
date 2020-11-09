package com.haero_kim.aac_practice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haero_kim.aac_practice.adapter.ContactAdapter
import com.haero_kim.aac_practice.data.Contact
import com.haero_kim.aac_practice.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView = findViewById<RecyclerView>(R.id.main_recyclerview)

        // Set contactItemClick & contactItemLongClick Lambda
        val adapter = ContactAdapter({
            // Put Extras of contact info & start AddActivity
            val intent = Intent(this, AddActivity::class.java)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NAME, contact.name)
            intent.putExtra(AddActivity.EXTRA_CONTACT_NUMBER, contact.number)
            intent.putExtra(AddActivity.EXTRA_CONTACT_ID, contact.id)
            startActivity(intent)
        }, {
            deleteDialog(it)
        })

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        @Suppress("DEPRECATION")
        // ViewModel 객체는 직접적으로 초기화하는 것이 아닌, 안드로이드 시스템을 통해 생성
        // ViewModelProviders 는 이미 생성된 ViewModel 인스턴스가 있다면 이를 반환하여
        // 메모리 낭비를 줄여주는 효과가 있음
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        // Observer 통해서 ViewModel 이 어느 Activity/Fragment 의 생명주기를 관찰할지 결정
        // MainActivity 에 연결했으므로, 이 Activity 가 파괴되면 ViewModel 도 파괴될 것

        // Observer 내부 구현을 보면 onChanged() 메소드가 있는데, 이를 통해서
        // LiveData 가 변하면 무엇을 할 것인지 액션을 지정할 수 있음
        contactViewModel.getAll().observe(this, Observer<List<Contact>> {
            // Update UI
        })
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}