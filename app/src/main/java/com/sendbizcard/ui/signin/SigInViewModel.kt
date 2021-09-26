
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sendbizcard.utils.ValidationUtils

class SigInViewModel : ViewModel() {

    var strEmailId = MutableLiveData<String>()


    fun registerUser() {

    }

    fun isValidEmailID(): Boolean {
         if (strEmailId.value.isNullOrBlank()) {
            return false
        } else if (!ValidationUtils.isValidEmail(strEmailId.value!!)) {
            return false
        } else {
            return true
        }

    }
}