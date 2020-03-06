package alura.com.appfinancask.ui

import alura.com.appfinancask.R
import alura.com.appfinancask.extension.formataParaBrasileiro
import alura.com.appfinancask.model.Resumo
import alura.com.appfinancask.model.Transacao
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(
    context: Context,
    private val view: View,
    transactionList: List<Transacao>
) {

    private val resumo: Resumo = Resumo(transactionList)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)

    fun atualiza() {
        adicionaDespesa()
        adicionaReceita()
        adicionaTotal()
    }

    private fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()
        with(view.resumo_card_despesa) {
            setTextColor(corDespesa)
            text = totalDespesa.formataParaBrasileiro()
        }
    }

    private fun adicionaReceita() {
        val totalReceita = resumo.receita()
        with(view.resumo_card_receita) {
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }
    }

    private fun adicionaTotal() {
        val total = resumo.total()
        val cor = corPorTotal(total)
        with(view.resumo_card_total) {
            setTextColor(cor)
            text = total.formataParaBrasileiro()
        }
    }

    private fun corPorTotal(valor: BigDecimal): Int {
        if (valor < BigDecimal.ZERO) {
            return corDespesa
        }
        return corReceita
    }
}