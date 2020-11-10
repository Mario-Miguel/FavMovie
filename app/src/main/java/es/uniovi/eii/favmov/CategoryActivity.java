package es.uniovi.eii.favmov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import es.uniovi.eii.favmov.model.Categoria;

public class CategoryActivity extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnOk;
    private TextView txTitulo;
    private EditText txNombre;
    private EditText txDescripcion;
    private int posCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        txTitulo = findViewById(R.id.txCategoriaTitulo);
        txNombre = findViewById(R.id.txCategoriaEditName);
        txDescripcion = findViewById(R.id.txCategoriaEditDescripcion);

        btnCancelar=findViewById(R.id.btCategoriaCancel);
        btnCancelar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                backToLastActivity();
            }
        });

        btnOk = findViewById(R.id.btCategoriaOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarCambios();

            }
        });

        Intent intent = getIntent();
        posCategoria = intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA, 0);
        Categoria categoria=null;

        if(posCategoria>0)
            categoria = intent.getParcelableExtra(MainActivity.CATEGORIA_SELECCIONADA);


        if(posCategoria==0){
            txTitulo.setText(R.string.CategoriaTituloCreacion);
        }else {
            txTitulo.setText(R.string.CategoriaTituloModificacion);
            txNombre.setText(categoria.getNombre());
            txDescripcion.setText(categoria.getDescripcion());
            txNombre.setEnabled(false);
        }



    }

    private void backToLastActivity(){
        setResult(RESULT_CANCELED);
        finish();
    }

    private void guardarCambios(){
        Categoria categoriaSalida = new Categoria(txNombre.getText().toString(), txDescripcion.getText().toString());
        Intent intentSalida = new Intent();
        intentSalida.putExtra(MainActivity.CATEGORIA_MODIFICADA, categoriaSalida);
        setResult(RESULT_OK, intentSalida);
        finish();
    }

}