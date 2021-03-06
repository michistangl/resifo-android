package at.fh.swengb.android_resifo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget._

/**
  * Created by Martin on 15.01.2017.
  * Hier werden Spinner gefüllt,
  * Daten gespeichert und
  * die Buttons programmiert.
  */
class AbmeldungActivity extends Activity{

  var db: Db = _
  var person_id = 0
  val d = new Data

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.abmeldung)

    db = Db(getApplicationContext())
    fillAllSpinner()

    val intent: Intent = getIntent
    person_id = intent.getExtras.get("person_id").asInstanceOf[Int]

    val dataMap = d.fillAbmeldeDaten(db, person_id)
    fillDataInTextView(dataMap, person_id)
  }

  def fillDataInTextView(abmeldungData: Map[Int, Any], person_id: Int) : Unit = {
    val bundesland = abmeldungData(person_id).asInstanceOf[Abmeldung].getBundesland()
    val verzugAusAusland = abmeldungData(person_id).asInstanceOf[Abmeldung].getVerzugAusAusland()

    findViewById(R.id.eT_abStraße).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getStrasse())
    findViewById(R.id.eT_abHausNr).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getHausnr())
    findViewById(R.id.eT_abStiege).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getStiege())
    findViewById(R.id.eT_abTuer).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getTuer())
    findViewById(R.id.eT_abPLZ).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getPlz())
    findViewById(R.id.eT_abOrt).asInstanceOf[TextView].setText(abmeldungData(person_id).asInstanceOf[Abmeldung].getOrt())

    if(bundesland == "Steiermark") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(0)
    } else if(bundesland == "Kärnten") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(1)
    } else if(bundesland == "Burgenland") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(2)
    } else if(bundesland == "Tirol") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(3)
    } else if(bundesland == "Vorarlberg") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(4)
    } else if(bundesland == "Salzburg") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(5)
    } else if(bundesland == "Niederösterreich") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(6)
    } else if(bundesland == "Oberösterreich") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(7)
    } else if(bundesland == "Wien") {
      findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].setSelection(8)
    }

    if(verzugAusAusland == "ja") {
      findViewById(R.id.rB_abAuslandJa).asInstanceOf[RadioButton].setChecked(true)
      findViewById(R.id.rb_abAuslandNein).asInstanceOf[RadioButton].setChecked(false)
    } else {
      findViewById(R.id.rB_abAuslandJa).asInstanceOf[RadioButton].setChecked(false)
      findViewById(R.id.rb_abAuslandNein).asInstanceOf[RadioButton].setChecked(true)
    }
  }


  def saveData(view: View): Unit = {
    val strasse = findViewById(R.id.eT_abStraße).asInstanceOf[EditText].getText.toString
    val hausnummer = findViewById(R.id.eT_abHausNr).asInstanceOf[EditText].getText.toString
    val stiege = findViewById(R.id.eT_abStiege).asInstanceOf[EditText].getText.toString
    val tuer = findViewById(R.id.eT_abTuer).asInstanceOf[EditText].getText.toString
    val plz = findViewById(R.id.eT_abPLZ).asInstanceOf[EditText].getText.toString
    val ort = findViewById(R.id.eT_abOrt).asInstanceOf[EditText].getText.toString
    val bundesland = findViewById(R.id.s_abBundesland).asInstanceOf[Spinner].getSelectedItem().toString()
    val rb_auslandJa = findViewById(R.id.rB_abAuslandJa).asInstanceOf[RadioButton]
    val ausland = if (rb_auslandJa.isChecked == true) "ja" else "nein"

    val abmeldeDaten: AbmeldeDaten = AbmeldeDaten(person_id, strasse, hausnummer, stiege, tuer, plz, ort, bundesland, ausland)

    val abmDao = db.mkAbmDao()
    abmDao.insert(abmeldeDaten)
  }

  def gotoNext(view:View): Unit ={
    saveData(view)
    val rb_auslandJa = findViewById(R.id.rB_abAuslandJa).asInstanceOf[RadioButton]
    val i = if (rb_auslandJa.isChecked == true) new Intent(this, classOf[FremdeActivity]) else new Intent(this, classOf[ErfolgreichActivity])
    i.putExtra("person_id", person_id)
    startActivity(i)
  }

  def goBack(view:View): Unit ={
    finish()
  }

  def fillAllSpinner(): Unit ={
    fillSpinner(findViewById(R.id.s_abBundesland).asInstanceOf[Spinner], Array("Steiermark", "Kärnten", "Burgenland", "Tirol", "Vorarlberg", "Salzburg", "Niederösterreich", "Oberösterreich", "Wien"))

    def fillSpinner(spinner: Spinner, content: Array[String]): Unit ={
      val adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, content)
      spinner.setAdapter(adapter)
    }
  }
}
