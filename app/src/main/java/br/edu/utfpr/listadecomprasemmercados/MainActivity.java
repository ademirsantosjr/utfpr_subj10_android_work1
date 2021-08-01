package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeDoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeDoItem = findViewById(R.id.editTextNomeDoItem);
    }

    public void limparCampos(View view) {
        editTextNomeDoItem.setText(null);

        editTextNomeDoItem.requestFocus();

        Toast.makeText(this,
                R.string.todos_os_campos_foram_limpos,
                Toast.LENGTH_LONG).show();
    }
}