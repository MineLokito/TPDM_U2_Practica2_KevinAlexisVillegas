package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(context: Context,
                name: String?,
                factory: SQLiteDatabase.CursorFactory?,
                version: Int): SQLiteOpenHelper(context,name,factory,version){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE ALMACEN(IDALMACEN INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCTO VARCHAR(400), CANTIDAD INTEGER, PRECIO INTEGER )")
        p0?.execSQL("CREATE TABLE DETALLECOMPRA(IDDETALLE INTEGER PRIMARY KEY AUTOINCREMENT, CANTIDAD INTEGER, PRECIO FLOAT, IDALMACEN INTEGER, FOREIGN KEY (IDALMACEN) REFERENCES ALMACEN(IDALMACEN))")
        p0?.execSQL("CREATE TABLE EMPRESA(IDEMPRESA INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE VARCHAR(400), DOMICILIO VARCHAR(400), DESCRIPCION VARCHAR(200) )")
        p0?.execSQL("CREATE TABLE CLIENTE(IDCLIENTE INTEGER PRIMARY KEY AUTOINCREMENT, NOTELEFONO VARCHAR(30), NOMBRE VARCHAR(200), IDEMPRESA INTEGER, FOREIGN KEY (IDEMPRESA) REFERENCES EMPRESA(IDEMPRESA))")
        p0?.execSQL("CREATE TABLE COMPRA(IDCOMPRA INTEGER PRIMARY KEY AUTOINCREMENT, FECHA VARCHAR(200), TOTAL FLOAT, IDCLIENTE INTEGER, FOREIGN KEY (IDCLIENTE) REFERENCES CLIENTE(IDCLIENTE))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}