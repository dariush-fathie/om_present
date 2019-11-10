package pro.ahoora.zhin.om.ui.draw

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_draw.*
import me.priyesh.chroma.ChromaDialog
import me.priyesh.chroma.ColorMode
import me.priyesh.chroma.ColorSelectListener
import pro.ahoora.zhin.fabricview.FabricView
import pro.ahoora.zhin.om.R
import pro.ahoora.zhin.om.base.BaseActivity

class DrawActivity : BaseActivity(), View.OnClickListener {

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)

        fabricView.backgroundMode = FabricView.BACKGROUND_STYLE_NOTEBOOK_PAPER
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tools_draw -> {
                fabricView.interactionMode = FabricView.DRAW_MODE
                Toast.makeText(this, getString(R.string.PaintingModeSelected), Toast.LENGTH_SHORT).show()
            }
            R.id.tools_undo -> {
                fabricView.undo()
            }
            R.id.tools_redo -> {
                fabricView.redo()
            }
            R.id.tools_eraser -> {
                fabricView.cleanPage()
            }
            R.id.tools_pallet -> {
                // todo show color pallet
                showColorPickerDialog(ColorDestination.LINE_COLOR)
                fabricView.interactionMode = FabricView.DRAW_MODE
            }
            R.id.tools_select -> {
                Toast.makeText(this, getString(R.string.YouCanSelectObjects), Toast.LENGTH_SHORT).show()
                fabricView.interactionMode = FabricView.SELECT_MODE
            }
            R.id.tools_style -> {
                Toast.makeText(this, getString(R.string.ChangeBackgroundLines), Toast.LENGTH_SHORT).show()
                when (i) {
                    0 -> {
                        fabricView.backgroundMode = FabricView.BACKGROUND_STYLE_BLANK
                        i++
                    }
                    1 -> {
                        fabricView.backgroundMode = FabricView.BACKGROUND_STYLE_GRAPH_PAPER
                        i++
                    }
                    2 -> {
                        fabricView.backgroundMode = FabricView.BACKGROUND_STYLE_NOTEBOOK_PAPER
                        i = 0
                    }
                }
            }
            R.id.tools_text -> {
                // todo show editText dialog
            }
            R.id.tools_fillDrop -> {
                tools_fillDrop.isSelected = true
                showColorPickerDialog(ColorDestination.BACKGROUND_COLOR)
                // todo show editText dialog
            }
        }
    }

    var mColorDestination = ColorDestination.LINE_COLOR

    private fun showColorPickerDialog(c: ColorDestination) {
        mColorDestination = c
        ChromaDialog.Builder()
                .initialColor(Color.GREEN)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(object : ColorSelectListener {
                    override fun onColorSelected(color: Int) {
                        when (c) {
                            ColorDestination.LINE_COLOR -> fabricView.color = color
                            ColorDestination.BACKGROUND_COLOR -> {
                                fabricView.backgroundColor = color
                                fabricView.invalidate()
                            }
                        }
                    }
                })
                .create()
                .show(supportFragmentManager, "colorPicker")
    }

    enum class ColorDestination {
        LINE_COLOR,
        BACKGROUND_COLOR
    }

}
