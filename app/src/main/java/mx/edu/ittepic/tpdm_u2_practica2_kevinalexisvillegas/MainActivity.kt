package mx.edu.ittepic.tpdm_u2_practica2_kevinalexisvillegas

import android.content.Intent
import android.database.SQLException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
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
        btnDeCompra=findViewById(R.id.DeCompra)
        btnCompra=findViewById(R.id.Compra)
        btnCliente=findViewById(R.id.Cliente)
        btnEmpresa=findViewById(R.id.Empresa)
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
            pedirID(btnEliminar?.text.toString())

        }
        btnActualizar?.setOnClickListener {
            mostrar()

        }
        btnDeCompra?.setOnClickListener {
            val ventanaTarea = Intent(this,Main2Activity::class.java)
            startActivity(ventanaTarea)
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
    fun pedirID(etiqueta:String){
        var elemento = EditText(this)
        elemento.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this).setTitle("Atención!").setMessage("Escriba el ID en ${etiqueta}: ").setView(elemento)
            .setPositiveButton("OK"){dialog,which ->
                if(validarCampo(elemento) == false){
                    Toast.makeText(this, "Error! campo vacío", Toast.LENGTH_LONG).show()
                    return@setPositiveButton
                }
                buscar(elemento.text.toString(),etiqueta)

            }.setNeutralButton("Cancelar"){dialog, which ->  }.show()
    }
    fun validarCampo(elemento: EditText): Boolean{
        if(elemento.text.toString().isEmpty()){
            return false
        }else{
            return true
        }
    }

    fun buscar(id: String, btnEtiqueta: String){
        try {
            var trans = Basedatos.readableDatabase
            var con="SELECT * FROM ALMACEN WHERE IDALMACEN="+id
            var  cur = trans.rawQuery(con,null)

            if (cur.moveToFirst()==true){
                if (btnEtiqueta.startsWith("e")){
                    var sel = "¿Seguro de eliminar la tarea: \"${cur.getString(1)}\" con el ID \"${cur.getString(0)}\" ?"
                    var alerta = AlertDialog.Builder(this)
                    alerta.setTitle("Atención").setMessage(sel).setNeutralButton("NO"){dialog,which->
                        return@setNeutralButton
                    }.setPositiveButton("si"){dialog,which->
                        eliminar(id)

                    }.show()
                }
            }else{
                mensaje("Error!","No existe el id: ${id}")
            }
        }catch (err: java.sql.SQLException){
            mensaje("Error!","No se encontro el registro")
        }
    }

    fun eliminar(id:String){
        try{
            var trans = Basedatos.writableDatabase
            var SQL = "DELETE FROM ALMACEN WHERE IDALMACEN="+id
            trans.execSQL(SQL)
            trans.close()
            mensaje("Exito!", "Se elimino correctamente el id: ${id}")

        }catch (err: java.sql.SQLException){
            mensaje("Error!", "No se pudo eliminar")

        }
    }
}







