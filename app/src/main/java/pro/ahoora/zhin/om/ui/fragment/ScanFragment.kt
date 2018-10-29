package pro.ahoora.zhin.om.ui.fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import android.os.VibrationEffect
import android.os.Build
import android.content.Context.VIBRATOR_SERVICE
import android.os.Bundle
import android.os.Vibrator
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_scan.*
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.event.DecodeQrEvent
import pro.ahoora.zhin.om.event.VisibilityQrEvent
import pro.ahoora.zhin.om.ui.qr.QrActivity


class ScanFragment : Fragment(), ZXingScannerView.ResultHandler, View.OnClickListener {


    private lateinit var context: QrActivity
    var light=false

    //qrCodeReaderView!!.setTorchEnabled(isChecked)

    override fun handleResult(p0: Result?) {
        shakeItBaby()
        EventBus.getDefault().post(DecodeQrEvent(p0?.text.toString(), p0?.barcodeFormat.toString()))

    }
    override fun onClick(v: View?) {
        light=!light

        if (light){
            context.iv_light.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            context.tv_light.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }else{
            context.iv_light.setColorFilter(ContextCompat.getColor(context, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            context.tv_light.setTextColor(ContextCompat.getColor(context, R.color.white))
        }
        context.scannerView.flash=light
    }
    private fun shakeItBaby() {
        if (Build.VERSION.SDK_INT >= 26) {
            (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(50)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context = (activity as QrActivity)
        context.rl_light.setOnClickListener(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    @Subscribe
    fun startScan(e: VisibilityQrEvent) {
        if (e.type == 0) {
            context.scannerView.setResultHandler(this)
            context.scannerView.startCamera()


        } else if (e.type == 1) {
            context.scannerView.stopCamera()

        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
}
