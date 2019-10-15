package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class DeCompraInsertar : AppCompatActivity() {
    var campoProducto: EditText?=null
    var campoCantidad: EditText?=null
    var campoPrecio: EditText?=null
    var botonInsertar: Button?=null
    var idlista : EditText ?= null
    var Basedatos = BaseDatos(this,"practica2",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_de_compra_insertar)
        campoProducto=findViewById(R.id.campoProdcuto)
        campoCantidad=findViewById(R.id.campoCantidad)
        campoPrecio=findViewById(R.id.campoPrecio)
        botonInsertar=findViewById(R.id.botonInsertar)

        botonInsertar?.setOnClickListener {
            insertar()
        }


    }
    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }

    fun limpiarCampos(){
        campoProducto?.setText("")
        campoCantidad?.setText("")
        campoPrecio?.setText("")
    }

    fun validaCampos(): Boolean{
        if(campoCantidad?.text!!.toString().isEmpty()||campoPrecio?.text!!.isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun insertar(){
        try {
            var trans = Basedatos.writableDatabase
            var SQL = "INSERT INTO DETALLECOMPRA VALUES(NULL,'CANTIDAD','PRECIO',IDALMACEN)"
            if (validaCampos() == false) {
                mensaje("Error!", "Existe algun campo vacio")
                return
            }
            SQL = SQL.replace("CANTIDAD", campoCantidad?.text.toString())
            SQL = SQL.replace("PRECIO", campoPrecio?.text.toString())

            trans.execSQL(SQL)
            trans.close()
            mensaje("Registro exitoso!", "Se inserto correctamente")
            limpiarCampos()
        }
        catch (er: SQLException) {
            mensaje("Error!","No se pudo insertar el registro, verifique sus datos!")
        }
    }
    fun mostrarTodas(){
        var sel = ""
        try {
            var trans = Basedatos.readableDatabase
            var con = "SELECT * FROM ALMACEN"
            var cur = trans.rawQuery(con,null)
            if(cur != null) {
                if (cur.moveToFirst() == true) {
                    do{
                        sel +="ID: ${cur.getString(0)}\nDescripción: ${cur.getString(1)}\nFecha de creación: ${cur.getString(2)}\nID Lista: ${cur.getString(3)}\n"+
                                "______________________________________________\n"
                    }while (cur.moveToNext())
                }else{
                    mensaje("Advertencia!","No existen tareas")
                }
            }
            cur.close()
        }
        catch (er: android.database.SQLException){
            mensaje("Error!","No se encuentran registros en la BD")
        }
    }
}
