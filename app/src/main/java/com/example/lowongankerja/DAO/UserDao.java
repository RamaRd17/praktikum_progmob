package com.example.lowongankerja.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.lowongankerja.Model.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("Select *from tb_user where email = :email and password = :password")
    User loginUser(String email, String password);

    @Query("select *from tb_user where id = :id")
    User getUser(int id);
}
