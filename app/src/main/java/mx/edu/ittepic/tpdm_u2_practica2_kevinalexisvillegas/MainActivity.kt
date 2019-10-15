package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.content.Intent
import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var btnInserta: Button?=null
    var btnEditar: Button?=null
    var btnEliminar: Button?=null
    var btnActualizar: Button?=null
    var btnDeCompra: Button?=null
    var btnCompra: Button?=null
    var btnCliente: Button?=null
    var btnEmpresa: Button?=null
    var mostrarTodasLi : TextView?= null
    var Basedatos = BaseDatos(this,"practica2",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInserta=findViewById(R.id.insertar)
        btnEditar=findViewById(R.id.editar)
        btnActualizar=findViewById(R.id.actualizar)
        btnEliminar=findViewById(R.id.eliminar)
        mostrarTodasLi=findViewById(R.id.mostrarTodasTa)
        mostrar()

        btnInserta?.setOnClickListener {
            val ventanaTarea = Intent(this,AlmacenInsertar::class.java)
            startActivity(ventanaTarea)

        }
        btnEditar?.setOnClickListener {
            val ventanaTarea = Intent(this,AlmacenEditar::class.java)
            startActivity(ventanaTarea)
        }

        btnEliminar?.setOnClickListener {

        }
        btnActualizar?.setOnClickListener {
            mostrar()

        }
    }

    fun mostrar(){
        var sel = ""
        try {
            var transicion = Basedatos.readableDatabase
            var con = "SELECT * FROM ALMACEN"
            var cur = transicion.rawQuery(con,null)
            if(cur != null) {
                if (cur.moveToFirst() == true) {
                    do{
                        sel +="ID: ${cur.getString(0)}\nProducto: ${cur.getString(1)}\nCantidad: ${cur.getString(2)}\nPrecio:${cur.getString(3)}\n "+
                                "______________________________________________\n"
                    }while (cur.moveToNext())
                    mostrarTodasLi?.setText(sel)
                }else{
                    mensaje("Advertencia!","No existen listas")
                }
            }
            cur.close()
        }
        catch (er: SQLException){
            mensaje("Error!","No se encuentran registros en la BD")
        }
    }
    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }
}






