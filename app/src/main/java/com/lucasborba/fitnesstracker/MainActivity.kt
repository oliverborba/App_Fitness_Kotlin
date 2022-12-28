package com.lucasborba.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItens = mutableListOf<MainItem>()
        mainItens.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_wb_sunny_24,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItens.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_videogame_asset_24,
                textStringId = R.string.label_tmb,
                color = Color.YELLOW
            )
        )
        // 1) o layout XML
        // 2) onde a RecyclerView vai aparecer(tela principal)
        // 3) conectar o CML da celula DENTRO do recyclerView + a sua quantidade de elementos dinamicos

        val adapter = MainAdapter(mainItens, object : OnItemClickListener {
            override fun onClick(id: Int) {
                when (id) {
                    1 -> {
                        val intent = Intent(this@MainActivity, ImcActivity::class.java)
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this@MainActivity, TmbActivity::class.java)
                        startActivity(intent)                    }
                }
                Log.i("Teste", "Clicou $id")
            }

        })
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter

        rvMain.layoutManager = GridLayoutManager(this, 2)

    }

//    override fun onClick(id: Int) {
//        when (id) {
//            1 -> {
//                val intent = Intent(this, ImcActivity::class.java)
//                startActivity(intent)
//            }
//            2 -> {
//                //Abrir outra activity
//            }
//        }
//        Log.i("Teste", "Clicou $id")
//    }

    private inner class MainAdapter(
        private val mainItens: List<MainItem>,
        private val onItemClickListener: OnItemClickListener
    ) :
        RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        // 1 - Qual o layout XML da celula especifica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - disparado toda vez que houver uma rolagem na tela e for necessário trocar o counteudo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCorrent = mainItens[position]
            holder.bind(itemCorrent)
        }

        // 3 - informar quantas celular esta listagem terá
        override fun getItemCount(): Int {
            return mainItens.size
        }

        //é a classe da celula em si
        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.onClick(item.id)
                }
            }
        }

    }


}
