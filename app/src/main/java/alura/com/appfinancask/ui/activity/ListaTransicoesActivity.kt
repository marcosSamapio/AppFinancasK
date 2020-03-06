package alura.com.appfinancask.ui.activity

import alura.com.appfinancask.R
import alura.com.appfinancask.delegate.TransacaoDelegate
import alura.com.appfinancask.model.Tipo
import alura.com.appfinancask.model.Transacao
import alura.com.appfinancask.ui.ResumoView
import alura.com.appfinancask.ui.adapter.ListaTransacoesAdapter
import alura.com.appfinancask.ui.dialog.AdicionaTransacaoDialog
import alura.com.appfinancask.ui.dialog.AlteraTransacaoDialog
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransicoesActivity : AppCompatActivity() {

    private val transactionList: MutableList<Transacao> = mutableListOf()
    private val viewOfActivity by lazy {
        window.decorView
    }
    private val viewGroupOfActivity by lazy {
        viewOfActivity as ViewGroup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraListaAdapter()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_despesa.setOnClickListener {
            chamaDialogDeAdicao(Tipo.DESPESA)
        }
        lista_transacoes_adiciona_receita.setOnClickListener {
            chamaDialogDeAdicao(Tipo.RECEITA)
        }
    }


    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupOfActivity, this)
            .chamaDialog(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    adicionaTransacao(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }
            })
    }

    private fun adicionaTransacao(transacao: Transacao) {
        transactionList.add(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraListaAdapter()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewOfActivity, transactionList)
        resumoView.atualiza()
    }

    private fun configuraListaAdapter() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transactionList, this)
        with(lista_transacoes_listview) {
            adapter =  listaTransacoesAdapter
            setOnItemClickListener { parent, view, position, id ->
                val transacao = transactionList[position]
                chamaDialodDeAlteracao(transacao, position)
            }
        }
    }

    private fun chamaDialodDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewGroupOfActivity, this)
            .chamaDialog(transacao, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    alteraTransacao( transacao, position)
                }
            })
    }

    private fun alteraTransacao(transacao: Transacao, position: Int) {
        transactionList[position] = transacao
        atualizaTransacoes()
    }
}