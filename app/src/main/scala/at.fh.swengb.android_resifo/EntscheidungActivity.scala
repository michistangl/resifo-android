package at.fh.swengb.android_resifo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

/**
  * Created by Martin on 02.02.2017.
  */
class EntscheidungActivity extends Activity{

  val intent: Intent = getIntent
  val person_id = intent.getStringExtra("person_id")

  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.entscheidung)
  }

  def gotoAnmeldung(view:View): Unit ={
    val i = new Intent(this, classOf[AnmeldungActivity])
    i.putExtra("person_id", person_id)
    startActivity(i)
  }

  def gotoAbmeldung(view:View): Unit ={
    val i = new Intent(this, classOf[AbmeldungActivity])
    i.putExtra("person_id", person_id)
    startActivity(i)
  }

  def goBack(view:View): Unit ={
    finish()
  }
}
