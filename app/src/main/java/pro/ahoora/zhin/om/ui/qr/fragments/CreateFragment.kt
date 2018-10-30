package pro.ahoora.zhin.om.ui.qr.fragments


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.activity_qr.*
import kotlinx.android.synthetic.main.fragment_create.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.event.DecodeQrEvent
import pro.ahoora.zhin.om.event.VisibilityQrEvent
import pro.ahoora.zhin.om.ui.qr.QrActivity
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CreateFragment : Fragment(), View.OnClickListener {

    var savePath = Environment.getExternalStorageDirectory().path + "/QRCode/"
    lateinit var bitmap: Bitmap


    lateinit var qrGenerator: AsyncQrGenerator

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.start -> {
                generateClick()
            }
            R.id.save -> {
                if (checkStoragePermissions()) {
                    save()
                }
            }
            R.id.iv_mic -> {
                speechToText()
            }
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start.setOnClickListener(this)
        save.setOnClickListener(this)
        iv_mic.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    @Subscribe
    fun decode(e: DecodeQrEvent) {
        edt_value.setText(e.value)
        if (e.type == "QR_CODE") {
            generateClick()
        } else {
            QR_Image.setImageResource(R.drawable.bgt)
        }
        (activity as QrActivity).vp_mainContainer.currentItem = 0
    }

    private fun generateClick() {
        val view = (activity as QrActivity).currentFocus
        if (view != null) {
            val imm = (activity as QrActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
        val qrCodeData = edt_value.text.toString().trim { it <= ' ' }
        if (qrCodeData.isNotEmpty()) {
            qrGenerator = if (!this::qrGenerator.isInitialized) {
                AsyncQrGenerator()
            } else {
                if (qrGenerator.status == AsyncTask.Status.RUNNING) {
                    Toast.makeText(activity, "لطفا صبر کنید", Toast.LENGTH_LONG).show()
                    return
                } else {
                    AsyncQrGenerator()
                }
            }
            qrGenerator.execute(qrCodeData)

        } else {
            edt_value.error = "متنی موجود نیست"
        }

    }

    private fun generate(qrCodeData: String): Bitmap? {
        val hintsMap = HashMap<EncodeHintType, Any>()
        val manager = (activity as QrActivity).getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width = point.x
        val height = point.y
        var smallerDimension = if (width < height) width else height
        smallerDimension = smallerDimension * 3 / 4
        hintsMap[EncodeHintType.CHARACTER_SET] = "utf-8"
        hintsMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.Q
        hintsMap[EncodeHintType.MARGIN] = 2

        try {
            val matrix = QRCodeWriter().encode(qrCodeData, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hintsMap)
            val width1 = matrix.width
            val height1 = matrix.height
            val pixels = IntArray(width1 * height1)
            // All are 0, or black, by default
            for (y in 0 until height1) {
                val offset = y * width1
                for (x in 0 until width1) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = if (matrix.get(x, y))
                        ResourcesCompat.getColor(resources, R.color.colorB, null)
                    else
                        ResourcesCompat.getColor(resources, R.color.androidDefBg, null)
                }
            }

            bitmap = Bitmap.createBitmap(width1, height1, Bitmap.Config.ARGB_8888)
            bitmap.setPixels(pixels, 0, width1, 0, 0, width1, height1)
            //setting bitmap to image view

            val overlay = BitmapFactory.decodeResource(resources, R.drawable.jin_logo)
            bitmap = mergeBitmaps(overlay, bitmap)
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }

    fun mergeBitmaps(overlay: Bitmap, bitmap: Bitmap): Bitmap {
        val height = bitmap.height
        val width = bitmap.width
        val combined = Bitmap.createBitmap(width, height, bitmap.config)
        val canvas = Canvas(combined)
        val canvasWidth = canvas.width
        val canvasHeight = canvas.height

        canvas.drawBitmap(bitmap, Matrix(), null)

        val centreX = (canvasWidth - overlay.width) / 2
        val centreY = (canvasHeight - overlay.height) / 2
        canvas.drawBitmap(overlay, centreX.toFloat(), centreY.toFloat(), null)

        return combined
    }

    private val rwRequest = 1080
    private val rwPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private fun checkStoragePermissions(): Boolean {
        val write = ActivityCompat.checkSelfPermission((activity as QrActivity), Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read = ActivityCompat.checkSelfPermission((activity as QrActivity), Manifest.permission.READ_EXTERNAL_STORAGE)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(
                        (activity as QrActivity),
                        rwPermissions,
                        rwRequest
                )
                false
            }
        } else {
            true
        }
    }

    private fun save() {
        val result = "در مسیر $savePath ذخیره شد "
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        // try {
        val myDir = File(savePath)
        myDir.mkdirs()

        val fname = "Image-$n.jpg"
        val file = File(myDir, fname)
        if (file.exists()) file.delete()
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
        Toast.makeText((activity as QrActivity), result, Toast.LENGTH_LONG).show()

        /* } catch (e: Exception) {
             e.printStackTrace()
             Toast.makeText((activity as QrActivity), "ذخیره نشد", Toast.LENGTH_LONG).show()
         }*/
    }

    @Subscribe
    fun saveS(e: VisibilityQrEvent) {
        if (e.type == 2)
            save()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun speechToText() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa_IR")
        Log.e("Locale.getDefault()", Locale.getDefault().toString())
        if (intent.resolveActivity((activity as QrActivity).packageManager) != null) {
            startActivityForResult(intent, 10)
        } else {
            Toast.makeText((activity as QrActivity), "دستگاه شما از زبان مورد نظر پشتیبانی نمی کند", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            10 -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val res = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    edt_value.setText(res[0])
                }
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    inner class AsyncQrGenerator : AsyncTask<String, Bitmap, Bitmap>() {

        private var alertBuilder = AlertDialog.Builder(activity)
        private lateinit var alert: AlertDialog

        override fun onPreExecute() {
            val view = LayoutInflater.from(activity!!).inflate(R.layout.loading, null)
            alertBuilder.setView(view)
            alertBuilder.setCancelable(false)
            alert = alertBuilder.create()
            alert.show()
        }

        override fun doInBackground(vararg params: String?): Bitmap {
            return generate(params[0]!!)!!
        }

        override fun onPostExecute(result: Bitmap?) {
            activity?.QR_Image?.setImageBitmap(result!!)
            alert.cancel()
        }
    }

}
