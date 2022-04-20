package com.example.rvcrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sqlite_helper: SQLiteHelper
    private lateinit var rv_daftar_siswa: RecyclerView

    private var adp_daftar_siswa: AdapterDaftarSiswa? = null
    private var ms: ModelSiswa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inisialLihat()
        inisialRv()
        sqlite_helper = SQLiteHelper(this)

        idbtn_tambah.setOnClickListener {
            tambahSiswa()
        }
        idbtn_lihat.setOnClickListener {
            lihatSiswa()
        }
        idbtn_update.setOnClickListener {
            updateSiswa()
        }

        adp_daftar_siswa?.aturSaatItemDiklik {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            idedt_name.setText(it.name)
            idedt_email.setText(it.email)
            ms = it
        }

        adp_daftar_siswa?.aturSaatItemDihapus {
            hapusSiswa(it.id)
        }
    }

    private fun tambahSiswa(){
        val name = idedt_name.text.toString()
        val email = idedt_email.text.toString()

        if (name.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Isilah data yang diminta",Toast.LENGTH_SHORT).show()
        } else {
            val ms = ModelSiswa(name = name, email = email)
            val status = sqlite_helper.insSiswa(ms)

            if (status > -1){
                Toast.makeText(this, "Siswa ditambahkan", Toast.LENGTH_SHORT).show()
                bersihkanData()
            } else {
                Toast.makeText(this, "Siswa tidak tersimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun bersihkanData(){
        idedt_name.setText("")
        idedt_email.setText("")
        idedt_name.requestFocus()
    }

    private fun inisialLihat(){
        idedt_name
        idedt_email
        idbtn_tambah
        idbtn_lihat
        idbtn_update
        rv_daftar_siswa = findViewById(R.id.idrv_item)
    }

    private fun inisialRv(){
        rv_daftar_siswa.layoutManager = LinearLayoutManager(this)
        adp_daftar_siswa = AdapterDaftarSiswa()
        rv_daftar_siswa.adapter = adp_daftar_siswa
    }

    private fun lihatSiswa(){
        val ds = sqlite_helper.ambilSemuaSiswa()
        adp_daftar_siswa?.tambahItem(ds)
    }

    private fun updateSiswa(){
        val name = idedt_name.text.toString()
        val email = idedt_email.text.toString()

        if (name == ms?.name && email == ms?.email){
            Toast.makeText(this, "Siswa tidak Diubah", Toast.LENGTH_SHORT).show()
            return
        }

        if (ms == null) return

        val ms = ModelSiswa(id = ms!!.id, name = name, email = email)
        val status = sqlite_helper.updSiswa(ms)
        if (status > -1){
            bersihkanData()
            lihatSiswa()
        } else {
            Toast.makeText(this, "Update gagal", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hapusSiswa(no_index: Int){
        val buatDialog = AlertDialog.Builder(this)
        buatDialog.setMessage("Anda yakin ingin menghapus")
        buatDialog.setCancelable(true)
        buatDialog.setPositiveButton("Yes"){dialog, _ ->
            sqlite_helper.hapusSiswaDgId(no_index)
            lihatSiswa()
            dialog.dismiss()
        }

        buatDialog.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }

        val pemberitahuan = buatDialog.create()
        pemberitahuan.show()
    }
}