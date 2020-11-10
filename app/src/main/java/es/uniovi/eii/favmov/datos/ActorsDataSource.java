package es.uniovi.eii.favmov.datos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.eii.favmov.model.Actor;
import es.uniovi.eii.favmov.model.Pelicula;

public class ActorsDataSource {

    /**
     * Referencia para manejar la base de datos. Este objeto lo obtenemos a partir de MyDBHelper
     * y nos proporciona metodos para hacer operaciones
     * CRUD (create, read, update and delete)
     */
    private SQLiteDatabase database;
    /**
     * Referencia al helper que se encarga de crear y actualizar la base de datos.
     */
    private DBHelper dbHelper;
    /**
     * Columnas de la tabla
     */
    private final String[] allColumns = {DBHelper.COLUMNA_ID_REPARTO, DBHelper.COLUMNA_NOMBRE_ACTOR,
            DBHelper.COLUMNA_IMAGEN_ACTOR, DBHelper.COLUMNA_URL_imdb};

    /**
     * Constructor.
     *
     * @param context
     */
    public ActorsDataSource(Context context) {
        //el último parámetro es la versión
        dbHelper = new DBHelper(context, null, null, 1);
    }

    /**
     * Abre una conexion para escritura con la base de datos.
     * Esto lo hace a traves del helper con la llamada a getWritableDatabase. Si la base de
     * datos no esta creada, el helper se encargara de llamar a onCreate
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    /**
     * Cierra la conexion con la base de datos
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Recibe la película y crea el registro en la base de datos.
     * @param actorToInsert
     * @return
     */
    public long createActor(Actor actorToInsert) {
        // Establecemos los valores que se insertaran
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMNA_ID_REPARTO, actorToInsert.getId());
        values.put(DBHelper.COLUMNA_NOMBRE_ACTOR, actorToInsert.getNombre());
        values.put(DBHelper.COLUMNA_IMAGEN_ACTOR, actorToInsert.getUrlImagenActor());
        values.put(DBHelper.COLUMNA_URL_imdb, actorToInsert.getUrlIMDB());


        // Insertamos la valoracion
        long insertId =
                database.insert(DBHelper.TABLA_REPARTO, null, values);

        return insertId;
    }

    /**
     * Obtiene todas las valoraciones andadidas por los usuarios.
     *
     * @return Lista de objetos de tipo Pelicula
     */
    public List<Actor> getAllValorations() {
        // Lista que almacenara el resultado
        List<Actor> actorList = new ArrayList<Actor>();
        //hacemos una query porque queremos devolver un cursor

        Cursor cursor = database.query(DBHelper.TABLA_REPARTO, allColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            final Actor actor = new Actor();
            actor.setId(cursor.getInt(0));
            actor.setNombre(cursor.getString(1));
            actor.setUrlImagenActor("https://image.tmdb.org/t/p/original/"+cursor.getString(2));
            actor.setUrlIMDB("https://www.imdb.com/name/" + cursor.getString(3));


            actorList.add(actor);
            cursor.moveToNext();
        }

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.

        return actorList;
    }


    /**
     * Obtiene todas las valoraciones andadidas por los usuarios con el filtro introducido en el SQL.
     *
     * @return Lista de objetos de tipo Pelicula
     */
    public List<Actor> actoresParticipantes(int idPelicula) {
        // Lista que almacenara el resultado
        List<Actor> actorList = new ArrayList<Actor>();
        Actor aux;
        //hacemos una query porque queremos devolver un cursor

        Cursor cursor = database.rawQuery("SELECT " +
                DBHelper.TABLA_REPARTO + "." + DBHelper.COLUMNA_NOMBRE_ACTOR + ", " +
                DBHelper.TABLA_PELICULAS_REPARTO + "." + DBHelper.COLUMNA_PERSONAJE + ", " +
                DBHelper.TABLA_REPARTO + "." + DBHelper.COLUMNA_IMAGEN_ACTOR + ", " +
                DBHelper.TABLA_REPARTO + "." + DBHelper.COLUMNA_URL_imdb +
                " FROM " + DBHelper.TABLA_PELICULAS_REPARTO +
                " JOIN " + DBHelper.TABLA_REPARTO + " ON " +
                DBHelper.TABLA_PELICULAS_REPARTO + "." + DBHelper.COLUMNA_ID_REPARTO +
                " = " + DBHelper.TABLA_REPARTO + "." + DBHelper.COLUMNA_ID_REPARTO +
                " WHERE " + DBHelper.TABLA_PELICULAS_REPARTO + "." + DBHelper.COLUMNA_ID_PELICULAS + " = " + idPelicula, null);

//        Cursor cursor = database.rawQuery("Select reparto.*, pr. " +DBHelper.COLUMNA_PERSONAJE +
//                " FROM " + DBHelper.TABLA_REPARTO + " reparto, " + DBHelper.TABLA_PELICULAS_REPARTO + " pr" +
//                " WHERE " + DBHelper.TABLA_REPARTO + "." + DBHelper.COLUMNA_ID_REPARTO + " = " + DBHelper.TABLA_PELICULAS_REPARTO + "." + DBHelper.COLUMNA_ID_REPARTO
//                + " and " + DBHelper.TABLA_PELICULAS_REPARTO + "." + DBHelper.COLUMNA_ID_PELICULAS + " = " +idPelicula  , null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println("------------------------------------------- ");

            aux = new Actor(cursor.getString(0),
                    cursor.getString(1),
                    //Añadimos el encabezado de las páginas web
                    "https://image.tmdb.org/t/p/original/" + cursor.getString(2),
                    "https://www.imdb.com/name/" + cursor.getString(3));
            System.out.println(aux);
            actorList.add(aux);
            cursor.moveToNext();
        }
        cursor.close();

        cursor.close();
        // Una vez obtenidos todos los datos y cerrado el cursor, devolvemos la
        // lista.

        return actorList;
    }


}

