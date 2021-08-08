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
        String[] itemsNames = getResources().getStringArray(R.array.itemsnames);
        String[] itemsBrands = getResources().getStringArray(R.array.itemsbrands);
        String[] packingTypes = getResources().getStringArray(R.array.packingtypes);
        int[] amounts= getResources().getIntArray(R.array.amounts);
        String[] units= getResources().getStringArray(R.array.units);

        ArrayList<GroceryItem> groceryItems = new ArrayList<>();

        for(int i = 0; i < itemsNames.length; i++) {
            groceryItems.add(new GroceryItem(itemsNames[i],
                    itemsBrands[i],
                    packingTypes[i],
                    amounts[i],
                    units[i]));
        }

        ArrayAdapter<GroceryItem> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        groceryItems);

        listViewGroceryItems.setAdapter(adapter);
    }
}