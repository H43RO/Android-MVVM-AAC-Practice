package com.haero_kim.aac_practice.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Entity 정의 및 SQLite 버전 지정
@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    // 데이터 베이스 인스턴스를 싱글톤으로 사용하기 위해 Companion Object 사용
    companion object {
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if(INSTANCE == null) {
                // 여러 Thread 가 접근하지 못하도록 Synchronized 로 설정
                synchronized(ContactDatabase::class){
                    // Room 인스턴스 생성
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    ContactDatabase::class.java, "contact")
                        // 데이터 베이스가 갱신될 때 기존의 테이블을 버리고 새로 사용하도록 설정
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            // 만들어지는 DB 인스턴스는 Repository 에서 호출되어 사용
            return INSTANCE
        }
    }
}