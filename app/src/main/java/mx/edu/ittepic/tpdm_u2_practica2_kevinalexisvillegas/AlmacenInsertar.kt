package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class AlmacenInsertar : AppCompatActivity() {
    var campoProducto: EditText?=null
    var campoCantidad: EditText?=null
    var campoPrecio: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"practica2",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almacen_insertar)

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
        if(campoCantidad?.text!!.toString().isEmpty()||campoProducto?.text!!.isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun insertar(){
        try {
            var trans = Basedatos.writableDatabase
            var SQL = "INSERT INTO ALMACEN VALUES(NULL,'PRODUCTO','CANTIDAD','PRECIO')"
            if (validaCampos() == false) {
                mensaje("Error!", "Existe algun campo vacio (\"Descripción\" y/o \"Fecha de creación\")")
                return
            }

            SQL = SQL.replace("PRODUCTO", campoProducto?.text.toString())
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
}
