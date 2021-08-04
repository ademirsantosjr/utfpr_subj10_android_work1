package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListView listViewGroceryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listViewGroceryItems = findViewById(R.id.listViewGroceryItems);

        listViewGroceryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryItem groceryItem =
                        (GroceryItem) listViewGroceryItems.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        getString(R.string.item_selecionado) + groceryItem.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        populateListOfItems();
    }

    private void populateListOfItems() {
        String[] itemsnames = getResources().getStringArray(R.array.itemsnames);
        String[] itemsbrands = getResources().getStringArray(R.array.itemsbrands);
        String[] packingtypes = getResources().getStringArray(R.array.packingtypes);
        int[] amounts= getResources().getIntArray(R.array.amounts);
        String[] units= getResources().getStringArray(R.array.units);

        ArrayList<GroceryItem> groceryItemsitems = new ArrayList<>();

        for(int i = 0; i < itemsnames.length; i++) {
            groceryItemsitems.add(new GroceryItem(itemsnames[i],
                    itemsbrands[i],
                    packingtypes[i],
                    amounts[i],
                    units[i]));
        }

        ArrayAdapter<GroceryItem> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        groceryItemsitems);

        listViewGroceryItems.setAdapter(adapter);
    }
}