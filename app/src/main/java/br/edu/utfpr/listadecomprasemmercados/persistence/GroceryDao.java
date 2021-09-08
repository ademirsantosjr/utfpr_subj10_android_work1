package br.edu.utfpr.listadecomprasemmercados.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpr.listadecomprasemmercados.model.Grocery;

@Dao
public interface GroceryDao {

    @Insert
    long insert(Grocery grocery);

    @Delete
    void delete(Grocery grocery);

    @Update
    void update(Grocery grocery);

    @Query("SELECT * FROM grocery WHERE id = :id")
    Grocery queryForId(long id);

    @Query("SELECT * FROM grocery ORDER BY itemName ASC")
    List<Grocery> queryAll();

    @Query("SELECT * FROM grocery WHERE isBasicItem = :isBasicItem")
    List<Grocery> findBasicItemsOnly(boolean isBasicItem);
}
