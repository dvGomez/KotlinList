package dvgomez.kotlinsampe

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import dvgomez.kotlinsampe.adapters.Kadapter
import dvgomez.kotlinsampe.listeners.OnTarefaItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnTarefaItemClickListener {

    var listHelp : MutableList<Tarefa> = mutableListOf()
    var mainList : MutableList<Tarefa> = mutableListOf()
    var mainAdapter : Kadapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tarefa1 : Tarefa = Tarefa("Primeira Tarefa")

        mainList.add(tarefa1)

        rcList.layoutManager = LinearLayoutManager(this)

        mainAdapter = Kadapter(this, mainList, this)

        rcList.adapter = mainAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        var itemSelecionado = item?.itemId

        if(itemSelecionado == R.id.nav_add){
            var acRegistro : Intent = Intent(this, RegistroActivity::class.java)
            startActivityForResult(acRegistro, 99)
        } else if (itemSelecionado == R.id.nav_reorder){

            val items : Array<String> = arrayOf("Todos", "Pendentes", "Finalizados") // Itens para serem mostrados no dialogo de Filtro

            var reDialog : AlertDialog = AlertDialog.Builder(this)
                    .setTitle("Filtrar Resultados")
                    .setItems(items, {
                        _, which -> when(which){
                        0 ->  mostrarTodasTarefas()
                        1 -> mostrarTarefasPendentes()
                        2 -> mostrarTarefasFinalizadas()
                    }
                    }).create()
            reDialog.show()


        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 99 && resultCode == Activity.RESULT_OK && data?.extras != null){
            val tarefa : Tarefa = Tarefa(data.getStringExtra("titulo"), data.getStringExtra("descricao"))
            mostrarTodasTarefas()
            mainList.add(tarefa)
            mainAdapter?.notifyDataSetChanged()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onTarefaItemClickListener(position : Int) {

        var tarefa : Tarefa = mainList[position]

        var infoDialog : AlertDialog = AlertDialog.Builder(this)
                .setTitle(tarefa.title)
                .setMessage(tarefa.content)
                .setPositiveButton("Finalizar", ( {_, _ ->
                        finalizarTarefa(tarefa)
                        Toast.makeText(applicationContext, "Tarefa Finalizada", Toast.LENGTH_SHORT).show()
                }))
                .setNegativeButton("Excluir", ({_, _ ->
                    var confirmDialog : AlertDialog = AlertDialog.Builder(this)
                            .setTitle("Excluir tarefa")
                            .setMessage("Deseja realmente excluir a terafa:\n ${tarefa.title}?")
                            .setPositiveButton("Sim", ({_, _ ->
                                mainList.remove(tarefa)
                                Toast.makeText(applicationContext, "Tarefa Excluida", Toast.LENGTH_SHORT).show()
                                mainAdapter?.notifyDataSetChanged()
                            }))
                            .setNegativeButton("Não", null)
                            .create()
                    confirmDialog.show()
                }))
                .create()
        infoDialog.show()

    }

    fun mostrarTodasTarefas(){
        if(listHelp.isNotEmpty()){
            mainList.clear()
            mainList.addAll(listHelp)
            listHelp.clear()
            mainAdapter?.notifyDataSetChanged()
        }
    }

    fun mostrarTarefasPendentes(){
        mostrarTodasTarefas()
        listHelp.addAll(mainList)
        mainList
                .filter { it.finalizada }
                .forEach { mainList.remove(it) }
        if(mainList.isEmpty()){
            Toast.makeText(this, "Nenhuma tarefa encontrada", Toast.LENGTH_SHORT).show()
            mainList.addAll(listHelp)
            mainAdapter?.notifyDataSetChanged()
        } else {
            mainAdapter?.notifyDataSetChanged()
        }
    }

    fun mostrarTarefasFinalizadas(){
        mostrarTodasTarefas()
        listHelp.addAll(mainList)
        mainList
                .filterNot { it.finalizada }
                .forEach { mainList.remove(it) }
        if(mainList.isEmpty()){
            Toast.makeText(this, "Nenhuma tarefa encontrada", Toast.LENGTH_SHORT).show()
            mainList.addAll(listHelp)
            mainAdapter?.notifyDataSetChanged()
        } else {
            mainAdapter?.notifyDataSetChanged()
        }
    }

    fun finalizarTarefa(tarefa: Tarefa){
        tarefa.finalizada = true
    }

    fun excluirTarefa(tarefa : Tarefa){
        mainList.remove(tarefa)
        if(listHelp.contains(tarefa)) listHelp.remove(tarefa)
    }

}
