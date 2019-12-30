package com.example.lowongankerja.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nama_perusahaan")
    private String name;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "is_admin")
    private int is_admin;

    public User(int id, String name, String password, String email, int is_admin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.is_admin = is_admin;
    }

    @Ignore
    public User(String name, String email, int is_admin) {
        this.name = name;
        this.email = email;
        this.is_admin = is_admin;
    }

    @Ignore
    public User(String name, String email, String password, int is_admin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.is_admin = is_admin;
    }

    @Ignore
    public User(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
