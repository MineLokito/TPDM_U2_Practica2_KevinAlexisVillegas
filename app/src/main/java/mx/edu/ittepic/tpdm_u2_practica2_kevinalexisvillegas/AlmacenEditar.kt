package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AlmacenEditar : AppCompatActivity() {
    var campoProducto: EditText?=null
    var campoCantidad: EditText?=null
    var campoPrecio: EditText?=null
    var botonInsertar: Button?=null
    var Basedatos = BaseDatos(this,"practica2",null,1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_almacen_editar)
        campoProducto=findViewById(R.id.campoProdcuto)
        campoCantidad=findViewById(R.id.campoCantidad)
        campoPrecio=findViewById(R.id.campoPrecio)
        botonInsertar=findViewById(R.id.botonInsertar)
        pedirID()

        botonInsertar?.setOnClickListener {
            actualiza()

        }
    }
    fun pedirID() {
        var campo = EditText(this)
        campo.inputType = InputType.TYPE_CLASS_NUMBER

        fun validar(text: EditText): Boolean {

            if (campo.text.toString().isEmpty()) {
                return false
            } else {
                return true
            }
        }
        AlertDialog.Builder(this).setTitle("ALERTA")
            .setMessage("ESCRIBA EL ID A   ").setView(campo)
            .setPositiveButton("OK") { dialog, which ->
                if (validar(campo) == false) {
                    Toast.makeText(this, "ERROR: CAMPO ID VACIO", Toast.LENGTH_LONG)
                        .show()
                    return@setPositiveButton
                }
                buscar(campo.text.toString())

            }
            .setNeutralButton("CANCELAR") { dialog, which -> }
            .show()
    }
    fun buscar(id: String) {
        try {
            var transaccion = Basedatos.readableDatabase
            var SQL = "SELECT * FROM ALMACEN WHERE IDALMACEN="+id
            var respuesta = transaccion.rawQuery(SQL, null)
            if (respuesta.moveToFirst() == true) {
                var cadena = "PRODUCTO: " + respuesta.getString(1) +
                        "\nCANTIDAD: " + respuesta.getString(2)+
                        "\nPRECIO: " + respuesta.getString(3)
            }


            transaccion.close()
            campoProducto?.setText(respuesta.getString(1))
            campoCantidad?.setText(respuesta.getString(2))
            campoPrecio?.setText(respuesta.getString(3))
            botonInsertar?.setText("Aplicar Cambios")
        }catch (err:SQLiteException){
            mensaje("ERROR", "NO SE PUEDE EJECUTAR EL SELECT")

        }

    }
    fun mensaje(a: String, b: String){
        AlertDialog.Builder(this)
            .setTitle(a)
            .setMessage(b)
            .setPositiveButton("OK")
            { dialogInterface, i ->}.show()
    }

    fun actualiza(){
        try {
            var transaccion = Basedatos.writableDatabase
            var SQL = "UPDATE ALMACEN SET PRODUCTO='CAMPOPRODUCTO', CANTIDAD='CAMPOCANTIDAD', PRECIO='CAMPOPRECIO' WHERE IDALMACEN=IDALMACEN "

            if (validarCampos() == false) {
                mensaje("ERROR", "AL PARECER HAY UN CAMPO DE TEXTO VACIO")

            } else {

                SQL = SQL.replace("CAMPOPRODUCTO", campoProducto?.text.toString())
                SQL = SQL.replace("CAMPOCANTIDAD", campoCantidad?.text.toString())
                SQL = SQL.replace("CAMPOPRECIO", campoPrecio?.text.toString())


                transaccion.execSQL(SQL)
                transaccion.close()//CIERRA LA TRANSACCION
                limpiar()
                mensaje("EXITO", "SE INSERTO CORRECTAMENTE")

            }
        } catch (err: SQLiteException) {
            mensaje("Error", "NO SE PUDO INSERTAR, TAL VEZ ID YA EXISTE")
        }

    }
    fun validarCampos(): Boolean {
        if (campoPrecio?.text.toString().isEmpty() || campoProducto?.text.toString().isEmpty() || campoCantidad?.text.toString().isEmpty()) {
            return false
        } else {
            return true
        }
    }
    fun limpiar() {
        campoPrecio?.setText("")
        campoProducto?.setText("")
        campoCantidad?.setText("")
    }
}
