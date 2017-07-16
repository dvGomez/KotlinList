package dvgomez.kotlinsampe.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dvgomez.kotlinsampe.R
import dvgomez.kotlinsampe.Tarefa
import dvgomez.kotlinsampe.listeners.OnTarefaItemClickListener
import kotlinx.android.synthetic.main.list_layout.view.*

/**
 * Created by Lucas on 15/07/2017.
 */

class Kadapter constructor(context : Context, list: MutableList<Tarefa>, listener : OnTarefaItemClickListener) : RecyclerView.Adapter<Kadapter.ViewHolder>() {

    private var itemListener : OnTarefaItemClickListener = listener
    private var kList : MutableList<Tarefa> = list
    private val kContext : Context = context

    override fun getItemCount(): Int = kList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(kContext).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val tarefa : Tarefa = kList[position]
        holder?.bind(tarefa)
        holder?.view?.setOnClickListener({
            itemListener.onTarefaItemClickListener(position)
        })
    }

    open class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){

        var view : View? = itemView

        fun bind(item : Tarefa){
            itemView.txtTitle.text = item.title
            itemView.txtContent.text = item.content
        }

    }
}