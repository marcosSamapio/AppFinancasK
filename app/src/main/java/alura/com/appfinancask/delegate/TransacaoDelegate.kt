package alura.com.appfinancask.delegate

import alura.com.appfinancask.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}