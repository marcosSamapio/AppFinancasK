package alura.com.appfinancask.model

import java.math.BigDecimal

class Resumo(private val transactionList: List<Transacao>) {


    fun receita() : BigDecimal = somaPorTipo(Tipo.RECEITA)

    fun despesa() : BigDecimal = somaPorTipo(Tipo.DESPESA)

    fun total() : BigDecimal = receita().subtract(despesa())

    fun somaPorTipo(tipo: Tipo) : BigDecimal {
        val somaDeTransacoesPeloTipo = transactionList
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble()}
        return BigDecimal(somaDeTransacoesPeloTipo)
    }
}