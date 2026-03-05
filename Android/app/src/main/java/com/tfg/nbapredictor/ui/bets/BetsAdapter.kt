package com.tfg.nbapredictor.ui.bets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tfg.nbapredictor.R
import com.tfg.nbapredictor.model.Apuesta

class BetsAdapter(
    private val apuestas: List<Apuesta>,
    private val showResultado: Boolean = false
) : RecyclerView.Adapter<BetsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bet, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(apuestas[position], showResultado)
    }

    override fun getItemCount() = apuestas.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPartido: TextView = itemView.findViewById(R.id.tvPartido)
        private val tvPrediccion: TextView = itemView.findViewById(R.id.tvPrediccion)
        private val tvPuntos: TextView = itemView.findViewById(R.id.tvPuntos)
        private val tvCuota: TextView = itemView.findViewById(R.id.tvCuota)
        private val tvResultado: TextView = itemView.findViewById(R.id.tvResultado)

        fun bind(apuesta: Apuesta, showResultado: Boolean) {
            val partido = apuesta.partido
            val partidoStr = if (partido != null && partido.equipoLocal != null && partido.equipoVisitante != null) {
                "${partido.equipoLocal.nombre} vs ${partido.equipoVisitante.nombre}"
            } else "Partido no disponible"
            tvPartido.text = partidoStr

            tvPrediccion.text = "Predicción: ${apuesta.prediccion}"
            tvPuntos.text = "${apuesta.puntosApostados} pts"
            tvCuota.text = "Cuota: ${apuesta.cuota?.let { "%.2f".format(it) } ?: "-"}"

            if (showResultado) {
                tvResultado.visibility = View.VISIBLE
                tvResultado.text = "Resultado: ${apuesta.resultado ?: "Pendiente"}"
                when {
                    apuesta.isGanada() -> {
                        val ganancia = apuesta.cuota?.let { (apuesta.puntosApostados * it).toInt() } ?: (apuesta.puntosApostados * 2)
                        tvResultado.text = "Ganada: +$ganancia pts"
                    }
                    apuesta.isPerdida() -> tvResultado.text = "Perdida: -${apuesta.puntosApostados} pts"
                    else -> tvResultado.text = "Pendiente"
                }
            } else {
                val gananciaPot = apuesta.getGananciaPotencial()
                if (gananciaPot > 0) {
                    tvResultado.visibility = View.VISIBLE
                    tvResultado.text = "Ganancia potencial: ${gananciaPot.toInt()} pts"
                } else {
                    tvResultado.visibility = View.GONE
                }
            }
        }
    }
}
