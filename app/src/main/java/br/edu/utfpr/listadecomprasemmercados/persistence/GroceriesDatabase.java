package br.edu.utfpr.listadecomprasemmercados.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.edu.utfpr.listadecomprasemmercados.model.Grocery;

@Database(entities = {Grocery.class}, version = 1, exportSchema = false)
public abstract class GroceriesDatabase extends RoomDatabase {

    public abstract GroceryDao groceryDao();

    private static GroceriesDatabase instance;

    public static GroceriesDatabase getDatabase(final Context context) {

        if (instance == null) {

            synchronized (GroceriesDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            GroceriesDatabase.class,
                            "groceries.db").allowMainThreadQueries().build();
                }
            }

        }

        return instance;
    }
}
