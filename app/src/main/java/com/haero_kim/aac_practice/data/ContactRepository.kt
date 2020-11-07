package com.haero_kim.aac_practice.data

import android.app.Application
import androidx.lifecycle.LiveData
import java.lang.Exception

class ContactRepository(application: Application) {

    private val contactDatabase = ContactDatabase.getInstance(application)!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    // Room DB 를 메인 쓰레드에서 접근하게되면 크래쉬 발생
    // - 따라서 별도의 Thread 를 생성하여 접근해야 함

    fun insert(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.insert(contact)
            })
            thread.start()
        } catch (e: Exception) {
        }
    }

    fun delete(contact: Contact) {
        try {
            val thread = Thread(Runnable {
                contactDao.delete(contact)
            })
            thread.start()
        } catch (e: Exception) {

        }
    }
}