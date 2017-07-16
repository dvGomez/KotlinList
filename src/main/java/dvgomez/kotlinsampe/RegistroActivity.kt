package dvgomez.kotlinsampe

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_registro.*

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        if(v == btnCadastrar){
            var titulo : String = edtTitulo.text.toString()
            var descricao : String = edtDescricao.text.toString()
            if(titulo.isEmpty()) edtTitulo.error = "Informe um título válido"
            else if (descricao.isEmpty()) edtDescricao.error = "Informe uma descrição válida"
            else {
                var acMain : Intent = Intent(this, MainActivity::class.java)
                acMain.putExtra("titulo", titulo)
                acMain.putExtra("descricao", descricao)
                setResult(Activity.RESULT_OK, acMain)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnCadastrar.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var click : Int? = item?.itemId
        when(click) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
