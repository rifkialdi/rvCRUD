package com.example.rvcrud

import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterDaftarSiswa: RecyclerView.Adapter<AdapterDaftarSiswa.tempatLihatSiswa>() {

    private var msDaftar: ArrayList<ModelSiswa> = ArrayList()
    private var saatItemDiKlik: ((ModelSiswa) -> Unit)? = null
    private var saatItemDihapus: ((ModelSiswa) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = tempatLihatSiswa(
        LayoutInflater.from(parent.context).inflate(R.layout.daftar_siswa, parent, false)
    )

    override fun onBindViewHolder(holder: AdapterDaftarSiswa.tempatLihatSiswa, position: Int) {
        val daftar = msDaftar[position]
        holder.ikatLihat(daftar)
        holder.itemView.setOnClickListener{ saatItemDiKlik?.invoke(daftar) }
        holder.btnHapus.setOnClickListener { saatItemDihapus?.invoke(daftar) }
    }

    override fun getItemCount(): Int {
        return msDaftar.size
    }

    class tempatLihatSiswa(var lihat: View): RecyclerView.ViewHolder(lihat) {
        private var id = lihat.findViewById<TextView>(R.id.idtv_id)
        private var name = lihat.findViewById<TextView>(R.id.idtv_name)
        private var email = lihat.findViewById<TextView>(R.id.idtv_email)
        var btnHapus = lihat.findViewById<Button>(R.id.idbtn_hapus)

        fun ikatLihat (ms: ModelSiswa){
            id.text = ms.id.toString()
            name.text = ms.name.toString()
            email.text = ms.email.toString()
        }
    }

    fun aturSaatItemDiklik (panggilBalik: (ModelSiswa) -> Unit){
        this.saatItemDiKlik = panggilBalik
    }

    fun tambahItem(item: ArrayList<ModelSiswa>){
        this.msDaftar = item
        notifyDataSetChanged()
    }

    fun aturSaatItemDihapus (panggilBalik: (ModelSiswa) -> Unit){
        this.saatItemDihapus = panggilBalik
    }


}