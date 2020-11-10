package es.uniovi.eii.favmov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmov.model.Categoria;
import es.uniovi.eii.favmov.model.Pelicula;

public class MainActivity extends AppCompatActivity {

    public static final String POS_CATEGORIA_SELECCIONADA = "pos_categoria_seleccionada";
    public static final String CATEGORIA_SELECCIONADA = "categoria_seleccionada";
    public static final String CATEGORIA_MODIFICADA = "categoria_modificada";

    //Identificador activity
    private static final int GESTION_CATEGORIA = 1;

    private Snackbar msgCreaCategoria;
    private List<Categoria> listaCategorias;
    private Button btnGuardar;
    private ImageButton btnEditCategoria;
    private Spinner spnCategoria;
    private TextView txtTitulo;
    private TextView txtSinapsis;
    private TextView txtDuracion;
    private TextView txtFecha;

    private boolean creandoCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarSpinner();

        btnGuardar = findViewById(R.id.btn_guardar);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (validarCampos()){

                    Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_guardado, Snackbar.LENGTH_LONG).show();
                    saveMovie();
                }
            }
        });


        btnEditCategoria = findViewById(R.id.btn_categoria);
        //Para mover el botón en pantalla
        btnEditCategoria.setVisibility(View.GONE);

        btnEditCategoria.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                spnCategoria = findViewById(R.id.spn_categoria);
                if(spnCategoria.getSelectedItemPosition()==0){
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_crear_categoria, Snackbar.LENGTH_LONG);
                }else{
                    msgCreaCategoria = Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_modif_categoria, Snackbar.LENGTH_LONG);
                }

               /* msgCreaCategoria.setAction(android.R.string.cancel, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_accion_cancelada, Snackbar.LENGTH_LONG).show();
                    }
                });
                msgCreaCategoria.show();*/


                msgCreaCategoria.setAction(android.R.string.ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_accion_aceptada, Snackbar.LENGTH_LONG).show();
                        modificarCategoria();
                    }
                });
                msgCreaCategoria.show();

            }
        });

        Intent intent = getIntent();
        Pelicula pelicula = intent.getParcelableExtra(MainRecycler.PELICULA_SELECCIONADA);
        if (pelicula !=null)
            abrirPeliculaEnConsulta(pelicula);

    }
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(getApplicationContext(), getString(R.string.onStart), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(), getString(R.string.onRestart), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(getApplicationContext(), getString(R.string.onPause), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(getApplicationContext(), getString(R.string.onStop), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getApplicationContext(), getString(R.string.onDestroy), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), getString(R.string.onResume), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GESTION_CATEGORIA) {
            if(resultCode == RESULT_OK){
                Categoria catAux = data.getParcelableExtra(CATEGORIA_MODIFICADA);

                if(creandoCategoria){
                    listaCategorias.add(catAux);
                    introListaSpinner(spnCategoria, listaCategorias);
                }
                else {
                   for(Categoria elemento : listaCategorias){
                       if(elemento.getNombre().equals(catAux.getNombre())){
                           elemento.setDescripcion(catAux.getDescripcion());
                       }
                   }
                }
            }
        }
    }

    private boolean validarCampos(){
        txtTitulo = findViewById(R.id.tx_editTitulo);
        txtSinapsis = findViewById(R.id.tx_editArgumento);
        txtDuracion = findViewById(R.id.tx_editDuracion);
        txtFecha = findViewById(R.id.tx_editFecha);
        spnCategoria = findViewById(R.id.spn_categoria);

        boolean hasErrors = false;

        if(txtTitulo.getText().toString().isEmpty()){
            txtTitulo.setError(getString(R.string.msg_error_titulo));
            txtTitulo.requestFocus();
            return false;
            //Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_error_titulo, Snackbar.LENGTH_LONG).show();
        }
        if(txtSinapsis.getText().toString().isEmpty()){
            txtSinapsis.setError(getString(R.string.msg_error_sinapsis));
            txtSinapsis.requestFocus();
            return false;
            //Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_error_sinapsis, Snackbar.LENGTH_LONG).show();
        }
        if(txtDuracion.getText().toString().isEmpty()){
            txtDuracion.setError(getString(R.string.msg_error_duracion));
            txtDuracion.requestFocus();
            return false;
            //Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_error_duracion, Snackbar.LENGTH_LONG).show();
        }
        if(txtFecha.getText().toString().isEmpty()){
            txtFecha.setError(getString(R.string.msg_error_fecha));
            txtFecha.requestFocus();
            return false;
            //Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_error_fecha, Snackbar.LENGTH_LONG).show();
        }

        if(spnCategoria.getSelectedItemPosition()==0){
            TextView spnErrorTxt = (TextView) spnCategoria.getSelectedView();
            spnErrorTxt.setError(getString(R.string.msg_error_categoria));
            spnErrorTxt.requestFocus();
            return false;
            //Snackbar.make(findViewById(R.id.mainLayout), R.string.msg_error_categoria, Snackbar.LENGTH_LONG).show();
        }
        return true;
    }

    private void modificarCategoria(){
        Intent categoryIntent = new Intent(MainActivity.this, CategoryActivity.class);
        categoryIntent.putExtra(POS_CATEGORIA_SELECCIONADA, spnCategoria.getSelectedItemPosition());
        creandoCategoria=true;
        if(spnCategoria.getSelectedItemPosition()>0){
            creandoCategoria=false;
            categoryIntent.putExtra(CATEGORIA_SELECCIONADA, listaCategorias.get(spnCategoria.getSelectedItemPosition()-1));
        }
        Log.e("Posicion Spinner", spnCategoria.getSelectedItemPosition()+"");
        //Main activity espera resultado de la siguiente actividad
        startActivityForResult(categoryIntent, GESTION_CATEGORIA);
    }

    private void inicializarSpinner(){
        spnCategoria = findViewById(R.id.spn_categoria);
        listaCategorias = new ArrayList<Categoria>();
        listaCategorias.add(new Categoria("Acción", "Peliculas de acción"));
        listaCategorias.add(new Categoria("Comedia", "Peliculas de comedia"));

        introListaSpinner(spnCategoria, listaCategorias);
    }

    private void introListaSpinner(Spinner spinner, List<Categoria> listaCategorias){
        ArrayList<String> nombres = new ArrayList<>();
        nombres.add("Sin definir");
        for (Categoria elemento: listaCategorias){
            nombres.add(elemento.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void abrirPeliculaEnConsulta(Pelicula pelicula){
        txtTitulo = findViewById(R.id.tx_editTitulo);
        txtSinapsis = findViewById(R.id.tx_editArgumento);
        txtDuracion = findViewById(R.id.tx_editDuracion);
        txtFecha = findViewById(R.id.tx_editFecha);
        btnGuardar = findViewById(R.id.btn_guardar);
        spnCategoria = findViewById(R.id.spn_categoria);

        btnGuardar.setVisibility(View.GONE);

        txtTitulo.setText(pelicula.getTitulo());
        txtTitulo.setEnabled(false);

        txtSinapsis.setText(pelicula.getArgumento());
        txtSinapsis.setEnabled(false);

        txtDuracion.setText(pelicula.getDuracion());
        txtDuracion.setEnabled(false);

        txtFecha.setText(pelicula.getFecha());
        txtFecha.setEnabled(false);

        ArrayList<String> nombres = new ArrayList<>();
        nombres.add(pelicula.getCategoria().getNombre());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombres);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategoria.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spnCategoria.setEnabled(false);

    }

    private void saveMovie(){
        Categoria categoriaPelicula = listaCategorias.get(spnCategoria.getSelectedItemPosition()-1);
        Pelicula peliculaCreada = new Pelicula(txtTitulo.getText().toString(), txtSinapsis.getText().toString(), categoriaPelicula, txtDuracion.getText().toString(), txtFecha.getText().toString());
        Intent saveIntent = new Intent(MainActivity.this, MainRecycler.class);
        saveIntent.putExtra(MainRecycler.PELICULA_CREADA, peliculaCreada);
        setResult(RESULT_OK, saveIntent);
        finish();
    }

}