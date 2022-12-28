package com.lucasborba.fitnesstracker

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.lucasborba.fitnesstracker.model.Calc

class ImcActivity : AppCompatActivity() {


    private lateinit var editWeight: EditText
    private lateinit var editHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc)

        editWeight = findViewById(R.id.edit_imc_weight)
        editHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)

        btnSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val weight = editWeight.text.toString().toInt()
            val height = editHeight.text.toString().toInt()

            val result = calculateImc(weight, height)
            Log.d("Teste", "resultado: $result")

            val imcResponseId = imcResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.imc_response, result))
                .setMessage(imcResponseId)
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ ->
                }
                .setNegativeButton(R.string.save) { _, _ ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "imc", res = result))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()

                }
                .create()
                .show()


            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


//            val dialog = AlertDialog.Builder(this)
//
//            dialog.setTitle(getString(R.string.imc_response, result))
//            dialog.setMessage(imcResponseId)
//            dialog.setPositiveButton(android.R.string.ok, object : DialogInterface.OnClickListener {
//                override fun onClick(p0: DialogInterface?, p1: Int) {
//                    //aqui vai rodar depois do click
//                }
//            })
//
//            val d = dialog.create()
//            d.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity() {
        val intent = Intent(this@ImcActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "imc")
        startActivity(intent)
    }

    @StringRes
    private fun imcResponse(imc: Double): Int {
        //Opção 3: return atribuido diretamente ao when, menos verboso
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }

        //Opção 2: when com retorno da função atribuida de forma individual
//        when{
//            imc < 15.0 -> return R.string.imc_severely_low_weight
//            imc < 16.0 -> return R.string.imc_very_low_weight
//            imc < 18.5 -> return R.string.imc_low_weight
//            imc < 25.0 -> return R.string.normal
//            imc < 30.0 -> return R.string.imc_high_weight
//            imc < 35.0 -> return R.string.imc_so_high_weight
//            imc < 40.0 -> return R.string.imc_severely_high_weight
//            else -> return R.string.imc_extreme_weight
//        }

        //Opção com if/else
//        if (imc < 15.0) {
//            return R.string.imc_severely_low_weight
//        } else if (imc < 16.0) {
//            return R.string.imc_very_low_weight
//        } else if (imc < 18.5) {
//            return R.string.imc_low_weight
//        } else if (imc < 25.0) {
//            return R.string.normal
//        } else if (imc < 30.0) {
//            return R.string.imc_high_weight
//        } else if (imc < 35.0) {
//            return R.string.imc_so_high_weight
//        } else if (imc < 40.0) {
//            return R.string.imc_severely_high_weight
//        } else {
//            return R.string.imc_extreme_weight
//        }
    }

    private fun calculateImc(weight: Int, height: Int): Double {

        //IMC - peso / (altura * altura)
        return weight / ((height / 100.0) * (height / 100.0))
    }


    private fun validate(): Boolean {
        //não pode inserir números nulos / vazios
        //não pode iniciar com valor 0


        //Opção 3: Retornar direto o que for verdadeiro, forma menos verbosa

        return (editWeight.text.toString().isNotEmpty()
                && editHeight.text.toString().isNotEmpty()
                && !editWeight.text.toString().startsWith("0")
                && !editHeight.text.toString().startsWith("0")
                )
        // Opção 2: Usar somente o return para simular o if/else
//        if (editWeight.text.toString().isNotEmpty()
//            && editHeight.text.toString().isNotEmpty()
//            && !editWeight.text.toString().startsWith("0")
//            && !editHeight.text.toString().startsWith("0")
//        ) {
//            return true
//        }
//        return false

//        //Opção 1 uso de if/else
//        if (editWeight.text.toString().isNotEmpty()
//            && editHeight.text.toString().isNotEmpty()
//            && !editWeight.text.toString().startsWith("0")
//            && !editHeight.text.toString().startsWith("0")
//        ) {
//            return true
//        } else {
//            return false
//        }
    }
}