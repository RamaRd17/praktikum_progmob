package com.example.lowongankerja.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.lowongankerja.DAO.UserDao;
import com.example.lowongankerja.Model.User;

@Database(entities = {User.class}, version =3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "widana").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
