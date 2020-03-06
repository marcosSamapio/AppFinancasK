package alura.com.appfinancask.ui.adapter

import alura.com.appfinancask.R
import alura.com.appfinancask.extension.formataParaBrasileiro
import alura.com.appfinancask.extension.limitaEmAte
import alura.com.appfinancask.model.Tipo
import alura.com.appfinancask.model.Transacao
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(
    private val transacoes: List<Transacao>,
    private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context)
            .inflate(R.layout.transacao_item, parent, false)
        val transacao = transacoes[position]

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adiconaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaData(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_data.text = transacao.data
            .formataParaBrasileiro()
    }

    private fun adiconaCategoria(
        viewCriada: View,
        transacao: Transacao
    ) {
        viewCriada.transacao_categoria.text = transacao.categoria
            .limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(
        transacao: Transacao,
        viewCriada: View) {
        val icone = iconePorTipo(transacao.tipo)
        viewCriada.transacao_icone
            .setBackgroundResource(icone)
    }

    private fun iconePorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.drawable.icone_transacao_item_receita
        }
        return  R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaValor(transacao: Transacao, viewCriada: View) {
        val cor: Int = corPorTipo(transacao.tipo)
        viewCriada.transacao_valor.setTextColor(cor)
        viewCriada.transacao_valor.text = transacao.valor
            .formataParaBrasileiro()
    }

    private fun corPorTipo(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
           return ContextCompat.getColor(context, R.color.receita)
        }
        return ContextCompat.getColor(context, R.color.despesa)
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}